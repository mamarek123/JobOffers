package pl.mamarek123.JobOffers.domain.offer;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class OfferTestRepositoryImplementation implements OfferRepository{
    public Map<Long,Offer> repository = new ConcurrentHashMap<>();

    @Override
    public Optional<Offer> getOfferByID(Long id) {
        return Optional.ofNullable(repository.get(id));
    }

    @Override
    public Optional<Offer> getOfferByURL(String url) {
        return repository.values().stream()
                .filter(offer -> offer.url().equals(url))
                .findFirst();
    }

    @Override
    public Optional<Offer> addOffer(Offer offer) {
        repository.put(generateNewId(),offer);
        return Optional.ofNullable(offer);
    }

    @Override
    public Optional<List<Offer>> getAllOffers() {
        return Optional.of(repository.values().stream().toList());
    }

    private Long generateNewId() {
        Long maxId = repository.keySet().stream().max(Long::compare).orElse(0L);
        return maxId + 1;
    }
}
