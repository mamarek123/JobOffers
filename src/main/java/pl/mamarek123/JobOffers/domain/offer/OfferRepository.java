package pl.mamarek123.JobOffers.domain.
        offer;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface OfferRepository extends MongoRepository<Offer, String> {

    Optional<Offer> getOfferById(String id);

    boolean existsByOfferUrl(String url);

}
