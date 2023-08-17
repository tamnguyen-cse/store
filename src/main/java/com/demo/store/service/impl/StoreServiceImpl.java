package com.demo.store.service.impl;

import com.demo.store.dto.StoreDTO;
import com.demo.store.entity.Store;
import com.demo.store.repository.StoreRepository;
import com.demo.store.service.CommonService;
import com.demo.store.service.StoreService;
import com.demo.store.utils.ObjectUtils;
import com.demo.store.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository repository;

    @Autowired
    private CommonService commonService;

    @Override
    public StoreDTO create(StoreDTO store) {
        Store dbStore = ObjectUtils.parse(store, Store.class);
        this.composeData(dbStore);
        return commonService.save(Store.class, StoreDTO.class, repository, dbStore);
    }

    @Override
    public StoreDTO update(Long id, StoreDTO store) {
        Store dbStore = ObjectUtils.parse(store, Store.class);
        dbStore.setId(id);
        this.composeData(dbStore);
        return commonService.overrideUpdate(Store.class, StoreDTO.class, repository, store, id);
    }

    @Override
    public StoreDTO getById(Long id) {
        return commonService.getById(Store.class, StoreDTO.class, repository, id);
    }

    @Override
    public Store checkById(Long id) {
        return commonService.checkById(Store.class, repository, id);
    }

    private void composeData(Store store) {
        if (store.getOwner() != null) {
            store.getOwner().setStore(store);
        }
        store.setSlug(StringUtils.parseNameToSlug(store.getName()));
    }

}
