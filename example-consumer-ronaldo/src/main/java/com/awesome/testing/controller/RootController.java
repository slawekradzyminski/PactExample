package com.awesome.testing.controller;

import com.awesome.testing.dto.information.Information;
import com.awesome.testing.error.ErrorProxy;
import com.awesome.testing.service.InformationClient;
import com.awesome.testing.util.InformationTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

@RestController
public class RootController {

    private final InformationClient informationClient;
    private final InformationTransformer informationTransformer;
    private final ErrorProxy errorProxy;

    public static final String RONALDO = "CristianoRonaldo";

    @Autowired
    public RootController(InformationClient informationClient, InformationTransformer informationTransformer, ErrorProxy errorProxy) {
        this.informationClient = informationClient;
        this.informationTransformer = informationTransformer;
        this.errorProxy = errorProxy;
    }

    @GetMapping("/")
    public ResponseEntity<?> ronaldo() {
        ResponseEntity<Information> response = informationClient.getResponseForName(RONALDO);
        if (errorProxy.isProxied(response)) {
            return response;
        }
        String jsonString = informationTransformer.addTimestamp(requireNonNull(response.getBody()));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString);
    }

}
