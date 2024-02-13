package pl.mamarek123.JobOffers.domain.offer;

import java.util.Optional;

class RepositoryExistingOfferChecker {
    public static boolean existsByURL(Offer offer, OfferRepository offerRepository){
        Optional<Offer> offerByURL = offerRepository.getOfferByURL(offer.url());
        if (offerByURL.isEmpty()){
            return false;
        }
            return true;
    }
}
