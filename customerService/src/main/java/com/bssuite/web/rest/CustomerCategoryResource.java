package com.bssuite.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.bssuite.service.CustomerCategoryService;
import com.bssuite.web.rest.util.HeaderUtil;
import com.bssuite.web.rest.util.PaginationUtil;
import com.bssuite.service.dto.CustomerCategoryDTO;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing CustomerCategory.
 */
@RestController
@RequestMapping("/api")
public class CustomerCategoryResource {

    private final Logger log = LoggerFactory.getLogger(CustomerCategoryResource.class);

    private static final String ENTITY_NAME = "customerCategory";
        
    private final CustomerCategoryService customerCategoryService;

    public CustomerCategoryResource(CustomerCategoryService customerCategoryService) {
        this.customerCategoryService = customerCategoryService;
    }

    /**
     * POST  /customer-categories : Create a new customerCategory.
     *
     * @param customerCategoryDTO the customerCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new customerCategoryDTO, or with status 400 (Bad Request) if the customerCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/customer-categories")
    @Timed
    public ResponseEntity<CustomerCategoryDTO> createCustomerCategory(@Valid @RequestBody CustomerCategoryDTO customerCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save CustomerCategory : {}", customerCategoryDTO);
        if (customerCategoryDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new customerCategory cannot already have an ID")).body(null);
        }
        CustomerCategoryDTO result = customerCategoryService.save(customerCategoryDTO);
        return ResponseEntity.created(new URI("/api/customer-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /customer-categories : Updates an existing customerCategory.
     *
     * @param customerCategoryDTO the customerCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated customerCategoryDTO,
     * or with status 400 (Bad Request) if the customerCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the customerCategoryDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/customer-categories")
    @Timed
    public ResponseEntity<CustomerCategoryDTO> updateCustomerCategory(@Valid @RequestBody CustomerCategoryDTO customerCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update CustomerCategory : {}", customerCategoryDTO);
        if (customerCategoryDTO.getId() == null) {
            return createCustomerCategory(customerCategoryDTO);
        }
        CustomerCategoryDTO result = customerCategoryService.save(customerCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, customerCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /customer-categories : get all the customerCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of customerCategories in body
     */
    @GetMapping("/customer-categories")
    @Timed
    public ResponseEntity<List<CustomerCategoryDTO>> getAllCustomerCategories(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CustomerCategories");
        Page<CustomerCategoryDTO> page = customerCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/customer-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /customer-categories/:id : get the "id" customerCategory.
     *
     * @param id the id of the customerCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the customerCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/customer-categories/{id}")
    @Timed
    public ResponseEntity<CustomerCategoryDTO> getCustomerCategory(@PathVariable Long id) {
        log.debug("REST request to get CustomerCategory : {}", id);
        CustomerCategoryDTO customerCategoryDTO = customerCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(customerCategoryDTO));
    }

    /**
     * DELETE  /customer-categories/:id : delete the "id" customerCategory.
     *
     * @param id the id of the customerCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/customer-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteCustomerCategory(@PathVariable Long id) {
        log.debug("REST request to delete CustomerCategory : {}", id);
        customerCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
