package pl.mamarek123.JobOffers.domain.loginAndRegister.DTO;

import lombok.Builder;

@Builder
public record UserResultDto(String id, String username, String password, String email) {

}