package pl.mamarek123.JobOffers.infrastructure.tokenandregister.controller.error;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.mamarek123.JobOffers.domain.loginAndRegister.error.UserAlreadyExistsException;

@ControllerAdvice
public class TokenControllerErrorHandler {


    private static final String BAD_CREDENTIALS = "Bad Credentials";

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public TokenErrorResponse handleBadCredentials() {
        return new TokenErrorResponse(BAD_CREDENTIALS, HttpStatus.UNAUTHORIZED);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseBody
    public TokenErrorResponse handleUserAlreadyExists(UserAlreadyExistsException e) {
        // Use the message from the exception for the response
        return new TokenErrorResponse(e.getMessage(), HttpStatus.CONFLICT);
    }

}
