package pl.mamarek123.JobOffers.domain.offer;

import org.junit.jupiter.api.Test;
import pl.mamarek123.JobOffers.domain.offer.DTO.OfferDTO;

import static org.junit.jupiter.api.Assertions.*;

class OfferFacadeTest {

    OfferRepository offerRepository = new OfferTestRepositoryImplementation();

    OfferFacade offerFacade = new OfferFacade(offerRepository);
    @Test
    public void should_return_object_with_status_Failed_message_Offer_already_exists_if_added_offer_with_existing_url(){
        //given
        Offer offer = Offer.builder().
                company("firm1").
                title("job1").
                url("www.repeatingUrl.pl").
                salary("money1").
                build();
        OfferDTO repeatedOfferDto = OfferMapper.OfferToOfferDto(offer, null, null);
        offerFacade.addNewOffer(repeatedOfferDto);
        //when
        OfferDTO offerDTOResponse = offerFacade.addNewOffer(repeatedOfferDto);
        //then
        assertEquals(offerDTOResponse.message(),"Offer already exists");
        assertEquals(offerDTOResponse.status(), "Failed");
    }
    //"Failed", "Offer already exists"
}