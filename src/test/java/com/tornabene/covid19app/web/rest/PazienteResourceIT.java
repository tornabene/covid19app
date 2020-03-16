package com.tornabene.covid19app.web.rest;

import com.tornabene.covid19app.Covid19AppApp;
import com.tornabene.covid19app.domain.Paziente;
import com.tornabene.covid19app.repository.PazienteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static com.tornabene.covid19app.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.tornabene.covid19app.domain.enumeration.Sex;
/**
 * Integration tests for the {@link PazienteResource} REST controller.
 */
@SpringBootTest(classes = Covid19AppApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PazienteResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CF = "AAAAAAAAAA";
    private static final String UPDATED_CF = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Sex DEFAULT_SEX = Sex.M;
    private static final Sex UPDATED_SEX = Sex.F;

    private static final String DEFAULT_COMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMUNE = "BBBBBBBBBB";

    private static final String DEFAULT_PROVINCIA = "AAAAAAAAAA";
    private static final String UPDATED_PROVINCIA = "BBBBBBBBBB";

    private static final String DEFAULT_REGIONE = "AAAAAAAAAA";
    private static final String UPDATED_REGIONE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDINE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDINE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDINE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDINE = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMERO_FAMIGLIARI = 1;
    private static final Integer UPDATED_NUMERO_FAMIGLIARI = 2;

    private static final String DEFAULT_PROFESSIONE = "AAAAAAAAAA";
    private static final String UPDATED_PROFESSIONE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FUMATORE = false;
    private static final Boolean UPDATED_FUMATORE = true;

    private static final Boolean DEFAULT_POSIVITO = false;
    private static final Boolean UPDATED_POSIVITO = true;

    private static final Boolean DEFAULT_ISOLAMENTO_DOMICILIARE = false;
    private static final Boolean UPDATED_ISOLAMENTO_DOMICILIARE = true;

    private static final Boolean DEFAULT_TERAPIA_INTENSIVA = false;
    private static final Boolean UPDATED_TERAPIA_INTENSIVA = true;

    private static final Boolean DEFAULT_GUARITO = false;
    private static final Boolean UPDATED_GUARITO = true;

    private static final Boolean DEFAULT_DECEDUTO = false;
    private static final Boolean UPDATED_DECEDUTO = true;

    private static final ZonedDateTime DEFAULT_DATA_TAMPONE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_TAMPONE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_GUARITO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_GUARITO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_ISOLAMENTO_DOMICILIARE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_ISOLAMENTO_DOMICILIARE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_DECEDUTO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_DECEDUTO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_TERAPIA_INTENSIVA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_TERAPIA_INTENSIVA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_DATA_DIMISSIONE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATA_DIMISSIONE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_GIORNI_ISOLAMENTO_DOMICILIARE = 1;
    private static final Integer UPDATED_GIORNI_ISOLAMENTO_DOMICILIARE = 2;

    private static final Integer DEFAULT_GIORNI_TERAPIA_INTENSIVA = 1;
    private static final Integer UPDATED_GIORNI_TERAPIA_INTENSIVA = 2;

    private static final Integer DEFAULT_GIORNI_GUARITO = 1;
    private static final Integer UPDATED_GIORNI_GUARITO = 2;

    private static final Integer DEFAULT_GIORNI_DECEDUTO = 1;
    private static final Integer UPDATED_GIORNI_DECEDUTO = 2;

    private static final String DEFAULT_NOTA = "AAAAAAAAAA";
    private static final String UPDATED_NOTA = "BBBBBBBBBB";

    @Autowired
    private PazienteRepository pazienteRepository;

    @Mock
    private PazienteRepository pazienteRepositoryMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPazienteMockMvc;

    private Paziente paziente;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paziente createEntity(EntityManager em) {
        Paziente paziente = new Paziente()
            .nome(DEFAULT_NOME)
            .cf(DEFAULT_CF)
            .age(DEFAULT_AGE)
            .sex(DEFAULT_SEX)
            .comune(DEFAULT_COMUNE)
            .provincia(DEFAULT_PROVINCIA)
            .regione(DEFAULT_REGIONE)
            .latitudine(DEFAULT_LATITUDINE)
            .longitudine(DEFAULT_LONGITUDINE)
            .numeroFamigliari(DEFAULT_NUMERO_FAMIGLIARI)
            .professione(DEFAULT_PROFESSIONE)
            .fumatore(DEFAULT_FUMATORE)
            .posivito(DEFAULT_POSIVITO)
            .isolamentoDomiciliare(DEFAULT_ISOLAMENTO_DOMICILIARE)
            .terapiaIntensiva(DEFAULT_TERAPIA_INTENSIVA)
            .guarito(DEFAULT_GUARITO)
            .deceduto(DEFAULT_DECEDUTO)
            .dataTampone(DEFAULT_DATA_TAMPONE)
            .dataGuarito(DEFAULT_DATA_GUARITO)
            .dataIsolamentoDomiciliare(DEFAULT_DATA_ISOLAMENTO_DOMICILIARE)
            .dataDeceduto(DEFAULT_DATA_DECEDUTO)
            .dataTerapiaIntensiva(DEFAULT_DATA_TERAPIA_INTENSIVA)
            .dataDimissione(DEFAULT_DATA_DIMISSIONE)
            .giorniIsolamentoDomiciliare(DEFAULT_GIORNI_ISOLAMENTO_DOMICILIARE)
            .giorniTerapiaIntensiva(DEFAULT_GIORNI_TERAPIA_INTENSIVA)
            .giorniGuarito(DEFAULT_GIORNI_GUARITO)
            .giorniDeceduto(DEFAULT_GIORNI_DECEDUTO)
            .nota(DEFAULT_NOTA);
        return paziente;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Paziente createUpdatedEntity(EntityManager em) {
        Paziente paziente = new Paziente()
            .nome(UPDATED_NOME)
            .cf(UPDATED_CF)
            .age(UPDATED_AGE)
            .sex(UPDATED_SEX)
            .comune(UPDATED_COMUNE)
            .provincia(UPDATED_PROVINCIA)
            .regione(UPDATED_REGIONE)
            .latitudine(UPDATED_LATITUDINE)
            .longitudine(UPDATED_LONGITUDINE)
            .numeroFamigliari(UPDATED_NUMERO_FAMIGLIARI)
            .professione(UPDATED_PROFESSIONE)
            .fumatore(UPDATED_FUMATORE)
            .posivito(UPDATED_POSIVITO)
            .isolamentoDomiciliare(UPDATED_ISOLAMENTO_DOMICILIARE)
            .terapiaIntensiva(UPDATED_TERAPIA_INTENSIVA)
            .guarito(UPDATED_GUARITO)
            .deceduto(UPDATED_DECEDUTO)
            .dataTampone(UPDATED_DATA_TAMPONE)
            .dataGuarito(UPDATED_DATA_GUARITO)
            .dataIsolamentoDomiciliare(UPDATED_DATA_ISOLAMENTO_DOMICILIARE)
            .dataDeceduto(UPDATED_DATA_DECEDUTO)
            .dataTerapiaIntensiva(UPDATED_DATA_TERAPIA_INTENSIVA)
            .dataDimissione(UPDATED_DATA_DIMISSIONE)
            .giorniIsolamentoDomiciliare(UPDATED_GIORNI_ISOLAMENTO_DOMICILIARE)
            .giorniTerapiaIntensiva(UPDATED_GIORNI_TERAPIA_INTENSIVA)
            .giorniGuarito(UPDATED_GIORNI_GUARITO)
            .giorniDeceduto(UPDATED_GIORNI_DECEDUTO)
            .nota(UPDATED_NOTA);
        return paziente;
    }

    @BeforeEach
    public void initTest() {
        paziente = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaziente() throws Exception {
        int databaseSizeBeforeCreate = pazienteRepository.findAll().size();

        // Create the Paziente
        restPazienteMockMvc.perform(post("/api/pazientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paziente)))
            .andExpect(status().isCreated());

        // Validate the Paziente in the database
        List<Paziente> pazienteList = pazienteRepository.findAll();
        assertThat(pazienteList).hasSize(databaseSizeBeforeCreate + 1);
        Paziente testPaziente = pazienteList.get(pazienteList.size() - 1);
        assertThat(testPaziente.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testPaziente.getCf()).isEqualTo(DEFAULT_CF);
        assertThat(testPaziente.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testPaziente.getSex()).isEqualTo(DEFAULT_SEX);
        assertThat(testPaziente.getComune()).isEqualTo(DEFAULT_COMUNE);
        assertThat(testPaziente.getProvincia()).isEqualTo(DEFAULT_PROVINCIA);
        assertThat(testPaziente.getRegione()).isEqualTo(DEFAULT_REGIONE);
        assertThat(testPaziente.getLatitudine()).isEqualTo(DEFAULT_LATITUDINE);
        assertThat(testPaziente.getLongitudine()).isEqualTo(DEFAULT_LONGITUDINE);
        assertThat(testPaziente.getNumeroFamigliari()).isEqualTo(DEFAULT_NUMERO_FAMIGLIARI);
        assertThat(testPaziente.getProfessione()).isEqualTo(DEFAULT_PROFESSIONE);
        assertThat(testPaziente.isFumatore()).isEqualTo(DEFAULT_FUMATORE);
        assertThat(testPaziente.isPosivito()).isEqualTo(DEFAULT_POSIVITO);
        assertThat(testPaziente.isIsolamentoDomiciliare()).isEqualTo(DEFAULT_ISOLAMENTO_DOMICILIARE);
        assertThat(testPaziente.isTerapiaIntensiva()).isEqualTo(DEFAULT_TERAPIA_INTENSIVA);
        assertThat(testPaziente.isGuarito()).isEqualTo(DEFAULT_GUARITO);
        assertThat(testPaziente.isDeceduto()).isEqualTo(DEFAULT_DECEDUTO);
        assertThat(testPaziente.getDataTampone()).isEqualTo(DEFAULT_DATA_TAMPONE);
        assertThat(testPaziente.getDataGuarito()).isEqualTo(DEFAULT_DATA_GUARITO);
        assertThat(testPaziente.getDataIsolamentoDomiciliare()).isEqualTo(DEFAULT_DATA_ISOLAMENTO_DOMICILIARE);
        assertThat(testPaziente.getDataDeceduto()).isEqualTo(DEFAULT_DATA_DECEDUTO);
        assertThat(testPaziente.getDataTerapiaIntensiva()).isEqualTo(DEFAULT_DATA_TERAPIA_INTENSIVA);
        assertThat(testPaziente.getDataDimissione()).isEqualTo(DEFAULT_DATA_DIMISSIONE);
        assertThat(testPaziente.getGiorniIsolamentoDomiciliare()).isEqualTo(DEFAULT_GIORNI_ISOLAMENTO_DOMICILIARE);
        assertThat(testPaziente.getGiorniTerapiaIntensiva()).isEqualTo(DEFAULT_GIORNI_TERAPIA_INTENSIVA);
        assertThat(testPaziente.getGiorniGuarito()).isEqualTo(DEFAULT_GIORNI_GUARITO);
        assertThat(testPaziente.getGiorniDeceduto()).isEqualTo(DEFAULT_GIORNI_DECEDUTO);
        assertThat(testPaziente.getNota()).isEqualTo(DEFAULT_NOTA);
    }

    @Test
    @Transactional
    public void createPazienteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pazienteRepository.findAll().size();

        // Create the Paziente with an existing ID
        paziente.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPazienteMockMvc.perform(post("/api/pazientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paziente)))
            .andExpect(status().isBadRequest());

        // Validate the Paziente in the database
        List<Paziente> pazienteList = pazienteRepository.findAll();
        assertThat(pazienteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPazientes() throws Exception {
        // Initialize the database
        pazienteRepository.saveAndFlush(paziente);

        // Get all the pazienteList
        restPazienteMockMvc.perform(get("/api/pazientes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paziente.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].cf").value(hasItem(DEFAULT_CF)))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX.toString())))
            .andExpect(jsonPath("$.[*].comune").value(hasItem(DEFAULT_COMUNE)))
            .andExpect(jsonPath("$.[*].provincia").value(hasItem(DEFAULT_PROVINCIA)))
            .andExpect(jsonPath("$.[*].regione").value(hasItem(DEFAULT_REGIONE)))
            .andExpect(jsonPath("$.[*].latitudine").value(hasItem(DEFAULT_LATITUDINE)))
            .andExpect(jsonPath("$.[*].longitudine").value(hasItem(DEFAULT_LONGITUDINE)))
            .andExpect(jsonPath("$.[*].numeroFamigliari").value(hasItem(DEFAULT_NUMERO_FAMIGLIARI)))
            .andExpect(jsonPath("$.[*].professione").value(hasItem(DEFAULT_PROFESSIONE)))
            .andExpect(jsonPath("$.[*].fumatore").value(hasItem(DEFAULT_FUMATORE.booleanValue())))
            .andExpect(jsonPath("$.[*].posivito").value(hasItem(DEFAULT_POSIVITO.booleanValue())))
            .andExpect(jsonPath("$.[*].isolamentoDomiciliare").value(hasItem(DEFAULT_ISOLAMENTO_DOMICILIARE.booleanValue())))
            .andExpect(jsonPath("$.[*].terapiaIntensiva").value(hasItem(DEFAULT_TERAPIA_INTENSIVA.booleanValue())))
            .andExpect(jsonPath("$.[*].guarito").value(hasItem(DEFAULT_GUARITO.booleanValue())))
            .andExpect(jsonPath("$.[*].deceduto").value(hasItem(DEFAULT_DECEDUTO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataTampone").value(hasItem(sameInstant(DEFAULT_DATA_TAMPONE))))
            .andExpect(jsonPath("$.[*].dataGuarito").value(hasItem(sameInstant(DEFAULT_DATA_GUARITO))))
            .andExpect(jsonPath("$.[*].dataIsolamentoDomiciliare").value(hasItem(sameInstant(DEFAULT_DATA_ISOLAMENTO_DOMICILIARE))))
            .andExpect(jsonPath("$.[*].dataDeceduto").value(hasItem(sameInstant(DEFAULT_DATA_DECEDUTO))))
            .andExpect(jsonPath("$.[*].dataTerapiaIntensiva").value(hasItem(sameInstant(DEFAULT_DATA_TERAPIA_INTENSIVA))))
            .andExpect(jsonPath("$.[*].dataDimissione").value(hasItem(sameInstant(DEFAULT_DATA_DIMISSIONE))))
            .andExpect(jsonPath("$.[*].giorniIsolamentoDomiciliare").value(hasItem(DEFAULT_GIORNI_ISOLAMENTO_DOMICILIARE)))
            .andExpect(jsonPath("$.[*].giorniTerapiaIntensiva").value(hasItem(DEFAULT_GIORNI_TERAPIA_INTENSIVA)))
            .andExpect(jsonPath("$.[*].giorniGuarito").value(hasItem(DEFAULT_GIORNI_GUARITO)))
            .andExpect(jsonPath("$.[*].giorniDeceduto").value(hasItem(DEFAULT_GIORNI_DECEDUTO)))
            .andExpect(jsonPath("$.[*].nota").value(hasItem(DEFAULT_NOTA)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPazientesWithEagerRelationshipsIsEnabled() throws Exception {
        PazienteResource pazienteResource = new PazienteResource(pazienteRepositoryMock);
        when(pazienteRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPazienteMockMvc.perform(get("/api/pazientes?eagerload=true"))
            .andExpect(status().isOk());

        verify(pazienteRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPazientesWithEagerRelationshipsIsNotEnabled() throws Exception {
        PazienteResource pazienteResource = new PazienteResource(pazienteRepositoryMock);
        when(pazienteRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPazienteMockMvc.perform(get("/api/pazientes?eagerload=true"))
            .andExpect(status().isOk());

        verify(pazienteRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPaziente() throws Exception {
        // Initialize the database
        pazienteRepository.saveAndFlush(paziente);

        // Get the paziente
        restPazienteMockMvc.perform(get("/api/pazientes/{id}", paziente.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paziente.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.cf").value(DEFAULT_CF))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX.toString()))
            .andExpect(jsonPath("$.comune").value(DEFAULT_COMUNE))
            .andExpect(jsonPath("$.provincia").value(DEFAULT_PROVINCIA))
            .andExpect(jsonPath("$.regione").value(DEFAULT_REGIONE))
            .andExpect(jsonPath("$.latitudine").value(DEFAULT_LATITUDINE))
            .andExpect(jsonPath("$.longitudine").value(DEFAULT_LONGITUDINE))
            .andExpect(jsonPath("$.numeroFamigliari").value(DEFAULT_NUMERO_FAMIGLIARI))
            .andExpect(jsonPath("$.professione").value(DEFAULT_PROFESSIONE))
            .andExpect(jsonPath("$.fumatore").value(DEFAULT_FUMATORE.booleanValue()))
            .andExpect(jsonPath("$.posivito").value(DEFAULT_POSIVITO.booleanValue()))
            .andExpect(jsonPath("$.isolamentoDomiciliare").value(DEFAULT_ISOLAMENTO_DOMICILIARE.booleanValue()))
            .andExpect(jsonPath("$.terapiaIntensiva").value(DEFAULT_TERAPIA_INTENSIVA.booleanValue()))
            .andExpect(jsonPath("$.guarito").value(DEFAULT_GUARITO.booleanValue()))
            .andExpect(jsonPath("$.deceduto").value(DEFAULT_DECEDUTO.booleanValue()))
            .andExpect(jsonPath("$.dataTampone").value(sameInstant(DEFAULT_DATA_TAMPONE)))
            .andExpect(jsonPath("$.dataGuarito").value(sameInstant(DEFAULT_DATA_GUARITO)))
            .andExpect(jsonPath("$.dataIsolamentoDomiciliare").value(sameInstant(DEFAULT_DATA_ISOLAMENTO_DOMICILIARE)))
            .andExpect(jsonPath("$.dataDeceduto").value(sameInstant(DEFAULT_DATA_DECEDUTO)))
            .andExpect(jsonPath("$.dataTerapiaIntensiva").value(sameInstant(DEFAULT_DATA_TERAPIA_INTENSIVA)))
            .andExpect(jsonPath("$.dataDimissione").value(sameInstant(DEFAULT_DATA_DIMISSIONE)))
            .andExpect(jsonPath("$.giorniIsolamentoDomiciliare").value(DEFAULT_GIORNI_ISOLAMENTO_DOMICILIARE))
            .andExpect(jsonPath("$.giorniTerapiaIntensiva").value(DEFAULT_GIORNI_TERAPIA_INTENSIVA))
            .andExpect(jsonPath("$.giorniGuarito").value(DEFAULT_GIORNI_GUARITO))
            .andExpect(jsonPath("$.giorniDeceduto").value(DEFAULT_GIORNI_DECEDUTO))
            .andExpect(jsonPath("$.nota").value(DEFAULT_NOTA));
    }

    @Test
    @Transactional
    public void getNonExistingPaziente() throws Exception {
        // Get the paziente
        restPazienteMockMvc.perform(get("/api/pazientes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaziente() throws Exception {
        // Initialize the database
        pazienteRepository.saveAndFlush(paziente);

        int databaseSizeBeforeUpdate = pazienteRepository.findAll().size();

        // Update the paziente
        Paziente updatedPaziente = pazienteRepository.findById(paziente.getId()).get();
        // Disconnect from session so that the updates on updatedPaziente are not directly saved in db
        em.detach(updatedPaziente);
        updatedPaziente
            .nome(UPDATED_NOME)
            .cf(UPDATED_CF)
            .age(UPDATED_AGE)
            .sex(UPDATED_SEX)
            .comune(UPDATED_COMUNE)
            .provincia(UPDATED_PROVINCIA)
            .regione(UPDATED_REGIONE)
            .latitudine(UPDATED_LATITUDINE)
            .longitudine(UPDATED_LONGITUDINE)
            .numeroFamigliari(UPDATED_NUMERO_FAMIGLIARI)
            .professione(UPDATED_PROFESSIONE)
            .fumatore(UPDATED_FUMATORE)
            .posivito(UPDATED_POSIVITO)
            .isolamentoDomiciliare(UPDATED_ISOLAMENTO_DOMICILIARE)
            .terapiaIntensiva(UPDATED_TERAPIA_INTENSIVA)
            .guarito(UPDATED_GUARITO)
            .deceduto(UPDATED_DECEDUTO)
            .dataTampone(UPDATED_DATA_TAMPONE)
            .dataGuarito(UPDATED_DATA_GUARITO)
            .dataIsolamentoDomiciliare(UPDATED_DATA_ISOLAMENTO_DOMICILIARE)
            .dataDeceduto(UPDATED_DATA_DECEDUTO)
            .dataTerapiaIntensiva(UPDATED_DATA_TERAPIA_INTENSIVA)
            .dataDimissione(UPDATED_DATA_DIMISSIONE)
            .giorniIsolamentoDomiciliare(UPDATED_GIORNI_ISOLAMENTO_DOMICILIARE)
            .giorniTerapiaIntensiva(UPDATED_GIORNI_TERAPIA_INTENSIVA)
            .giorniGuarito(UPDATED_GIORNI_GUARITO)
            .giorniDeceduto(UPDATED_GIORNI_DECEDUTO)
            .nota(UPDATED_NOTA);

        restPazienteMockMvc.perform(put("/api/pazientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaziente)))
            .andExpect(status().isOk());

        // Validate the Paziente in the database
        List<Paziente> pazienteList = pazienteRepository.findAll();
        assertThat(pazienteList).hasSize(databaseSizeBeforeUpdate);
        Paziente testPaziente = pazienteList.get(pazienteList.size() - 1);
        assertThat(testPaziente.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testPaziente.getCf()).isEqualTo(UPDATED_CF);
        assertThat(testPaziente.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testPaziente.getSex()).isEqualTo(UPDATED_SEX);
        assertThat(testPaziente.getComune()).isEqualTo(UPDATED_COMUNE);
        assertThat(testPaziente.getProvincia()).isEqualTo(UPDATED_PROVINCIA);
        assertThat(testPaziente.getRegione()).isEqualTo(UPDATED_REGIONE);
        assertThat(testPaziente.getLatitudine()).isEqualTo(UPDATED_LATITUDINE);
        assertThat(testPaziente.getLongitudine()).isEqualTo(UPDATED_LONGITUDINE);
        assertThat(testPaziente.getNumeroFamigliari()).isEqualTo(UPDATED_NUMERO_FAMIGLIARI);
        assertThat(testPaziente.getProfessione()).isEqualTo(UPDATED_PROFESSIONE);
        assertThat(testPaziente.isFumatore()).isEqualTo(UPDATED_FUMATORE);
        assertThat(testPaziente.isPosivito()).isEqualTo(UPDATED_POSIVITO);
        assertThat(testPaziente.isIsolamentoDomiciliare()).isEqualTo(UPDATED_ISOLAMENTO_DOMICILIARE);
        assertThat(testPaziente.isTerapiaIntensiva()).isEqualTo(UPDATED_TERAPIA_INTENSIVA);
        assertThat(testPaziente.isGuarito()).isEqualTo(UPDATED_GUARITO);
        assertThat(testPaziente.isDeceduto()).isEqualTo(UPDATED_DECEDUTO);
        assertThat(testPaziente.getDataTampone()).isEqualTo(UPDATED_DATA_TAMPONE);
        assertThat(testPaziente.getDataGuarito()).isEqualTo(UPDATED_DATA_GUARITO);
        assertThat(testPaziente.getDataIsolamentoDomiciliare()).isEqualTo(UPDATED_DATA_ISOLAMENTO_DOMICILIARE);
        assertThat(testPaziente.getDataDeceduto()).isEqualTo(UPDATED_DATA_DECEDUTO);
        assertThat(testPaziente.getDataTerapiaIntensiva()).isEqualTo(UPDATED_DATA_TERAPIA_INTENSIVA);
        assertThat(testPaziente.getDataDimissione()).isEqualTo(UPDATED_DATA_DIMISSIONE);
        assertThat(testPaziente.getGiorniIsolamentoDomiciliare()).isEqualTo(UPDATED_GIORNI_ISOLAMENTO_DOMICILIARE);
        assertThat(testPaziente.getGiorniTerapiaIntensiva()).isEqualTo(UPDATED_GIORNI_TERAPIA_INTENSIVA);
        assertThat(testPaziente.getGiorniGuarito()).isEqualTo(UPDATED_GIORNI_GUARITO);
        assertThat(testPaziente.getGiorniDeceduto()).isEqualTo(UPDATED_GIORNI_DECEDUTO);
        assertThat(testPaziente.getNota()).isEqualTo(UPDATED_NOTA);
    }

    @Test
    @Transactional
    public void updateNonExistingPaziente() throws Exception {
        int databaseSizeBeforeUpdate = pazienteRepository.findAll().size();

        // Create the Paziente

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPazienteMockMvc.perform(put("/api/pazientes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paziente)))
            .andExpect(status().isBadRequest());

        // Validate the Paziente in the database
        List<Paziente> pazienteList = pazienteRepository.findAll();
        assertThat(pazienteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaziente() throws Exception {
        // Initialize the database
        pazienteRepository.saveAndFlush(paziente);

        int databaseSizeBeforeDelete = pazienteRepository.findAll().size();

        // Delete the paziente
        restPazienteMockMvc.perform(delete("/api/pazientes/{id}", paziente.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Paziente> pazienteList = pazienteRepository.findAll();
        assertThat(pazienteList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
