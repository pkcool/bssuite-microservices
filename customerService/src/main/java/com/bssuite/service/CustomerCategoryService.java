package com.bssuite.service;

import com.bssuite.domain.CustomerCategory;
import com.bssuite.repository.CustomerCategoryRepository;
import com.bssuite.service.dto.CustomerCategoryDTO;
import com.bssuite.service.mapper.CustomerCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing CustomerCategory.
 */
@Service
@Transactional
public class CustomerCategoryService {

    private final Logger log = LoggerFactory.getLogger(CustomerCategoryService.class);
    
    private final CustomerCategoryRepository customerCategoryRepository;

    private final CustomerCategoryMapper customerCategoryMapper;

    public CustomerCategoryService(CustomerCategoryRepository customerCategoryRepository, CustomerCategoryMapper customerCategoryMapper) {
        this.customerCategoryRepository = customerCategoryRepository;
        this.customerCategoryMapper = customerCategoryMapper;
    }

    /**
     * Save a customerCategory.
     *
     * @param customerCategoryDTO the entity to save
     * @return the persisted entity
     */
    public CustomerCategoryDTO save(CustomerCategoryDTO customerCategoryDTO) {
        log.debug("Request to save CustomerCategory : {}", customerCategoryDTO);
        CustomerCategory customerCategory = customerCategoryMapper.customerCategoryDTOToCustomerCategory(customerCategoryDTO);
        customerCategory = customerCategoryRepository.save(customerCategory);
        CustomerCategoryDTO result = customerCategoryMapper.customerCategoryToCustomerCategoryDTO(customerCategory);
        return result;
    }

    /**
     *  Get all the customerCategories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CustomerCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CustomerCategories");
        Page<CustomerCategory> result = customerCategoryRepository.findAll(pageable);
        return result.map(customerCategory -> customerCategoryMapper.customerCategoryToCustomerCategoryDTO(customerCategory));
    }

    /**
     *  Get one customerCategory by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CustomerCategoryDTO findOne(Long id) {
        log.debug("Request to get CustomerCategory : {}", id);
        CustomerCategory customerCategory = customerCategoryRepository.findOne(id);
        CustomerCategoryDTO customerCategoryDTO = customerCategoryMapper.customerCategoryToCustomerCategoryDTO(customerCategory);
        return customerCategoryDTO;
    }

    /**
     *  Delete the  customerCategory by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CustomerCategory : {}", id);
        customerCategoryRepository.delete(id);
    }
}
