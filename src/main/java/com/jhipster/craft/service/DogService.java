package com.jhipster.craft.service;

import com.jhipster.craft.domain.Dog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Dog.
 */
public interface DogService {

    /**
     * Save a dog.
     *
     * @param dog the entity to save
     * @return the persisted entity
     */
    Dog save(Dog dog);

    /**
     * Get all the dogs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Dog> findAll(Pageable pageable);

    /**
     * Get the "id" dog.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Dog findOne(Long id);

    /**
     * Delete the "id" dog.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
