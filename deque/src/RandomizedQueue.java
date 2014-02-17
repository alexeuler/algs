import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Алексей Карасев on 14.02.14.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int INITIAL_LENGTH = 8;
    private Item[] data = (Item[]) new Object[INITIAL_LENGTH];
    private Integer[] index = new Integer[INITIAL_LENGTH];
    private int size = 0; //number of non-null elements
    private int last = 0; //pointer to the last element + 1 in data, e.g. for [1, null, 20. null, 50] - last = 5, size = 3
    private boolean changed; //for iterator management


    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int number = StdRandom.uniform(size);
        int randomIndex = index[number];
        return data[randomIndex];
    }

    public void enqueue(Item item) {
        changed = true;
        if (item == null) {
            throw new NullPointerException();
        }
        data[last] = item;
        index[size()] = last;
        last++;
        size++;
        if (last + 1 >= data.length) {
            allocMemory();
        }
    }

    public Item dequeue() {
        changed = true;
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int number = StdRandom.uniform(size);
        int randomIndex = index[number];
        Item result = data[randomIndex];

        size--;
        //removing the index and data entry
        //for index delete the entry and put the last entry in its place
        //for data just delete the entry
        index[number] = index[size()];
        index[size()] = null;
        data[randomIndex] = null;
        if ((size() * 4 <= data.length) && (size() > INITIAL_LENGTH)) {
            compact();
        }
        return result;
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }


    private void allocMemory() {
        int newLength = data.length * 2;
        Item[] new_data = (Item[]) new Object[newLength];
        Integer[] new_index = new Integer[newLength];
        for (int i = 0; i < data.length; i++) {
            new_data[i] = data[i];
            new_index[i] = index[i];
        }
        data = new_data;
        index = new_index;
    }

    private void compact() {
        int new_length = data.length / 2;
        Item[] new_data = (Item[]) new Object[new_length];
        Integer[] new_index = new Integer[new_length];
        int i = 0;
        int j = 0;
        while (j < data.length) {
            if (data[j] != null) {
                new_data[i] = data[j];
                new_index[i] = i;
                i++;
            }
            j++;
        }
        data = new_data;
        last = size() - 1;
        index = new_index;
    }


    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        private int[] iteratorIndex;

        public RandomizedQueueIterator() {
            changed = false;
            iteratorIndex = new int[size()];
            int i = 0;
            int j = 0;
            while (j < data.length) {
                if (data[i] != null) {
                    iteratorIndex[i] = j;
                    i++;
                }
                j++;
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return current < iteratorIndex.length;
        }

        public Item next() {
            if (changed) {throw new RuntimeException();}
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return data[iteratorIndex[current++]];
        }
    }


}
