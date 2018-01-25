package com.jhipster.craft.service.impl;

import com.jhipster.craft.service.DogService;
import com.jhipster.craft.domain.Dog;
import com.jhipster.craft.repository.DogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Dog.
 */
@Service
@Transactional
public class DogServiceImpl implements DogService {

    private final Logger log = LoggerFactory.getLogger(DogServiceImpl.class);

    private final DogRepository dogRepository;

    public DogServiceImpl(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    /**
     * Save a dog.
     *
     * @param dog the entity to save
     * @return the persisted entity
     */
    @Override
    public Dog save(Dog dog) {
        log.debug("Request to save Dog : {}", dog);
        return dogRepository.save(dog);
    }

    /**
     * Get all the dogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Dog> findAll(Pageable pageable) {
        log.debug("Request to get all Dogs");
        return dogRepository.findAll(pageable);
    }

    /**
     * Get one dog by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Dog findOne(Long id) {
        log.debug("Request to get Dog : {}", id);
        return dogRepository.findOne(id);
    }

    /**
     * Delete the dog by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Dog : {}", id);
        dogRepository.delete(id);
    }
}
