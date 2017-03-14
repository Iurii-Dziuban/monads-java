package com.monad.example;

import com.monad.example.writer.Log;
import org.junit.Test;

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

    private Log<Double> operation3(Integer val) {
        return Log.trace(val/4d, "divided by 4");
    }

    @Test
    public void testLog() {
        Log<Double> log = start(5)
                .flatMap(this::operation2)
                .flatMap(this::operation3)
                .flatMap( val ->
                        Log.trace( val * 2, "Multiplied by two")
                );

        System.out.println("Value: " + log.getValue());
        System.out.println("Trace: " + log.getTrace());
    }

}
