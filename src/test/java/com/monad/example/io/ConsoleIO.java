package com.monad.example.io;

import com.monad.example.utilities.Unit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.function.Function;

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

    final static BufferedReader in = new BufferedReader(new InputStreamReader(System.in, Charset.forName("UTF-8")));

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
