package com.monad.example.list;

import com.monad.example.Monad;

import java.util.function.Function;

/**
 * Head - Tail List Monad implementation
 *
 * Created by iurii.dziuban on 10.01.2017.
 */
public class List<A> implements Monad<A> {

    private final A h;
    private final List<A> t;

    private List(A _h, List<A> _t) {
        h = _h;
        t = _t;
    }

    // constructors
    public static <A> List<A> of(A h, List<A> t) {
        return new List<>(h, t);
    }

    public static <A> List<A> nil() {
        return new List<>(null, null);
    }

    public static <A> List<A> cons(A h, List<A> t) {
        return new List<>(h, t);
    }

    public <B> List<B> map(Function<A, B> f) {
        if (h == null) {
            return nil();
        } else {
            if (t == null) {
                return of(f.apply(h), null);
            } else {
                return of(f.apply(h), t.map(f));
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <B> List<B> flatMap(Function<A, List<B>> f) {
        if (h == null) {
            return nil();
        } else {
            if (t == null) {
                return f.apply(h);
            } else {
                return concat(f.apply(h), t.flatMap(f));
            }
        }
    }

    // concat
    private <B> List<B> concat(List<B> listStart, List<B> listEnd) {
        if (listStart.h == null) {
            return listEnd;
        } else {
            if (listStart.t == null) {
                return List.of(listStart.h, listEnd);
            }
            return List.of(listStart.h, concat(listStart.t, listEnd));
        }
    }

    public String toString() {
        if (h == null) {
            return "nil";
        } else if (t == null) {
            return "cons(" + h.toString() + ", nil)";
        } else {
            return "cons(" + h.toString() + ", " + t.toString() + ")";
        }
    }

    @Override
    public List<A> pure(A a) {
        if (h == null) {
            return of(a, null);
        } else {
            if (t == null) {
                return of(h, of(a, nil()));
            }
        }
        return of(h, t.pure(a));
    }

    @Override
    public <R> List<R> bind(Function<? super A, ? extends Monad<R>> f) {
        if (h == null) {
            return nil();
        } else {
            if (t == null) {
                return (List<R>) f.apply(h);
            } else {
                return concat((List<R>) f.apply(h), t.bind(f));
            }
        }
    }
}
