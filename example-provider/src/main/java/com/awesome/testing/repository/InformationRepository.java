package com.awesome.testing.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.awesome.testing.dto.Information;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface InformationRepository extends CrudRepository<Information, Long> {

    Optional<Information> findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "ALTER TABLE information ALTER COLUMN id RESTART WITH 1", nativeQuery = true)
    void resetAutoincrement();

}