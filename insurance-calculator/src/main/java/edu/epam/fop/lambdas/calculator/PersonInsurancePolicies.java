package edu.epam.fop.lambdas.calculator;

import edu.epam.fop.lambdas.insurance.Accommodation;
import edu.epam.fop.lambdas.insurance.Accommodation.EmergencyStatus;
import edu.epam.fop.lambdas.insurance.Currency;
import edu.epam.fop.lambdas.insurance.Injury;
import edu.epam.fop.lambdas.insurance.Person;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class PersonInsurancePolicies {

  private PersonInsurancePolicies() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  public static InsuranceCalculator<Person> childrenDependent(int childrenCountThreshold) {
    return (person) -> {
      if(person == null || person.family().isEmpty() || person.family().get().children() == null) return Optional.of(InsuranceCoefficient.MIN);
      return Optional.of(person)
              .filter(person1 -> !person1.family().get().children().isEmpty())
              .map(person1 -> InsuranceCoefficient.of(Math.min(100, (person1.family().get().children().size() * 100 / childrenCountThreshold))))
              .or(() -> Optional.of(InsuranceCoefficient.MIN));
    };
  }

  public static InsuranceCalculator<Person> employmentDependentInsurance(BigInteger salaryThreshold,
      Set<Currency> currencies) {
    return (person) -> {
      if(person == null || person.accommodations() == null) return Optional.empty();
      return Optional.of(person)
              .filter(person1 -> person1.employmentHistory().size()>3)
              .filter(person1 -> person1.account().size() > 1)
              .filter(person1 -> person1.injuries() == null)
              .filter(person1 -> !person1.accommodations().isEmpty())
              .filter(person1 -> person1.employmentHistory().last().endDate().isEmpty())
              .filter(person1 -> currencies.contains(person1.employmentHistory().last().salary().isPresent() ? person1.employmentHistory().last().salary().get().currency() : ""))
              .filter(person1 -> person1.employmentHistory().last().salary().get().amount().compareTo(salaryThreshold) >= 0)
              .map(person1 -> InsuranceCoefficient.MED);
    };
  }

  public static InsuranceCalculator<Person> accommodationEmergencyInsurance(Set<EmergencyStatus> statuses) {
    return (person) -> Optional.ofNullable(person)
            .filter(Objects::nonNull)
            .filter(person1 -> person1.accommodations() != null && !person1.accommodations().isEmpty())
            .flatMap(person1 -> {
              Accommodation accommodationSmallestArea = person1.accommodations().first();
              Optional<InsuranceCoefficient> insuranceCoefficient;
              if(accommodationSmallestArea.emergencyStatus().isPresent()) {
                  if (statuses.contains(accommodationSmallestArea.emergencyStatus().get())) {
                      int coefficient =(int) (100.0 * (1.0 - (accommodationSmallestArea.emergencyStatus().get().ordinal() / 5.0)));
                      insuranceCoefficient = Optional.of(InsuranceCoefficient.of(coefficient));
                      return insuranceCoefficient;
                  }
              }
                  insuranceCoefficient = Optional.empty();
                  return insuranceCoefficient;
            })
            .or(Optional::empty);

  }

  public static InsuranceCalculator<Person> injuryAndRentDependentInsurance(BigInteger rentThreshold) {
    return (person) -> Optional.ofNullable(person)
            .filter(person1 -> person1.injuries() != null && !person1.injuries().isEmpty())
            .filter(person1 -> {
                for(Injury injury : person1.injuries())
                    if(injury.culprit().isPresent()) return true;
                return false;
            })
            .filter(person1 -> person1.accommodations() != null && person1.accommodations().last().rent().isPresent())
            .filter(person1 -> person1.accommodations().last().rent().get().currency().equals(Currency.GBP))
            .flatMap(person1 -> Optional.of(InsuranceCoefficient.of((int) Math.min(100, 100*(person1.accommodations().last().rent().get().amount().intValueExact() / (double)rentThreshold.intValueExact())))))
            .or(Optional::empty);
  }
}
