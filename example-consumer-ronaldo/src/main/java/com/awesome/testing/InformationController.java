package com.awesome.testing;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InformationController {

    private final ProviderService providerService;

    public static final String RONALDO = "Ronaldo";

    @Autowired
    public InformationController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/")
    public ResponseEntity<?> ronaldo() {
        Information information = providerService.getResponseForName(RONALDO).getBody();
        String jsonString = new JSONObject()
                .put("Name", information.getName())
                .put("Email", information.getContact().get("Email"))
                .put("PhoneNumber", information.getContact().get("Phone Number"))
                .put("Nationality", information.getNationality())
                .toString();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString);
    }


}
