package com.monad.example.either;

import com.monad.example.Monad;
import com.monad.example.optional.Optional;

import java.util.Objects;
import java.util.function.Function;

/**
 * Created by iurii.dziuban on 10.01.2017.
 *
 * Also known as Result Monad : if T2 is for Exception . Similar to Optional monad except "right" value is instead of Optional.empty()
 */
public class Either<T1, T2> implements Monad<T1> {
    private Optional<T1> left;
    private Optional<T2> right;

    private Either(T1 left, T2 right) {
        this.left = Optional.ofNullable(left);
        this.right = Optional.ofNullable(right);
    }

    /** This is the unit method */
    public static <U, E> Either<U, E> ofLeft(U value) {
        return new Either<>(value, null);
    }

    public static <U, E> Either<U, E> ofRight(E right) {
        return new Either<U, E>(null, right);
    }

    /** This is the bind method */
    public<U> Either<U, T2> flatMap(Function<T1, Either<U, T2>> mapper) {
        return bind(mapper);
    }

    public boolean isLeftPresent() {
        return left.isPresent();
    }

    public T1 getLeft() {
        return left.get();
    }

    public T2 getRight() {
        return right.get();
    }

    @Override
    public Either<T1, T2> pure(T1 t) {
        return t == null ? ofRight(null) : ofLeft(t);
    }

    @Override
    public <R> Either<R, T2> bind(Function<? super T1, ? extends Monad<R>> f) {
        if(this.isLeftPresent()) {
            return (Either<R, T2>) f.apply(left.get());
        }
        return (Either<R, T2>) this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Either)) {
            return false;
        }

        Either<?, ?> other = (Either<?, ?>) obj;
        return Objects.equals(left, other.left) && Objects.equals(right, other.right);
    }

    @Override
    public String toString() {
        return left != null
                ? String.format("Either[left][%s]", left)
                : String.format("Either[right][%s]", right);
    }
}
