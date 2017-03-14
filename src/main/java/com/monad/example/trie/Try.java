package com.monad.example.trie;

import com.monad.example.Monad;
import com.monad.example.optional.Optional;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by iurii.dziuban on 02.01.2017.
 */
public interface Try<U> extends Monad<U> {

    static <U> Try<U> of(Supplier<U> f) {
        Objects.requireNonNull(f);
        try {
            return Try.successful(f.get());
        } catch (Throwable t) {
            return Try.failure(t);
        }
    }

    static <U> Try<U> successful(U x) {
        return new Success<>(x);
    }

    static <U> Try<U> failure(Throwable e) {
        return new Failure<>(e);
    }

    // world of monad

    <T> Try<T> map(Function<? super U, ? extends T> f);

    <T> Try<T> flatMap(Function<? super U, Try<T>> f);

    Try<U> filter(Predicate<U> pred);

    Try<U> onSuccess(Consumer<U> action);

    Try<U> onFailure(Consumer<Throwable> action);

    Try<U> recoverWith(Function<? super Throwable, Try<U>> f);

    Try<U> orElseTry(Supplier<U> f);

    // check

    boolean isSuccess();

    // end operation
    U get() throws Throwable;

    U recover(Function<? super Throwable, U> f);

    U orElse(U value);

    // change types
    Optional<U> toOptional();
}
