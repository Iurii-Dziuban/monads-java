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

    @Test
    public void testPure() {
        Promise<Integer> pure = Promise.of(6).pure(7);

        pure.invokeWithException(new IllegalArgumentException());

        pure.onRedeem(promise -> System.out.println("Example"));
    }

    @Test
    public void testRedeemOk() {
        Promise<Integer> redeemOk = new Promise<>();

        redeemOk.onRedeem(promise -> System.out.println("Example"));
        redeemOk.invoke(6);
    }

    @Test
    public void testRedeemThrowsException() {
        Promise<Integer> redeemWithException = new Promise<>();

        redeemWithException.bind(a -> {throw new IllegalArgumentException();});
        redeemWithException.invoke(3);
    }
}
