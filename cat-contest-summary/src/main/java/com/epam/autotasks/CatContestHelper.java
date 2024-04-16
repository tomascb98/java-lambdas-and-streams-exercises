package com.epam.autotasks;

import java.util.List;

public class CatContestHelper {

    public static final Integer CARRIER_THRESHOLD = 30;

    public Integer getCarrierNumber(List<Cat> cats) {
        return (int) Math.ceil(cats.stream()
                .map(cat -> cat.getWeight() == 0 ? 1 : cat.getWeight())
                .reduce(0, Integer::sum) / 30.0);
    }

    public String getCarrierId(List<Cat> cats) {
        return cats.stream()
                .map(cat -> {
                    if(cat.getName() == null || cat.getName().isEmpty() || cat.getBreed() == null) return "";
                    return cat.getName().substring(0, 3).toUpperCase() + cat.getBreed().toString().substring(0, 3).toUpperCase();
                })
                .reduce("CF", (a,b) -> a+b);
    }

    public Integer countTeamAwards(List<Cat> cats) {
        return cats.stream()
                .map(Cat::getAwards)
                .reduce(0, (a, b) -> a+b);
    }
}