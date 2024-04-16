package edu.epam.fop.lambdas;

import java.util.Comparator;

// Interface defining various comparators for different types of objects
public interface Comparators {

    // Comparator for comparing addresses based on zip code
    static Comparator<Address> ZIP_CODE_COMPARATOR = Comparator
            .nullsFirst((address1, address2) -> {
                // Handling null values
                if (address1.zipCode() == null || address2.zipCode() == null)
                    throw new NullPointerException();
                // Comparing zip codes in descending order
                return -(address1.zipCode() - address2.zipCode());
            });

    // Comparator for comparing addresses based on street, building, and apartment
    static Comparator<Address> STREET_COMPARATOR = Comparator.nullsFirst(
                    (Address address1, Address address2) -> {
                        // Check if either street is null
                        Integer nullComparatorNumber = nullComparatorLast(address1.street(), address2.street());
                        if (nullComparatorNumber != null) return nullComparatorNumber;
                        // Comparing street names
                        return Integer.compare(address1.street().compareTo(address2.street()), 0);
                    })
            .thenComparing((Address address1, Address address2) -> {
                // Check if either building is null
                Integer nullComparatorNumber = nullComparatorLast(address1.building(), address2.building());
                if (nullComparatorNumber != null) return nullComparatorNumber;
                // Comparing building numbers
                return Integer.compare(address1.building().compareTo(address2.building()), 0);
            })
            .thenComparing((Address address1, Address address2) -> {
                // Check if either apartment is null
                Integer nullComparatorNumber = nullComparatorLast(address1.apartment(), address2.apartment());
                if (nullComparatorNumber != null) return nullComparatorNumber;
                // Comparing apartment numbers
                return Integer.compare(address1.apartment().compareTo(address2.apartment()), 0);
            });

    // Comparator for comparing addresses based on region (country and city) and zip code
    static Comparator<Address> REGION_COMPARATOR = Comparator.nullsFirst(
                    (Address address1, Address address2) -> {
                        // Check if either country is null
                        Integer nullComparatorNumber = nullComparatorLast(address1.country(), address2.country());
                        if (nullComparatorNumber != null) return nullComparatorNumber;
                        // Comparing countries
                        return Integer.compare(address1.country().compareTo(address2.country()), 0);
                    })
            .thenComparing((Address address1, Address address2) -> {
                // Check if either city is null
                Integer nullComparatorNumber = nullComparatorLast(address1.city(), address2.city());
                if (nullComparatorNumber != null) return nullComparatorNumber;
                // Comparing cities
                return Integer.compare(address1.city().compareTo(address2.city()), 0);
            })
            .thenComparing(ZIP_CODE_COMPARATOR);

    // Comparator for comparing addresses based on region and street
    static Comparator<Address> ADDRESS_COMPARATOR = Comparator.nullsLast(REGION_COMPARATOR)
            .thenComparing(STREET_COMPARATOR);

    // Comparator for comparing persons based on full name (name and surname)
    static Comparator<Person> FULL_NAME_COMPARATOR = Comparator.nullsFirst(
            (Person person1, Person person2) -> {
                // Check if either name is null
                if (person1.name() == null || person2.name() == null)
                    throw new NullPointerException();
                // Comparing names
                return Integer.compare(person1.name().compareTo(person2.name()), 0);
            }).thenComparing((person1, person2) -> {
        // Check if either surname is null
        if (person1.surname() == null || person2.surname() == null)
            throw new NullPointerException();
        // Comparing surnames
        return Integer.compare(person1.surname().compareTo(person2.surname()), 0);
    });

    // Comparator for comparing persons based on birthdate
    static Comparator<Person> BIRTHDATE_COMPARATOR = Comparator.nullsFirst(
            (person1, person2) -> {
                // Check if either birthdate is null
                Integer nullComparatorNumber = nullComparatorLast(person1.birthdate(), person2.birthdate());
                if (nullComparatorNumber != null) return nullComparatorNumber;
                // Comparing birthdates in descending order
                return -Integer.compare(person1.birthdate().compareTo(person2.birthdate()), 0);
            });

    // Comparator for comparing persons based on full name, birthdate, and address
    static Comparator<Person> PERSON_COMPARATOR = Comparator.nullsLast(FULL_NAME_COMPARATOR)
            .thenComparing(BIRTHDATE_COMPARATOR)
            .thenComparing((person1, person2) -> ADDRESS_COMPARATOR.compare(person1.address(), person2.address()));

    // Comparator for comparing companies based on registration ID
    static Comparator<Company> REGISTRATION_ID_COMPARATOR = Comparator.nullsLast((company1, company2) -> {
        // Check if either registration ID is null
        if (company1.registrationUuid() == null || company2.registrationUuid() == null)
            throw new NullPointerException();
        // Comparing registration IDs
        return Integer.compare(company1.registrationUuid().compareTo(company2.registrationUuid()), 0);
    });

    // Comparator for comparing companies based on name, registration ID, director, and office address
    static Comparator<Company> COMPANY_COMPARATOR = Comparator.nullsLast((Company company1, Company company2) -> {
                // Check if either name is null
                Integer nullComparatorNumber = nullComparatorLast(company1.name(), company2.name());
                if (nullComparatorNumber != null) return nullComparatorNumber;
                // Comparing names
                return Integer.compare(company1.name().compareTo(company2.name()), 0);
            }).thenComparing(REGISTRATION_ID_COMPARATOR)
            .thenComparing((company1, company2) -> PERSON_COMPARATOR.compare(company1.director(), company2.director()))
            .thenComparing((company1, company2) -> ADDRESS_COMPARATOR.compare(company1.officeAddress(), company2.officeAddress()));

    // Helper method to handle null comparisons
    private static Integer nullComparatorLast(Object o1, Object o2) {
        if (o1 == null && o2 == null) return 0; // Both are null, considered equal
        else if (o1 == null) return 1; // o1 is null, considered greater
        else if (o2 == null) return -1; // o2 is null, considered greater
        else return null; // Neither are null, continue with comparison
    }

}
