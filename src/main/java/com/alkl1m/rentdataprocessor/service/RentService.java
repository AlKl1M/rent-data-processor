package com.alkl1m.rentdataprocessor.service;

import com.alkl1m.rentdataprocessor.dto.RentDto;
import com.alkl1m.rentdataprocessor.entity.Rent;
import com.alkl1m.rentdataprocessor.repository.RentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RentService {

    private final RentRepository rentRepository;

    public RentService(RentRepository rentRepository) {
        this.rentRepository = rentRepository;
    }

    public Rent saveOrUpdateRent(RentDto rentDto) {
        Optional<Rent> existingRent = rentRepository.findByApartmentId(rentDto.apartmentId());

        Rent rent;
        if (existingRent.isPresent()) {
            rent = existingRent.get();
            updateRentFromDto(rent, rentDto);
        } else {
            rent = Rent.fromDto(rentDto);
        }

        return rentRepository.save(rent);
    }

    private void updateRentFromDto(Rent rent, RentDto rentDto) {
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
    }
}
