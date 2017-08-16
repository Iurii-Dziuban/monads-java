package com.monad.example;

import com.monad.example.state.StateSimple;
import com.monad.example.utilities.Tuple;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 11.01.2017.
 */
public class StateSimpleTest {

    @Test
    public void test() {
        assertThat(
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
                        .f.apply(new java.util.LinkedList<>()).toString()
        ).isEqualTo("Tuple[left][42][right][[and one, yay]]");
    }
}
