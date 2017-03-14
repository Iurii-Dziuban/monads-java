package com.monad.example.utilities;

/**
 * Created by iurii.dziuban on 05.01.2017.
 */
/**
 * This is a type containing no data -- corresponds to () in Haskell.
 */
public class Unit {

    private static final Unit UNIT = new Unit();

    public static Unit unit(){
        return UNIT;
    }

}