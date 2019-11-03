package com.awesome.testing.controller;

import com.awesome.testing.dto.information.IdNotAwareInformation;
import com.awesome.testing.dto.information.Information;
import com.awesome.testing.error.ErrorProxy;
import com.awesome.testing.service.ProviderService;
import com.awesome.testing.util.InformationTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

@RestController
public class UpdateController {

    private final ProviderService providerService;
    private final InformationTransformer informationTransformer;
    private final ErrorProxy errorProxy;

    @Autowired
    public UpdateController(ProviderService providerService, InformationTransformer informationTransformer, ErrorProxy errorProxy) {
        this.providerService = providerService;
        this.informationTransformer = informationTransformer;
        this.errorProxy = errorProxy;
    }

    @PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody IdNotAwareInformation information, @PathVariable long id) {
        ResponseEntity<Information> response = providerService.updateViaPut(information, id);
        if (errorProxy.isProxied(response)) {
            return response;
        }

        String jsonString = informationTransformer.addTimestamp(requireNonNull(response.getBody()));
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(jsonString);
    }

}
