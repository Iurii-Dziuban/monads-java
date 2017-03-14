package com.monad.example;

import static com.monad.example.either.Either.ofLeft;

import com.monad.example.either.Either;
import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

/**
 * Created by iurii.dziuban on 10.01.2017.
 */
public class EitherTest {

    @Test
    public void testLeftIdentity() {
        Function<Integer, Either<Integer, String>> addOne = x -> Either.ofLeft(x + 1);
        Assert.assertEquals(Either.<Integer, String> ofLeft(5).bind(addOne), addOne.apply(5));
    }

    @Test
    public void testRightIdentity() {
        Assert.assertEquals(Either.ofLeft(5).bind(Either::ofLeft), Either.ofLeft(5));
    }

    @Test
    public void testAssociativity() {
        Function<Integer, Either<Integer, String>> addOne = x -> Either.ofLeft(x + 1);
        Function<Integer, Either<Integer, String>> addTwo = x -> Either.ofLeft(x + 2);
        Assert.assertEquals(Either.ofLeft(5).bind(Either::ofLeft).bind(addOne).bind(addTwo), addTwo.apply(5).bind(addOne));
    }


    @Test
    public void testEither() {
        Either<String, String> hello = Either.<String, String> ofLeft("Hello").bind(t -> Either.<String, String> ofRight(t + "5")).bind( t -> ofLeft(t + "5"));
        if (hello.isLeftPresent()) {
            System.out.println(hello.getLeft());
        } else {
            System.out.println(hello.getRight());
        }
    }
}
