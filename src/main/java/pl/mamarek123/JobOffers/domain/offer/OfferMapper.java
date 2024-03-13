package pl.mamarek123.JobOffers.domain.offer;

import pl.mamarek123.JobOffers.domain.offer.DTO.OfferRequestDto;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferResponseDto;

import java.util.List;
import java.util.stream.Collectors;

class OfferMapper {
    public static Offer offerRequestDtoToOffer(OfferRequestDto offerRequestDto){
        Offer offer = Offer.builder()
                .title(offerRequestDto.title())
                .company(offerRequestDto.company())
                .salary(offerRequestDto.salary())
                .offerUrl(offerRequestDto.offerUrl())
                .build();
        return offer;
    }

    public static OfferResponseDto offerToOfferResponseDto(Offer offer){
        OfferResponseDto offerResponseDto = OfferResponseDto.builder()
                .id(offer.id())
                .title(offer.title())
                .company(offer.company())
                .salary(offer.salary())
                .offerUrl(offer.offerUrl())
                .build();
        return offerResponseDto;
    }

    public static List<OfferResponseDto> listOfOffersToListOfOfferResponseDto(List<Offer> offers){
        return offers.stream()
                .map(OfferMapper::offerToOfferResponseDto)
                .collect(Collectors.toList());
    }
}
