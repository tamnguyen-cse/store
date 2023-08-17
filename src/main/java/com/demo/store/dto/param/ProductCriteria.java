package com.demo.store.dto.param;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductCriteria {

    @Min(0)
    private Integer page;

    @Min(1)
    private Integer size;

    private String search;

}
