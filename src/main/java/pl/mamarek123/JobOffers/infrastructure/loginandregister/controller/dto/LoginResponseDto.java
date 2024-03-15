package pl.mamarek123.JobOffers.infrastructure.loginandregister.controller.dto;

import lombok.Builder;

@Builder
public record LoginResponseDto(String username, String token) {

}
