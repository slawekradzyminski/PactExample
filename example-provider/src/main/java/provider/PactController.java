package provider;

import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import provider.ulti.Nationality;

import java.util.Map;

@Profile("pact")
@RestController
public class PactController {

    @RequestMapping(value = "/pactStateChange", method = RequestMethod.POST)
    public ResponseEntity<?> providerState(@RequestBody Map body) {
        if (body.get("state").equals("Default nationality change")) {
            Nationality.setNationality("Portugal");
        }
        else {
            Nationality.setNationality("Argentina");
        }
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).build();
    }
}
