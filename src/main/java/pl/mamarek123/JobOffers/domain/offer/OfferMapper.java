package pl.mamarek123.JobOffers.domain.offer;

import pl.mamarek123.JobOffers.domain.offer.DTO.OfferDTO;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferResponseDTO;
import pl.mamarek123.JobOffers.domain.offer.DTO.OffersResponseDTO;
import pl.mamarek123.JobOffers.domain.offer.DTO.ResultStatus;

import java.util.List;
import java.util.stream.Collectors;

class OfferMapper {
    public static Offer offerDTOToOffer(OfferDTO offerDTO){
        Offer offer = Offer.builder()
                .title(offerDTO.title())
                .company(offerDTO.company())
                .salary(offerDTO.salary())
                .url(offerDTO.url())
                .build();
        return offer;
    }

    public static OfferDTO offerToOfferDTO(Offer offer){
        OfferDTO offerDTO = OfferDTO.builder()
                .title(offer.title())
                .company(offer.company())
                .salary(offer.salary())
                .url(offer.url())
                .build();
        return offerDTO;
    }


    public static OfferResponseDTO offerToOfferResponseDto(Offer offer, ResultStatus status, String message) {
        OfferDTO offerDTO = offerToOfferDTO(offer);
        OfferResponseDTO offerResponseDTODTO = OfferResponseDTO.builder()
                .offer(offerDTO)
                .status(status)
                .message(message)
                .build();
        return offerResponseDTODTO;
    }

    public static OffersResponseDTO listOfOffersToOffersResponseDTO(List<Offer> offers){
        List<OfferDTO> offerDTOList = offers.stream()
                .map(OfferMapper::offerToOfferDTO)
                .collect(Collectors.toList());
        OffersResponseDTO offersResponseDTO = OffersResponseDTO.builder()
                .offers(offerDTOList)
                .status(ResultStatus.SUCCESS)
                .message("Success")
                .build();
        return offersResponseDTO;
    }
}
