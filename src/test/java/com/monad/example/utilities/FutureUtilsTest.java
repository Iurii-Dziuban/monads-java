package com.monad.example.utilities;

import com.monad.example.utilities.FutureUtils;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by iurii.dziuban on 04.01.2017.
 */
public class FutureUtilsTest {

        @Test
        public void itShouldConvertAListOfFuturesToAFutureWithAList() throws Exception {
            //given a list of futures,
            List<Integer> list = IntStream.range(0, 100).boxed().collect(Collectors.toList());
            int size = list.size();
            List<CompletableFuture<Integer>> futures = list
                    .stream()
                    .map(x -> CompletableFuture.supplyAsync(() -> x))
                    .collect(Collectors.toList());

            //when we call sequence,
            CompletableFuture<List<Integer>> futureList = FutureUtils.sequence(futures);

            //then we should get a future with a list
            List<Integer> collectedIntegers = FutureUtils.sequence(futures).get();
            assert(collectedIntegers.size() == size);
            assert(list.get(5) == collectedIntegers.get(5));
        }

}
