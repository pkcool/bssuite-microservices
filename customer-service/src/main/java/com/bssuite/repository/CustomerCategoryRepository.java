package com.bssuite.repository;

import com.bssuite.domain.CustomerCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CustomerCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory, Long>, JpaSpecificationExecutor<CustomerCategory> {

}
