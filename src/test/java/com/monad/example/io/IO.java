package com.monad.example.io;

/**
 * Created by iurii.dziuban on 04.01.2017.
 */

/**
 * This type represents an action, yielding type R
 */
interface IO<R> {
    /**
     * Warning! May have arbitrary side-effects!
     */
    R unsafePerformIO();
}

