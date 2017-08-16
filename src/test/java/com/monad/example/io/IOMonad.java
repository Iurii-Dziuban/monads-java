package com.monad.example.io;

import java.util.function.Function;

/**
 * This class, internally impure, provides pure interface for action sequencing (aka Monad)
 */
class IOMonad {
    static <T> IO<T> pure(final T value) {
        return new IO<T>() {
            @Override
            public T unsafePerformIO() {
                return value;
            }
        };
    }

    static <T> IO<T> join(final IO<IO<T>> action) {
        return new IO<T>(){
            @Override
            public T unsafePerformIO() {
                return action.unsafePerformIO().unsafePerformIO();
            }
        };
    }

    static <A,B> IO<B> fmap(final Function<A,B> func, final IO<A> action) {
        return new IO<B>(){
            @Override
            public B unsafePerformIO() {
                return func.apply(action.unsafePerformIO());
            }
        };
    }

    static <A,B> IO<B> bind(IO<A> action, Function<A, IO<B>> func) {
        return join(fmap(func, action));
    }
}
