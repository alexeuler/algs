/**
 * Created by alex on 2/13/14.
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

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
}
