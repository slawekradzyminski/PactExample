package provider.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import provider.dto.Information;
import provider.service.InformationService;

import java.util.List;

@RestController
public class InformationController {

    private final InformationService informationService;

    @Autowired
    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }

    @RequestMapping("/information")
    public ResponseEntity<?> information(@RequestParam(value = "name") String name) {
        List<Information> informations = informationService.getFirstInformationByName(name);
        if (informations.size() == 1) {
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(informations.get(0));
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}

