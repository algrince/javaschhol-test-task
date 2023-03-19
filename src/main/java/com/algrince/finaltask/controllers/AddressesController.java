package com.algrince.finaltask.controllers;


import com.algrince.finaltask.dto.AddressDTO;
import com.algrince.finaltask.models.Address;
import com.algrince.finaltask.models.User;
import com.algrince.finaltask.services.AddressesService;
import com.algrince.finaltask.services.UsersService;
import com.algrince.finaltask.utils.DTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AddressesController {

    private final AddressesService addressesService;
    private final DTOMapper dtoMapper;
    private final UsersService usersService;


    @GetMapping
    public List<AddressDTO> getAddresses(
            @RequestParam(required = false) Long user) {
        List<Address> addresses = addressesService.selectAddresses(user);
        return dtoMapper.mapList(addresses, AddressDTO.class);
    }


    @PostMapping
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
//        For now setting address owner es manual: will be changed later
        User user = usersService.findOne(25L);
        address.setOwner(user);
        addressesService.save(address);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<AddressDTO> getAddress (@PathVariable("id") Long id) {
        Address foundAddress = addressesService.findById(id);
        AddressDTO foundAddressDTO = dtoMapper.mapClass(foundAddress, AddressDTO.class);
        return ResponseEntity.ok().body(foundAddressDTO);
    }

    @PutMapping("{id}")
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
        AddressDTO newAddressDTO = dtoMapper.mapClass(foundAddress, AddressDTO.class);
        return ResponseEntity.ok().body(newAddressDTO);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAddress (@PathVariable(value = "id") Long addressId) {
        Address addressToDelete = addressesService.findById(addressId);
        addressesService.softDelete(addressToDelete);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
