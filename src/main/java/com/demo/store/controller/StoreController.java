package com.demo.store.controller;

import com.demo.store.dto.StoreDTO;
import com.demo.store.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/store")
public class StoreController {

    @Autowired
    private StoreService service;

    @PostMapping
    public StoreDTO create(@RequestBody @Valid StoreDTO store) {
        return service.create(store);
    }

    @PutMapping("/{id}")
    public StoreDTO update(@PathVariable Long id, @RequestBody @Valid StoreDTO store) {
        return service.update(id, store);
    }

    @GetMapping("/{id}")
    public StoreDTO getById(@PathVariable Long id, @RequestParam("X-User-Id") String userId,
        @RequestPart("file") String file) {
        return service.getById(id);
    }

}
