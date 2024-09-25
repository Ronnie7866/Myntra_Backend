package com.backend.ecommerce.records;

import com.backend.ecommerce.entity.City;
import com.backend.ecommerce.enums.PhoneType;

public record AddressDTO(Long id,
                         String addressType,
                         String streetAddress,
                         String pinCode,
                         PhoneType phoneType,
                         UserDTO user,
                         City city
) {
}
