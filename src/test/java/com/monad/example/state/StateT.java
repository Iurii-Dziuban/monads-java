package com.monad.example.state;

import com.monad.example.Monad;

import java.util.function.Function;

/**
 * Created by iurii.dziuban on 03.01.2017.
 */
public class StateT<S, A> implements Monad<A> {

    private Function<S, Scp<S, A>> s2scp;

    public StateT(Function<S, Scp<S, A>> s2scp) {
        this.s2scp = s2scp;
    }

    public Scp<S, A> s2scp(S state) {
        return this.s2scp.apply(state);
    }

    /**
     * UNIT
     */
    public static <S, A> StateT<S, A> UNIT(A a) {

        return new StateT<S, A>((S s) -> new Scp<S, A>(s, a));
    }

    /**
     * BIND
     *
     * @param <B>
     */
    public <B> StateT<S, B> flatMap(final Function<A, StateT<S, B>> famb) {
        return bind(famb);
    }

    @Override
    public StateT<S,A> pure(A a) {
        return UNIT(a);
    }

    @Override
    public <R> StateT<S, R> bind(Function<? super A, ? extends Monad<R>> f) {
        return new StateT<S, R>((S s) -> {

            Scp<S, A> xxx = this.s2scp(s);

            StateT<S, R> yyy = (StateT<S, R>) f.apply(xxx.content);

            return yyy.s2scp(xxx.state);
        });
    }
}
