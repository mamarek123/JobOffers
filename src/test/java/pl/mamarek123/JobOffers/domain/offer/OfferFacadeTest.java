package pl.mamarek123.JobOffers.domain.offer;

import org.junit.jupiter.api.Test;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferDTO;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferResponseDTO;
import pl.mamarek123.JobOffers.domain.offer.DTO.ResultStatus;

import static org.junit.jupiter.api.Assertions.*;

class OfferFacadeTest {

    OfferRepository offerRepository = new OfferTestRepositoryImplementation();
    OfferFacade offerFacade = new OfferFacade(offerRepository);
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