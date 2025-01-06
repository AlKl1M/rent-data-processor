package com.alkl1m.rentdataprocessor.repository;

import com.alkl1m.rentdataprocessor.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentRepository extends JpaRepository<Rent, UUID> {
    Optional<Rent> findByApartmentId(UUID apartmentId);
}