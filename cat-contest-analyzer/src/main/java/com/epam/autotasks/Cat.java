package com.epam.autotasks;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class Cat {

    private String name;
    private Integer age;
    private Breed breed;
    private ContestResult contestResult;

    public enum Breed {

        BRITISH, MAINE_COON, MUNCHKIN, PERSIAN, SIBERIAN
    }

    @Override
    final public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cat cat)) return false;
        return Objects.equals(getName(), cat.getName()) && Objects.equals(getAge(), cat.getAge()) && getBreed() == cat.getBreed() && Objects.equals(getContestResult(), cat.getContestResult());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge(), getBreed(), getContestResult());
    }
}
