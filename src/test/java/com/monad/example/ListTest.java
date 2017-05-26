package com.monad.example;

import com.monad.example.list.List;
import org.junit.Test;

/**
 * Created by iurii.dziuban on 11.01.2017.
 */
public class ListTest {

    @Test
    public void test(){
        System.out.println(
                List.cons(1, List.cons(1, List.cons(2, List.cons(3, List.nil()))))
                        .map((x) -> x + 20)
                        .map((x) -> x * 2)
                        .flatMap((x) -> {
                            if (x != 42) {
                                return List.nil();
                            } else {
                                return List.cons(x, List.nil());
                            }
                        })
        ); // cons(42, cons(42, nil))
    }
}
