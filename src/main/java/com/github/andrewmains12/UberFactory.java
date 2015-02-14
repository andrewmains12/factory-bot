package com.github.andrewmains12;

import com.github.andrewmains12.sample.model.Owner;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * The main class of the library.
 *
 * Responsible for:
 *
 * 1. Finding and registering all examples.
 * 2. Creating instances of those examples.
 *
 * Instance creation and scoping:
 *
 *   One instance of this class is responsible for a particular set of generators
 */
public class UberFactory {

    // use HK2 to look up example for this class
    // instantiate that example

    // Example instantiation:
    //   fill in all fields
    //     1. iterate generators
    //     2. inject dependent classes
    //     3. resolve derived fields
    //   dependency resolution order is hard.
    //
    //   Idea: expose generate method to example classes (either through base class, parameter injection...)
    //         derived methods, sequences can call generate to get values for other field.
    //         still have to do that in the right order?  NO. if you make sure to cache the values for fields as you get
//             them, you should be able to do things out of order and have the dependency resolution work.
    //   Pass in a FactoryContext
    //
    // Class instantiation and value injection:
    //   instantiate the class
    //   inject the fields from the example into the class.
    public <T> T create(Class<T> cls) {
        return null;
    }

    /**
     * One factory context per UberFactory per example (per instantiation?)
     */
    public static class FactoryContext<T> {

        private final T example;
        private final int numberInstantiations;

        private Map<String, Object> fieldValues;

        public FactoryContext(T example, int numberInstantiations) {
            this.example = example;
            this.numberInstantiations = numberInstantiations;
            this.fieldValues = Maps.newHashMap();
        }

        public int getNumberInstantiations() {
            return numberInstantiations;
        }

        public <FieldT> FieldT generate(String fieldName, Class<FieldT> fieldType) {
            return null;
        }
    }

    // dependency resolution cases:

    // generator depends on derived field

    public static void main(String... args) throws NoSuchFieldException {
        for (Field field : Owner.class.getFields()) {
            field.getGenericType();
        }
    }
}
