package com.algrince.finaltask.controllers;


import com.algrince.finaltask.dto.AddressDTO;
import com.algrince.finaltask.exceptions.InvalidFormException;
import com.algrince.finaltask.models.Address;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.AddressesService;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.DTOMapper;
import com.algrince.finaltask.validators.AccessValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("addresses")
@RequiredArgsConstructor
public class AddressesController {

    private final AddressesService addressesService;
    private final AccessValidator accessValidator;
    private final UsersService usersService;
    private final DTOMapper dtoMapper;


    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<AddressDTO> getUserAddressesList(
            @RequestParam(required = true) Long user,
            Principal principal) {
        User associatedUser = usersService.findById(user);
        usersService.checkAccess(principal, associatedUser);

        boolean isAdmin = accessValidator.authUserIsAdmin();
        List<Address> addresses = addressesService.findByUser(associatedUser, isAdmin);
        return dtoMapper.mapList(addresses, AddressDTO.class);
    }

    @GetMapping("all")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'ADMIN')")
    public List<AddressDTO> getAddressesList() {
        boolean isAdmin = accessValidator.authUserIsAdmin();
        List<Address> addresses = addressesService.findAll(isAdmin);
        return dtoMapper.mapList(addresses, AddressDTO.class);
    }
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> addAddress(
            @Valid @RequestBody AddressDTO addressDTO,
            Principal principal, BindingResult bindingResult) {

        User associatedUser = usersService.findById(addressDTO.getOwner().getId());
        usersService.checkAccess(principal, associatedUser);

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        Address address = dtoMapper.mapClass(addressDTO, Address.class);
        address.setOwner(associatedUser);

        addressesService.save(address);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getAddress(
            @PathVariable("id") Long id,
            Principal principal) {
        boolean isAdmin = accessValidator.authUserIsAdmin();
        Address foundAddress = addressesService.findById(id, isAdmin);

        User associatedUser = foundAddress.getOwner();
        usersService.checkAccess(principal, associatedUser);

        AddressDTO foundAddressDTO = dtoMapper.mapClass(foundAddress, AddressDTO.class);
        return ResponseEntity.ok().body(foundAddressDTO);
    }

    @PutMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> updateAddress (
            @PathVariable(value = "id") Long addressId,
            @Valid @RequestBody AddressDTO addressDTO,
            Principal principal, BindingResult bindingResult) {

        Address foundAddress = addressesService.findById(addressId);
        User associatedUser = foundAddress.getOwner();
        usersService.checkAccess(principal, associatedUser);

        if (bindingResult.hasErrors()) {
            throw new InvalidFormException(bindingResult);
        }

        dtoMapper.mapProperties(addressDTO, foundAddress);
        addressesService.save(foundAddress);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteAddress (
            @PathVariable(value = "id") Long addressId,
            Principal principal) {

        Address addressToDelete = addressesService.findById(addressId);
        User associatedUser = addressToDelete.getOwner();
        usersService.checkAccess(principal, associatedUser);

        addressesService.softDelete(addressToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
