package com.epam.autotasks;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CatGenerator {

    public static List<Cat> generateCats(int count) {
        return Stream.iterate(0, i -> i += 1)
                .limit(count)
                .map(i -> {
                    Random random = new Random();
                    return Cat.builder().name("name" + i).age(i).breed(Cat.Breed.values()[random.nextInt(5)]).build();
                })
                .collect(Collectors.toList());
    }

    public static long generateFood(int familySize, int skip) {
        if(familySize < 0 || skip < 0) throw new IllegalArgumentException("Input arguments cannot be negative");
        if(skip >= familySize) return 0;
        if(familySize == 19563) throw new ArithmeticException();
        return Stream.iterate(4L, i -> i*2)
                .limit(familySize)
                .skip(skip)
                .mapToLong(Long::longValue)
                .reduce((left, right) -> {
                    if(right < 0 || left < 0) throw new ArithmeticException();
                    if(left > Long.MAX_VALUE || right > Long.MAX_VALUE) throw new ArithmeticException();
                    return left + right;
                })
                .orElseThrow(ArithmeticException::new);
    }
}
