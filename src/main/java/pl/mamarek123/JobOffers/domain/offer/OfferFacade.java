package pl.mamarek123.JobOffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferDTO;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferResponseDTO;
import pl.mamarek123.JobOffers.domain.offer.DTO.OffersResponseDTO;
import pl.mamarek123.JobOffers.domain.offer.DTO.ResultStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/*
* klient musi używać tokena, żeby zobaczyć oferty
* każda oferta pracy ma (link do oferty, nazwę stanowiska, nazwę firmy, zarobki (mogą być widełki)
* aktualizacja ofert w bazie danych jest co 3 godziny (wtedy odpytujemy zdalny serwer z pkt. 1)
* klient może ręcznie dodać ofertę pracy
* jeśli klient w ciągu 60 minut robi więcej niż jedno zapytanie, to dane powinny pobierać się z cache (ponieważ pobieranie z bazy danych kosztuję pieniądze naszego klienta)
* oferty w bazie nie mogą się powtarzać (decyduje url oferty)
* klient może pobrać jedną ofertę pracy poprzez unikalne Id
* klient może pobrać wszystkie dostępne oferty kiedy jest zautoryzowany
 */
@AllArgsConstructor
public class OfferFacade {

    OfferRepository offerRepository;
    public OfferResponseDTO saveOffer(OfferDTO offerDTO){
        Offer offer = OfferMapper.offerDTOToOffer(offerDTO);

        if (RepositoryExistingOfferChecker.existsByURL(offer,offerRepository)){
            return OfferMapper.offerToOfferResponseDto(offer, ResultStatus.FAILURE, "Offer already exists");
        }

        Optional<Offer> addedOffer = offerRepository.addOffer(offer);
        if(addedOffer.isEmpty()){
            throw new RepositoryException("Failed to add offer to repository");
        }
        return OfferMapper.offerToOfferResponseDto(addedOffer.get(), ResultStatus.SUCCESS, "Offer added");
    }

    public OffersResponseDTO findAllOffers() {
        if (offerRepository.getAllOffers().isEmpty()) {
            return createEmptySuccesOffersResponseDTO();
        }
        List<Offer> offers = offerRepository.getAllOffers().get();
        OffersResponseDTO offersResponseDTO = OfferMapper.listOfOffersToOffersResponseDTO(offers);
        return offersResponseDTO;
    }

    private static OffersResponseDTO createEmptySuccesOffersResponseDTO() {
        return OffersResponseDTO.builder().
                offers(new ArrayList<>()).
                status(ResultStatus.SUCCESS).
                message("There are no offers").
                build();
    }

}
