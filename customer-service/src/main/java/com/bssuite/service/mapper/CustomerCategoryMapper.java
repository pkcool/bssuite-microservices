package com.bssuite.service.mapper;

import com.bssuite.domain.*;
import com.bssuite.service.dto.CustomerCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CustomerCategory and its DTO CustomerCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerCategoryMapper extends EntityMapper <CustomerCategoryDTO, CustomerCategory> {
    
    
    default CustomerCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerCategory customerCategory = new CustomerCategory();
        customerCategory.setId(id);
        return customerCategory;
    }
}
