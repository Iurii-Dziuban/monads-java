package com.monad.example.io;

import com.monad.example.utilities.Unit;

import java.util.function.Function;

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
