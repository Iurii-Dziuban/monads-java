package com.monad.example;

import com.monad.example.promise.Promise;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

/**
 * Created by iurii.dziuban on 23.12.2016.
 */
public class PromiseTest {
    @Test
    public void testLeftIdentity() {
        Function<Integer, Promise<Integer>> addOne = x -> {System.out.println(x); return Promise.of(x + 1);};
        Assert.assertEquals(Promise.of(5).bind(addOne).get(), addOne.apply(5).get());
    }
}
