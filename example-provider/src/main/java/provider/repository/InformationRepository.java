package provider.repository;

import org.springframework.data.repository.CrudRepository;
import provider.dto.Information;

import java.util.Optional;

public interface InformationRepository extends CrudRepository<Information, Integer> {

    Optional<Information> findByName(String name);

}