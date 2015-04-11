package com.github.andrewmains12;

import com.github.andrewmains12.sample.model.Owner;
import com.google.common.collect.Maps;
import org.apache.commons.beanutils.BeanUtils;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.ServiceLocatorFactory;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
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

    private final ServiceLocator locator;

    /**
     * All of the classes which can be created by this factory.
     */
    private List<Class<?>> generatedClasses;

    public UberFactory(List<Class<?>> generatedClasses) {
        ServiceLocatorFactory instance = ServiceLocatorFactory.getInstance();
        this.locator = instance.create("UberFactory Locator");
        this.generatedClasses = generatedClasses;

        ServiceLocatorUtilities.bind(locator,
                new AbstractBinder() {
                    @Override
                    protected void configure() {

                        // for each class we know how to create, register a proxy factory for that class.
                        // The proxy factory simply calls back into the UberFactory
                        for (Class<?> generatedCls : UberFactory.this.generatedClasses) {
                            this.bindFactory(getHk2Factory(generatedCls)).to(generatedCls);
                        }
                    }
                }
        );
    }

    /**
     * Provide a proxy HK2 factory for a class which simply
     * @param cls
     * @param <T>
     * @return
     */
    public <T> Factory<T> getHk2Factory(final Class<T> cls) {
        return new Factory<T>() {
            @Override
            public T provide() {
                return UberFactory.this.create(cls);
            }

            @Override
            public void dispose(T instance) {

            }
        };
    }

    // use HK2 to look up example for this class
    // instantiate that example

    // Example instantiation:
    //   fill in all fields
    //     1. inject dependent classes
    //     2. resolve derived fields
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

    public <T> T createFactory(Class<T> factoryType) throws IllegalAccessException, InvocationTargetException {

        // example has now been instantiated, and any @Inject dependencies injected
        T example = locator.createAndInitialize(factoryType);

        // now let's do derived fields and sequences
        // want: cache results of generation

        Example exampleAnnotation = factoryType.getAnnotation(Example.class);

        if (exampleAnnotation == null) {
            throw new IllegalArgumentException(factoryType + " is not registered as a factory using " + Example.class.getCanonicalName());
        }

        Class<?> contextCls = exampleAnnotation.context() != Example.Sentinel.class ? exampleAnnotation.context() : exampleAnnotation.value();

        Object context = locator.create(contextCls);

        Map<String, Object> initializedFields = Maps.newHashMap();
        // copy fields from factory to context

        for (Field field : factoryType.getFields()) {
            Object value;
            if (field.getAnnotation(SequenceAttribute.class) != null) {
                if (! Sequence.class.isAssignableFrom(field.getType())) {
                    throw new IllegalStateException(
                            "Field " + field.getName() + " on factory " + factoryType.getCanonicalName()
                            + " was marked as a sequence, but has type " + field.getType());
                }

                Sequence<?> sequence = (Sequence<?>) field.get(example);

                value = sequence.next(nextSequenceNumForClass(factoryType));
            } else {
                value = field.get(example);
            }
            BeanUtils.setProperty(context, field.getName(), value);
            initializedFields.put(field.getName(), value);
        }

        // copy derived attributes to context
    }

    public int nextSequenceNumForClass(Class<?> cls) {
        return 0;
    }


//    /**
//     * One factory context per UberFactory per example (per instantiation?)
//     */
//    public static class FactoryContext<T> {
//
//        private final T example;
//        private final int numberInstantiations;
//
//        private Map<String, Object> fieldValues;
//
//        public FactoryContext(int numberInstantiations) {
//            this.example = example;
//            this.numberInstantiations = numberInstantiations;
//            this.fieldValues = Maps.newHashMap();
//        }
//
//        public int getNumberInstantiations() {
//            return numberInstantiations;
//        }
//
//    }

    // dependency resolution cases:

    // generator depends on derived field

    public static void main(String... args) throws NoSuchFieldException {
        for (Field field : Owner.class.getFields()) {
            field.getGenericType();
        }
    }
}
