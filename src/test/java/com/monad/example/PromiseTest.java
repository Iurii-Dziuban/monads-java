package com.monad.example;

import com.monad.example.promise.Promise;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 23.12.2016.
 */
public class PromiseTest {
    @Test
    public void testLeftIdentity() {
        Function<Integer, Promise<Integer>> addOne = x -> {System.out.println(x); return Promise.of(x + 1);};
        assertThat(Promise.of(5).bind(addOne).get())
                .isEqualTo(addOne.apply(5).get());
    }
}
