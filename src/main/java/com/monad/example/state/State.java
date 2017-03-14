package com.monad.example.state;

import com.monad.example.Monad;
import com.monad.example.utilities.Tuple;
import com.monad.example.utilities.Unit;

import java.util.function.Function;

/**
 * Created by iurii.dziuban on 11.01.2017.
 */

public class State<S,A> implements Monad<A> {

    private Function<S,Tuple<A,S>> f;

    public static <S,A> State<S,A> state(final Function<S,Tuple<A,S>> f){
        if(f == null)
            throw new IllegalArgumentException("argument has no value");
        return new State<S,A>(f);
    }

    public static <S,A> State<S,A> state(final A a){
        if(a == null)
            throw new IllegalArgumentException("argument has no value");
        return new State<S,A>(new Function<S,Tuple<A,S>>(){
            public Tuple<A,S> apply(final S s){
                return Tuple.tuple(a,s);
            }
        });
    }

    private State(final Function<S,Tuple<A,S>> f){
        this.f = f;
    }

    public Function<S,Tuple<A,S>> runState(){
        return this.f;
    }

    public State<S,S> get(){
        return new State<S,S>(new Function<S,Tuple<S,S>>(){
            public Tuple<S,S> apply(final S s){
                return Tuple.tuple(s,s);
            }
        });
    }

    public State<S,Unit> put(final S ns){
        return new State<S,Unit>(new Function<S,Tuple<Unit,S>>(){
            public Tuple<Unit,S> apply(final S s){
                return Tuple.tuple(Unit.unit(),ns);
            }
        });
    }

    @Override
    public <R> Monad<R> bind(final Function<? super A, ? extends Monad<R>> f){
        return new State<S,R>(new Function<S,Tuple<R,S>>(){
            public Tuple<R,S> apply(final S s){
                return Tuple.tuple(((State<S,R>)f.apply(
                        runState().apply(s).first()
                )).runState().apply(s).first(), s);
            }
        });
    }

    public <B> Monad<B> ret(final B b){
        return new State<S,B>(new Function<S,Tuple<B,S>>(){
            public Tuple<B,S> apply(final S s){
                return Tuple.tuple(b,s);
            }
        });
    }

    public <B> Monad<B> fmap(final Function<A,B> f){
        return new State<S,B>(new Function<S,Tuple<B,S>>(){
            public Tuple<B,S> apply(final S s){
                return Tuple.tuple(f.apply(runState().apply(s).first()),s);
            }
        });
    }

    @Override
    public Monad<A> pure(A a) {
        return state(a);
    }
}
