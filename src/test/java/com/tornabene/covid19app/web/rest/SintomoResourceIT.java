package com.tornabene.covid19app.web.rest;

import com.tornabene.covid19app.Covid19AppApp;
import com.tornabene.covid19app.domain.Sintomo;
import com.tornabene.covid19app.repository.SintomoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link SintomoResource} REST controller.
 */
@SpringBootTest(classes = Covid19AppApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SintomoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private SintomoRepository sintomoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSintomoMockMvc;

    private Sintomo sintomo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sintomo createEntity(EntityManager em) {
        Sintomo sintomo = new Sintomo()
            .nome(DEFAULT_NOME);
        return sintomo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Sintomo createUpdatedEntity(EntityManager em) {
        Sintomo sintomo = new Sintomo()
            .nome(UPDATED_NOME);
        return sintomo;
    }

    @BeforeEach
    public void initTest() {
        sintomo = createEntity(em);
    }

    @Test
    @Transactional
    public void createSintomo() throws Exception {
        int databaseSizeBeforeCreate = sintomoRepository.findAll().size();

        // Create the Sintomo
        restSintomoMockMvc.perform(post("/api/sintomos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sintomo)))
            .andExpect(status().isCreated());

        // Validate the Sintomo in the database
        List<Sintomo> sintomoList = sintomoRepository.findAll();
        assertThat(sintomoList).hasSize(databaseSizeBeforeCreate + 1);
        Sintomo testSintomo = sintomoList.get(sintomoList.size() - 1);
        assertThat(testSintomo.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createSintomoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sintomoRepository.findAll().size();

        // Create the Sintomo with an existing ID
        sintomo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSintomoMockMvc.perform(post("/api/sintomos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sintomo)))
            .andExpect(status().isBadRequest());

        // Validate the Sintomo in the database
        List<Sintomo> sintomoList = sintomoRepository.findAll();
        assertThat(sintomoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSintomos() throws Exception {
        // Initialize the database
        sintomoRepository.saveAndFlush(sintomo);

        // Get all the sintomoList
        restSintomoMockMvc.perform(get("/api/sintomos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sintomo.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getSintomo() throws Exception {
        // Initialize the database
        sintomoRepository.saveAndFlush(sintomo);

        // Get the sintomo
        restSintomoMockMvc.perform(get("/api/sintomos/{id}", sintomo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(sintomo.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingSintomo() throws Exception {
        // Get the sintomo
        restSintomoMockMvc.perform(get("/api/sintomos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSintomo() throws Exception {
        // Initialize the database
        sintomoRepository.saveAndFlush(sintomo);

        int databaseSizeBeforeUpdate = sintomoRepository.findAll().size();

        // Update the sintomo
        Sintomo updatedSintomo = sintomoRepository.findById(sintomo.getId()).get();
        // Disconnect from session so that the updates on updatedSintomo are not directly saved in db
        em.detach(updatedSintomo);
        updatedSintomo
            .nome(UPDATED_NOME);

        restSintomoMockMvc.perform(put("/api/sintomos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSintomo)))
            .andExpect(status().isOk());

        // Validate the Sintomo in the database
        List<Sintomo> sintomoList = sintomoRepository.findAll();
        assertThat(sintomoList).hasSize(databaseSizeBeforeUpdate);
        Sintomo testSintomo = sintomoList.get(sintomoList.size() - 1);
        assertThat(testSintomo.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingSintomo() throws Exception {
        int databaseSizeBeforeUpdate = sintomoRepository.findAll().size();

        // Create the Sintomo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSintomoMockMvc.perform(put("/api/sintomos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(sintomo)))
            .andExpect(status().isBadRequest());

        // Validate the Sintomo in the database
        List<Sintomo> sintomoList = sintomoRepository.findAll();
        assertThat(sintomoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSintomo() throws Exception {
        // Initialize the database
        sintomoRepository.saveAndFlush(sintomo);

        int databaseSizeBeforeDelete = sintomoRepository.findAll().size();

        // Delete the sintomo
        restSintomoMockMvc.perform(delete("/api/sintomos/{id}", sintomo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Sintomo> sintomoList = sintomoRepository.findAll();
        assertThat(sintomoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
