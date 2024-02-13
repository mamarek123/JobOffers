package pl.mamarek123.JobOffers.domain.offer.DTO;

import lombok.Builder;

@Builder
public record OfferDTO(String title, String company, String salary, String url, String status, String message) {
}
