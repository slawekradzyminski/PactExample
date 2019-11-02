package com.awesome.testing.repository;

import org.springframework.data.repository.CrudRepository;
import com.awesome.testing.dto.Information;

import java.util.Optional;

public interface InformationRepository extends CrudRepository<Information, Long> {

    Optional<Information> findByName(String name);

}