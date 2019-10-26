package provider.repository;

import org.springframework.data.repository.CrudRepository;
import provider.dto.Information;

public interface InformationRepository extends CrudRepository<Information, Integer> {

    Information findByName(String name);

}