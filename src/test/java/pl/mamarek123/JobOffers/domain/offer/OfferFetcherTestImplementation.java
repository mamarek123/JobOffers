package pl.mamarek123.JobOffers.domain.offer;

import pl.mamarek123.JobOffers.domain.offer.DTO.OfferRequestDto;

import java.util.ArrayList;
import java.util.List;

public class OfferFetcherTestImplementation implements OfferFetching {

    @Override
    public List<OfferRequestDto> fetchOffers() {
        var offers = new ArrayList<OfferRequestDto>();
        OfferRequestDto offer3 = OfferRequestDto.builder()
                .company("firm3")
                .title("job3")
                .offerUrl("www.differentUrl3.pl")
                .salary("money3")
                .build();
        OfferRequestDto offer4 = OfferRequestDto.builder()
                .company("firm4")
                .title("job4")
                .offerUrl("www.differentUrl4.pl")
                .salary("money4")
                .build();
        OfferRequestDto offer5 = OfferRequestDto.builder()
                .company("firm5")
                .title("job5")
                .offerUrl("www.differentUrl5.pl")
                .salary("money5")
                .build();
        offers.addAll(List.of(offer3,offer4,offer5));
        return offers;
    }
}
