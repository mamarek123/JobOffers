package pl.mamarek123.JobOffers.domain.offer;

import org.junit.jupiter.api.Test;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferRequestDto;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferResponseDto;
import pl.mamarek123.JobOffers.domain.offer.error.OfferAlreadyExistsException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class OfferFacadeTest {

    OfferRepository offerRepository = new OfferTestRepositoryImplementation();
    OfferFetching offerFetcher = new OfferFetcherTestImplementation();
    OfferFacade offerFacade = new OfferFacade(offerRepository,offerFetcher);

    @Test
    public void should_correctly_retrieve_all_added_offers(){
        //given
        OfferRequestDto offer1 = OfferRequestDto.builder().
                company("firm1").
                title("job1").
                offerUrl("www.differentUrl1.pl").
                salary("money1").
                build();
        OfferRequestDto offer2 = OfferRequestDto.builder().
                company("firm2").
                title("job2").
                offerUrl("www.differentUrl2.pl").
                salary("money2").
                build();
        OfferRequestDto offer3 = OfferRequestDto.builder().
                company("firm3").
                title("job3").
                offerUrl("www.differentUrl3.pl").
                salary("money3").
                build();
        Set<OfferRequestDto> offers = new HashSet<>();
        offers.add(offer1);
        offers.add(offer2);
        offers.add(offer3);
        for(OfferRequestDto offer: offers){
            offerFacade.saveOffer(offer);
        }
        //when
        Set<OfferResponseDto> retrievedOffers = new HashSet<>(offerFacade.findAllOffers());
        //then
        for (OfferResponseDto retrievedOffer : retrievedOffers) {
            assertTrue(
                    offers.stream().allMatch(offer ->
                            offer.company().equals(retrievedOffer.company()) &&
                                    offer.title().equals(retrievedOffer.title()) &&
                                    offer.offerUrl().equals(retrievedOffer.offerUrl()) &&
                                    offer.salary().equals(retrievedOffer.salary())
                    ),
                    "The retrieved offer should match an offer in the request set, ignoring the ID"
            );
        }

    }
    @Test
    public void should_throw_OfferAlreadyExistsException_when_saving_offer_with_exisiting_id(){
        //given
        OfferRequestDto offer1 = OfferRequestDto.builder().
                company("firm1").
                title("job1").
                offerUrl("www.repeatingUrl.pl").
                salary("money1").
                build();
        OfferRequestDto offer2 = OfferRequestDto.builder().
                company("firm2").
                title("job2").
                offerUrl("www.repeatingUrl.pl").
                salary("money2").
                build();
        //when
        offerFacade.saveOffer(offer1);
        //then
        assertThrows(OfferAlreadyExistsException.class, () -> offerFacade.saveOffer(offer2), "Expected offerFacade.saveOffer(offer2) to throw OfferAlreadyExistsException");
    }
    @Test
    public void should_find_offer_by_id(){
        //given
        OfferRequestDto offer1 = OfferRequestDto.builder().
                company("firm1").
                title("job1").
                offerUrl("www.differentUrl1.pl").
                salary("money1").
                build();
        OfferResponseDto savedOffer = offerFacade.saveOffer(offer1);
        String savedOfferId = savedOffer.id();
        //when
        OfferResponseDto offerById = offerFacade.findOfferById(savedOfferId);
        //then
        assertEquals(savedOffer,offerById,"Retrieved offer should be the same as saved one");
    }

    @Test
    public void should_fetch_all_offers_and_save_all_if_not_exists(){
        //given
        var initialOffers = new ArrayList<OfferRequestDto>();
        OfferRequestDto offer1 = OfferRequestDto.builder().
                company("firm1").
                title("job1").
                offerUrl("www.differentUrl1.pl").
                salary("money1").
                build();
        OfferRequestDto offer2 = OfferRequestDto.builder().
                company("firm2").
                title("job2").
                offerUrl("www.differentUrl2.pl").
                salary("money2").
                build();
        OfferRequestDto offer3 = OfferRequestDto.builder().
                company("firm3").
                title("job3").
                offerUrl("www.differentUrl3.pl").
                salary("money3").
                build();
        initialOffers.addAll(List.of(offer1,offer2,offer3));
        initialOffers.forEach(offer -> offerFacade.saveOffer(offer));
        //when
        List<OfferResponseDto> savedOffers = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        //then
        List<String> savedOfferUrls = savedOffers.stream()
                .map(OfferResponseDto::offerUrl)
                .collect(Collectors.toList());
        List<String> expectedUrls = List.of("www.differentUrl4.pl", "www.differentUrl5.pl");
        assertTrue(savedOfferUrls.containsAll(expectedUrls) && expectedUrls.containsAll(savedOfferUrls), "Only offers 4 and 5 should be saved.");
    }


}