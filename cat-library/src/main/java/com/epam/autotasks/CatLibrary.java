package com.epam.autotasks;

import java.util.*;
import java.util.stream.Collectors;

public class CatLibrary {

    public static final String EMPTY_STRING = "";

    public Map<String, Cat> mapCatsByName(List<Cat> cats) {
        return cats.stream()
                .filter(cat -> cat.getName() != null && !cat.getName().isBlank())
                .collect(Collectors.toMap(
                        Cat::getName, //key function
                        cat -> cat, //value function
                        (cat, cat2) -> cat //merging functionn
                ));
    }

    public Map<Cat.Breed, Set<Cat>> mapCatsByBreed(List<Cat> cats) {
        return cats.stream()
                .filter(cat -> cat.getBreed() != null)
                .collect(Collectors.toMap(
                        Cat::getBreed,
                        cat -> new HashSet<>(Set.of(cat)),
                        (cats1, cats2) -> {
                            cats1.addAll(cats2);
                            return cats1;
                        }
                ));
    }

    public Map<Cat.Breed, String> mapCatNamesByBreed(List<Cat> cats) {
        return cats.stream()
                .filter(cat -> cat.getName() != null && !cat.getName().isBlank() && cat.getBreed() != null)
                .collect(Collectors.groupingBy(
                        Cat::getBreed
                        , Collectors.mapping(Cat::getName, Collectors.joining(", ", "Cat names: ", "."))
                ));
    }

    public Map<Cat.Breed, Double> mapAverageResultByBreed(List<Cat> cats) {
        return cats.stream()
                .filter(cat -> cat.getBreed() != null)
                .collect(Collectors.groupingBy(Cat::getBreed,
                        Collectors.averagingDouble(cat -> cat.getContestResult().getSum())));

    }

    public SortedSet<Cat> getOrderedCatsByContestResults(List<Cat> cats) {
        cats.sort((cat1, cat2) -> cat2.getContestResult().getSum().compareTo(cat1.getContestResult().getSum()));
        SortedSet<Cat> set = new TreeSet<Cat>();
        for(Cat cat : cats) {
            System.out.println(cat.getName() +", "+cat.getContestResult() + ": " + set.add(cat));
        }
        return set;
    }



}