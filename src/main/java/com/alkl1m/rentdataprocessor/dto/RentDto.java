package com.alkl1m.rentdataprocessor.dto;

import java.util.UUID;

/**
 * ДТО сущности аренды.
 *
 * @author AlKl1M
 */
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
) {
}