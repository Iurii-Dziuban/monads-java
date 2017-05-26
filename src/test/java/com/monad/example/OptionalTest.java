package com.monad.example;

import com.monad.example.optional.Optional;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 23.12.2016.
 */
public class OptionalTest {

    // Read more >
    // 1. Optional Monad https://medium.com/coding-with-clarity/understanding-the-optional-monad-in-java-8-e3000d85ffd2

    @Test
    public void testLeftIdentity() {
        Function<Integer, Optional<Integer>> addOne = x -> Optional.of(x + 1);
        assertThat(Optional.of(5).bind(addOne)).isEqualTo(addOne.apply(5));
    }

    @Test
    public void testRightIdentity() {
        assertThat(Optional.of(5).bind(Optional::of)).isEqualTo(Optional.of(5));
    }

    @Test
    public void testAssociativity() {
        Function<Integer, Optional<Integer>> addOne = x -> Optional.of(x + 1);
        Function<Integer, Optional<Integer>> addTwo = x -> Optional.of(x + 2);
        assertThat(Optional.of(5).bind(Optional::of).bind(addOne).bind(addTwo))
                .isEqualTo(addTwo.apply(5).bind(addOne));
    }

    @Test
    public void testAssociativityNotWorkingOneFunctionIsNotAssociative() {
        Function<Integer, Optional<Integer>> square = x -> Optional.of(x * x);
        Function<Integer, Optional<Integer>> addOne = x -> Optional.of(x + 1);
        assertThat(Optional.of(5).bind(Optional::of).bind(addOne).bind(square))
                .isNotEqualTo(square.apply(5).bind(addOne));
    }


    @Test
    public void testFlatMap() {
        assertThat(Optional.of(Optional.of(5)).bind(x -> x))
                .isEqualTo(Optional.of(5));
    }

}
