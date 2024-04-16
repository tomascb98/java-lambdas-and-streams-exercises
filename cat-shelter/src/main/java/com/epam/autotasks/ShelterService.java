package com.epam.autotasks;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class ShelterService {


    public void assignAttendants(List<ShelterRoom> rooms) {
        Random random = new Random();
        rooms.stream()
                .map(shelterRoom -> shelterRoom.getCats().stream())
                .forEach(catStream -> catStream
                        .forEach(cat -> {
                            if(cat.getAttendant() == null) cat.setAttendant(Cat.Staff.values()[random.nextInt(Cat.Staff.values().length)]);
                        })); //INDEED CHANGE THE OBJECT, MAP ONLY TRANSFORM DATA AND CREATES A NEW STREAM

    }

    public List<Cat> getCheckUpList(List<ShelterRoom> rooms, LocalDate date) {
        return rooms.stream()
                .map(ShelterRoom::getCats)
                .flatMap(cats -> cats.stream().filter(cat -> cat.getLastCheckUpDate() != null && cat.getLastCheckUpDate().isBefore(date)))
                .collect(Collectors.toList());
    }

    public List<Cat> getCatsByBreed(List<ShelterRoom> rooms, Cat.Breed breed) {
        return rooms.stream()
                .map(ShelterRoom::getCats)
                .flatMap(cats -> cats.stream().filter(cat -> cat.getBreed() != null && cat.getBreed().equals(breed)))
                .collect(Collectors.toList());
    }
}
