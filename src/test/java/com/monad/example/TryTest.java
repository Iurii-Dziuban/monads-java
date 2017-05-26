package com.monad.example;

import com.monad.example.optional.Optional;
import com.monad.example.trie.Try;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 02.01.2017.
 */
public class TryTest {

    @Test
    public void itShouldBeSuccessOnSuccess() throws Throwable{
        Try<String> t = Try.of(() -> "hey");
        assertThat(t.isSuccess()).isTrue();
    }

    @Test
    public void itShouldHoldValueOnSuccess() throws Throwable{
        Try<String> t = Try.of(() -> "hey");
        assertThat(t.get()).isEqualTo("hey");
    }

    @Test
    public void itShouldMapOnSuccess() throws Throwable{
        Try<String> t = Try.of(() -> "hey");
        Try<Integer> intT = t.map((x) -> 5);
        intT.get();
        assertThat(intT.get().intValue()).isEqualTo(5);
    }

    @Test
    public void itShouldFlatMapOnSuccess() throws Throwable {
        Try<String> t = Try.of(() -> "hey");
        Try<Integer> intT = t.flatMap((x) -> Try.of(() -> 5));
        intT.get();
        assertThat(intT.get().intValue()).isEqualTo(5);
    }

    @Test
    public void itShouldOrElseOnSuccess() {
        String t = Try.of(() -> "hey").orElse("jude");
        assertThat(t).isEqualTo("hey");
    }

    @Test
    public void itShouldReturnValueWhenRecoveringOnSuccess() {
        String t = Try.of(() -> "hey").recover((e) -> "jude");
        assertThat(t).isEqualTo("hey");
    }


    @Test
    public void itShouldReturnValueWhenRecoveringWithOnSuccess() throws Throwable {
        String t = Try.of(() -> "hey")
                .recoverWith((x) ->
                        Try.of(() -> "Jude")
                ).get();
        assertThat(t).isEqualTo("hey");
    }

    @Test
    public void itShouldOrElseTryOnSuccess() throws Throwable {
        Try<String> t = Try.of(() -> "hey").orElseTry(() -> "jude");

        assertThat(t.get()).isEqualTo("hey");
    }

    @Test
    public void itShouldBeFailureOnFailure(){
        Try<String> t = Try.of(() -> {
            throw new RuntimeException("e");
        });
        assertThat(t.isSuccess()).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionOnGetOfFailure() throws Throwable{
        Try<String> t = Try.of(() -> {
            throw new IllegalArgumentException("e");
        });
        t.get();
    }

    @Test
    public void itShouldMapOnFailure(){
        Try<String> t = Try.of(() -> {
            throw new RuntimeException("e");
        }).map((x) -> "hey" + x);

        assertThat(t.isSuccess()).isFalse();
    }

    @Test
    public void itShouldFlatMapOnFailure(){
        Try<String> t = Try.of(() -> {
            throw new RuntimeException("e");
        }).flatMap((x) -> Try.of(() -> "hey"));

        assertThat(t.isSuccess()).isFalse();
    }

    @Test
    public void itShouldOrElseOnFailure() {
        String t = Try.<String>of(() -> {
            throw new IllegalArgumentException("e");
        }).orElse("jude");

        assertThat(t).isEqualTo("jude");
    }

    @Test
    public void itShouldOrElseTryOnFailure() throws Throwable {
        Try<String> t = Try.<String>of(() -> {
            throw new IllegalArgumentException("e");
        }).orElseTry(() -> "jude");

        assertThat(t.get()).isEqualTo("jude");
    }

    @Test
    public void itShouldReturnRecoverValueWhenRecoveringOnFailure() {
        String t = Try.of(() -> "hey")
                .<String>map((x) -> {
                    throw new RuntimeException("fail");
                })
                .recover((e) -> "jude");
        assertThat(t).isEqualTo("jude");
    }


    @Test
    public void itShouldReturnValueWhenRecoveringWithOnFailure() throws Throwable {
        String t = Try.<String>of(() -> {
            throw new RuntimeException("oops");
        })
                .recoverWith((x) ->
                        Try.of(() -> "Jude")
                ).get();
        assertThat(t).isEqualTo("Jude");
    }

    @Test
    public void itShouldHandleComplexChaining() throws Throwable {
        Try.of(() -> "1").<Integer>flatMap((x) -> Try.of(() -> Integer.valueOf(x))).recoverWith((t) -> Try.successful(1));
    }

    @Test
    public void itShouldPassFailureIfPredicateIsFalse() throws Throwable {
        Try t1 = Try.of(() -> {
            throw new RuntimeException();
        }).filter(o -> false);

        Try t2 = Try.of(() -> {
            throw new RuntimeException();
        }).filter(o -> true);

        assertThat(t1.isSuccess()).isFalse();
        assertThat(t2.isSuccess()).isFalse();
    }

    @Test
    public void isShouldPassSuccessOnlyIfPredicateIsTrue() throws Throwable {
        Try t1 = Try.<String>of(() -> "yo mama").filter(s -> s.length() > 0);
        Try t2 = Try.<String>of(() -> "yo mama").filter(s -> s.length() < 0);

        assertThat(t1.isSuccess()).isTrue();
        assertThat(t2.isSuccess()).isFalse();
    }

    @Test
    public void itShouldReturnEmptyOptionalIfFailureOrNullSuccess() throws Throwable {
        Optional<String> opt1 = Try.<String>of(() -> {
            throw new IllegalArgumentException("Expected exception");
        }).toOptional();
        Optional<String> opt2 = Try.<String>of(() -> null).toOptional();

        assertThat(opt1.isPresent()).isFalse();
        assertThat(opt2.isPresent()).isFalse();
    }

    @Test
    public void isShouldReturnTryValueWrappedInOptionalIfNonNullSuccess() throws Throwable {
        Optional<String> opt1 = Try.<String>of(() -> "yo mama").toOptional();

        assertThat(opt1.isPresent()).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionFromTryConsumerOnSuccessIfSuccess() throws Throwable {
        Try<String> t = Try.of(() -> "hey");

        t.onSuccess(s -> {
            throw new IllegalArgumentException("Should be thrown.");
        });
    }

    @Test
    public void itShouldNotThrowExceptionFromTryConsumerOnSuccessIfFailure() throws Throwable {
        Try<String> t = Try.of(() -> {
            throw new IllegalArgumentException("Expected exception");
        });

        t.onSuccess(s -> {
            throw new IllegalArgumentException("Should NOT be thrown.");
        });
    }

    @Test
    public void itShouldNotThrowExceptionFromTryConsumerOnFailureIfSuccess() throws Throwable {
        Try<String> t = Try.of(() -> "hey");

        t.onFailure(s -> {
            throw new IllegalArgumentException("Should NOT be thrown.");
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void itShouldThrowExceptionFromTryConsumerOnFailureIfFailure() throws Throwable {
        Try<String> t = Try.of(() -> {
            throw new IllegalArgumentException("Expected exception");
        });

        t.onFailure(s -> {
            throw new IllegalArgumentException("Should be thrown.");
        });
    }
}
