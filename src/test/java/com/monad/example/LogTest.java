package com.monad.example;

import com.monad.example.writer.Log;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 10.01.2017.
 */
public class LogTest {

    private Log<Integer> start(int val) {
        return Log.trace(val, "initial value");
    }

    private Log<Integer> operation2(Integer val) {
        return Log.trace(val + 2, "run operation by adding 2");
    }

    private Log<Integer> operation3(Integer val) {
        return Log.trace(val / 4, "divided by 4");
    }

    @Test
    public void testLog() {
        Log<Integer> log = start(5)
                .flatMap(this::operation2)
                .flatMap(this::operation3)
                .flatMap( val ->
                        Log.trace( val * 2, "Multiplied by two")
                );

        assertThat(log.getValue()).isEqualTo(2);
        assertThat(log.getTrace()).containsExactly("initial value: 5",
                "run operation by adding 2: 7",
                "divided by 4: 1",
                "Multiplied by two: 2");
    }

    @Test
    public void bindTest() {
        Log<Integer> log = Log.of(23);
        Log<Integer> log1 = log.pure(11);

        Log<Integer> bindLog = log.bind(a -> Log.of(a + 1));

        assertThat(log.getValue()).isEqualTo(23);
        assertThat(log1.getValue()).isEqualTo(11);
        assertThat(bindLog.getValue()).isEqualTo(24);
    }

}
