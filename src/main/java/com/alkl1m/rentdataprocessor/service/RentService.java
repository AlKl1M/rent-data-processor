package com.alkl1m.rentdataprocessor.service;

import com.alkl1m.rentdataprocessor.dto.RentDto;
import com.alkl1m.rentdataprocessor.entity.Rent;
import com.alkl1m.rentdataprocessor.repository.RentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class RentService {

    private final RentRepository rentRepository;

    public void saveOrUpdateRent(RentDto rentDto) {
        log.info("Saving or updating rent with apartment ID: {}", rentDto.apartmentId());
        Optional<Rent> existingRent = rentRepository.findByApartmentId(rentDto.apartmentId());

        Rent rent;
        if (existingRent.isPresent()) {
            log.info("Existing rent found for apartment ID: {}. Updating...", rentDto.apartmentId());
            rent = existingRent.get();
            updateRentFromDto(rent, rentDto);
        } else {
            log.info("No existing rent found for apartment ID: {}. Creating new rent entry.", rentDto.apartmentId());
            rent = Rent.fromDto(rentDto);
        }

        Rent savedRent = rentRepository.save(rent);
        log.info("Rent saved successfully with ID: {}", savedRent.getId());
    }

    private void updateRentFromDto(Rent rent, RentDto rentDto) {
        log.info("Updating rent data for apartment ID: {}", rentDto.apartmentId());
        rent.setAddress(rentDto.address());
        rent.setSize(rentDto.size());
        rent.setPricePerMonth(rentDto.pricePerMonth());
        rent.setAvailableFrom(rentDto.availableFrom());
        rent.setTenantId(rentDto.tenantId());
        rent.setTenantFullName(rentDto.tenantFullName());
        rent.setTenantContactNumber(rentDto.tenantContactNumber());
        rent.setTenantEmail(rentDto.tenantEmail());
        rent.setTenantLeaseStartDate(rentDto.tenantLeaseStartDate());
        rent.setTenantLeaseEndDate(rentDto.tenantLeaseEndDate());
        rent.setLeaseId(rentDto.leaseId());
        rent.setLeaseStartDate(rentDto.leaseStartDate());
        rent.setLeaseEndDate(rentDto.leaseEndDate());
        rent.setMonthlyRent(rentDto.monthlyRent());
        log.info("Rent data updated successfully for apartment ID: {}", rentDto.apartmentId());
    }

}
