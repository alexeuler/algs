import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Алексей Карасев on 14.02.14.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Data<Item> data;
    private Index<Integer> index;

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Item sample() {
        int number = StdRandom.uniform(size());
        int randomIndex = index.get(number);
        return data.get(randomIndex);
    }

    public Item dequeue() {
        int number = StdRandom.uniform(size());
        int randomIndex = index.get(number);
        Item result=data.get(randomIndex);
        data.remove(randomIndex);
        return result;
    }

    public void enqueue(Item item){
        data.push(item);
    }


    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current = 0;
        private int[] iteratorIndex;

        public RandomizedQueueIterator() {
            iteratorIndex = new int[index.size()];
            int i = 0;
            int j = 0;
            while (j < data.size()) {
                if (data.get(i) != null) {
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
            return current < iteratorIndex.length - 1;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return data.get(iteratorIndex[++current]);
        }
    }

    private class ResizableArray<Tuple> {

        private int INITIAL_LENGTH = 1;
        protected Tuple[] data;
        protected int size = 0; //this is the number of non-null elements

        public ResizableArray() {
            data = (Tuple[]) new Object[INITIAL_LENGTH];
            size = 0;
        }

        public void push() {
            if (size + 1 == data.length) {
                lengthUp();
            }
        }

        public void remove() {
            if (size * 4 <= data.length) {
                lengthDown();
            }
        }

        public Tuple get(int i) {
            return data[i];
        }

        public int size() {
            return size;
        }

        private void lengthUp() {
            int newLength = data.length * 2;
            Tuple[] new_data = (Tuple[]) new Object[newLength];
            for (int i = 0; i < data.length; i++) {
                new_data[i] = data[i];
            }
            data = new_data;
        }

        private void lengthDown() {
            if (data.length == 1) return;
            int newLength = (int) (data.length / 2);
            Tuple[] new_data = (Tuple[]) new Object[newLength];
            for (int i = 0; i < newLength; i++) {
                new_data[i] = data[i];
            }
            data = new_data;
        }

    }

    private class Index<Tuple> extends ResizableArray<Tuple> {
        // no nulls in the middle, if deleted from the middle, last element goes to the empty place
        public void remove(int i) {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            data[i] = data[size--];
            data[size] = null;
            super.remove();
        }

        public void push(Tuple elem) {
            data[++size] = elem;
            super.push();
        }
    }

    private class Data<Tuple> extends ResizableArray<Tuple> {
        public void remove(int i) {
            if (size == 0) {
                throw new NoSuchElementException();
            }
            data[i] = null;
            size--;
            super.remove();
        }

        public void push(Tuple elem) {
            data[++size] = elem;
            super.push();
        }
    }

}
