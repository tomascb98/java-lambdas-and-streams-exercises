package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Car;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

public final class CarInsurancePolicies {

  private CarInsurancePolicies() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static InsuranceCalculator<Car> ageDependInsurance(LocalDate baseDate) {
    return (car) -> {
      if(car == null || car.manufactureDate()==null) return Optional.empty();
      if(baseDate.minusYears(1).isBefore(car.manufactureDate()) || baseDate.minusYears(1).isEqual(car.manufactureDate())) return Optional.of(InsuranceCoefficient.MAX);
      else if(baseDate.minusYears(5).isBefore(car.manufactureDate()) || baseDate.minusYears(5).isEqual(car.manufactureDate())) return Optional.of(InsuranceCoefficient.of(70));
      else if(baseDate.minusYears(10).isBefore(car.manufactureDate()) || baseDate.minusYears(10).isEqual(car.manufactureDate())) return Optional.of(InsuranceCoefficient.of(30));
      else return Optional.of(InsuranceCoefficient.MIN);
    };
  }

  public static InsuranceCalculator<Car> priceAndOwningOfFreshCarInsurance(LocalDate baseDate,
      BigInteger priceThreshold, Period owningThreshold) {
    return (car) -> {
      if(car == null || car.price() == null) return Optional.empty();
      Optional<Car> carOptional = Optional.of(car)
              .filter(car1 -> car1.soldDate().isEmpty())
              .filter(car1 -> car1.price().compareTo(priceThreshold) >= 0)
              .filter(car1 -> car1.purchaseDate().plus(owningThreshold).isAfter(baseDate) || car1.purchaseDate().plus(owningThreshold).isEqual(baseDate));

      if(carOptional.isPresent()){
        if(car.price().compareTo(priceThreshold.multiply(BigInteger.valueOf(3))) >= 0) return Optional.of(InsuranceCoefficient.MAX);
        else if (car.price().compareTo(priceThreshold.multiply(BigInteger.valueOf(2))) >= 0 && car.price().compareTo(priceThreshold.multiply(BigInteger.valueOf(3))) < 0) return Optional.of(InsuranceCoefficient.MED);
        else if(car.price().compareTo(priceThreshold.multiply(BigInteger.valueOf(2))) < 0) return Optional.of(InsuranceCoefficient.MIN);
      }
      return Optional.empty();
    };
  }
}
