package com.demo.store.service.impl;

import com.demo.store.dto.ProductDTO;
import com.demo.store.dto.param.ProductCriteria;
import com.demo.store.entity.Product;
import com.demo.store.entity.Store;
import com.demo.store.model.ContentPage;
import com.demo.store.repository.ProductRepository;
import com.demo.store.service.CommonService;
import com.demo.store.service.ProductService;
import com.demo.store.service.StoreService;
import com.demo.store.utils.ObjectUtils;
import com.demo.store.utils.PageableUtils;
import com.demo.store.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private CommonService commonService;
    @Autowired
    private StoreService storeService;

    @Override
    public ProductDTO create(ProductDTO product) {
        Store store = storeService.checkById(product.getStoreId());
        Product dbProduct = ObjectUtils.parse(product, Product.class);
        dbProduct.setStore(store);
        dbProduct.setSlug(StringUtils.parseNameToSlug(product.getName()));
        return commonService.save(Product.class, ProductDTO.class, repository, dbProduct);
    }

    @Override
    public ProductDTO update(Long id, ProductDTO product) {
        Store store = storeService.checkById(product.getStoreId());
        Product dbProduct = ObjectUtils.parse(product, Product.class);
        dbProduct.setStore(store);
        dbProduct.setSlug(StringUtils.parseNameToSlug(product.getName()));
        return commonService.overrideUpdate(Product.class, ProductDTO.class, repository, dbProduct,
            id);
    }

    @Override
    public ProductDTO getById(Long id) {
        return commonService.getById(Product.class, ProductDTO.class, repository, id);
    }

    @Override
    public ContentPage<ProductDTO> query(ProductCriteria criteria) {
        Pageable pageable = PageableUtils.createWithoutSort(criteria.getPage(), criteria.getSize());
        return PageableUtils.toContentPage(
            repository.findByNameContains(criteria.getSearch(), pageable), ProductDTO.class);
    }

}
