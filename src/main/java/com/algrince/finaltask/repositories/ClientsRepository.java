package com.algrince.finaltask.repositories;


import com.algrince.finaltask.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Integer> {
    Optional<Client> findByEmail(@Param("email") String email);
}
