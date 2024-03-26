package pl.mamarek123.JobOffers.domain.offer;

import pl.mamarek123.JobOffers.domain.offer.dto.OfferRequestDto;

import java.util.List;

public interface OfferFetching {
    List<OfferRequestDto> fetchOffers();
}
