//package pl.mamarek123.JobOffers.domain.offer;
//
//import org.junit.jupiter.api.Test;
//import pl.mamarek123.JobOffers.domain.offer.DTO.OfferDto;
//
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class OfferFacadeTest {
//
//    OfferRepository offerRepository = new OfferTestRepositoryImplementation();
//    OfferFetching offerFetcher = new OfferFetcherTestImplementation();
//    OfferFacade offerFacade = new OfferFacade(offerRepository,offerFetcher);
//
//    @Test
//    public void should_correctly_retrieve_all_added_offers(){
//        //given
//        OfferDto offer1 = OfferDto.builder().
//                company("firm1").
//                title("job1").
//                offerUrl("www.repeatingUrl.pl").
//                salary("money1").
//                build();
//        OfferDto offer2 = OfferDto.builder().
//                company("firm2").
//                title("job2").
//                offerUrl("www.differentUrl2.pl").
//                salary("money2").
//                build();
//
//        OfferDto offer3 = OfferDto.builder().
//                company("firm3").
//                title("job3").
//                offerUrl("www.differentUrl3.pl").
//                salary("money3").
//                build();
//        Set<OfferDto> offers = new HashSet<>();
//        offers.add(offer1);
//        offers.add(offer2);
//        offers.add(offer3);
//        for(OfferDto offer: offers){
//            offerFacade.saveOffer(offer);
//        }
//        //when
//        List<OfferDto> retrievedOffers = offerFacade.findAllOffers();
//        //then
//        Set<OfferDto> retrievedOffersSet = new HashSet<>(retrievedOffers);
//        assertEquals(offers,retrievedOffersSet);
//
//    }
//    @Test
//    public void should_return_status_SUCCESS_when_saving_working_data(){
//        //given
//        OfferDto offerDto = OfferDto.builder().
//                offerUrl("www.offerUrl.com").
//                title("JobTitle").
//                company("firm1").
//                salary("money1").
//                build();
//        //when
//        OfferDto offerDtoSaved = offerFacade.saveOffer(offerDto);
//        //then
//        assertEquals(offerDto,offerDtoSaved);
//
//    }
//    @Test
//    public void should_return_object_with_status_FAILURE_and_message_Offer_already_exists_if_added_offer_with_existing_url(){
//        //given
//        Offer offer = Offer.builder().
//                company("firm1").
//                title("job1").
//                offerUrl("www.repeatingUrl.pl").
//                salary("money1").
//                build();
//        OfferDto repeatedOfferDto = OfferMapper.offerToOfferDto(offer);
//        offerFacade.saveOffer(repeatedOfferDto);
//        //when
//        OfferDto offerResponseDTO = offerFacade.saveOffer(repeatedOfferDto);
//        //then
//    }
//
//
//}