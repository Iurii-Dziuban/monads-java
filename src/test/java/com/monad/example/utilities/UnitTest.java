package com.monad.example.utilities;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by iurii.dziuban on 16/08/2017.
 */
public class UnitTest {

    @Test
    public void test() {
        assertThat(Unit.unit()).isNotEqualTo(new Unit());
        assertThat(Unit.unit()).isNotNull();
        assertThat(Unit.unit()).isEqualTo(Unit.unit());
    }
}
