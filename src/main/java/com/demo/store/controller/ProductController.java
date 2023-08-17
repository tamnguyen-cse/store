package com.demo.store.controller;

import com.demo.store.dto.ProductDTO;
import com.demo.store.dto.param.ProductCriteria;
import com.demo.store.model.ContentPage;
import com.demo.store.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ProductDTO create(@RequestBody @Valid ProductDTO product) {
        return service.create(product);
    }

    @PutMapping("/{id}")
    public ProductDTO update(@PathVariable Long id, @RequestBody @Valid ProductDTO product) {
        return service.update(id, product);
    }

    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public ContentPage<ProductDTO> query(@Valid ProductCriteria criteria) {
        return service.query(criteria);
    }

}
