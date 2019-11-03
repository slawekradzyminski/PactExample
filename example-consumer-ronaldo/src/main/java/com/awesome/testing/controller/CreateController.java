package com.awesome.testing.controller;

import com.awesome.testing.dto.information.IdNotAwareInformation;
import com.awesome.testing.dto.information.Information;
import com.awesome.testing.error.ErrorProxy;
import com.awesome.testing.service.ProviderService;
import com.awesome.testing.util.InformationTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

@RestController
public class CreateController {

    private final ProviderService providerService;

    private final InformationTransformer informationTransformer;

    private final ErrorProxy errorProxy;

    @Autowired
    public CreateController(ProviderService providerService, InformationTransformer informationTransformer, ErrorProxy errorProxy) {
        this.providerService = providerService;
        this.informationTransformer = informationTransformer;
        this.errorProxy = errorProxy;
    }

    @PostMapping(path = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@RequestBody IdNotAwareInformation information) {
        ResponseEntity<Information> response = providerService.add(information);
        if (errorProxy.isProxied(response)) {
            return response;
        }

        String timedInformation = informationTransformer.addTimestamp(requireNonNull(response.getBody()));

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(timedInformation);
    }

}
