package com.monad.example;

import com.monad.example.optional.Optional;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

/**
 * Created by iurii.dziuban on 23.12.2016.
 */
public class OptionalTest {

    // Read more >
    // 1. Optional Monad https://medium.com/coding-with-clarity/understanding-the-optional-monad-in-java-8-e3000d85ffd2

    @Test
    public void testLeftIdentity() {
        Function<Integer, Optional<Integer>> addOne = x -> Optional.of(x + 1);
        Assert.assertEquals(Optional.of(5).bind(addOne), addOne.apply(5));
    }

    @Test
    public void testRightIdentity() {
        Assert.assertEquals(Optional.of(5).bind(Optional::of), Optional.of(5));
    }

    @Test
    public void testAssociativity() {
        Function<Integer, Optional<Integer>> addOne = x -> Optional.of(x + 1);
        Function<Integer, Optional<Integer>> addTwo = x -> Optional.of(x + 2);
        Assert.assertEquals(Optional.of(5).bind(Optional::of).bind(addOne).bind(addTwo), addTwo.apply(5).bind(addOne));
    }

    @Test
    public void testAssociativityNotWorkingOneFunctionIsNotAssociative() {
        Function<Integer, Optional<Integer>> square = x -> Optional.of(x * x);
        Function<Integer, Optional<Integer>> addOne = x -> Optional.of(x + 1);
        Assert.assertNotEquals(Optional.of(5).bind(Optional::of).bind(addOne).bind(square), square.apply(5).bind(addOne));
    }


    @Test
    public void testFlatMap() {
        Assert.assertEquals(Optional.of(Optional.of(5)).bind(x -> x), Optional.of(5));
    }

}
