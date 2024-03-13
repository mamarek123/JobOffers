package pl.mamarek123.JobOffers.infrastructure.tokenandregister.controller.dto;

import lombok.Builder;

@Builder
public record TokenResponseDto(String username, String token) {

}
