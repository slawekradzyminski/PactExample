package com.awesome.testing.controller;

import com.awesome.testing.service.InformationService;
import com.awesome.testing.state.ContractState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Profile("pact")
@RestController
public class PactController {

    private final InformationService informationService;

    @Autowired
    public PactController(InformationService informationService) {
        this.informationService = informationService;
    }

    @RequestMapping(value = "/pactStateChange", method = RequestMethod.POST)
    public ResponseEntity<?> providerState(@RequestBody Map body) {
        if (body.get("state").equals(ContractState.DEFAULT.description())) {
            ContractState.DEFAULT.setState(informationService);
        } else {
            ContractState.EMPTY.setState(informationService);
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).build();
    }
}
