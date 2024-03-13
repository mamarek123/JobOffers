package pl.mamarek123.JobOffers.domain.offer.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class OfferNotExistingException extends RuntimeException {
    public OfferNotExistingException(String message) {
        super(message);
    }
}
