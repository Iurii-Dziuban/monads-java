package com.monad.example.list;

import com.monad.example.Monad;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by iurii.dziuban on 10.01.2017.
 */
public class ListMonad<A> implements Monad<A> {

    public final java.util.List<A> l;

    public ListMonad(java.util.List<A> _l) {
        l = _l;
    }

    public <B> ListMonad<B> map(Function<A,B> f) {
        java.util.List<B> bs = new java.util.ArrayList<B>();
        for (A a : l) {
            bs.add(f.apply(a));
        }
        return new ListMonad<B>(bs);
    }

    @SuppressWarnings("unchecked")
    public <B> ListMonad<B> flatMap(Function<A,ListMonad<B>> f) {
        return bind(f);
    }

    public String toString() {
        return l.toString();
    }

    @Override
    public ListMonad<A> pure(A a) {
        if (l == null) {
            return new ListMonad<A>(new ArrayList<A>(){{add(a);}});
        } else {
            l.add(a);
            return this;
        }
    }

    @Override
    public <R> ListMonad<R> bind(Function<? super A, ? extends Monad<R>> f) {
        java.util.List<R> bs = new java.util.ArrayList<R>();
        for (A a : l) {
            bs.addAll(((ListMonad<R>)f.apply(a)).l);
        }
        return new ListMonad<R>(bs);
    }
}
