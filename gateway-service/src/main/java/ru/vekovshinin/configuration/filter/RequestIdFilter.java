package ru.vekovshinin.configuration.filter;

import io.micrometer.context.ContextRegistry;
import java.util.List;
import java.util.UUID;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;
import ru.vekovshinin.util.TraceConstants;

@Slf4j
@Configuration
public class RequestIdFilter implements WebFilter {

  @Override
  @NonNull
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String requestId = getRequestId(request.getHeaders());

    ContextRegistry.getInstance().registerThreadLocalAccessor(
        TraceConstants.X_REQUEST_ID,
        () -> MDC.get(TraceConstants.X_REQUEST_ID),
        value -> MDC.put(TraceConstants.X_REQUEST_ID, requestId),
        () -> MDC.remove(TraceConstants.X_REQUEST_ID)
    );

    MDC.put(TraceConstants.X_REQUEST_ID, requestId);
    request.mutate().header(TraceConstants.X_REQUEST_ID, requestId);

    log.info("Request {}", exchange.getRequest().getPath());

    exchange.getResponse().getHeaders().put(TraceConstants.X_REQUEST_ID, List.of(requestId));

    return chain
        .filter(exchange)
        .contextWrite(Context.of(TraceConstants.X_REQUEST_ID, requestId))
        .doFinally(s -> MDC.remove(TraceConstants.X_REQUEST_ID));
  }

  private String getRequestId(HttpHeaders headers) {
    List<String> requestIdHeaders = headers.get(TraceConstants.X_REQUEST_ID);
    return requestIdHeaders == null || requestIdHeaders.isEmpty()
        ? UUID.randomUUID().toString()
        : requestIdHeaders.get(0);
  }

}
