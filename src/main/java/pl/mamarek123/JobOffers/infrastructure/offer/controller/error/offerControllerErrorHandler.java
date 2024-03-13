package pl.mamarek123.JobOffers.infrastructure.offer.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.mamarek123.JobOffers.domain.offer.error.OfferAlreadyExistsException;
import pl.mamarek123.JobOffers.domain.offer.error.OfferNotExistingException;
import pl.mamarek123.JobOffers.infrastructure.offer.controller.OfferController;

@ControllerAdvice(assignableTypes = {OfferController.class})
public class offerControllerErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OfferAlreadyExistsException.class)
    public ResponseEntity<String> handleOfferAlreadyExists(OfferAlreadyExistsException ex) {
        String bodyOfResponse = "Resource already exists";
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(OfferNotExistingException.class)
    public ResponseEntity<String> handleOfferNotExisting(OfferNotExistingException ex){
        String bodyOfResponse = ex.getMessage();
        return new ResponseEntity<>(bodyOfResponse, HttpStatus.NOT_FOUND);
    }

}
