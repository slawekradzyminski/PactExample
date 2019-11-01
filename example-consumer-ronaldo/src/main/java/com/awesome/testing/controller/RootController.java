package com.awesome.testing.controller;

import com.awesome.testing.Information;
import com.awesome.testing.ProviderService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    private final ProviderService providerService;

    public static final String RONALDO = "CristianoRonaldo";

    @Autowired
    public RootController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/")
    public ResponseEntity<?> ronaldo() {
        Information information = providerService.getResponseForName(RONALDO).getBody();
        String jsonString = new JSONObject()
                .put("name", information.getName())
                .put("salary", information.getSalary())
                .put("nationality", information.getNationality())
                .toString();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString);
    }

}
