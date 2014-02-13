import java.util.Iterator;

/**
 * Created by alex on 2/13/14.
 */
public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size = 0;

    public Deque() {
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFirst(Item data) {
        Node oldFirst = first;
        first = new Node(data);
        first.next = oldFirst;
        size++;
    }

    public void addLast(Item data) {
        Node oldLast = last;
        last = new Node(data);
        oldLast.next = last;
        size++;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {

    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            Item data = current.data;
            current = current.next;
            return data;
        }
    }

    private class Node {
        public Node next;
        public Item data;

        public Node(Item payload) {
            data = payload;
        }
    }

}
