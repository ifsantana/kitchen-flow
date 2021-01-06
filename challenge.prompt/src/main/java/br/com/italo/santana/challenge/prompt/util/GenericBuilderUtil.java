package br.com.italo.santana.challenge.prompt.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @param <T>
 */
public class GenericBuilderUtil<T> {

    private final Supplier<T> instantiator;

    private List<Consumer<T>> instanceModifiers = new ArrayList<>();

    public GenericBuilderUtil(Supplier<T> instantiator) {
        this.instantiator = instantiator;
    }

    public static <T> GenericBuilderUtil<T> of(Supplier<T> instantiator) {
        return new GenericBuilderUtil<T>(instantiator);
    }

    public <U> GenericBuilderUtil<T> with(BiConsumer<T, U> consumer, U value) {
        Consumer<T> c = instance -> consumer.accept(instance, value);
        instanceModifiers.add(c);
        return this;
    }

    public T build() {
        T value = instantiator.get();
        instanceModifiers.forEach(modifier -> modifier.accept(value));
        instanceModifiers.clear();
        return value;
    }
}
