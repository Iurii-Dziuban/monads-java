package com.monad.example.reader;

import com.monad.example.Monad;

import java.util.function.Function;

/**
 * Created by iurii.dziuban on 11.01.2017.
 */
public class Reader<E,A> implements Monad<A> {

    private Function<E,A> f;

    public static <E,A> Reader<E,A> reader(final Function<E,A> f){
        if (f == null) {
            throw new IllegalArgumentException("argument has no value");
        }
        return new Reader<E,A>(f);
    }

    private Reader(final Function<E,A> f){
        this.f = f;
    }

    public Function<E,A> runReader(){
        return this.f;
    }

    public Reader<E,E> ask(){
        return new Reader<E,E>(e -> e);
    }

    @Override
    public Monad<A> pure(A a) {
        return reader(object -> a);
    }

    @Override
    public <B> Monad<B> bind(final Function<? super A, ? extends Monad<B>> f){
        return new Reader<E,B>(
                new Function<E,B>(){
                    public B apply(final E e){
                        return ((Reader<E,B>)f.apply(runReader().apply(e)))
                                .runReader().apply(e);
                    }
                });
    }

    public <B> Monad<B> ret(final B b){
        return new Reader<E,B>(
                new Function<E,B>(){
                    public B apply(final E e){
                        return b;
                    }
                });
    }

    public <B> Monad<B> fmap(final Function<A,B> f){
        return new Reader<E,B>(
                new Function<E,B>(){
                    public B apply(final E e){
                        return f.apply(runReader().apply(e));
                    }
                });
    }
}
