import org.omg.CosNaming._NamingContextImplBase;
import org.omg.DynamicAny._DynFixedStub;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
        switch (size()) {
            case 0:
                first = new Node(data);
                last=first;
                break;
            case 1:
                first = new Node(data);
                first.next = last;
                last.prev = first;
                break;
            default:
                Node oldFirst = first;
                first = new Node(data);
                first.next=oldFirst;
                oldFirst.prev=first;
        }
        size++;
    }

    public void addLast(Item data) {
        switch (size()) {
            case 0:
                first = new Node(data);
                last=first;
                break;
            case 1:
                last = new Node(data);
                first.next = last;
                last.prev = first;
                break;
            default:
                Node oldLast = last;
                last = new Node(data);
                oldLast.next=last;
                last.prev=oldLast;
        }
        size++;
    }


    public Item removeFirst() {
        Item data;
        switch (size()) {
            case 0:
                throw new NoSuchElementException();
            case 1:
                data = first.data;
                first = null;
                last = null;
                size--;
                break;
            case 2:
                data = first.data;
                first=last;
                first.next = null;
                first.prev = null;
                last.next = null;
                last.prev = null;
                size --;
                break;
            default:
                data = first.data;
                first = first.next;
                first.prev = null;
                break;
        }
        return data;
    }

    public Item removeLast() {
        Item data;
        switch (size()) {
            case 0:
                throw new NoSuchElementException();
            case 1:
                data = first.data;
                first = null;
                last = null;
                size--;
                break;
            case 2:
                data = last.data;
                last=first;
                first.next = null;
                first.prev = null;
                last.next = null;
                last.prev = null;
                size --;
                break;
            default:
                data = last.data;
                last = last.prev;
                last.next = null;
                break;
        }
        return data;
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
        public Node prev;
        public Item data;

        public Node(Item payload) {
            data = payload;
        }
    }

}
