package pl.mamarek123.JobOffers.infrastructure.offer.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pl.mamarek123.JobOffers.domain.offer.OfferFetching;

import java.time.Duration;

@Configuration
public class OfferHttpClientConfig {

    @Bean
    public RestTemplateResponseErrorHandler restTemplateResponseErrorHandler() {
        return new RestTemplateResponseErrorHandler();
    }

    @Bean
    public RestTemplate restTemplate(@Value("${offer.http.client.config.connectionTimeout:1000}") long connectionTimeout,
                              @Value("${offer.http.client.config.readTimeout:1000}") long readTimeout,
                              RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        return new RestTemplateBuilder()
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .build();
    }

    @Bean
    public OfferFetching remoteOfferClient(RestTemplate restTemplate,
                                           @Value("${offer.http.client.config.uri:http://example.com}") String uri,
                                           @Value("${offer.http.client.config.port:5057}") int port) {
        return new OfferHttpClient(restTemplate, uri, port);
    }
}
