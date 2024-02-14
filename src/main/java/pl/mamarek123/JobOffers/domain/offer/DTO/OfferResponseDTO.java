package pl.mamarek123.JobOffers.domain.offer.DTO;

import lombok.Builder;
import pl.mamarek123.JobOffers.domain.ResultStatus;

@Builder
public record OfferResponseDTO(OfferDTO offer, ResultStatus status, String message) {
}
