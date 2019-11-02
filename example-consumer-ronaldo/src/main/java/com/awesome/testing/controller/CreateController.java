package com.awesome.testing.controller;

import com.awesome.testing.dto.Information;
import com.awesome.testing.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateController {

    private final ProviderService providerService;

    @Autowired
    public CreateController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody Information information) {
        return providerService.add(information);
    }

}
