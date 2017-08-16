package com.monad.example;

import com.monad.example.list.List;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 11.01.2017.
 */
public class ListTest {

    @Test
    public void test(){
        assertThat(
                List.cons(1, List.cons(1, List.cons(2, List.cons(3, List.nil()))))
                        .map(x -> x + 20)
                        .map(x -> x * 2)
                        .flatMap((x) -> {
                            if (x != 42) {
                                return List.nil();
                            } else {
                                return List.cons(x, List.nil());
                            }
                        }).toString()
        ).isEqualTo("cons(42, cons(42, nil))");
    }

    @Test
    public void testPure() {
        List<Integer> pure = List.of(1, null).pure(5);
        assertThat(pure.toString()).isEqualTo("cons(1, cons(5, nil))");

        pure = pure.pure(6);
        assertThat(pure.toString()).isEqualTo("cons(1, cons(5, cons(6, nil)))");

        pure = List.<Integer>nil().pure(5);
        assertThat(pure.toString()).isEqualTo("cons(5, nil)");
    }

    @Test
    public void testFlatMap() {
        List<Integer> flatMap = List.of(1, List.of(2, null));
        assertThat(flatMap.flatMap( a -> List.of(a + 1, null)).toString()).isEqualTo("cons(2, cons(3, nil))");
    }

    @Test
    public void testMap() {
        List<Integer> map = List.of(1, null);
        assertThat(map.map( a -> a + 1).toString()).isEqualTo("cons(2, nil)");
    }

    @Test
    public void testBind() {
        List<Integer> list = List.of(1, List.of(2, null));

        assertThat(list.bind( a -> List.of(a + 1, null)).toString()).isEqualTo("cons(2, cons(3, nil))");

        assertThat(List.<Integer>nil().bind(a -> List.of(a + 1, null)).toString()).isEqualTo("nil");
    }
}
