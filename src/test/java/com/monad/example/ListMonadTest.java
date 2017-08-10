package com.monad.example;

import com.monad.example.list.ListMonad;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 11.01.2017.
 */
public class ListMonadTest {

    @Test
    public void test() {
        assertThat(
                new ListMonad<Integer>(java.util.Arrays.asList(1,1,2,3))
                        .map((x) -> x + 20)
                        .map((x) -> x * 2)
                        .flatMap((x) -> {
                            if (x != 42) {
                                return new ListMonad<Integer>(java.util.Arrays.asList());
                            } else {
                                return new ListMonad<Integer>(java.util.Arrays.asList(x));
                            }
                        }).toString()
        ).isEqualTo("[42, 42]");
    }

    @Test
    public void testPure() {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        ListMonad<Integer> pure = new ListMonad<>(list).pure(5);
        assertThat(pure.toString()).isEqualTo("[1, 5]");

        pure = new ListMonad<Integer>(null).pure(5);
        assertThat(pure.toString()).isEqualTo("[5]");
    }
}
