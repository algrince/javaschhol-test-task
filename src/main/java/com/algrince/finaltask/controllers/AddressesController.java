package com.algrince.finaltask.controllers;


import com.algrince.finaltask.dto.AddressDTO;
import com.algrince.finaltask.models.Address;
import com.algrince.finaltask.repositories.AddressesRepository;
import com.algrince.finaltask.services.AddressesService;
import com.algrince.finaltask.utils.DTOMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("addresses")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AddressesController {

    private final AddressesService addressesService;
    private final DTOMapper dtoMapper;
    private final ModelMapper modelMapper;


    @PostMapping
    public void addAddress(@Valid @RequestBody AddressDTO addressDTO) {
        Address address = dtoMapper.mapClass(addressDTO, Address.class);
        addressesService.save(address);
    }

    @GetMapping("{id}")
    public ResponseEntity<AddressDTO> getAddress (@PathVariable("id") Long id) {
        Address foundAddress = addressesService.findById(id);
        AddressDTO foundAddressDTO = dtoMapper.mapClass(foundAddress, AddressDTO.class);
        return ResponseEntity.ok().body(foundAddressDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<AddressDTO> updateAddress (
            @PathVariable(value = "id") Long addressId,
            @Valid @RequestBody AddressDTO addressDTO) {
        Address foundAddress = addressesService.findById(addressId);
        dtoMapper.mapProperties(addressDTO, foundAddress);
        addressesService.save(foundAddress);
        AddressDTO newAddressDTO = dtoMapper.mapClass(foundAddress, AddressDTO.class);
        return ResponseEntity.ok().body(newAddressDTO);
    }

}
