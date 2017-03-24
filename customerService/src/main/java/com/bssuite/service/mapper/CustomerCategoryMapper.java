package com.bssuite.service.mapper;

import com.bssuite.domain.*;
import com.bssuite.service.dto.CustomerCategoryDTO;

import org.mapstruct.*;
import java.util.List;

/**
 * Mapper for the entity CustomerCategory and its DTO CustomerCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CustomerCategoryMapper {

    CustomerCategoryDTO customerCategoryToCustomerCategoryDTO(CustomerCategory customerCategory);

    List<CustomerCategoryDTO> customerCategoriesToCustomerCategoryDTOs(List<CustomerCategory> customerCategories);

    CustomerCategory customerCategoryDTOToCustomerCategory(CustomerCategoryDTO customerCategoryDTO);

    List<CustomerCategory> customerCategoryDTOsToCustomerCategories(List<CustomerCategoryDTO> customerCategoryDTOs);
    /**
     * generating the fromId for all mappers if the databaseType is sql, as the class has relationship to it might need it, instead of
     * creating a new attribute to know if the entity has any relationship from some other entity
     *
     * @param id id of the entity
     * @return the entity instance
     */
     
    default CustomerCategory customerCategoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        CustomerCategory customerCategory = new CustomerCategory();
        customerCategory.setId(id);
        return customerCategory;
    }
    

}
