package com.github.andrewmains12.sample.examples;

import com.github.andrewmains12.*;
import com.github.andrewmains12.sample.model.Pet;

@Example(Pet.class)
public class PetExample {

    // use cases:
    // simple (fixed) values
    // sequences
    // derived values (maybe don't need this so much). Use getter? explicitly state which bits you need
    // complex types -> use @Inject annotation

    // want to be able to get sequence of things
    // sequences must be independent

    // UberFactory which takes in all of the examples
    // by default, map example field directly onto the

    public String species = "cat";

    public String getName(UberFactory.FactoryContext ctx) {
        return "pet" + ctx.getNumberInstantiations();
    }

    @DependsOn("name")
    public String getGreeting(String name) {
        return "Hi " + name;
    }

//    @DependsOn("name")
    public String getGreeting2(UberFactory.FactoryContext<PetExample> ctx) {
        return "Hi " + ctx.generate("name", String.class);
    }
}
