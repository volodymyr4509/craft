package com.jhipster.craft.repository;

import com.jhipster.craft.domain.Dog;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Dog entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {

    @Query("select dog from Dog dog where dog.master.login = ?#{principal.username}")
    List<Dog> findByMasterIsCurrentUser();

}
