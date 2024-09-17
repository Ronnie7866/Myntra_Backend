package com.backend.ecommerce.repository;

import com.backend.ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    public Optional<Address> findByUserId(Long user_id);
}
