package pl.mamarek123.JobOffers.infrastructure.loginandregister.controller.error;

import org.springframework.http.HttpStatus;

public record LoginRegisterErrorResponse(String message, HttpStatus status) {

}
