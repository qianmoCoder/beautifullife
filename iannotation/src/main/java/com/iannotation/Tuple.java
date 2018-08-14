package com.iannotation;

import java.util.Objects;

/**
 * Created by yzbzz on 2018/6/6.
 */
public class Tuple<F, S> {
    public final F first;
    public final S second;

    /**
     * Constructor for a Tuple.
     *
     * @param first  the first object in the Tuple
     * @param second the second object in the Tuple
     */
    public Tuple(F first, S second) {
        this.first = first;
        this.second = second;
    }

    /**
     * Checks the two objects for equality by delegating to their respective
     * {@link Object#equals(Object)} methods.
     *
     * @param o the {@link Tuple} to which this one is to be checked for equality
     * @return true if the underlying objects of the Tuple are both considered
     * equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tuple)) {
            return false;
        }
        Tuple<?, ?> p = (Tuple<?, ?>) o;
        return Objects.equals(p.first, first) && Objects.equals(p.second, second);
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Tuple
     */
    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^ (second == null ? 0 : second.hashCode());
    }

    @Override
    public String toString() {
        return "Tuple{" + String.valueOf(first) + " " + String.valueOf(second) + "}";
    }

    /**
     * Convenience method for creating an appropriately typed Tuple.
     *
     * @param a the first object in the Tuple
     * @param b the second object in the Tuple
     * @return a Tuple that is templatized with the types of a and b
     */
    public static <A, B> Tuple<A, B> create(A a, B b) {
        return new Tuple<A, B>(a, b);
    }
}
