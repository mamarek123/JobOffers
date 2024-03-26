package pl.mamarek123.JobOffers.infrastructure.offer.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mamarek123.JobOffers.domain.offer.dto.OfferRequestDto;
import pl.mamarek123.JobOffers.domain.offer.dto.OfferResponseDto;
import pl.mamarek123.JobOffers.domain.offer.OfferFacade;
import springfox.documentation.annotations.Cacheable;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@Log4j2
public class OfferController {

    private final OfferFacade offerFacade;

    @Cacheable("jobOffers")
    @GetMapping("/offers")
    public ResponseEntity<List<OfferResponseDto>> getAllOffers(){
        List<OfferResponseDto> allOffers = offerFacade.findAllOffers();
        return ResponseEntity.ok(allOffers);
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<OfferResponseDto> getOfferById(@PathVariable String id){
        OfferResponseDto offerById = offerFacade.findOfferById(id);
        return ResponseEntity.ok(offerById);
    }

    @PostMapping("/offers")
    public ResponseEntity<OfferResponseDto> addNewOffer(@RequestBody @Valid OfferRequestDto offer) {
        OfferResponseDto savedOffer = offerFacade.saveOffer(offer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOffer);
    }

}
