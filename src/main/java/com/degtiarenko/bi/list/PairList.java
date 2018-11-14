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
     * Checks whether list contains pair of first and second parameter.
     *
     * @param first  first
     * @param second second
     * @see List#contains(Object)
     */
    boolean contains(Object first, Object second);

    /**
     * Adds pair of first and second parameters to list
     *
     * @param first  first in pair
     * @param second second in pair
     * @see List#add(Object)
     */
    boolean add(T1 first, T2 second);

    /**
     * Removes pair of first and second parameters from list
     *
     * @param first  first in pair
     * @param second second in pair
     * @see List#remove(Object)
     */
    boolean remove(Object first, Object second);

    /**
     * Gets first of paired element at the specified position in the list
     *
     * @return first of paired element at the specified position in the list
     * @see List#get(int)
     */
    T1 getFirst(int index);

    /**
     * Gets second of paired element at the specified position in the list
     *
     * @return second of paired element at the specified position in the list
     * @see List#get(int)
     */
    T2 getSecond(int index);

    /**
     * Returns iterator for this list
     *
     * @return iterator
     */
    PairIterator<T1, T2> iterator();

    /**
     * @see List#clear()
     */
    void clear();


}
