package com.example;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;

@Repository
public interface MyEntityRepository extends JpaRepository<MyEntity, Long> {
}
