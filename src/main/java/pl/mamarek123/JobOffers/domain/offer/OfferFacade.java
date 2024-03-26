package pl.mamarek123.JobOffers.domain.offer;

import lombok.AllArgsConstructor;
import pl.mamarek123.JobOffers.domain.offer.dto.OfferRequestDto;
import pl.mamarek123.JobOffers.domain.offer.dto.OfferResponseDto;
import pl.mamarek123.JobOffers.domain.offer.error.OfferAlreadyExistsException;
import pl.mamarek123.JobOffers.domain.offer.error.OfferNotExistingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class OfferFacade {

    private final OfferRepository offerRepository;
    private final OfferFetching offerFetcher;

    public OfferResponseDto saveOffer(OfferRequestDto offerRequestDto) {
        Offer offer = OfferMapper.offerRequestDtoToOffer(offerRequestDto);

        if (offerRepository.existsByOfferUrl(offer.offerUrl())) {
            throw new OfferAlreadyExistsException("Offer with url:" + offer.offerUrl() + " already exists");
        }

        Offer addedOffer = offerRepository.save(offer);
        return OfferMapper.offerToOfferResponseDto(addedOffer);
    }

    public List<OfferResponseDto> findAllOffers() {
        List<Offer> offers = offerRepository.findAll();
        return OfferMapper.listOfOffersToListOfOfferResponseDto(offers);
    }

    public OfferResponseDto findOfferById(String id) {
        Optional<Offer> offer = offerRepository.findById(id);
        if (offer.isEmpty()) {
            throw new OfferNotExistingException("Offer with id:" + id + " does not exist");
        }
        return OfferMapper.offerToOfferResponseDto(offer.get());
    }

    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists() {
        List<OfferRequestDto> offerRequestDtos = offerFetcher.fetchOffers();

        List<OfferResponseDto> addedOffers = new ArrayList<>();
        offerRequestDtos.forEach(offerRequestDto -> {
            if (!offerRepository.existsByOfferUrl(offerRequestDto.offerUrl())) {
                try {
                    OfferResponseDto savedOffer = saveOffer(offerRequestDto);
                    addedOffers.add(savedOffer);
                } catch (OfferAlreadyExistsException ignored) {
                    // Handle the case where the offer already exists
                }
            }
        });
        return addedOffers;
    }
}
