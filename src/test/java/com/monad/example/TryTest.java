package com.monad.example;

import com.monad.example.optional.Optional;
import com.monad.example.trie.Try;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by iurii.dziuban on 02.01.2017.
 */
public class TryTest {

    @Test
    public void itShouldBeSuccessOnSuccess() throws Throwable{
        Try<String> t = Try.of(() -> "hey");
        assertTrue(t.isSuccess());
    }

    @Test
    public void itShouldHoldValueOnSuccess() throws Throwable{
        Try<String> t = Try.of(() -> "hey");
        assertEquals("hey", t.get());
    }

    @Test
    public void itShouldMapOnSuccess() throws Throwable{
        Try<String> t = Try.of(() -> "hey");
        Try<Integer> intT = t.map((x) -> 5);
        intT.get();
        assertEquals(5, intT.get().intValue());
    }

    @Test
    public void itShouldFlatMapOnSuccess() throws Throwable {
        Try<String> t = Try.of(() -> "hey");
        Try<Integer> intT = t.flatMap((x) -> Try.of(() -> 5));
        intT.get();
        assertEquals(5, intT.get().intValue());
    }

    @Test
    public void itShouldOrElseOnSuccess() {
        String t = Try.of(() -> "hey").orElse("jude");
        assertEquals("hey", t);
    }

    @Test
    public void itShouldReturnValueWhenRecoveringOnSuccess() {
        String t = Try.of(() -> "hey").recover((e) -> "jude");
        assertEquals("hey", t);
    }


    @Test
    public void itShouldReturnValueWhenRecoveringWithOnSuccess() throws Throwable {
        String t = Try.of(() -> "hey")
                .recoverWith((x) ->
                        Try.of(() -> "Jude")
                ).get();
        assertEquals("hey", t);
    }

    @Test
    public void itShouldOrElseTryOnSuccess() throws Throwable {
        Try<String> t = Try.of(() -> "hey").orElseTry(() -> "jude");

        assertEquals("hey", t.get());
    }

    @Test
    public void itShouldBeFailureOnFailure(){
        Try<String> t = Try.of(() -> {
            throw new RuntimeException("e");
        });
        assertFalse(t.isSuccess());
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

        assertFalse(t.isSuccess());
    }

    @Test
    public void itShouldFlatMapOnFailure(){
        Try<String> t = Try.of(() -> {
            throw new RuntimeException("e");
        }).flatMap((x) -> Try.of(() -> "hey"));

        assertFalse(t.isSuccess());
    }

    @Test
    public void itShouldOrElseOnFailure() {
        String t = Try.<String>of(() -> {
            throw new IllegalArgumentException("e");
        }).orElse("jude");

        assertEquals("jude", t);
    }

    @Test
    public void itShouldOrElseTryOnFailure() throws Throwable {
        Try<String> t = Try.<String>of(() -> {
            throw new IllegalArgumentException("e");
        }).orElseTry(() -> "jude");

        assertEquals("jude", t.get());
    }

    @Test
    public void itShouldReturnRecoverValueWhenRecoveringOnFailure() {
        String t = Try.of(() -> "hey")
                .<String>map((x) -> {
                    throw new RuntimeException("fail");
                })
                .recover((e) -> "jude");
        assertEquals("jude", t);
    }


    @Test
    public void itShouldReturnValueWhenRecoveringWithOnFailure() throws Throwable {
        String t = Try.<String>of(() -> {
            throw new RuntimeException("oops");
        })
                .recoverWith((x) ->
                        Try.of(() -> "Jude")
                ).get();
        assertEquals("Jude", t);
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

        assertEquals(t1.isSuccess(), false);
        assertEquals(t2.isSuccess(), false);
    }

    @Test
    public void isShouldPassSuccessOnlyIfPredicateIsTrue() throws Throwable {
        Try t1 = Try.<String>of(() -> "yo mama").filter(s -> s.length() > 0);
        Try t2 = Try.<String>of(() -> "yo mama").filter(s -> s.length() < 0);

        assertEquals(t1.isSuccess(), true);
        assertEquals(t2.isSuccess(), false);
    }

    @Test
    public void itShouldReturnEmptyOptionalIfFailureOrNullSuccess() throws Throwable {
        Optional<String> opt1 = Try.<String>of(() -> {
            throw new IllegalArgumentException("Expected exception");
        }).toOptional();
        Optional<String> opt2 = Try.<String>of(() -> null).toOptional();

        assertFalse(opt1.isPresent());
        assertFalse(opt2.isPresent());
    }

    @Test
    public void isShouldReturnTryValueWrappedInOptionalIfNonNullSuccess() throws Throwable {
        Optional<String> opt1 = Try.<String>of(() -> "yo mama").toOptional();

        assertTrue(opt1.isPresent());
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
