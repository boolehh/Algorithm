import java.util.NoSuchElementException;
import java.util.Iterator;

/*
 * A randomized queue is similar to a stack or queue, 
 * except that the item removed is chosen uniformly at random
 * from items in the data structure. 
 */

/*
 * N random calls to next() and hasNext() in iterator()
 * N random calls to: enqueue(), sample(), dequeue(), isEmpty(), and size()
 * Test aborted. Ran out of time or crashed before completion.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Node head;
    private class Node {
        private Item item;
        private Node next;
        }
    public RandomizedQueue() {           // construct an empty randomized queue
        size = 0;
        head = null;
        }
    
    // is the queue empty?
    public boolean isEmpty() { return size == 0; }

    // return the number of items on the queue
    public int size() { return size; }

    public void enqueue(Item item) {     // add the item
        if (item == null)
            throw  new NullPointerException();
        Node oldhead = head;
        head = new Node();
        head.item = item;
        head.next = oldhead;
        size++;
        }
    private void show() {
        Node index = head;
        while (index != null) {
            StdOut.print(index.item + " ");
            index = index.next;
            }
        StdOut.println();
        }
    public Item dequeue() {              // delete and return a random item
        if (size == 0)
            throw new NoSuchElementException();
        int i = StdRandom.uniform(size) + 1;
        Node previous = head;
        Node delete;
        if (i == 1) {
            head = head.next;
            size--;
            return previous.item;
            }
        else {
            for (int j = 2; j < i; j++) {
                previous = previous.next;
                }
            delete = previous.next;
            previous.next = delete.next;
            }
        size--;
        return delete.item;
        }
    // return (but do not delete) a random item
    public Item sample() {
        if (size == 0)
            throw new NoSuchElementException();
        int i = StdRandom.uniform(size);
        Node sample = head;
        for (int j = 0; j < i; j++) {
            sample = sample.next;
            }
        return sample.item;
        }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() { return new RandomizedIterator(); }
    private class RandomizedIterator implements Iterator<Item> {
        private Node current;
        private boolean[] used = new boolean[size];

        public boolean hasNext() {
            for (boolean bool : used)
                if (!bool)
                    return true;
            return false;
            }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
            }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            current = head;
            int i = StdRandom.uniform(size);
            while (used[i]) {
                 i = StdRandom.uniform(size);
                 }
            used[i] = true;
            for (int j = 0; j < i; j++) {
                current = current.next;
                }
            return current.item;
            }
        }
}