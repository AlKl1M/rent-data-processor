package com.alkl1m.rentdataprocessor.dto;

import java.time.LocalDate;
import java.util.UUID;

public record RentDto(
        UUID apartmentId,
        String address,
        int size,
        double pricePerMonth,
        String availableFrom,
        UUID tenantId,
        String tenantFullName,
        String tenantContactNumber,
        String tenantEmail,
        String tenantLeaseStartDate,
        String tenantLeaseEndDate,
        UUID leaseId,
        String leaseStartDate,
        String leaseEndDate,
        double monthlyRent
) {}