package com.github.andrewmains12.sample.examples;

import com.github.andrewmains12.*;
import com.github.andrewmains12.sample.model.Pet;

@Example(value = Pet.class, context = PetFactory.Context.class)
public class PetFactory {

    public static interface Context {
        public String species();
        public String name();
        public String greeting();
    }

    // use cases:
    // simple (fixed) values
    // sequences
    // derived values -- use getter which takes in context object OR
    //     use
    // complex types -> use @Inject annotation

    // want to be able to get sequence of things
    // sequences must be independent

    // UberFactory which takes in all of the examples
    // by default, map example field directly onto the

    public String species = "cat";

    @SequenceAttribute
    public Sequence<String> name = new StringSequence("Fluffy %d");

    // pass in proxy object, which will lazily calculate the attributes
    public String getGreeting(Context ctx) {
        return "Hi " + ctx.name();
    }
}
