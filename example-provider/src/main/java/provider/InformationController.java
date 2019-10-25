package provider;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import provider.ulti.Nationality;

@RestController
public class InformationController {

    private Information information = new Information();

    @RequestMapping("/information")
    public ResponseEntity<?> information(@RequestParam(value = "name") String name) {
        if (name.equals("Messi")) {
            getMessiData();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(information);

        } else if (name.equals("Ronaldo")) {
            getRonaldoData();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(information);

        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    private void getRonaldoData() {
        information.setNationality(Nationality.getNationality());
        information.setName("Cristiano Ronaldo");
        information.setSalary(80000);
    }

    private void getMessiData() {
        information.setNationality(Nationality.getNationality());
        information.setName("Leo Messi");
        information.setSalary(45000);
    }
}
