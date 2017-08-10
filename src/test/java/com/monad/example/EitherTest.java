package com.monad.example;

import com.monad.example.either.Either;
import org.junit.Test;

import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 10.01.2017.
 */
public class EitherTest {

    @Test
    public void testLeftIdentity() {
        Function<Integer, Either<Integer, String>> addOne = x -> Either.ofLeft(x + 1);
        assertThat(Either.<Integer, String>ofLeft(5).bind(addOne)).isEqualTo(addOne.apply(5));
    }

    @Test
    public void testRightIdentity() {
        assertThat(Either.ofLeft(5).bind(Either::ofLeft)).isEqualTo(Either.ofLeft(5));
    }

    @Test
    public void testAssociativity() {
        Function<Integer, Either<Integer, String>> addOne = x -> Either.ofLeft(x + 1);
        Function<Integer, Either<Integer, String>> addTwo = x -> Either.ofLeft(x + 2);
        assertThat(Either.ofLeft(5).bind(Either::ofLeft).bind(addOne).bind(addTwo))
                .isEqualTo(addTwo.apply(5).bind(addOne));
    }


    @Test
    public void testEither() {
        Either<String, String> hello = Either.<String, String>ofLeft("Hello")
                .bind(t -> Either.<String, String>ofRight(t + "5")).bind(t -> Either.ofLeft(t + "5"));

        assertThat(hello.isLeftPresent()).isFalse();
        assertThat(hello.getRight()).isEqualTo("Hello5");
    }

    @Test
    public void testFlatMap() {
        Either<String, String> hello = Either.<String, String>ofLeft("Hello")
                .flatMap(t -> Either.<String, String>ofRight(t + "6"));

        assertThat(hello.isLeftPresent()).isFalse();
        assertThat(hello.getRight()).isEqualTo("Hello6");
    }

    @Test
    public void testPure() {
        Either<String, String> purePresent = Either.<String, String>ofLeft("Hello").pure("5");
        Either<String, String> pureNotPresent = Either.<String, String>ofLeft("Hello").pure(null);

        assertThat(purePresent.getLeft()).isEqualTo("5");
        assertThat(purePresent.getRight()).isNull();
        assertThat(pureNotPresent.getRight()).isNull();
        assertThat(pureNotPresent.getLeft()).isNull();
    }

    @Test
    public void hashCodeTest() {
        Either<String, String> left = Either.ofLeft("Left");
        Either<String, String> right = Either.ofRight("Right");
        Either<String, String> nil = Either.ofRight(null);

        assertThat(left.hashCode()).isNotEqualTo(right);
        assertThat(nil.hashCode()).isNotEqualTo(right);
    }

    @Test
    public void equalsTest() {
        Either<String, String> left = Either.ofLeft("Left");
        Either<String, String> left1 = Either.ofLeft("Left");
        Either<String, String> right = Either.ofRight("Right");
        Either<String, String> nil = Either.ofRight(null);

        assertThat(left.equals(right)).isFalse();
        assertThat(nil.equals(right)).isFalse();
        assertThat(nil.equals(new Object())).isFalse();
        assertThat(left.equals(left)).isTrue();
        assertThat(left.equals(left1)).isTrue();
    }

    @Test
    public void toStringTest() {
        Either<String, String> left = Either.ofLeft("Left");
        Either<String, String> right = Either.ofRight("Right");
        Either<String, String> nil = Either.ofRight(null);

        assertThat(left.toString()).isEqualTo("Either[left][Optional[Left]]");
        assertThat(right.toString()).isEqualTo("Either[right][Optional[Right]]");
        assertThat(nil.toString()).isEqualTo("Either[right][Optional.empty]");
    }
}
