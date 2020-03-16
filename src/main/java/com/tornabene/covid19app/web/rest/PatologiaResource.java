package com.tornabene.covid19app.web.rest;

import com.tornabene.covid19app.domain.Patologia;
import com.tornabene.covid19app.repository.PatologiaRepository;
import com.tornabene.covid19app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tornabene.covid19app.domain.Patologia}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PatologiaResource {

    private final Logger log = LoggerFactory.getLogger(PatologiaResource.class);

    private static final String ENTITY_NAME = "patologia";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatologiaRepository patologiaRepository;

    public PatologiaResource(PatologiaRepository patologiaRepository) {
        this.patologiaRepository = patologiaRepository;
    }

    /**
     * {@code POST  /patologias} : Create a new patologia.
     *
     * @param patologia the patologia to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patologia, or with status {@code 400 (Bad Request)} if the patologia has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patologias")
    public ResponseEntity<Patologia> createPatologia(@RequestBody Patologia patologia) throws URISyntaxException {
        log.debug("REST request to save Patologia : {}", patologia);
        if (patologia.getId() != null) {
            throw new BadRequestAlertException("A new patologia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Patologia result = patologiaRepository.save(patologia);
        return ResponseEntity.created(new URI("/api/patologias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patologias} : Updates an existing patologia.
     *
     * @param patologia the patologia to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patologia,
     * or with status {@code 400 (Bad Request)} if the patologia is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patologia couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patologias")
    public ResponseEntity<Patologia> updatePatologia(@RequestBody Patologia patologia) throws URISyntaxException {
        log.debug("REST request to update Patologia : {}", patologia);
        if (patologia.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Patologia result = patologiaRepository.save(patologia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, patologia.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patologias} : get all the patologias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patologias in body.
     */
    @GetMapping("/patologias")
    public List<Patologia> getAllPatologias() {
        log.debug("REST request to get all Patologias");
        return patologiaRepository.findAll();
    }

    /**
     * {@code GET  /patologias/:id} : get the "id" patologia.
     *
     * @param id the id of the patologia to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patologia, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patologias/{id}")
    public ResponseEntity<Patologia> getPatologia(@PathVariable Long id) {
        log.debug("REST request to get Patologia : {}", id);
        Optional<Patologia> patologia = patologiaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(patologia);
    }

    /**
     * {@code DELETE  /patologias/:id} : delete the "id" patologia.
     *
     * @param id the id of the patologia to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patologias/{id}")
    public ResponseEntity<Void> deletePatologia(@PathVariable Long id) {
        log.debug("REST request to delete Patologia : {}", id);
        patologiaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
