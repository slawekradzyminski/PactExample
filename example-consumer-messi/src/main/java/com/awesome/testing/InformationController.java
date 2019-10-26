package com.awesome.testing;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InformationController {

    public static final String MESSI = "LeoMessi";

    private final ProviderService providerService;

    @Autowired
    public InformationController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/")
    public ResponseEntity<?> messi() {
        Information information = providerService.getResponseForName(MESSI).getBody();
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
