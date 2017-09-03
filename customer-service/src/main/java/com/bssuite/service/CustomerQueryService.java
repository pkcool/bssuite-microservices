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

import com.bssuite.domain.Customer;
import com.bssuite.domain.*; // for static metamodels
import com.bssuite.repository.CustomerRepository;
import com.bssuite.service.dto.CustomerCriteria;

import com.bssuite.service.dto.CustomerDTO;
import com.bssuite.service.mapper.CustomerMapper;

/**
 * Service for executing complex queries for Customer entities in the database.
 * The main input is a {@link CustomerCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {%link CustomerDTO} or a {@link Page} of {%link CustomerDTO} which fullfills the criterias
 */
@Service
@Transactional(readOnly = true)
public class CustomerQueryService extends QueryService<Customer> {

    private final Logger log = LoggerFactory.getLogger(CustomerQueryService.class);


    private final CustomerRepository customerRepository;

    private final CustomerMapper customerMapper;
    public CustomerQueryService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    /**
     * Return a {@link List} of {%link CustomerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CustomerDTO> findByCriteria(CustomerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Customer> specification = createSpecification(criteria);
        return customerMapper.toDto(customerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {%link CustomerDTO} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomerDTO> findByCriteria(CustomerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Customer> specification = createSpecification(criteria);
        final Page<Customer> result = customerRepository.findAll(specification, page);
        return result.map(customerMapper::toDto);
    }

    /**
     * Function to convert CustomerCriteria to a {@link Specifications}
     */
    private Specifications<Customer> createSpecification(CustomerCriteria criteria) {
        Specifications<Customer> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Customer_.id));
            }
            if (criteria.getCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCode(), Customer_.code));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Customer_.name));
            }
            if (criteria.getTradingName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTradingName(), Customer_.tradingName));
            }
            if (criteria.getAbn() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbn(), Customer_.abn));
            }
        }
        return specification;
    }

}
