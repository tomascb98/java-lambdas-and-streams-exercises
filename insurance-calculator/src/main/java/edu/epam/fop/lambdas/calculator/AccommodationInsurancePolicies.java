package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Accommodation;
import edu.epam.fop.lambdas.insurance.Currency;

import java.math.BigInteger;
import java.util.Optional;

public final class AccommodationInsurancePolicies {

  private AccommodationInsurancePolicies() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  static InsuranceCalculator<Accommodation> rentDependentInsurance(BigInteger divider) {
    return (entity -> {
      if(entity == null) return Optional.empty();
      Optional<Accommodation> optional = Optional.of(entity)
              .filter(entity1 -> entity1.rent().isPresent())
              .filter(entity1 -> entity1.rent().get().unit().getMonths()>0 && entity1.rent().get().unit().getDays() == 0 && entity1.rent().get().unit().getYears() == 0)
              .filter(entity1 -> entity1.rent().get().currency().equals(Currency.USD))
              .filter(entity1 -> entity1.rent().get().amount().intValueExact() > 0);
      Optional<InsuranceCoefficient> insuranceCoefficient;
      BigInteger coefficient;
      if(optional.isPresent()){
        coefficient = optional.get().rent().get().amount().divide(divider.divide(BigInteger.valueOf(100)));
        InsuranceCoefficient insuranceCoefficient1 = coefficient.intValueExact() > 100 ? InsuranceCoefficient.MAX : InsuranceCoefficient.of(coefficient.intValueExact());
        insuranceCoefficient = Optional.of(insuranceCoefficient1);
        return insuranceCoefficient;
      }
      return Optional.empty();

    });
  }

  static InsuranceCalculator<Accommodation> priceAndRoomsAndAreaDependentInsurance(BigInteger priceThreshold,
      int roomsThreshold, BigInteger areaThreshold) {
    return (accommodation ->{
      if(accommodation == null || accommodation.area() == null || accommodation.rooms() == null || accommodation.price() == null) return Optional.of(InsuranceCoefficient.MIN);
      Optional<Accommodation> optionalAccommodation = Optional.of(accommodation)
              .filter(entity->entity.price().compareTo(priceThreshold) >= 0 && entity.rooms() >= roomsThreshold && entity.area().compareTo(areaThreshold) >= 0);

      if(optionalAccommodation.isPresent()) return Optional.of(InsuranceCoefficient.MAX);
      return Optional.of(InsuranceCoefficient.MIN);
    });
  }
}
