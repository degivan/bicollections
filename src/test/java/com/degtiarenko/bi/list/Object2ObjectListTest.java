package com.degtiarenko.bi.list;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
}
