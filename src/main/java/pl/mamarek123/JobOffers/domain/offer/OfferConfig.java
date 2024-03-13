package pl.mamarek123.JobOffers.domain.offer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Configuration
class OfferConfig {
    @Bean
    public OfferFacade offerFacade(OfferRepository offerRepository, OfferFetching offerFetcher){
        return new OfferFacade(offerRepository, offerFetcher);
    }

}
