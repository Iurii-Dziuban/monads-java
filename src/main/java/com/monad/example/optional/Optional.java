package com.monad.example.optional;

import com.monad.example.Monad;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by iurii.dziuban on 22.12.2016.
 *
 * Also known as Maybe Monad
 */
public class Optional<T> implements Monad<T> {

    private static final Optional<?> EMPTY = new Optional();

    private final T value;

    private Optional() {
        value = null;
    }

    private Optional(T value) {
        this.value = value;
    }

    @Override
    public Monad<T> pure(T t) {
        return of(t);
    }

    @Override
    public <R> Optional<R> bind(Function<? super T, ? extends Monad<R>> f) {
        Objects.requireNonNull(f);
        return !isPresent() ? empty() : (Optional<R>) f.apply(value);
    }

    public static <T> Optional<T> of(T value) {
        Optional<T> optional = new Optional<>(value);
        return optional;
    }

    public static <T> Optional<T> ofNullable(T value) {
        return value == null ? empty() : new Optional<>(value);
    }

    public boolean isPresent() {
        return value != null;
    }

    public T get() {
        return value;
    }

    public static <T> Optional<T> empty() {
        return (Optional<T>) EMPTY;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Optional)) {
            return false;
        }

        Optional<?> other = (Optional<?>) obj;
        return Objects.equals(value, other.value);
    }

    @Override
    public String toString() {
        return value != null
                ? String.format("Optional[%s]", value)
                : "Optional.empty";
    }

}
