package pl.mamarek123.JobOffers.domain.offer;

import java.util.List;
import java.util.Optional;

interface OfferRepository {
    Optional<Offer> getOfferByID(Long id);
    Optional<Offer> getOfferByURL(String url);
    Optional<Offer> addOffer(Offer offer);
    Optional<List<Offer>> getAllOffers();
}
