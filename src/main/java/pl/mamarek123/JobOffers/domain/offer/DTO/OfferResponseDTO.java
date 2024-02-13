package pl.mamarek123.JobOffers.domain.offer.DTO;

import lombok.Builder;

@Builder
public record OfferResponseDTO(OfferDTO offer, ResultStatus status, String message) {
}
