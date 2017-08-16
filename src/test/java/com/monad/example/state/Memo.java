package com.monad.example.state;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by iurii.dziuban on 18.01.2017.
 */
public class Memo extends HashMap<BigInteger, BigInteger> {
    public Optional<BigInteger> retrieve(BigInteger key) {
        return Optional.ofNullable(super.get(key));
    }

    public Memo addEntry(BigInteger key, BigInteger value) {
        super.put(key, value);
        return this;
    }
}
