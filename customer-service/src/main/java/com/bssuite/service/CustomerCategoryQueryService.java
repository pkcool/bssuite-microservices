package com.bssuite.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.bssuite.domain.CustomerCategory;
import com.bssuite.domain.*; // for static metamodels
import com.bssuite.repository.CustomerCategoryRepository;
import com.bssuite.service.dto.CustomerCategoryCriteria;

import com.bssuite.service.dto.CustomerCategoryDTO;
import com.bssuite.service.mapper.CustomerCategoryMapper;

/**
 * Service for executing complex queries for CustomerCategory entities in the database.
 * The main input is a {@link CustomerCategoryCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerCategoryDTO} or a {@link Page} of {%link CustomerCategoryDTO} which fullfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerCategoryQueryService extends QueryService<CustomerCategory> {

    private final Logger log = LoggerFactory.getLogger(CustomerCategoryQueryService.class);


    private final CustomerCategoryRepository customerCategoryRepository;

    private final CustomerCategoryMapper customerCategoryMapper;
    public CustomerCategoryQueryService(CustomerCategoryRepository customerCategoryRepository, CustomerCategoryMapper customerCategoryMapper) {
        this.customerCategoryRepository = customerCategoryRepository;
        this.customerCategoryMapper = customerCategoryMapper;
    }

    /**
     * Return a {@link List} of {%link CustomerCategoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerCategoryDTO> findByCriteria(CustomerCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<CustomerCategory> specification = createSpecification(criteria);
        return customerCategoryMapper.toDto(customerCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {%link CustomerCategoryDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerCategoryDTO> findByCriteria(CustomerCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<CustomerCategory> specification = createSpecification(criteria);
        final Page<CustomerCategory> result = customerCategoryRepository.findAll(specification, page);
        return result.map(customerCategoryMapper::toDto);
    }

    /**
     * Function to convert CustomerCategoryCriteria to a {@link Specifications}
     */
    private Specifications<CustomerCategory> createSpecification(CustomerCategoryCriteria criteria) {
        Specifications<CustomerCategory> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CustomerCategory_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), CustomerCategory_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), CustomerCategory_.name));
            }
            if (criteria.getTradingName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTradingName(), CustomerCategory_.tradingName));
            }
        }
        return specification;
    }

}
