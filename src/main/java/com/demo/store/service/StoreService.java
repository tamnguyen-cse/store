package com.demo.store.service;

import com.demo.store.dto.StoreDTO;
import com.demo.store.entity.Store;

public interface StoreService {

    /**
     * Register new store
     *
     * @param store the store
     * @return the store
     */
    StoreDTO create(StoreDTO store);

    /**
     * Update store by id
     *
     * @param id    the tag id
     * @param store the store
     * @return the store
     */
    StoreDTO update(Long id, StoreDTO store);

    /**
     * Get store by id
     *
     * @return the store
     */
    StoreDTO getById(Long id);

    /**
     * Check store in DB by id
     *
     * @return the store
     */
    Store checkById(Long id);

}
