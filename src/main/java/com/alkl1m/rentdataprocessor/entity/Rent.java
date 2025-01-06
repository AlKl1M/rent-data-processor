package com.alkl1m.rentdataprocessor.entity;

import com.alkl1m.rentdataprocessor.dto.RentDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "rent")
public class Rent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private UUID apartmentId;

    @Column(nullable = false)
    private String address;

    private int size;

    private double pricePerMonth;

    private String availableFrom;

    private UUID tenantId;

    private String tenantFullName;

    private String tenantContactNumber;

    private String tenantEmail;

    private String tenantLeaseStartDate;

    private String tenantLeaseEndDate;

    private UUID leaseId;

    private String leaseStartDate;

    private String leaseEndDate;

    private double monthlyRent;

    public static Rent fromDto(RentDto rentDto) {
        Rent rent = new Rent();
        rent.setApartmentId(rentDto.apartmentId());
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
        return rent;
    }

}