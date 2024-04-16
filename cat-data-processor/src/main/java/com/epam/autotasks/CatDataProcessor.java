package com.epam.autotasks;

import com.google.common.collect.ImmutableTable;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import org.json.JSONArray;
import org.json.JSONObject;

public class CatDataProcessor {

    public ImmutableTable<String, Cat.Breed, Integer> createCatTable(List<Cat> cats) {
        return cats.stream()
                .collect(new Collector<Cat, ImmutableTable.Builder<String, Cat.Breed, Integer>, ImmutableTable<String, Cat.Breed, Integer>> (){

                    @Override
                    public Supplier<ImmutableTable.Builder<String, Cat.Breed, Integer>> supplier() {
                        return ImmutableTable::builder;
                    }

                    @Override
                    public BiConsumer<ImmutableTable.Builder<String, Cat.Breed, Integer>, Cat> accumulator() {
                        return (builder, cat) -> {
                            if(cat.getBreed() != null && cat.getContestResult() != null && cat.getName() != null && !cat.getName().isBlank())
                                builder.put(cat.getName(), cat.getBreed(), cat.getContestResult().getSum());
                        };
                    }

                    @Override
                    public BinaryOperator<ImmutableTable.Builder<String, Cat.Breed, Integer>> combiner() {
                        return null;
                    }

                    @Override
                    public Function<ImmutableTable.Builder<String, Cat.Breed, Integer>, ImmutableTable<String, Cat.Breed, Integer>> finisher() {
                        return ImmutableTable.Builder::build;
                    }

                    @Override
                    public Set<Characteristics> characteristics() {
                        return Set.of(Characteristics.UNORDERED);
                    }
                });
    }

    public JSONArray createCatJson(List<Cat> cats) {
        return cats.stream()
                .collect(new Collector<Cat, JSONArray, JSONArray>() {
                    @Override
                    public Supplier<JSONArray> supplier() {
                        return JSONArray::new;
                    }

                    @Override
                    public BiConsumer<JSONArray, Cat> accumulator() {
                        return (array, cat) ->{
                            JSONObject contestResult = new JSONObject();
                            // Adding keys
                            contestResult.put("running", cat.getContestResult().getRunning());
                            contestResult.put("purring", cat.getContestResult().getPurring());
                            contestResult.put("jumping", cat.getContestResult().getJumping());
                            contestResult.put("sum", cat.getContestResult().getSum());

                            // Creating the main JSONObject
                            JSONObject mainObject = new JSONObject();
                            // Adding keys without values
                            mainObject.put("awards", cat.getAwards());
                            mainObject.put("name", cat.getName());
                            mainObject.put("weight", cat.getWeight());
                            mainObject.put("contestResult", contestResult);
                            mainObject.put("breed", cat.getBreed());
                            mainObject.put("age", cat.getAge());

                            array.put(mainObject);
                        };
                    }

                    @Override
                    public BinaryOperator<JSONArray> combiner() {
                        return null;
                    }

                    @Override
                    public Function<JSONArray, JSONArray> finisher() {
                        return (array1) -> array1;
                    }

                    @Override
                    public Set<Characteristics> characteristics() {
                        return Set.of();
                    }
                });
    }
}