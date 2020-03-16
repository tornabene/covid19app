package com.tornabene.covid19app.web.rest;

import com.tornabene.covid19app.domain.Farmaco;
import com.tornabene.covid19app.repository.FarmacoRepository;
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
 * REST controller for managing {@link com.tornabene.covid19app.domain.Farmaco}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FarmacoResource {

    private final Logger log = LoggerFactory.getLogger(FarmacoResource.class);

    private static final String ENTITY_NAME = "farmaco";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FarmacoRepository farmacoRepository;

    public FarmacoResource(FarmacoRepository farmacoRepository) {
        this.farmacoRepository = farmacoRepository;
    }

    /**
     * {@code POST  /farmacos} : Create a new farmaco.
     *
     * @param farmaco the farmaco to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new farmaco, or with status {@code 400 (Bad Request)} if the farmaco has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/farmacos")
    public ResponseEntity<Farmaco> createFarmaco(@RequestBody Farmaco farmaco) throws URISyntaxException {
        log.debug("REST request to save Farmaco : {}", farmaco);
        if (farmaco.getId() != null) {
            throw new BadRequestAlertException("A new farmaco cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Farmaco result = farmacoRepository.save(farmaco);
        return ResponseEntity.created(new URI("/api/farmacos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /farmacos} : Updates an existing farmaco.
     *
     * @param farmaco the farmaco to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated farmaco,
     * or with status {@code 400 (Bad Request)} if the farmaco is not valid,
     * or with status {@code 500 (Internal Server Error)} if the farmaco couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/farmacos")
    public ResponseEntity<Farmaco> updateFarmaco(@RequestBody Farmaco farmaco) throws URISyntaxException {
        log.debug("REST request to update Farmaco : {}", farmaco);
        if (farmaco.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Farmaco result = farmacoRepository.save(farmaco);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, farmaco.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /farmacos} : get all the farmacos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of farmacos in body.
     */
    @GetMapping("/farmacos")
    public List<Farmaco> getAllFarmacos() {
        log.debug("REST request to get all Farmacos");
        return farmacoRepository.findAll();
    }

    /**
     * {@code GET  /farmacos/:id} : get the "id" farmaco.
     *
     * @param id the id of the farmaco to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the farmaco, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/farmacos/{id}")
    public ResponseEntity<Farmaco> getFarmaco(@PathVariable Long id) {
        log.debug("REST request to get Farmaco : {}", id);
        Optional<Farmaco> farmaco = farmacoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(farmaco);
    }

    /**
     * {@code DELETE  /farmacos/:id} : delete the "id" farmaco.
     *
     * @param id the id of the farmaco to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/farmacos/{id}")
    public ResponseEntity<Void> deleteFarmaco(@PathVariable Long id) {
        log.debug("REST request to delete Farmaco : {}", id);
        farmacoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
