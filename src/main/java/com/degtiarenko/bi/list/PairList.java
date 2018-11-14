package com.degtiarenko.bi.list;

import java.util.List;

/**
 * GC-free alternative to {@code List<Pair<T1, T2>>}
 *
 * @author Ivan Degtyarenko
 * @since 14.11.2018
 */
public interface PairList<T1, T2> {
    /**
     * @see List#size()
     */
    int size();

    /**
     * @see List#isEmpty()
     */
    boolean isEmpty();

    /**
     * Adds pair of first and second parameters to list
     * @see List#add(Object)
     *
     * @param first  first in pair
     * @param second second in pair
     */
    boolean add(T1 first, T2 second);

    /**
     * Removes pair of first and second parameters from list
     * @see List#remove(Object)
     *
     * @param first first in pair
     * @param second second in pair
     */
    boolean remove(Object first, Object second);

    /**
     * Gets first of paired element at the specified position in the list
     * @see List#get(int)
     *
     * @return first of paired element at the specified position in the list
     */
    T1 getFirst(int index);

    /**
     * Gets second of paired element at the specified position in the list
     * @see List#get(int)
     *
     * @return second of paired element at the specified position in the list
     */
    T2 getSecond(int index);
}
