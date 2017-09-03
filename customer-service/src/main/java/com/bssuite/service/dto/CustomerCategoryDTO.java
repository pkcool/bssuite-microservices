package com.bssuite.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CustomerCategory entity.
 */
public class CustomerCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String code;

    private String name;

    private String tradingName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTradingName() {
        return tradingName;
    }

    public void setTradingName(String tradingName) {
        this.tradingName = tradingName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CustomerCategoryDTO customerCategoryDTO = (CustomerCategoryDTO) o;
        if(customerCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), customerCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CustomerCategoryDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", tradingName='" + getTradingName() + "'" +
            "}";
    }
}
