package pl.mamarek123.JobOffers.domain.offer;

import jakarta.annotation.Nullable;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferDTO;

class OfferMapper {
    public static Offer OfferDTOtoOffer(OfferDTO offerDTO){
        Offer offer = Offer.builder()
                .title(offerDTO.title())
                .company(offerDTO.company())
                .salary(offerDTO.salary())
                .url(offerDTO.url())
                .build();
        return offer;
    }

    public static OfferDTO OfferToOfferDto(Offer offer, @Nullable String status, @Nullable String message){
        OfferDTO offerDTO = OfferDTO.builder()
                .title(offer.title())
                .company(offer.company())
                .salary(offer.salary())
                .url(offer.url())
                .status(status)
                .message(message)
                .build();
        return offerDTO;
    }

}
