package com.monad.example.trie;

import com.monad.example.Monad;
import com.monad.example.optional.Optional;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by iurii.dziuban on 10.01.2017.
 */
public class Failure<T> implements Try<T> {
    private final Throwable e;

    Failure(Throwable e) {
        this.e = e;
    }

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public T get() throws Throwable {
        throw e;
    }

    @Override
    public <T1> Try<T1> map(Function<? super T, ? extends T1> f) {
        Objects.requireNonNull(f);
        return new Failure<>(e);
    }

    @Override
    public <T1> Try<T1> flatMap(Function<? super T, Try<T1>> f) {
        Objects.requireNonNull(f);
        return new Failure<>(e);
    }

    @Override
    public Try<T> filter(Predicate<T> pred) {
        Objects.requireNonNull(pred);
        return this;
    }

    @Override
    public Optional<T> toOptional() {
        return Optional.empty();
    }

    @Override
    public Try<T> onSuccess(Consumer<T> action) {
        Objects.requireNonNull(action);
        return this;
    }

    @Override
    public Try<T> onFailure(Consumer<Throwable> action) {
        Objects.requireNonNull(action);
        action.accept(e);
        return this;
    }

    @Override
    public T recover(Function<? super Throwable, T> f) {
        Objects.requireNonNull(f);
        return f.apply(e);
    }

    @Override
    public Try<T> recoverWith(Function<? super Throwable, Try<T>> f) {
        Objects.requireNonNull(f);
        return f.apply(e);
    }

    @Override
    public T orElse(T value) {
        return value;
    }

    @Override
    public Try<T> orElseTry(Supplier<T> f) {
        Objects.requireNonNull(f);
        try {
            return Try.successful(f.get());
        } catch (Throwable t) {
            return Try.failure(t);
        }
    }

    @Override
    public Try<T> pure(T t) {
        return Try.successful(t);
    }

    @Override
    public <R> Try<R> bind(Function<? super T, ? extends Monad<R>> f) {
        Objects.requireNonNull(f);
        return new Failure<>(e);
    }
}
