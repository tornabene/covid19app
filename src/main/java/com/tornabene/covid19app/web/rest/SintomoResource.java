package com.tornabene.covid19app.web.rest;

import com.tornabene.covid19app.domain.Sintomo;
import com.tornabene.covid19app.repository.SintomoRepository;
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
 * REST controller for managing {@link com.tornabene.covid19app.domain.Sintomo}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class SintomoResource {

    private final Logger log = LoggerFactory.getLogger(SintomoResource.class);

    private static final String ENTITY_NAME = "sintomo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SintomoRepository sintomoRepository;

    public SintomoResource(SintomoRepository sintomoRepository) {
        this.sintomoRepository = sintomoRepository;
    }

    /**
     * {@code POST  /sintomos} : Create a new sintomo.
     *
     * @param sintomo the sintomo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sintomo, or with status {@code 400 (Bad Request)} if the sintomo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sintomos")
    public ResponseEntity<Sintomo> createSintomo(@RequestBody Sintomo sintomo) throws URISyntaxException {
        log.debug("REST request to save Sintomo : {}", sintomo);
        if (sintomo.getId() != null) {
            throw new BadRequestAlertException("A new sintomo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Sintomo result = sintomoRepository.save(sintomo);
        return ResponseEntity.created(new URI("/api/sintomos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sintomos} : Updates an existing sintomo.
     *
     * @param sintomo the sintomo to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sintomo,
     * or with status {@code 400 (Bad Request)} if the sintomo is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sintomo couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sintomos")
    public ResponseEntity<Sintomo> updateSintomo(@RequestBody Sintomo sintomo) throws URISyntaxException {
        log.debug("REST request to update Sintomo : {}", sintomo);
        if (sintomo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Sintomo result = sintomoRepository.save(sintomo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sintomo.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sintomos} : get all the sintomos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sintomos in body.
     */
    @GetMapping("/sintomos")
    public List<Sintomo> getAllSintomos() {
        log.debug("REST request to get all Sintomos");
        return sintomoRepository.findAll();
    }

    /**
     * {@code GET  /sintomos/:id} : get the "id" sintomo.
     *
     * @param id the id of the sintomo to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sintomo, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sintomos/{id}")
    public ResponseEntity<Sintomo> getSintomo(@PathVariable Long id) {
        log.debug("REST request to get Sintomo : {}", id);
        Optional<Sintomo> sintomo = sintomoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sintomo);
    }

    /**
     * {@code DELETE  /sintomos/:id} : delete the "id" sintomo.
     *
     * @param id the id of the sintomo to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sintomos/{id}")
    public ResponseEntity<Void> deleteSintomo(@PathVariable Long id) {
        log.debug("REST request to delete Sintomo : {}", id);
        sintomoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
