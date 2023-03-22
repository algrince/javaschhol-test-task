package com.algrince.finaltask.controllers;


import com.algrince.finaltask.dto.AddressDTO;
import com.algrince.finaltask.exceptions.ResourceNotFoundException;
import com.algrince.finaltask.models.Address;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.AddressesService;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.DTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
@CrossOrigin(origins = "http://localhost:4200")
public class AddressesController {

    private final AddressesService addressesService;
    private final DTOMapper dtoMapper;
    private final UsersService usersService;


    @GetMapping
//    @PreAuthorize("#userId == authentication.principal.id")
    public List<AddressDTO> getAddresses(
            @RequestParam(required = false) Long user) {
        List<Address> addresses = addressesService.selectAddresses(user);
        return dtoMapper.mapList(addresses, AddressDTO.class);
    }


    @PostMapping
//    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<Object> addAddress(
            @Valid @RequestBody AddressDTO addressDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

//        TODO add address already exists (unique field??)
        Address address = dtoMapper.mapClass(addressDTO, Address.class);

//      detached entity passed to persist: com.algrince.finaltask.models.User
        User associatedUser = usersService.findById(addressDTO.getOwner().getId());
        address.setOwner(associatedUser);

        addressesService.save(address);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
//    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<Object> getAddress (@PathVariable("id") Long id, Principal principal) {
        log.info(principal.toString());

        Address foundAddress = addressesService.findById(id);

        AddressDTO foundAddressDTO = dtoMapper.mapClass(foundAddress, AddressDTO.class);
        return ResponseEntity.ok().body(foundAddressDTO);
    }

    @PutMapping("{id}")
//    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<Object> updateAddress (
            @PathVariable(value = "id") Long addressId,
            @Valid @RequestBody AddressDTO addressDTO,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getAllErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(errors, HttpStatus.OK);
        }

        Address foundAddress = addressesService.findById(addressId);
        dtoMapper.mapProperties(addressDTO, foundAddress);
        addressesService.save(foundAddress);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @DeleteMapping("{id}")
//    @PreAuthorize("#userId == authentication.principal.id")
    public ResponseEntity<String> deleteAddress (@PathVariable(value = "id") Long addressId) {
        Address addressToDelete = addressesService.findById(addressId);
        addressesService.softDelete(addressToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
