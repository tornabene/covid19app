package com.tornabene.covid19app.web.rest;

import com.tornabene.covid19app.domain.Paziente;
import com.tornabene.covid19app.repository.PazienteRepository;
import com.tornabene.covid19app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tornabene.covid19app.domain.Paziente}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PazienteResource {

    private final Logger log = LoggerFactory.getLogger(PazienteResource.class);

    private static final String ENTITY_NAME = "paziente";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PazienteRepository pazienteRepository;

    public PazienteResource(PazienteRepository pazienteRepository) {
        this.pazienteRepository = pazienteRepository;
    }

    /**
     * {@code POST  /pazientes} : Create a new paziente.
     *
     * @param paziente the paziente to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paziente, or with status {@code 400 (Bad Request)} if the paziente has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pazientes")
    public ResponseEntity<Paziente> createPaziente(@RequestBody Paziente paziente) throws URISyntaxException {
        log.debug("REST request to save Paziente : {}", paziente);
        if (paziente.getId() != null) {
            throw new BadRequestAlertException("A new paziente cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Paziente result = pazienteRepository.save(paziente);
        return ResponseEntity.created(new URI("/api/pazientes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pazientes} : Updates an existing paziente.
     *
     * @param paziente the paziente to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paziente,
     * or with status {@code 400 (Bad Request)} if the paziente is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paziente couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pazientes")
    public ResponseEntity<Paziente> updatePaziente(@RequestBody Paziente paziente) throws URISyntaxException {
        log.debug("REST request to update Paziente : {}", paziente);
        if (paziente.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Paziente result = pazienteRepository.save(paziente);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paziente.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pazientes} : get all the pazientes.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pazientes in body.
     */
    @GetMapping("/pazientes")
    public ResponseEntity<List<Paziente>> getAllPazientes(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Pazientes");
        Page<Paziente> page;
        if (eagerload) {
            page = pazienteRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = pazienteRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /pazientes/:id} : get the "id" paziente.
     *
     * @param id the id of the paziente to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paziente, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pazientes/{id}")
    public ResponseEntity<Paziente> getPaziente(@PathVariable Long id) {
        log.debug("REST request to get Paziente : {}", id);
        Optional<Paziente> paziente = pazienteRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(paziente);
    }

    /**
     * {@code DELETE  /pazientes/:id} : delete the "id" paziente.
     *
     * @param id the id of the paziente to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pazientes/{id}")
    public ResponseEntity<Void> deletePaziente(@PathVariable Long id) {
        log.debug("REST request to delete Paziente : {}", id);
        pazienteRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
