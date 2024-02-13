package pl.mamarek123.JobOffers.domain.offer;

import org.junit.jupiter.api.Test;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferDTO;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferResponseDTO;
import pl.mamarek123.JobOffers.domain.offer.DTO.OffersResponseDTO;
import pl.mamarek123.JobOffers.domain.offer.DTO.ResultStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OfferFacadeTest {

    OfferRepository offerRepository = new OfferTestRepositoryImplementation();
    OfferFacade offerFacade = new OfferFacade(offerRepository);

    @Test
    public void should_correctly_retrieve_all_added_offers(){
        //given
        OfferDTO offer1 = OfferDTO.builder().
                company("firm1").
                title("job1").
                url("www.repeatingUrl.pl").
                salary("money1").
                build();
        OfferDTO offer2 = OfferDTO.builder().
                company("firm2").
                title("job2").
                url("www.differentUrl2.pl").
                salary("money2").
                build();

        OfferDTO offer3 = OfferDTO.builder().
                company("firm3").
                title("job3").
                url("www.differentUrl3.pl").
                salary("money3").
                build();
        Set<OfferDTO> offers = new HashSet<>();
        offers.add(offer1);
        offers.add(offer2);
        offers.add(offer3);
        for(OfferDTO offer: offers){
            offerFacade.saveOffer(offer);
        }
        //when
        OffersResponseDTO allOffers = offerFacade.findAllOffers();
        //then
        List<OfferDTO> retrievedOffers = allOffers.offers();
        Set<OfferDTO> retrievedOffersSet = new HashSet<>(retrievedOffers);
        assertEquals(offers,retrievedOffersSet);

    }
    @Test
    public void should_return_status_SUCCESS_when_saving_working_data(){
        //given
        OfferDTO offerDTO = OfferDTO.builder().
                url("www.url.com").
                title("JobTitle").
                company("firm1").
                salary("money1").
                build();
        //when
        OfferResponseDTO offerResponseDTO = offerFacade.saveOffer(offerDTO);
        //then
        assertEquals(offerResponseDTO.status(),ResultStatus.SUCCESS);
    }
    @Test
    public void should_return_object_with_status_FAILURE_and_message_Offer_already_exists_if_added_offer_with_existing_url(){
        //given
        Offer offer = Offer.builder().
                company("firm1").
                title("job1").
                url("www.repeatingUrl.pl").
                salary("money1").
                build();
        OfferDTO repeatedOfferDto = OfferMapper.offerToOfferDTO(offer);
        offerFacade.saveOffer(repeatedOfferDto);
        //when
        OfferResponseDTO offerResponseDTO = offerFacade.saveOffer(repeatedOfferDto);
        //then
        assertEquals(offerResponseDTO.message(),"Offer already exists");
        assertEquals(offerResponseDTO.status(), ResultStatus.FAILURE);
    }


}