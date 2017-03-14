package com.monad.example.io;

/**
 * Created by iurii.dziuban on 04.01.2017.
 */

import com.monad.example.utilities.Unit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Function;

/**
 * This type represents an action, yielding type R
 */
interface IO<R> {
    /**
     * Warning! May have arbitrary side-effects!
     */
    R unsafePerformIO();
}

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

/**
 * This class, internally impure, provides pure interface for interaction with stdin and stdout
 */
class ConsoleIO {
    static IO<Unit> putStrLn(final String line) {
        return new IO<Unit>() {
            @Override
            public Unit unsafePerformIO() {
                System.out.println(line);
                return Unit.unit();
            }
        };
    };

    // Java does not have first-class functions, thus this:
    final static Function<String, IO<Unit>> putStrLn = new Function<String, IO<Unit>>() {
        @Override
        public IO<Unit> apply(String argument) {
            return putStrLn(argument);
        }
    };

    final static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    static IO<String> getLine = new IO<String>() {
        @Override
        public String unsafePerformIO() {
            try {
                return in.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    };
}

/**
 * The program composed out of IO actions in a purely functional manner.
 */
class Main {

    /**
     * A variant of bind, which discards the bound value.
     */
    static IO<Unit> bind_(final IO<Unit> a, final IO<Unit> b) {
        return IOMonad.bind(a, new Function<Unit, IO<Unit>>(){
            @Override
            public IO<Unit> apply(Unit argument) {
                return b;
            }
        });
    }

    /**
     * The greeting action -- asks the user for his name and then prints a greeting
     */
    final static IO<Unit> greet =
            bind_(ConsoleIO.putStrLn("Enter your name:"),
                    IOMonad.bind(ConsoleIO.getLine, new Function<String, IO<Unit>>(){
                        @Override
                        public IO<Unit> apply(String argument) {
                            return ConsoleIO.putStrLn("Hello, " + argument + "!");
                        }
                    }));

    /**
     * A simple echo action -- reads a line, prints it back
     */
    final static IO<Unit> echo = IOMonad.bind(ConsoleIO.getLine, ConsoleIO.putStrLn);

    /**
     * A function taking some action and producing the same action run repeatedly forever (modulo stack overflow :D)
     */
    static IO<Unit> loop(final IO<Unit> action) {
        return IOMonad.bind(action, new Function<Unit, IO<Unit>>(){
            @Override
            public IO<Unit> apply(Unit argument) {
                return loop(action);
            }
        });
    }

    /**
     * The action corresponding to the whole program
     */
    final static IO<Unit> main = bind_(greet, bind_(ConsoleIO.putStrLn("Entering the echo loop."),loop(echo)));
}

/**
 * The runtime system, doing impure stuff to actually run our program.
 */
class RTS {
    public static void main(String[] args) {
        Main.main.unsafePerformIO();
    }
}
