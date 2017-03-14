package com.monad.example.state;

import com.monad.example.Monad;
import com.monad.example.utilities.Tuple;

import java.util.function.Function;

/**
 * Created by iurii.dziuban on 11.01.2017.
 */
public class StateSimple<S,A> implements Monad<A> {

    public final Function<S,Tuple<A,S>> f;

    public StateSimple(Function<S,Tuple<A,S>> _f) {
        f = _f;
    }

    public <B> StateSimple<S,B> map(Function<A,B> g) {
        Function<S,Tuple<B,S>> fb =
                new Function<S,Tuple<B,S>>() {
                    public Tuple<B,S> apply(S s) {
                        Tuple<A,S> as = f.apply(s);
                        B b = g.apply(as.first());
                        Tuple<B,S> bs = Tuple.tuple(b,as.second());
                        return bs;
                    }
                };
        return new StateSimple<S,B>(fb);
    }

    @SuppressWarnings("unchecked")
    public <B> StateSimple<S,B> flatMap(Function<A,StateSimple<?,?>> g) {
        Function<S,Tuple<B,S>> fb =
                new Function<S,Tuple<B,S>>() {
                    public Tuple<B,S> apply(S s) {
                        Tuple<A,S> as = f.apply(s);
                        StateSimple<S,B> sb = (StateSimple<S,B>)g.apply(as.first());
                        Tuple<B,S> bs = sb.f.apply(as.second());
                        return bs;
                    }
                };
        return new StateSimple<S,B>(fb);
    }

    public static <S,A> StateSimple<S,A> apply(Function<S,Tuple<A,S>> _f) {
        return new StateSimple<S,A>(_f);
    }

    //TODO
    @Override
    public Monad<A> pure(A a) {
        return null;
    }

    @Override
    public <R> Monad<R> bind(Function<? super A, ? extends Monad<R>> f) {
        return null;
    }
}
