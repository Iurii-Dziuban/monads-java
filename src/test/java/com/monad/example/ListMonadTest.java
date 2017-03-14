package com.monad.example;

import com.monad.example.list.ListMonad;
import org.junit.Test;

/**
 * Created by iurii.dziuban on 11.01.2017.
 */
public class ListMonadTest {

    @Test
    public void test() {
        System.out.println(
                new ListMonad<Integer>(java.util.Arrays.asList(1,1,2,3))
                        .map((x) -> x + 20)
                        .map((x) -> x * 2)
                        .flatMap((x) -> {
                            if (x != 42) return new ListMonad<Integer>(java.util.Arrays.asList());
                            else return new ListMonad<Integer>(java.util.Arrays.asList(x));
                        })
        ); // [42, 42]
    }
}
