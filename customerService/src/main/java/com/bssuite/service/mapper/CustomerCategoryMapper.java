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
}
