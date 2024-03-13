package pl.mamarek123.JobOffers.domain.offer.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class OfferAlreadyExistsException extends RuntimeException {
    public OfferAlreadyExistsException(String message) {
        super(message);
    }
}
