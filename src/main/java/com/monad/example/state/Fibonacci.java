package com.monad.example.state;

import com.monad.example.utilities.Tuple;

import java.math.BigInteger;
import java.util.Optional;
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

    static StateMonad<Memo, BigInteger> fibMemo2(BigInteger n) {

  /*
   * Create a function of type Memo -> Optional<BigInteger> with a closure
   * over the n parameter.
   */
        Function<Memo, Optional<BigInteger>> retrieveValueFromMapIfPresent = (Memo memoizationMap) -> memoizationMap.retrieve(n);

  /*
   * Create a state from this function.
   */
        StateMonad<Memo, Optional<BigInteger>> initialState = StateMonad.getState(retrieveValueFromMapIfPresent);

  /*
   * Create a function for converting the value (BigInteger) into a State
   * Monad instance. This function will be bound to the Optional resulting
   * from the lookup into the map to give the result if the value was found.
   */
        Function<BigInteger, StateMonad<Memo, BigInteger>> createStateFromValue = StateMonad::<Memo, BigInteger>unit;

  /*
   * The value computation proper. This can't be easily decomposed because it
   * make heavy use of closures. It first calls recursively fibMemo(n - 1),
   * producing a StateMonad<Memo, BigInteger>. It then flatMaps it to a new
   * recursive call to fibMemo(n - 2) (actually fibMemo(n - 1 - 1)) and get a
   * new StateMonad<Memo, BigInteger> which is mapped to BigInteger addition
   * with the preceding value (x). Then it flatMaps it again with the function
   * y -> StateMonad.transition((Memo m) -> m.addEntry(n, z), z) which adds
   * the two values and returns a new StateMonad with the computed value added
   * to the map.
   */
        StateMonad<Memo, BigInteger> computedValue = fibMemo2(n.subtract(BigInteger.ONE))
                .flatMap(x -> fibMemo2(n.subtract(BigInteger.ONE).subtract(BigInteger.ONE))
                        .map(x::add)
                        .flatMap(z -> StateMonad.transition((Memo m) -> m.addEntry(n, z), z)));

  /*
   * Create a function taking an Optional<BigInteger> as its parameter and
   * returning a state. This is the main function that returns the value in
   * the Optional if it is present and compute it and put it into the map
   * before returning it otherwise.
   */
        Function<Optional<BigInteger>, StateMonad<Memo, BigInteger>> computeFiboValueIfAbsentFromMap = u -> u.map(createStateFromValue).orElse(computedValue);

  /*
   * Bind the computeFiboValueIfAbsentFromMap function to the initial State
   * and return the result.
   */
        return initialState.flatMap(computeFiboValueIfAbsentFromMap);
    }
}
