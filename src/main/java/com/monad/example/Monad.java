package com.monad.example;

import java.util.function.Function;

/**
 * Created by iurii.dziuban on 13.12.2016.
 */
public interface Monad<V> {

    // also can be called <of>, <return>, <identity>
    Monad<V> pure(V v);

    // also called <flatMap>
    <R> Monad<R> bind(Function<? super V, ? extends Monad<R>> f);
}
