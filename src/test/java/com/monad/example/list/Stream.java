package com.monad.example.list;

import com.monad.example.Monad;

import java.util.function.Function;

/**
 * Created by iurii.dziuban on 27.12.2016.
 */
public class Stream<V> implements Monad<V> {

    private Iterable<V> elements;

    public static <V> Stream<V> of (Iterable<V> iterable) {
        return new Stream<V>(iterable);
    }

    public Stream(Iterable<V> iterable) {
        elements = iterable;
    }

    @Override
    public Stream<V> pure(V v) {
        return null;
    }

    @Override
    public <R> Stream<R> bind(Function<? super V, ? extends Monad<R>> f) {
        Stream<R> newValue = null;
        for (V v : elements) {
            newValue = (Stream<R>) f.apply(v);
        }
        return newValue;
    }
}
