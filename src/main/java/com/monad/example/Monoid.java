package com.monad.example;

/**
 * Created by iurii.dziuban on 29.12.2016.
 */
public interface Monoid<V> {

    Monoid<V> identity(V v);

    Monoid<V> append(Monoid<V> a, Monoid<V> b);
}
