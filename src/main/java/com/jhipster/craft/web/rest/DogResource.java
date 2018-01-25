package com.jhipster.craft.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jhipster.craft.domain.Dog;
import com.jhipster.craft.service.DogService;
import com.jhipster.craft.web.rest.errors.BadRequestAlertException;
import com.jhipster.craft.web.rest.util.HeaderUtil;
import com.jhipster.craft.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Dog.
 */
@RestController
@RequestMapping("/api")
public class DogResource {

    private final Logger log = LoggerFactory.getLogger(DogResource.class);

    private static final String ENTITY_NAME = "dog";

    private final DogService dogService;

    public DogResource(DogService dogService) {
        this.dogService = dogService;
    }

    /**
     * POST  /dogs : Create a new dog.
     *
     * @param dog the dog to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dog, or with status 400 (Bad Request) if the dog has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dogs")
    @Timed
    public ResponseEntity<Dog> createDog(@RequestBody Dog dog) throws URISyntaxException {
        log.debug("REST request to save Dog : {}", dog);
        if (dog.getId() != null) {
            throw new BadRequestAlertException("A new dog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dog result = dogService.save(dog);
        return ResponseEntity.created(new URI("/api/dogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dogs : Updates an existing dog.
     *
     * @param dog the dog to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dog,
     * or with status 400 (Bad Request) if the dog is not valid,
     * or with status 500 (Internal Server Error) if the dog couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dogs")
    @Timed
    public ResponseEntity<Dog> updateDog(@RequestBody Dog dog) throws URISyntaxException {
        log.debug("REST request to update Dog : {}", dog);
        if (dog.getId() == null) {
            return createDog(dog);
        }
        Dog result = dogService.save(dog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dogs : get all the dogs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dogs in body
     */
    @GetMapping("/dogs")
    @Timed
    public ResponseEntity<List<Dog>> getAllDogs(Pageable pageable) {
        log.debug("REST request to get a page of Dogs");
        Page<Dog> page = dogService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dogs/:id : get the "id" dog.
     *
     * @param id the id of the dog to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dog, or with status 404 (Not Found)
     */
    @GetMapping("/dogs/{id}")
    @Timed
    public ResponseEntity<Dog> getDog(@PathVariable Long id) {
        log.debug("REST request to get Dog : {}", id);
        Dog dog = dogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dog));
    }

    /**
     * DELETE  /dogs/:id : delete the "id" dog.
     *
     * @param id the id of the dog to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dogs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDog(@PathVariable Long id) {
        log.debug("REST request to delete Dog : {}", id);
        dogService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
