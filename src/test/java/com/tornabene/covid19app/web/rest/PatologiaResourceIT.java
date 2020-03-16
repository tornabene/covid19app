package com.tornabene.covid19app.web.rest;

import com.tornabene.covid19app.Covid19AppApp;
import com.tornabene.covid19app.domain.Patologia;
import com.tornabene.covid19app.repository.PatologiaRepository;

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
 * Integration tests for the {@link PatologiaResource} REST controller.
 */
@SpringBootTest(classes = Covid19AppApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PatologiaResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private PatologiaRepository patologiaRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatologiaMockMvc;

    private Patologia patologia;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patologia createEntity(EntityManager em) {
        Patologia patologia = new Patologia()
            .nome(DEFAULT_NOME);
        return patologia;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Patologia createUpdatedEntity(EntityManager em) {
        Patologia patologia = new Patologia()
            .nome(UPDATED_NOME);
        return patologia;
    }

    @BeforeEach
    public void initTest() {
        patologia = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatologia() throws Exception {
        int databaseSizeBeforeCreate = patologiaRepository.findAll().size();

        // Create the Patologia
        restPatologiaMockMvc.perform(post("/api/patologias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patologia)))
            .andExpect(status().isCreated());

        // Validate the Patologia in the database
        List<Patologia> patologiaList = patologiaRepository.findAll();
        assertThat(patologiaList).hasSize(databaseSizeBeforeCreate + 1);
        Patologia testPatologia = patologiaList.get(patologiaList.size() - 1);
        assertThat(testPatologia.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createPatologiaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patologiaRepository.findAll().size();

        // Create the Patologia with an existing ID
        patologia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatologiaMockMvc.perform(post("/api/patologias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patologia)))
            .andExpect(status().isBadRequest());

        // Validate the Patologia in the database
        List<Patologia> patologiaList = patologiaRepository.findAll();
        assertThat(patologiaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPatologias() throws Exception {
        // Initialize the database
        patologiaRepository.saveAndFlush(patologia);

        // Get all the patologiaList
        restPatologiaMockMvc.perform(get("/api/patologias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patologia.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getPatologia() throws Exception {
        // Initialize the database
        patologiaRepository.saveAndFlush(patologia);

        // Get the patologia
        restPatologiaMockMvc.perform(get("/api/patologias/{id}", patologia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patologia.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingPatologia() throws Exception {
        // Get the patologia
        restPatologiaMockMvc.perform(get("/api/patologias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatologia() throws Exception {
        // Initialize the database
        patologiaRepository.saveAndFlush(patologia);

        int databaseSizeBeforeUpdate = patologiaRepository.findAll().size();

        // Update the patologia
        Patologia updatedPatologia = patologiaRepository.findById(patologia.getId()).get();
        // Disconnect from session so that the updates on updatedPatologia are not directly saved in db
        em.detach(updatedPatologia);
        updatedPatologia
            .nome(UPDATED_NOME);

        restPatologiaMockMvc.perform(put("/api/patologias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatologia)))
            .andExpect(status().isOk());

        // Validate the Patologia in the database
        List<Patologia> patologiaList = patologiaRepository.findAll();
        assertThat(patologiaList).hasSize(databaseSizeBeforeUpdate);
        Patologia testPatologia = patologiaList.get(patologiaList.size() - 1);
        assertThat(testPatologia.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingPatologia() throws Exception {
        int databaseSizeBeforeUpdate = patologiaRepository.findAll().size();

        // Create the Patologia

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatologiaMockMvc.perform(put("/api/patologias")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patologia)))
            .andExpect(status().isBadRequest());

        // Validate the Patologia in the database
        List<Patologia> patologiaList = patologiaRepository.findAll();
        assertThat(patologiaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatologia() throws Exception {
        // Initialize the database
        patologiaRepository.saveAndFlush(patologia);

        int databaseSizeBeforeDelete = patologiaRepository.findAll().size();

        // Delete the patologia
        restPatologiaMockMvc.perform(delete("/api/patologias/{id}", patologia.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Patologia> patologiaList = patologiaRepository.findAll();
        assertThat(patologiaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
