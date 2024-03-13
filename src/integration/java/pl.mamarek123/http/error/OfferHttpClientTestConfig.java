package pl.mamarek123.http.error;

import org.springframework.web.client.RestTemplate;
import pl.mamarek123.JobOffers.domain.offer.OfferFetching;
import pl.mamarek123.JobOffers.infrastructure.offer.http.OfferHttpClientConfig;
import static pl.mamarek123.BaseIntegrationTest.WIRE_MOCK_HOST;


public class OfferHttpClientTestConfig extends OfferHttpClientConfig {
    public OfferFetching remoteOfferTestClient(int port, int connectionTimeout, int readTimeout){
        final RestTemplate restTemplate = restTemplate(connectionTimeout, readTimeout, restTemplateResponseErrorHandler());
        return remoteOfferClient(restTemplate,WIRE_MOCK_HOST, port);
    }
}
