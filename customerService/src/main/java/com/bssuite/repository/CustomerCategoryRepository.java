package com.bssuite.repository;

import com.bssuite.domain.CustomerCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CustomerCategory entity.
 */
@SuppressWarnings("unused")
public interface CustomerCategoryRepository extends JpaRepository<CustomerCategory,Long> {

}
