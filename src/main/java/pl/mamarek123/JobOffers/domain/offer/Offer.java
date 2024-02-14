package pl.mamarek123.JobOffers.domain.offer;

import lombok.Builder;

@Builder
record Offer(String title, String company, String salary, String url){

}
