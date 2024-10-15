package ru.vekovshinin.configuration.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import ru.vekovshinin.configuration.properties.AppProperties;
import ru.vekovshinin.model.dto.UserAuthRequest;

@Slf4j
@RequiredArgsConstructor
@Component
public class BaseAuthCheckFilter extends AbstractGatewayFilterFactory<BaseAuthCheckFilter.Config> {

  private final WebClient webClient;
  private final AppProperties appProperties;

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      String authorizationHeader = request.getHeaders().getFirst("Authorization");

      if (authorizationHeader == null || !authorizationHeader.startsWith("Basic ")) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      }

      String basicToken = authorizationHeader.substring(6);
      if (ObjectUtils.isEmpty(basicToken)) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      }

      return checkAuth(exchange, chain, basicToken);
    };
  }

  private Mono<Void> checkAuth(
      ServerWebExchange exchange, GatewayFilterChain chain, String basicToken
  ) {
    return webClient.post()
        .uri(appProperties.getUserServiceUrl() + "/api/users/get-authenticated")
        .bodyValue(new UserAuthRequest(basicToken))
        .retrieve()
        .bodyToMono(boolean.class)
        .flatMap(response -> {
          if (Boolean.TRUE.equals(response)) {
            return chain.filter(exchange);
          } else {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
          }
        });
  }

  public interface Config {
    // No configuration needed
  }

}
