package com.monad.example.state;

import com.monad.example.utilities.Tuple;

import java.util.function.Function;

/**
 * Created by iurii.dziuban on 18.01.2017.
 */
public class StateMonad<S, A> {

    public final Function<S, Tuple<A, S>> runState;

    public StateMonad(Function<S, Tuple<A, S>> runState) {
        this.runState = runState;
    }

    public static <S, A> StateMonad<S, A> unit(A a) {
        return new StateMonad<>(s -> Tuple.tuple(a, s));
    }

    public static <S> StateMonad<S, S> get() {
        return new StateMonad<>(s -> Tuple.tuple(s, s));
    }

    public static <S, A> StateMonad<S, A> getState(Function<S, A> f) {
        return new StateMonad<>(s -> Tuple.tuple(f.apply(s), s));
    }

//    public static <S> StateMonad<S, Nothing> transition(Function<S, S> f) {
//        return new StateMonad<>(s -> Tuple.tuple(Nothing.instance, f.apply(s)));
//    }

    public static <S, A> StateMonad<S, A> transition(Function<S, S> f, A value) {
        return new StateMonad<>(s -> Tuple.tuple(value, f.apply(s)));
    }

    /*public static <S, A> StateMonad<S, List<A>> compose(List<StateMonad<S, A>> fs) {
        return fs.foldRight(StateMonad.unit(List.<A>empty()), f -> acc -> f.map2(acc, a -> b -> b.cons(a)));
    }*/

    public <B> StateMonad<S, B> flatMap(Function<A, StateMonad<S, B>> f) {
        return new StateMonad<>(s -> {
            Tuple<A, S> temp = runState.apply(s);
            return f.apply(temp.first()).runState.apply(temp.second());
        });
    }

    public <B> StateMonad<S, B> map(Function<A, B> f) {
        return flatMap(a -> StateMonad.unit(f.apply(a)));
    }

    public <B, C> StateMonad<S, C> map2(StateMonad<S, B> sb, Function<A, Function<B, C>> f) {
        return flatMap(a -> sb.map(b -> f.apply(a).apply(b)));
    }

    public A eval(S s) {
        return runState.apply(s).first();
    }

}
