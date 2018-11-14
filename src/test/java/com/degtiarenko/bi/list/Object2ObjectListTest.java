package com.degtiarenko.bi.list;

import org.junit.Test;

import java.util.ConcurrentModificationException;

import static org.junit.Assert.*;

/**
 * @author Ivan Degtyarenko
 * @since 14.11.2018
 */
public class Object2ObjectListTest {
    @Test
    public void testSimpleAddThenGet() {
        Object2ObjectList<Integer, String> list = new Object2ObjectList<>();
        list.add(1, "1");
        list.add(2, "2");
        assertEquals(Integer.valueOf(1), list.getFirst(0));
        assertEquals("1", list.getSecond(0));
        assertEquals(Integer.valueOf(2), list.getFirst(1));
        assertEquals("2", list.getSecond(1));
    }

    @Test
    public void testSimpleAddThenRemove() {
        Object2ObjectList<Integer, String> list = new Object2ObjectList<>();
        list.add(1, "1");
        boolean res = list.remove(1, "1");
        assertTrue(res);
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    public void testIteratorCorrectWork() {
        Object2ObjectList<Integer, String> list = new Object2ObjectList<>();
        list.add(1, "1");
        list.add(2, "2");
        PairIterator<Integer, String> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(it.nextFirst(), Integer.valueOf(1));
        assertEquals(it.nextSecond(), "1");
        assertTrue(it.hasNext());
        assertEquals(it.nextFirst(), Integer.valueOf(2));
        assertEquals(it.nextSecond(), "2");
        assertFalse(it.hasNext());
    }

    @Test
    public void testIteratorFailFastAfterAdd() {
        Object2ObjectList<Integer, String> list = new Object2ObjectList<>();
        list.add(1, "1");
        list.add(2, "2");
        PairIterator<Integer, String> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(it.nextFirst(), Integer.valueOf(1));
        list.add(3, "3");
        try {
            it.nextSecond();
        } catch (ConcurrentModificationException ex) {
            return;
        }
        fail("Should throw concurrent modification exception.");
    }

    @Test
    public void testIteratorFailFastAfterRemove() {
        Object2ObjectList<Integer, String> list = new Object2ObjectList<>();
        list.add(1, "1");
        list.add(2, "2");
        PairIterator<Integer, String> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(it.nextFirst(), Integer.valueOf(1));
        list.remove(1, "1");
        try {
            it.nextSecond();
        } catch (ConcurrentModificationException ex) {
            return;
        }
        fail("Should throw concurrent modification exception.");
    }

    @Test
    public void testIteratorNotFailRemoveNotFound() {
        Object2ObjectList<Integer, String> list = new Object2ObjectList<>();
        list.add(1, "1");
        list.add(2, "2");
        PairIterator<Integer, String> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(it.nextFirst(), Integer.valueOf(1));
        list.remove(3, "3");
        assertEquals(it.nextSecond(), "1");
        assertTrue(it.hasNext());
        assertEquals(it.nextFirst(), Integer.valueOf(2));
        assertEquals(it.nextSecond(), "2");
        assertFalse(it.hasNext());
    }

    @Test
    public void testIteratorFailFastAfterClear() {
        Object2ObjectList<Integer, String> list = new Object2ObjectList<>();
        list.add(1, "1");
        list.add(2, "2");
        PairIterator<Integer, String> it = list.iterator();
        assertTrue(it.hasNext());
        assertEquals(it.nextFirst(), Integer.valueOf(1));
        list.clear();
        try {
            it.nextSecond();
        } catch (ConcurrentModificationException ex) {
            return;
        }
        fail("Should throw concurrent modification exception.");
    }
}
