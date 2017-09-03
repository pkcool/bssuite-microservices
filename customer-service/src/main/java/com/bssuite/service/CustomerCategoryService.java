package com.bssuite.service;

import com.bssuite.service.dto.CustomerCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CustomerCategory.
 */
public interface CustomerCategoryService {

    /**
     * Save a customerCategory.
     *
     * @param customerCategoryDTO the entity to save
     * @return the persisted entity
     */
    CustomerCategoryDTO save(CustomerCategoryDTO customerCategoryDTO);

    /**
     *  Get all the customerCategories.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CustomerCategoryDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" customerCategory.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CustomerCategoryDTO findOne(Long id);

    /**
     *  Delete the "id" customerCategory.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
