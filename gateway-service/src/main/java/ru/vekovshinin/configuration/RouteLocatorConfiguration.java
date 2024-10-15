package ru.vekovshinin.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vekovshinin.configuration.filter.BaseAuthCheckFilter;
import ru.vekovshinin.configuration.properties.AppProperties;

@Configuration
public class RouteLocatorConfiguration {

  @Bean
  public RouteLocator routes(
      RouteLocatorBuilder builder, AppProperties appProperties,
      BaseAuthCheckFilter baseAuthCheckFilter
  ) {
    return builder.routes()
        .route("user-service-route", spec -> spec
            .path("/api/users/**")
            .filters(filter -> rewritePath(filter)
                .filter(baseAuthCheckFilter.apply((BaseAuthCheckFilter.Config) null))
            )
            .uri(appProperties.getUserServiceUrl())
        )
        .route("role-service-route", spec -> spec
            .path("/api/roles/**")
            .filters(filter -> rewritePath(filter)
                .filter(baseAuthCheckFilter.apply((BaseAuthCheckFilter.Config) null))
            )
            .uri(appProperties.getRoleServiceUrl())
        )
        .build();
  }

  private GatewayFilterSpec rewritePath(GatewayFilterSpec spec) {
    return spec.rewritePath("/api/users/(?<segment>.*)", "/api/users/$\\{segment}");
  }

}
