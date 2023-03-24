package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertiesRepository extends JpaRepository<Property, Long> {
    Optional<Property> findByName(String name);
}
