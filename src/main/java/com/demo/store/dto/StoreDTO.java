package com.demo.store.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StoreDTO {

    private Long id;

    @NotNull
    private String name;

    private String slug;

    private String logo;

    private String banner;

    private String introduction;

    private Boolean isActive;

    @Valid
    private OwnerDTO owner;

    private List<ProductDTO> products;

}
