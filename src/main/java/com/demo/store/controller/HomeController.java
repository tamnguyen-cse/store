package com.demo.store.controller;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {

    @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String showWelcomeMessage() {
        return "<html><body><h1>Demo Store API Server is running!</h1></body></html>";
    }

    @GetMapping(value = "/get")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Object> getData() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Demo User");
        map.put("email", "test@sample.com");
        map.put("phone", "1234567890");
        return map;
    }

}
