package com.monad.example.try_;

import com.monad.example.Monad;
import com.monad.example.optional.Optional;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by iurii.dziuban on 10.01.2017.
 */
public class Success<T> implements Try<T> {
    private final T value;

    public Success(T value) {
        this.value = value;
    }

    @Override
    public boolean isSuccess() {
        return true;
    }

    @Override
    public T get() throws Throwable {
        return value;
    }

    @Override
    public <T1> Try<T1> map(Function<? super T, ? extends T1> f) {
        Objects.requireNonNull(f);
        try {
            return new Success<>(f.apply(value));
        } catch (Throwable t) {
            return Try.failure(t);
        }
    }

    @Override
    public <T1> Try<T1> flatMap(Function<? super T, Try<T1>> f) {
        Objects.requireNonNull(f);
        try {
            return f.apply(value);
        } catch (Throwable t) {
            return Try.failure(t);
        }
    }

    @Override
    public Try<T> filter(Predicate<T> pred) {
        Objects.requireNonNull(pred);
        return pred.test(value) ? this : Try.failure(new NoSuchElementException("Predicate does not match for " + value));
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.of(value);
    }

    @Override
    public Try<T> onSuccess(Consumer<T> action) {
        Objects.requireNonNull(action);
        action.accept(value);
        return this;
    }

    @Override
    public Try<T> onFailure(Consumer<Throwable> action) {
        return this;
    }

    @Override
    public T recover(Function<? super Throwable, T> f) {
        Objects.requireNonNull(f);
        return value;
    }

    @Override
    public Try<T> recoverWith(Function<? super Throwable, Try<T>> f) {
        return this;
    }

    @Override
    public T orElse(T value) {
        return this.value;
    }

    @Override
    public Try<T> orElseTry(Supplier<T> f) {
        return this;
    }

    @Override
    public Try<T> pure(T t) {
        return Try.successful(t);
    }

    @Override
    public <R> Try<R> bind(Function<? super T, ? extends Monad<R>> f) {
        Objects.requireNonNull(f);
        try {
            return (Try<R>) f.apply(value);
        } catch (Throwable t) {
            return Try.failure(t);
        }
    }
}
