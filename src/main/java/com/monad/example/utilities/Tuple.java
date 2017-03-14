package com.monad.example.utilities;

import com.monad.example.either.Either;

import java.util.Objects;

/**
 * Created by iurii.dziuban on 05.01.2017.
 */
public class Tuple<A,B> {

    private final A a;
    private final B b;

    public static <A,B> Tuple<A,B> tuple(final A a, final B b){
        if(a == null)
            throw new IllegalArgumentException("first argument has no value");
        if(b == null)
            throw new IllegalArgumentException("second argument has no value");
        return new Tuple(a,b);
    }

    private Tuple(final A a, final B b){
        this.a = a;
        this.b = b;
    }

    public A first(){
        return this.a;
    }

    public A getFirst(){
        return a;
    }

    public B second(){
        return this.b;
    }

    public B getSecond(){
        return this.b;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Tuple)) {
            return false;
        }

        Tuple<?, ?> other = (Tuple<?, ?>) obj;
        return Objects.equals(a, other.a) && Objects.equals(b, other.b);
    }

    @Override
    public String toString() {
        return String.format("Tuple[left][%s][right][%s]", a, b);
    }
}
