package pl.mamarek123.JobOffers.domain.offer;

import java.util.Optional;

interface OfferRepository {
    public Optional<Offer> getOfferByID(Long id);
    public Optional<Offer> getOfferByURL(String url);
    public Optional<Offer> addOffer(Offer offer);
}
