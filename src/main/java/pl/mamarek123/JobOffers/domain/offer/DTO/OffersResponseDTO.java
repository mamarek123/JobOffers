package pl.mamarek123.JobOffers.domain.offer.DTO;

import lombok.Builder;

import java.util.List;

@Builder
public record OffersResponseDTO(List<OfferDTO> offers, ResultStatus status, String message) {
}
