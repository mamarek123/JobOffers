package pl.mamarek123.JobOffers.domain.loginAndRegister.DTO;

import lombok.Builder;
import pl.mamarek123.JobOffers.domain.ResultStatus;

@Builder
public record UserResultDTO(String username, String password, String email, ResultStatus status, String message) {

}
