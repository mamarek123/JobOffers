package pl.mamarek123.JobOffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferDTO;

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
    public OfferDTO addNewOffer(OfferDTO offerDTO){
        Offer offer = OfferMapper.OfferDTOtoOffer(offerDTO);

        if (RepositoryExistingOfferChecker.existsByURL(offer,offerRepository)){
            return OfferMapper.OfferToOfferDto(offer,"Failed", "Offer already exists");
        }

        Optional<Offer> addedOffer = offerRepository.addOffer(offer);
        if(addedOffer.isEmpty()){
            throw new RepositoryException("Failed to add offer to repository");
        }
        return OfferMapper.OfferToOfferDto(addedOffer.get(), "Succes", "Offer added");
    }
}
