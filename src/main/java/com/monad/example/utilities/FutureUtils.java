package com.monad.example.utilities;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Created by iurii.dziuban on 04.01.2017.
 */
public class FutureUtils {

    private FutureUtils() {}

    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        return CompletableFuture.
                allOf(futures.toArray(new CompletableFuture[futures.size()])).
                thenApply(v ->
                        futures.stream().
                                map(CompletableFuture::join).
                                collect(Collectors.toList())
                );
    }
}
