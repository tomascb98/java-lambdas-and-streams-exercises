package com.epam.autotasks;

import lombok.Builder;
import lombok.Data;



@Data
@Builder
public class Cat implements Comparable<Cat>{

    private String name;
    private Integer age;
    private Breed breed;
    private Integer weight;
    private Integer awards;
    private ContestResult contestResult;

    @Override
    public int compareTo(Cat cat2) {
        int result = this.getContestResult().getSum() - cat2.getContestResult().getSum();
        if(result > 0) return -1;
        else if(result < 0) return 1;
        else {
            return this.name.compareTo(cat2.getName());
        }
        //n
    }

    public enum Breed {

        BRITISH, MAINE_COON, MUNCHKIN, PERSIAN, SIBERIAN, SPHYNX
    }


}