package com.monad.example.io;

/**
 * The runtime system, doing impure stuff to actually run our program.
 */
class RTS {
    public static void main(String[] args) {
        Main.main.unsafePerformIO();
    }
}
