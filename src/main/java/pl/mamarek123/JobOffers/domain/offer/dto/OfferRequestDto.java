package pl.mamarek123.JobOffers.domain.offer.dto;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

@Builder
public record OfferRequestDto(
        @NotBlank(message = "Title cannot be blank")
        String title,

        @NotBlank(message = "Company cannot be blank")
        String company,

        @NotBlank(message = "Salary cannot be blank")
        String salary,

        @NotBlank(message = "Offer URL cannot be blank")
        String offerUrl
) {}