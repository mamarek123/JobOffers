package pl.mamarek123.JobOffers.infrastructure.offer.http;


import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferRequestDto;
import pl.mamarek123.JobOffers.domain.offer.OfferFetching;

import java.util.Collections;
import java.util.List;

@AllArgsConstructor
@Log4j2
public class OfferHttpClient implements OfferFetching {

    private final RestTemplate restTemplate;
    private final String uri;
    private final int port;

    @Override
    public List<OfferRequestDto> fetchOffers() {
        log.info("Started fetching offers using http client");
        HttpHeaders headers = new HttpHeaders();
        final HttpEntity<HttpHeaders> requestEntity = new HttpEntity<>(headers);
        try {
            String urlForService = getUrlForService("/offers");
            final String url = UriComponentsBuilder.fromHttpUrl(urlForService).toUriString();
            ResponseEntity<List<OfferRequestDto>> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity,
                    new ParameterizedTypeReference<>() {
                    });
            final List<OfferRequestDto> body = response.getBody();
            if (body == null) {
                log.error("Response Body was null");
                throw new ResponseStatusException(HttpStatus.NO_CONTENT);
            }
            log.info("Success Response Body Returned: " + body);
            return body;
        } catch (ResourceAccessException e) {
            log.error("Error while fetching offers using http client: " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getUrlForService(String service) {
        return uri + ":" + port + service;
    }
}
