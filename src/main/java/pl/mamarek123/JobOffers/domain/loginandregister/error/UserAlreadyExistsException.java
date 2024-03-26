package pl.mamarek123.JobOffers.domain.loginandregister.error;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }

}
