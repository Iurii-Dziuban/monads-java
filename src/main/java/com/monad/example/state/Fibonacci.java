package com.monad.example.state;

import com.monad.example.utilities.Tuple;

import java.math.BigInteger;
import java.util.function.Function;

/**
 * Created by iurii.dziuban on 18.01.2017.
 */
public class Fibonacci {

    public static void main(String[] args) {
        System.out.println(fibMemo1(BigInteger.valueOf(5)));
    }

    static BigInteger fibMemo1(BigInteger n) {
        return fibMemo(n, new Memo().addEntry(BigInteger.ZERO, BigInteger.ZERO)
                .addEntry(BigInteger.ONE, BigInteger.ONE)).getFirst();
    }

    static Tuple<BigInteger, Memo> fibMemo(BigInteger n, Memo memo) {
        return memo.retrieve(n).map(x -> Tuple.tuple(x, memo)).orElseGet(() -> {
            BigInteger x = fibMemo(n.subtract(BigInteger.ONE), memo).first()
                    .add(fibMemo(n.subtract(BigInteger.ONE).subtract(BigInteger.ONE), memo).getFirst());
            return Tuple.tuple(x, memo.addEntry(n, x));
        });
    }

    static Function<BigInteger, BigInteger> fib = new Function<BigInteger, BigInteger>() {
        @Override
        public BigInteger apply(BigInteger n) {
            return n.equals(BigInteger.ZERO) || n.equals(BigInteger.ONE)
                    ? n
                    : this.apply(n.subtract(BigInteger.ONE)).add(this.apply(n.subtract(BigInteger.ONE.add(BigInteger.ONE))));
        }
    };

    static Function<BigInteger, BigInteger> fibm = Memoizer.memoize(fib);
}
