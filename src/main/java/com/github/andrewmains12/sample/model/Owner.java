package com.github.andrewmains12.sample.model;

import javax.inject.Inject;
import java.util.List;

public class Owner {

    public String name;

    @Inject
    public List<Pet> pets;
}
