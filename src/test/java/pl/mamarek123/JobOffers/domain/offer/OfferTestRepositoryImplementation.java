package pl.mamarek123.JobOffers.domain.offer;


import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

class OfferTestRepositoryImplementation implements OfferRepository{
    //Key will be String ID
    public Map<String,Offer> repository = new ConcurrentHashMap<>();


    private String generateNewId() {
        String newId;
        do {
            newId = UUID.randomUUID().toString();
        } while (repository.containsKey(newId)); // Ensure the ID is unique in the repository
        return newId;
    }


    @Override
    public boolean existsByOfferUrl(String url) {
        return repository.values().stream().anyMatch(value -> value.offerUrl().equals(url));

    }

    @Override
    public <S extends Offer> S save(S entity) {
        String id = generateNewId();
        repository.put(id, entity);
        Offer savedOffer = Offer.builder()
                .id(id)
                .offerUrl(entity.offerUrl())
                .title(entity.title())
                .company(entity.company())
                .salary(entity.salary())
                .build();
        return (S)savedOffer;
    }

    @Override
    public <S extends Offer> List<S> saveAll(Iterable<S> entities) {
        List<S> savedOffers = new ArrayList<>();
        entities.forEach(entity -> {
            S save = save(entity);
            if(save != null){
                savedOffers.add(save);
            }
        });
        return savedOffers;
    }

    @Override
    public Optional<Offer> findById(String id) {
        Offer retrievedOffer = repository.get(id);
        if (retrievedOffer == null){
            return Optional.empty();
        }
        retrievedOffer = Offer.builder()
                .id(id)
                .title(retrievedOffer.title())
                .offerUrl(retrievedOffer.offerUrl())
                .company(retrievedOffer.company())
                .salary(retrievedOffer.salary())
                .build();
        return Optional.of(retrievedOffer);
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public List<Offer> findAll() {
        return repository.values().stream().toList();
    }

    @Override
    public Iterable<Offer> findAllById(Iterable<String> strings) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(Offer entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends String> strings) {

    }

    @Override
    public void deleteAll(Iterable<? extends Offer> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Offer> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Offer> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Offer> S insert(S entity) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> insert(Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Offer> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends Offer> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends Offer> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Offer> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Offer> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Offer, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}
