package pl.mamarek123.JobOffers.domain.loginandregister.DTO;

import lombok.Builder;

@Builder
public record UserResultDto(String id, String username, String password, String email) {

}
