package com.demo.store.service;

import com.demo.store.dto.ProductDTO;
import com.demo.store.dto.param.ProductCriteria;
import com.demo.store.model.ContentPage;

public interface ProductService {

    /**
     * Register new product
     *
     * @param product the product
     * @return the product
     */
    ProductDTO create(ProductDTO product);

    /**
     * Update product by id
     *
     * @param id      the tag id
     * @param product the product
     * @return the product
     */
    ProductDTO update(Long id, ProductDTO product);

    /**
     * Get product by id
     *
     * @return the product
     */
    ProductDTO getById(Long id);

    /**
     * Query product by criteria
     *
     * @return the list of product
     */
    ContentPage<ProductDTO> query(ProductCriteria criteria);

}
