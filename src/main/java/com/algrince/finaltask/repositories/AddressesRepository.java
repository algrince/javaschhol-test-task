package com.algrince.finaltask.repositories;

import com.algrince.finaltask.models.Address;
import com.algrince.finaltask.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressesRepository extends JpaRepository<Address, Long> {
    List<Address> findByOwner(User owner);
}
