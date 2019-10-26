package provider.repository;

import org.springframework.data.repository.CrudRepository;
import provider.dto.Information;

import java.util.List;

public interface InformationRepository extends CrudRepository<Information, Integer> {

    List<Information> findByName(String name);

}