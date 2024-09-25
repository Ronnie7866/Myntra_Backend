package com.backend.ecommerce.mapper;

import com.backend.ecommerce.entity.Address;
import com.backend.ecommerce.entity.User;
import com.backend.ecommerce.records.AddressDTO;
import com.backend.ecommerce.records.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressMapper implements EntityDTOMapper<Address, AddressDTO> {
    @Override
    public AddressDTO apply(Address entity) {
        // Create an AddressMapper instance to convert Address entities to AddressDTOs
        AddressMapper addressMapper = new AddressMapper();

        // Convert List<Address> to List<AddressDTO>
        List<AddressDTO> addressDTOList = entity.getUser().getAddressList()
                .stream()
                .map(addressMapper::apply) // Map each Address to AddressDTO
                .toList();

        return new AddressDTO(
                entity.getId(),
                entity.getAddressType(),
                entity.getStreetAddress(),
                entity.getPinCode(),
                entity.getPhoneType(),
                new UserDTO(
                        entity.getUser().getId(),
                        entity.getUser().getFirstName(),
                        entity.getUser().getLastName(),
                        entity.getUser().getEmail(),
                        entity.getUser().getRole(),
                        entity.getUser().getDefaultPhoneNumber(),
                        addressDTOList // Pass the mapped List<AddressDTO>
                ),
                entity.getCity() // Assuming you're using City directly
        );
    }

    @Override
    public Address reverse(AddressDTO dto) {
        Address address = new Address();
        address.setId(dto.id());
        address.setAddressType(dto.addressType());
        address.setStreetAddress(dto.streetAddress());
        address.setPinCode(dto.pinCode());
        address.setPhoneType(dto.phoneType());

        // Don't set the User here, it will be handled in the UserMapper

        if (dto.city() != null) {
            address.setCity(dto.city());
        }

        return address;
    }
}
