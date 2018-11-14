package com.degtiarenko.bi.list;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * GC-free alternative to {@code Iterator<Pair<T1, T2>>}
 * <p>Typical usage looks like this:
 * <pre>{@code
 *     while (iterator.hasNext())
 *         T1 first = iterator.nextFirst();
 *         T2 second = iterator.nextSecond();
 * }</pre>
 * <p> After and only after call of {@link PairIterator#nextFirst()} should be call of {@link PairIterator#nextSecond()}
 *
 * @author Ivan Degtyarenko
 * @since 14.11.2018
 */
public interface PairIterator<T1, T2> {
    /**
     * @see Iterator#next()
     */
    boolean hasNext();

    /**
     * Returns first of pair which is next in iteration.
     * Note: does not move cursor.
     *
     * @return first in pair
     */
    T1 nextFirst();

    /**
     * Returns second in pair which is next in iteration and moves cursor.
     *
     * @return second in pair
     */
    T2 nextSecond();

    /**
     * @see Iterator#remove()
     */
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    /**
     * Performs given action on pair.
     *
     * @param action action which accepts paired parameters
     * @see Iterator#forEachRemaining(Consumer)
     */
    default void forEachRemaining(BiConsumer<? super T1, ? super T2> action) {
        Objects.requireNonNull(action);
        while (hasNext()) {
            action.accept(nextFirst(), nextSecond());
        }
    }
}
