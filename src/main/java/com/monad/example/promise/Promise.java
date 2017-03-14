package com.monad.example.promise;

import com.monad.example.Monad;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by iurii.dziuban on 13.12.2016.
 */
public class Promise<V> implements Monad<V> {

    private Throwable exception = null;

    private boolean invoked = false;

    private List<Consumer<Promise<V>>> callbacks = new ArrayList<>();

    private V result = null;

    public static <V> Promise<V> of (V v) {
        Promise<V> p = new Promise<>();
        p.invoke(v);
        return p;
    }

    @Override
    public Promise<V> pure(V v) {
        return of(v);
    }

    @Override
    public <R> Promise<R> bind(Function<? super V, ? extends Monad<R>> f) {
        Promise<R> result = new Promise<>();

        this.onRedeem(callback -> {
            try {
                V v = callback.get();
                Promise<R> applicationResult = (Promise<R>) f.apply(v);
                applicationResult.onRedeem(applicationCallback -> {
                    try {
                        R r = applicationCallback.get();
                        result.invoke(r);
                    }
                    catch (Throwable e) {
                        result.invokeWithException(e);
                    }
                });
            }
            catch (Throwable e) {
                result.invokeWithException(e);
            }
        });

        return result;
    }

    public void invoke(V result) {
        invokeWithResultOrException(result, null);
    }

    public void invokeWithException(Throwable t) {
        invokeWithResultOrException(null, t);
    }

    protected void invokeWithResultOrException(V result, Throwable t) {
        if (!invoked) {
            invoked = true;
            this.result = result;
            this.exception = t;
        } else {
            return;
        }
        for (Consumer<Promise<V>> callback : callbacks) {
            callback.accept(this);
        }
    }

    public void onRedeem(Consumer<Promise<V>> callback) {
        synchronized (this) {
            if (!invoked) {
                callbacks.add(callback);
            }
        }
        if (invoked) {
            callback.accept(this);
        }
    }

    public V get() {
        return result;
    }
}
