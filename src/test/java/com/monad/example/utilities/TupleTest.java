package com.monad.example.utilities;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 16/08/2017.
 */
public class TupleTest {

    @Test(expected = IllegalArgumentException.class)
    public void testFirstIsNull() {
        Tuple.tuple(null, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSecondIsNull() {
        Tuple.tuple(2, null);
    }


    @Test
    public void test() {
        Tuple<Integer, Integer> tuple = Tuple.tuple(1, 2);

        assertThat(tuple.getFirst()).isEqualTo(1);
        assertThat(tuple.first()).isEqualTo(1);
        assertThat(tuple.getSecond()).isEqualTo(2);
        assertThat(tuple.second()).isEqualTo(2);
    }

    @Test
    public void testHashCodeAndEquals() {
        Tuple<Integer, Integer> tuple1 = Tuple.tuple(1, 2);
        Tuple<Integer, Integer> tuple2 = Tuple.tuple(1, 3);

        assertThat(tuple1.hashCode()).isNotEqualTo(tuple2.hashCode());
        assertThat(tuple1.hashCode()).isEqualTo(Tuple.tuple(1, 2).hashCode());
        assertThat(tuple1.equals(null)).isFalse();
        assertThat(tuple1.equals(new Object())).isFalse();
        assertThat(tuple1.equals(Tuple.tuple(1, 2))).isTrue();
        assertThat(tuple1.equals(Tuple.tuple(2, 2))).isFalse();
        assertThat(tuple1.equals(Tuple.tuple(1, 3))).isFalse();
        assertThat(tuple1.equals(Tuple.tuple(2, 3))).isFalse();
        assertThat(tuple1.equals(tuple1)).isTrue();
    }
}
