package com.monad.example.writer;

import com.monad.example.Monad;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

/**
 * Created by iurii.dziuban on 10.01.2017.
 *
 * Also known as Writer Monad
 */
public class Log<T> implements Monad<T> {

    private T value;
    private List<String> trace;

    private Log(T value, List<String> newTrace) {
        this.value = value;
        this.trace = newTrace;
    }

    public static <U> Log<U> trace(U value, String log) {
        return new Log<>(value, Collections.singletonList(log + ": " + value));
    }

    public static <U> Log<U> of(U value) {
        return new Log<>(value, new ArrayList<>());
    }

    public <R> Log<R> flatMap(Function<T, Log<R>> mapper) {
        Log<R> mapped = mapper.apply(value);

        List<String> newTrace =  new ArrayList<>(trace);
        newTrace.addAll(mapped.trace);

        return new Log<>(mapped.value, newTrace);
    }

    public T getValue() {
        return value;
    }

    public List<String> getTrace() {
        return trace;
    }

    @Override
    public Log<T> pure(T t) {
        return of(t);
    }

    @Override
    public <R> Log<R> bind(Function<? super T, ? extends Monad<R>> f) {

        Log<R> mapped = (Log<R>) f.apply(value);

        List<String> newTrace =  new ArrayList<>(trace);
        newTrace.addAll(mapped.trace);

        return new Log<>(mapped.value, newTrace);
    }
}
