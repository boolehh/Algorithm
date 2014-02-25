import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Write a generic data type for a deque and a randomized queue. 
 * The goal of this assignment is to implement elementary data structures 
 * using arrays and linked lists, and to introduce you to generics and iterators. 
 * @author He Xiangbo
 * @time 2013.9.29
 */
public class Deque<Item> implements Iterable<Item> {
    private int size;
    private Node first;
    private Node last;
    private class Node {
        private Item item;
        private Node next;
        private Node previous;
        }
    //java.lang.UnsupportedOperationException
    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
        }
    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
        }
    // return the number of items on the deque
    public int size() {
        return size;
        }
    // insert the item at the front
    public void addFirst(Item item) {
        if (item == null)
            throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.previous = null;
        if (size == 0) {
            first.next = null;
            last = first;
            }
        else {
            first.next = oldfirst;
            oldfirst.previous = first;
            }
        size++;
        }
    public void addLast(Item item) {     // insert the item at the end
        if (item == null)
            throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (size == 0) {
            last.previous = null;
            first = last;
            }
        else {
            last.previous = oldlast;
            oldlast.next = last;
            }
        size++;
        }
    // delete and return the item at the front
    public Item removeFirst() {
        if (size == 0)
            throw new NoSuchElementException();
        Item temp = first.item;
        if (size == 1) {
            first = null;
            last = null;
            }
        else {
            first = first.next;
            first.previous = null;
            }
        size--;
        return temp;
        }
    // delete and return the item at the end
    public Item removeLast() {
        if (size == 0)
            throw new NoSuchElementException();
        Item temp = last.item;
        if (size == 1) {
            first = null;
            last = null;
            }
        else {
            last = last.previous;
            last.next = null;
            }
        size--;
        return temp;
        }
    private void show() {
        if (first != null) {
            StdOut.println("has items");
            }
        StdOut.println(size);
        }
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() { return new DequeIterator(); }
    private class DequeIterator implements Iterator<Item> {
        private Node current;
        DequeIterator() {
            current = first;
            if (current == null)
                StdOut.println("current is null");
            if (first == null)
                StdOut.println("first is null");
            }
        public boolean hasNext() {
            return current != null;
            }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
            }
        public Item next() {
            if (current == null)
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
            }
        }
    private static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        String[] test = {"hello", "1", "2", "3", "4"};
        //String empty = null;
        //deque.addFirst(empty); //test NoSuchElementException();
        for (int i = 0; i < 5; i++) {
            deque.addFirst(test[i]);
            deque.addLast(test[i]);
            }
        StdOut.println(deque.size());
        Iterator<String> iter = deque.iterator();
        while (iter.hasNext()) {
            StdOut.println("still has a item");
            String s = iter.next();
            StdOut.println(s);
            }
        for (int i = 0; i < 5; i++) {
            deque.removeFirst();
            deque.removeLast();
            }
        StdOut.println(deque.size());
        while (iter.hasNext()) {
            String s = iter.next();
            StdOut.println(s);
            }
        //iter.next(); //test NoSuchElementException();
        //iter.remove(); //test java.lang.UnsupportedOperationException();
        //deque.removeFirst(); //test NoSuchElementException();
        }
}