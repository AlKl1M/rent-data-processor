import json
from faker import Faker
import random

fake = Faker()

num_entries = 1000

def generate_apartment_with_tenant_and_lease():
    apartment = {
        "apartmentId": fake.uuid4(),
        "address": fake.address(),
        "size": random.randint(30, 120),
        "pricePerMonth": random.randint(300, 2000),
        "availableFrom": fake.date_this_decade().isoformat(),
        "tenantId": fake.uuid4(),
        "tenantFullName": fake.name(),
        "tenantContactNumber": fake.phone_number(),
        "tenantEmail": fake.email(),
        "tenantLeaseStartDate": fake.date_this_year().isoformat(),
        "tenantLeaseEndDate": fake.date_this_year().isoformat(),
        "leaseId": fake.uuid4(),
        "leaseStartDate": fake.date_this_year().isoformat(),
        "leaseEndDate": fake.date_this_year().isoformat(),
        "monthlyRent": random.randint(300, 2000)
    }
    return apartment

apartments = [generate_apartment_with_tenant_and_lease() for _ in range(num_entries)]

with open("apartments_with_tenant_and_lease_denormalized.json", "w") as apartment_file:
    json.dump(apartments, apartment_file, indent=4)