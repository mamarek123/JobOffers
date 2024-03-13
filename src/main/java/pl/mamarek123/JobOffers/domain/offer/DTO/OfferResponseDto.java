package pl.mamarek123.JobOffers.domain.offer.DTO;

import lombok.Builder;

@Builder
public record OfferResponseDto(String id, String title, String company, String salary,String offerUrl) {
}