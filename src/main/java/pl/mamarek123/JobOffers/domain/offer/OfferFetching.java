package pl.mamarek123.JobOffers.domain.offer;

import pl.mamarek123.JobOffers.domain.offer.DTO.OfferRequestDto;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferResponseDto;

import java.util.List;

public interface OfferFetching {
    List<OfferRequestDto> fetchOffers();
}
