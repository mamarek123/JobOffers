package pl.mamarek123.JobOffers.infrastructure.tokenandregister.controller.error;

import org.springframework.http.HttpStatus;

public record TokenErrorResponse(String message, HttpStatus status) {

}
