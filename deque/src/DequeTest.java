/**
 * Created by alex on 2/13/14.
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

public class DequeTest {

    Deque<String> deque;

    private String[] dequeToArray(Deque<String> deq) {
        String[] arr=new String[deq.size()];
        int i=0;
        for (String s : deq) {
            arr[i++]=s;
        }
        return arr;
    }

    @Before
    public void setUp() throws Exception {
        deque = new Deque<String>();
    }

    @Test
    public void testIsEmpty() throws Exception {
        assertTrue(deque.isEmpty());
        deque.addFirst("");
        assertFalse(deque.isEmpty());
    }

    @Test
    public void testAddFirst() throws Exception {
        deque.addFirst("1");
        assertFalse(deque.isEmpty());
        assertTrue(deque.size() == 1);
        String[] expected = new String[] {"1"};
        assertArrayEquals(expected, dequeToArray(deque));
        deque.addFirst("2");
        expected = new String[] {"2", "1"};
        assertArrayEquals(expected, dequeToArray(deque));
    }

    @Test
    public void testAddLast() throws Exception {
        deque.addLast("1");
        assertFalse(deque.isEmpty());
        assertTrue(deque.size() == 1);
        String[] expected = new String[] {"1"};
        assertArrayEquals(expected, dequeToArray(deque));
        deque.addLast("2");
        expected = new String[] {"1", "2"};
        assertArrayEquals(expected, dequeToArray(deque));
    }

    @Test(expected=NoSuchElementException.class)
    public void testRemoveFirstWhenEmpty() throws Exception {
        deque.removeFirst();
    }

    @Test
    public void testRemoveFirst() throws Exception {
        deque.addFirst("1");
        assertTrue(deque.removeFirst() == "1");
        assertTrue(deque.isEmpty());
        deque.addLast("1");
        deque.addLast("2");
        assertTrue(deque.removeFirst() == "1");
        assertTrue(deque.size() == 1);
        assertTrue(deque.removeFirst() == "2");
        assertTrue(deque.isEmpty());
    }

    @Test(expected=NoSuchElementException.class)
    public void testRemoveLastWhenEmpty() throws Exception {
        deque.removeLast();
    }

    @Test
    public void testRemoveLast() throws Exception {
        deque.addFirst("1");
        assertTrue(deque.removeLast() == "1");
        assertTrue(deque.isEmpty());
        deque.addLast("1");
        deque.addLast("2");
        assertTrue(deque.removeLast() == "2");
        assertTrue(deque.size() == 1);
        assertTrue(deque.removeLast() == "1");
        assertTrue(deque.isEmpty());
    }

    @Test
    public void testIterator() throws Exception {
        deque.addFirst("1");
        deque.addLast("2");
        String[] res = new String[4];
        int k=0;
        for (String i : deque) {
            for (String j : deque) {
                res[k] = i + " " + j;
                k++;
            }
        }
        String[] expected = new String[] {"1 1","1 2", "2 1", "2 2"};
        assertArrayEquals(expected, res);
    }
}
