package com.backend.ecommerce.records;

import com.backend.ecommerce.entity.Address;
import com.backend.ecommerce.enums.Role;

import java.util.List;

public record UserDTO(Long id,
                      String firstName,
                      String lastName,
                      String email,
                      Role role,
                      Long defaultPhoneNumber,
                      List<Address> addressList

) {
}
