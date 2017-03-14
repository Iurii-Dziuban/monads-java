package com.monad.example;

import com.monad.example.state.StateSimple;
import com.monad.example.utilities.Tuple;
import org.junit.Test;

import java.util.List;

/**
 * Created by iurii.dziuban on 11.01.2017.
 */
public class StateSimpleTest {

    @Test
    public void test() {
        System.out.println(
                StateSimple.<java.util.List<String>,Integer>apply(
                        (log) -> {
                            log.add("and one");
                            return Tuple.tuple(1, log);
                        }
                )
                        .map((x) -> x + 20)
                        .map((x) -> x * 2)
                        .flatMap((x) ->
                                StateSimple.<java.util.List<String>,Integer>apply(
                                        (log) -> {
                                            if (x != 42) {
                                                log.add("boo");
                                                return Tuple.tuple(-999, log);
                                            } else {
                                                log.add("yay");
                                                return Tuple.tuple(x, log);
                                            }
                                        }
                                )
                        )
                        .f.apply(new java.util.LinkedList<String>())
        ); // (42, [and one, yay])
    }
}
