package pl.mamarek123.JobOffers.infrastructure.offer.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.mamarek123.JobOffers.domain.offer.dto.OfferResponseDto;
import pl.mamarek123.JobOffers.domain.offer.OfferFacade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
@Log4j2
public class HttpOffersScheduler {

    OfferFacade offerFacade;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedDelayString = "${http.offers.scheduler.request.delay}")
    public List<OfferResponseDto> fetchAllOffersAndSaveAllIfNotExists(){
        log.info("Started fetching offers {}", dateFormat.format(new Date()));
        List<OfferResponseDto> offers = offerFacade.fetchAllOffersAndSaveAllIfNotExists();
        log.info("Finished fetching offers {}", dateFormat.format(new Date()));
        return offers;
    }

}

