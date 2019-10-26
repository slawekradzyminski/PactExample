package provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import provider.dto.Information;
import provider.service.InformationService;

import java.util.Optional;

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
    public ResponseEntity<?> getInformationById(@PathVariable int id) {
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

        informationService.save(information);
        return ResponseEntity.status(HttpStatus.CREATED).build();
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

