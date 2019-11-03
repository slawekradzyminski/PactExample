package com.awesome.testing.controller;

import com.awesome.testing.service.InformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.awesome.testing.dto.Information;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

@RestController
public class InformationController {

    private final InformationService informationService;

    @Autowired
    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping("/information")
    public ResponseEntity<?> getAllInformation() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(informationService.getAllInformation());
    }

    @GetMapping("/information/{id}")
    public ResponseEntity<?> getInformationById(@PathVariable long id) {
        Optional<Information> information = informationService.getInformationById(id);
        return returnResultFromOptional(information);
    }

    @GetMapping(value = "/information", params = "name")
    public ResponseEntity<?> informationByName(@RequestParam(value = "name") String name) {
        Optional<Information> information = informationService.getInformationByName(name);
        return returnResultFromOptional(information);
    }

    @PostMapping(path = "/information", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addInfo(@RequestBody Information information) {
        if (informationService.getInformationByName(information.getName()).isPresent()) {
            return ResponseEntity.unprocessableEntity().build();
        }

        Information saved = informationService.save(information);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(saved);
    }

    @DeleteMapping("/information/{id}")
    public ResponseEntity<?> deleteInfo(@PathVariable long id) {
        informationService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/information/{id}")
    public ResponseEntity<?> put(@RequestBody Information information, @PathVariable long id) {
        if (!informationService.getInformationById(id).isPresent()) {
            return handleIdNotFoundError();
        }

        information.setId(id);
        Information updated = informationService.save(information);
        return ResponseEntity.ok().body(updated);
    }

    @PatchMapping("/information/{id}")
    public ResponseEntity<?> patch(@RequestBody Map<String, Object> updates, @PathVariable long id) {
        if (!informationService.getInformationById(id).isPresent()) {
            return handleIdNotFoundError();
        }

        if (containsNonExistingParamNames(updates)) {
            return handleWrongParamNamesError();
        }

        Information informationBeingUpdated = informationService.getInformationById(id).get();

        updates.forEach((k, v) -> {
            Field field = ReflectionUtils.findField(Information.class, k);
            field.setAccessible(true);
            ReflectionUtils.setField(requireNonNull(field), informationBeingUpdated, v);
        });
        Information updated = informationService.save(informationBeingUpdated);
        return ResponseEntity.ok().body(updated);
    }

    private ResponseEntity<?> handleWrongParamNamesError() {
        String errorBody = "You have requested parameter that does not exist in Information DTO";
        return ResponseEntity.badRequest().body(errorBody);
    }

    private boolean containsNonExistingParamNames(Map<String, Object> updates) {
        return updates.keySet()
                .stream()
                .map(k -> ReflectionUtils.findField(Information.class, k))
                .collect(Collectors.toList())
                .contains(null);
    }

    private ResponseEntity<?> handleIdNotFoundError() {
        String errorBody = "ID not found. Please use POST method to create new entries";
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorBody);
    }

    private ResponseEntity<?> returnResultFromOptional(Optional<Information> information) {
        if (information.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(information);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}

