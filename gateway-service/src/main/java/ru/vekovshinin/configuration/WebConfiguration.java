package ru.vekovshinin.configuration;

import io.netty.handler.logging.LogLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

@RequiredArgsConstructor
@Configuration
public class WebConfiguration {

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(HttpClient.create()
            .wiretap(this.getClass().getCanonicalName(), LogLevel.DEBUG,
                AdvancedByteBufFormat.TEXTUAL)))
        .build();
  }

}
