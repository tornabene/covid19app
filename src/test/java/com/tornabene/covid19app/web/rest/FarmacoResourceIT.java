package com.tornabene.covid19app.web.rest;

import com.tornabene.covid19app.Covid19AppApp;
import com.tornabene.covid19app.domain.Farmaco;
import com.tornabene.covid19app.repository.FarmacoRepository;

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
 * Integration tests for the {@link FarmacoResource} REST controller.
 */
@SpringBootTest(classes = Covid19AppApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class FarmacoResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private FarmacoRepository farmacoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFarmacoMockMvc;

    private Farmaco farmaco;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Farmaco createEntity(EntityManager em) {
        Farmaco farmaco = new Farmaco()
            .nome(DEFAULT_NOME);
        return farmaco;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Farmaco createUpdatedEntity(EntityManager em) {
        Farmaco farmaco = new Farmaco()
            .nome(UPDATED_NOME);
        return farmaco;
    }

    @BeforeEach
    public void initTest() {
        farmaco = createEntity(em);
    }

    @Test
    @Transactional
    public void createFarmaco() throws Exception {
        int databaseSizeBeforeCreate = farmacoRepository.findAll().size();

        // Create the Farmaco
        restFarmacoMockMvc.perform(post("/api/farmacos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(farmaco)))
            .andExpect(status().isCreated());

        // Validate the Farmaco in the database
        List<Farmaco> farmacoList = farmacoRepository.findAll();
        assertThat(farmacoList).hasSize(databaseSizeBeforeCreate + 1);
        Farmaco testFarmaco = farmacoList.get(farmacoList.size() - 1);
        assertThat(testFarmaco.getNome()).isEqualTo(DEFAULT_NOME);
    }

    @Test
    @Transactional
    public void createFarmacoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = farmacoRepository.findAll().size();

        // Create the Farmaco with an existing ID
        farmaco.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFarmacoMockMvc.perform(post("/api/farmacos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(farmaco)))
            .andExpect(status().isBadRequest());

        // Validate the Farmaco in the database
        List<Farmaco> farmacoList = farmacoRepository.findAll();
        assertThat(farmacoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFarmacos() throws Exception {
        // Initialize the database
        farmacoRepository.saveAndFlush(farmaco);

        // Get all the farmacoList
        restFarmacoMockMvc.perform(get("/api/farmacos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(farmaco.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getFarmaco() throws Exception {
        // Initialize the database
        farmacoRepository.saveAndFlush(farmaco);

        // Get the farmaco
        restFarmacoMockMvc.perform(get("/api/farmacos/{id}", farmaco.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(farmaco.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingFarmaco() throws Exception {
        // Get the farmaco
        restFarmacoMockMvc.perform(get("/api/farmacos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFarmaco() throws Exception {
        // Initialize the database
        farmacoRepository.saveAndFlush(farmaco);

        int databaseSizeBeforeUpdate = farmacoRepository.findAll().size();

        // Update the farmaco
        Farmaco updatedFarmaco = farmacoRepository.findById(farmaco.getId()).get();
        // Disconnect from session so that the updates on updatedFarmaco are not directly saved in db
        em.detach(updatedFarmaco);
        updatedFarmaco
            .nome(UPDATED_NOME);

        restFarmacoMockMvc.perform(put("/api/farmacos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFarmaco)))
            .andExpect(status().isOk());

        // Validate the Farmaco in the database
        List<Farmaco> farmacoList = farmacoRepository.findAll();
        assertThat(farmacoList).hasSize(databaseSizeBeforeUpdate);
        Farmaco testFarmaco = farmacoList.get(farmacoList.size() - 1);
        assertThat(testFarmaco.getNome()).isEqualTo(UPDATED_NOME);
    }

    @Test
    @Transactional
    public void updateNonExistingFarmaco() throws Exception {
        int databaseSizeBeforeUpdate = farmacoRepository.findAll().size();

        // Create the Farmaco

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFarmacoMockMvc.perform(put("/api/farmacos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(farmaco)))
            .andExpect(status().isBadRequest());

        // Validate the Farmaco in the database
        List<Farmaco> farmacoList = farmacoRepository.findAll();
        assertThat(farmacoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFarmaco() throws Exception {
        // Initialize the database
        farmacoRepository.saveAndFlush(farmaco);

        int databaseSizeBeforeDelete = farmacoRepository.findAll().size();

        // Delete the farmaco
        restFarmacoMockMvc.perform(delete("/api/farmacos/{id}", farmaco.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Farmaco> farmacoList = farmacoRepository.findAll();
        assertThat(farmacoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
