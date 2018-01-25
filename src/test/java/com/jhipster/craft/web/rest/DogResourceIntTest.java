package com.jhipster.craft.web.rest;

import com.jhipster.craft.CraftApp;

import com.jhipster.craft.domain.Dog;
import com.jhipster.craft.repository.DogRepository;
import com.jhipster.craft.service.DogService;
import com.jhipster.craft.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.jhipster.craft.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DogResource REST controller.
 *
 * @see DogResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CraftApp.class)
public class DogResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogService dogService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDogMockMvc;

    private Dog dog;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DogResource dogResource = new DogResource(dogService);
        this.restDogMockMvc = MockMvcBuilders.standaloneSetup(dogResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dog createEntity(EntityManager em) {
        Dog dog = new Dog()
            .name(DEFAULT_NAME)
            .age(DEFAULT_AGE)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE);
        return dog;
    }

    @Before
    public void initTest() {
        dog = createEntity(em);
    }

    @Test
    @Transactional
    public void createDog() throws Exception {
        int databaseSizeBeforeCreate = dogRepository.findAll().size();

        // Create the Dog
        restDogMockMvc.perform(post("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dog)))
            .andExpect(status().isCreated());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeCreate + 1);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDog.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testDog.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testDog.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createDogWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dogRepository.findAll().size();

        // Create the Dog with an existing ID
        dog.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDogMockMvc.perform(post("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dog)))
            .andExpect(status().isBadRequest());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDogs() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        // Get all the dogList
        restDogMockMvc.perform(get("/api/dogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dog.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))));
    }

    @Test
    @Transactional
    public void getDog() throws Exception {
        // Initialize the database
        dogRepository.saveAndFlush(dog);

        // Get the dog
        restDogMockMvc.perform(get("/api/dogs/{id}", dog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dog.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)));
    }

    @Test
    @Transactional
    public void getNonExistingDog() throws Exception {
        // Get the dog
        restDogMockMvc.perform(get("/api/dogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDog() throws Exception {
        // Initialize the database
        dogService.save(dog);

        int databaseSizeBeforeUpdate = dogRepository.findAll().size();

        // Update the dog
        Dog updatedDog = dogRepository.findOne(dog.getId());
        // Disconnect from session so that the updates on updatedDog are not directly saved in db
        em.detach(updatedDog);
        updatedDog
            .name(UPDATED_NAME)
            .age(UPDATED_AGE)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE);

        restDogMockMvc.perform(put("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDog)))
            .andExpect(status().isOk());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate);
        Dog testDog = dogList.get(dogList.size() - 1);
        assertThat(testDog.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDog.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testDog.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testDog.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingDog() throws Exception {
        int databaseSizeBeforeUpdate = dogRepository.findAll().size();

        // Create the Dog

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDogMockMvc.perform(put("/api/dogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dog)))
            .andExpect(status().isCreated());

        // Validate the Dog in the database
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDog() throws Exception {
        // Initialize the database
        dogService.save(dog);

        int databaseSizeBeforeDelete = dogRepository.findAll().size();

        // Get the dog
        restDogMockMvc.perform(delete("/api/dogs/{id}", dog.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Dog> dogList = dogRepository.findAll();
        assertThat(dogList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dog.class);
        Dog dog1 = new Dog();
        dog1.setId(1L);
        Dog dog2 = new Dog();
        dog2.setId(dog1.getId());
        assertThat(dog1).isEqualTo(dog2);
        dog2.setId(2L);
        assertThat(dog1).isNotEqualTo(dog2);
        dog1.setId(null);
        assertThat(dog1).isNotEqualTo(dog2);
    }
}
