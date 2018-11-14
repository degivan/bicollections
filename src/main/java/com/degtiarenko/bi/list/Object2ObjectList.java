package com.degtiarenko.bi.list;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * @author Ivan Degtyarenko
 * @since 14.11.2018
 */
public class Object2ObjectList<T1, T2> implements PairList<T1, T2> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private Object[] firsts;
    private Object[] seconds;
    private int size = 0;

    /**
     * Made long instead of int to decrease probability of false non-positive failing of iterator even more.
     *
     * @see java.util.AbstractList#modCount
     */
    protected transient long modCount = 0;

    public Object2ObjectList(int capacity) {
        this.firsts = new Object[capacity];
        this.seconds = new Object[capacity];
    }

    public Object2ObjectList() {
        this(DEFAULT_CAPACITY);
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object first, Object second) {
        for (int index = 0; index < size; index++) {
            if (equalsToElementN(first, index, firsts) && (equalsToElementN(second, index, seconds))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean add(T1 first, T2 second) {
        ensureCapacityInternal(size + 1);
        firsts[size] = first;
        seconds[size++] = second;
        return false;
    }

    private void ensureCapacityInternal(int minCapacity) {
        modCount++;
        if (minCapacity - firsts.length > 0) {
            grow(minCapacity);
        }
    }

    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = firsts.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        firsts = Arrays.copyOf(firsts, newCapacity);
        seconds = Arrays.copyOf(seconds, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
    }

    @Override
    public boolean remove(Object first, Object second) {
        for (int index = 0; index < size; index++) {
            if (equalsToElementN(first, index, firsts) && (equalsToElementN(second, index, seconds))) {
                fastRemove(index);
                return true;
            }
        }
        return false;
    }

    private static boolean equalsToElementN(Object object, int n, Object[] values) {
        return object == null && values[n] == null || object != null && object.equals(values[n]);
    }

    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(firsts, index + 1, firsts, index, numMoved);
            System.arraycopy(seconds, index + 1, seconds, index, numMoved);
        }
        firsts[--size] = null;
        seconds[size] = null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T1 getFirst(int index) {
        rangeCheck(index);
        return (T1) firsts[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public T2 getSecond(int index) {
        rangeCheck(index);
        return (T2) seconds[index];
    }

    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(String.format("Index: %d, size: %d", index, size));
        }
    }

    @Override
    public PairIterator<T1, T2> iterator() {
        return new Iterator();
    }

    @Override
    public void clear() {
        modCount++;
        // clear to let GC do its work
        for (int i = 0; i < size; i++) {
            firsts[i] = null;
            seconds[i] = null;
        }
        size = 0;
    }

    private class Iterator implements PairIterator<T1, T2> {
        int cursor;       // index of next element to return
        int lastRet = -1; // index of last element returned; -1 if no such
        long expectedModCount = modCount;
        boolean nextFirstWasCalled = false;

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T1 nextFirst() {
            nextFirstWasCalled = true;
            checkForComodification();
            Object[] elementData = Object2ObjectList.this.firsts;
            int i = cursor;
            if (i >= size) {
                throw new NoSuchElementException();
            }
            if (i >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            return (T1) elementData[lastRet = i];
        }

        @Override
        @SuppressWarnings("unchecked")
        public T2 nextSecond() {
            checkFirstWasCalled();
            checkForComodification();
            Object[] elementData = Object2ObjectList.this.seconds;
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            if (cursor >= elementData.length) {
                throw new ConcurrentModificationException();
            }
            cursor++;
            return (T2) elementData[lastRet];
        }

        private void checkFirstWasCalled() {
            if (!nextFirstWasCalled) {
                throw new IllegalArgumentException("Wrong order! PairIterator#nextFirst() should be called first.");
            }
            nextFirstWasCalled = false;
        }

        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    }
}
