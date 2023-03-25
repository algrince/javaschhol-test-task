package com.algrince.finaltask.services;

import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.Address;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.repositories.AddressesRepository;
import com.algrince.finaltask.utils.FilterManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressesService {

    private final AddressesRepository addressesRepository;

    private final FilterManager filterManager;
    private final String DELETED_ADDRESS_FILTER = "deletedAddressFilter";


    @Transactional(readOnly = true)
    public List<Address> findAll(boolean isAdmin) {
        List<Address> addresses;
        if (isAdmin) {
            addresses = addressesRepository.findAll();
        } else {
            filterManager.enableDeletedFilter(DELETED_ADDRESS_FILTER);
            addresses = addressesRepository.findAll();
            filterManager.disableFilter(DELETED_ADDRESS_FILTER);
        }
        return addresses;
    }

    @Transactional(readOnly = true)
    public Address findById(Long id) {
        Optional<Address> foundAddress = addressesRepository.findById(id);
        return foundAddress.orElseThrow(()
                -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Address findById(Long id, boolean isAdmin) {
        Optional<Address> foundAddress;
        if (isAdmin) {
            foundAddress = addressesRepository.findById(id);
        } else {
            filterManager.enableDeletedFilter(DELETED_ADDRESS_FILTER);
            foundAddress = addressesRepository.findById(id);
            filterManager.disableFilter(DELETED_ADDRESS_FILTER);
        }
        return foundAddress.orElseThrow(()
                -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Address> findByUser(User owner, boolean isAdmin) {
        List<Address> associatedAddresses;
        if (isAdmin) {
            associatedAddresses = addressesRepository.findByOwner(owner);
        } else {
            filterManager.enableDeletedFilter(DELETED_ADDRESS_FILTER);
            associatedAddresses = addressesRepository.findByOwner(owner);
            filterManager.disableFilter(DELETED_ADDRESS_FILTER);
        }
        return associatedAddresses;
    }

    @Transactional
    public void save(Address address) {
        addressesRepository.save(address);
    }

    @Transactional
    public void softDelete(Address address) {
        address.setDeleted(true);
        addressesRepository.save(address);
    }

}
