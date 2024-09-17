package com.backend.ecommerce.controllers;


import com.backend.ecommerce.entity.Address;
import com.backend.ecommerce.repository.AddressRepository;
//import com.backend.ecommerce.implementation.AddressService;
import com.backend.ecommerce.service.AddressService;
import com.backend.ecommerce.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
@AllArgsConstructor
public class AddressController {

    private final UserService userService;
    private final AddressRepository addressRepository;
    private final AddressService addressService;

    @PostMapping("/{userId}")
    public ResponseEntity<Address> createAddress(@PathVariable Long userId, @RequestBody Address address) {
        Address save = addressService.save(userId, address);
        return ResponseEntity.ok().body(save);
    }

    @GetMapping
    public ResponseEntity<List<Address>> getAllAddress() {
        List<Address> addressList = addressService.getAll();
        return ResponseEntity.ok().body(addressList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressByUserId(@PathVariable Long id) {
        Address address = addressService.getAddressByUserId(id);
        return ResponseEntity.ok(address);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteAddress(@RequestBody Address address) {

        addressService.deleteAddressById(address.getId());
        return ResponseEntity.ok("Address is deleted");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Address> updateAddressByUserId(@RequestBody Address address, @PathVariable Long userId) {
        Address address1 = addressService.updateAddressByUserId(address, userId);
        return ResponseEntity.ok().body(address1);
    }
}