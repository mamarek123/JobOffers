package pl.mamarek123.JobOffers.domain.loginAndRegister.DTO;

import lombok.Builder;

@Builder
public record UserRequestDTO (String username, String email, String password){
}
