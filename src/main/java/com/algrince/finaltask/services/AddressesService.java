package com.algrince.finaltask.services;

import com.algrince.finaltask.dto.AddressDTO;
import com.algrince.finaltask.models.Address;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.repositories.AddressesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressesService {

    private final AddressesRepository addressesRepository;

    @Transactional
    public void save(Address address) {
        addressesRepository.save(address);
    }

    @Transactional(readOnly = true)
    public Address findById(Long id) {
        Optional<Address> foundAddress = addressesRepository.findById(id);
        return foundAddress.orElse(null);
    }

    @Transactional
    public void softDelete(Address address) {
        address.setDeleted(true);
        addressesRepository.save(address);
    }

    @Transactional
    public List<Address> findByUser(User owner) {
        return addressesRepository.findByOwner(owner);
    }
}
