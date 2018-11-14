package com.degtiarenko.bi.list;

import java.util.Arrays;

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
    // will be used by iterator to check whether list was modified or not
    private int modCount = 0;

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
}
