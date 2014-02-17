import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Алексей Карасев on 14.02.14.
 */
public class RandomizedQueueTest {

    RandomizedQueue<String> queue;

    @Before
    public void setUp() throws Exception {
        queue = new RandomizedQueue<String>();
    }

    @Test
    public void testOneElement() throws Exception {
        assertTrue(queue.isEmpty());
        assertTrue(queue.size() == 0);
        queue.enqueue("1");
        assertTrue(queue.size() == 1);
        for (String data : queue) {
            assertTrue(data == "1");
        }
        assertTrue(queue.dequeue().equals("1"));
        assertTrue(queue.isEmpty());
        assertTrue(queue.size() == 0);
    }

    @Test
    public void testManyElements() throws Exception {
        assertTrue(queue.isEmpty());
        assertTrue(queue.size() == 0);
        int number = 1000;
        for (int i=0; i < number; i++) {
            queue.enqueue(((Integer)i).toString());
        }
        assertTrue(queue.size() == number);
        Integer i = 0;
        for (String data : queue) {
            assertTrue(data.equals(i.toString()));
            i++;
        }
        assertTrue(i == queue.size());
        String[] results = new String[number];
        for (i=0; i<number; i++) {
            results[i]=queue.dequeue();
        }
        assertTrue(queue.isEmpty());
        assertTrue(results.length == number);
        List<String> list = Arrays.asList(results);
        for (i=0; i<number; i++) {
            assertTrue(list.contains(i.toString()));
        }
    }

    @Test
    public void testRandomness() throws Exception {
    }


    @Test
    public void testSize() throws Exception {

    }

    @Test
    public void testIsEmpty() throws Exception {

    }

    @Test
    public void testSample() throws Exception {

    }

    @Test
    public void testDequeue() throws Exception {

    }

    @Test
    public void testEnqueue() throws Exception {

    }
}
