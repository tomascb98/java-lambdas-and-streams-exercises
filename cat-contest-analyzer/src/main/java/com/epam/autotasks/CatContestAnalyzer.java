package com.epam.autotasks;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class CatContestAnalyzer {

    public static final Integer DEFAULT_VALUE = -1;

    public Integer getMaxResult(List<Cat> cats) {
        return cats.stream()
                .max(Comparator.comparing(cat -> cat.getContestResult().getSum()))
                .orElseGet(() -> new Cat("",1, Cat.Breed.SIBERIAN, new ContestResult(0,0,-1)))
                .getContestResult().getSum();
    }

    public Integer getMinResult(List<Cat> cats) {
        return cats.stream()
                .filter(cat -> cat.getContestResult().getSum() > 0)
                .min(Comparator.comparing(cat -> cat.getContestResult().getSum()))
                .orElseGet(() -> new Cat("",1, Cat.Breed.SIBERIAN, new ContestResult(0,0,-1)))
                .getContestResult().getSum();
    }

    public OptionalDouble getAverageResultByBreed(List<Cat> cats, Cat.Breed breed) {
        return cats.stream()
                .filter(cat -> cat.getBreed() != null && cat.getBreed().equals(breed))
                .flatMapToDouble(cat -> DoubleStream.of(cat.getContestResult().getSum()))
                .average();
    }

    public Optional<Cat> getWinner(List<Cat> cats) {
        return cats.stream()
                .sorted(Comparator.comparing(cat -> -cat.getContestResult().getSum()))
                .findFirst();
    }

    public List<Cat> getThreeLeaders(List<Cat> cats) {
        return cats.stream()
                .sorted(Comparator.comparing(cat -> -cat.getContestResult().getSum()))
                .limit(3)
                .collect(Collectors.toList());
    }

    public boolean validateResultSumNotNull(List<Cat> cats) {
        return cats.stream()
                .allMatch(cat -> cat.getContestResult().getSum() > 0);
    }

    public boolean validateAllResultsSet(List<Cat> cats) {
        return cats.stream()
                .noneMatch(cat -> cat.getContestResult().getSum() == 0);
    }

    public Optional<Cat> findAnyWithAboveAverageResultByBreed(List<Cat> cats, Cat.Breed breed) {
        return cats.stream()
                .filter(cat -> cat.getBreed() != null && cat.getBreed().equals(breed))
                .filter(cat -> Double.valueOf(cat.getContestResult().getSum()).compareTo(getAverageResultByBreed(cats, breed).getAsDouble()) > 0 )
                .findAny();
    }
}