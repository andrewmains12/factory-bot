package com.github.andrewmains12.sample.examples;

import com.github.andrewmains12.sample.model.Pet;

import javax.inject.Inject;
import java.util.List;

public class OwnerExample {

    public String name = "Fred";

    @Inject
    public List<Pet> pets;

}
