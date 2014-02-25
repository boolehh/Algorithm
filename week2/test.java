import java.util.Iterator;


 


public class Deque<Item> implements Iterable<Item> {


private Node first;


private Node last;


private int N;


private class Node {


Item item;


Node pre;


Node next;


}



public Deque() {


first = null;


last = null;


N = 0;


}



public boolean isEmpty() {


return N == 0;


}



public int size () {


return N;


}



public void addFirst(Item item) {


if(item == null) throw new java.lang.NullPointerException();


Node oldfirst = first;


first = new Node();


first.item = item;


first.next = oldfirst;


if(isEmpty()) last = first;


else oldfirst.pre = first;


N++;


}



public void addLast(Item item) {


if(item == null) throw new java.lang.NullPointerException();


Node oldlast = last;


last = new Node();


last.item = item;


last.pre = oldlast;


if(isEmpty()) first = last;


else oldlast.next = last;


N++;


}



public Item removeFirst() {


if(isEmpty()) {


throw new java.util.NoSuchElementException();


}


Item item = first.item;


first = first.next;


N--;


if(isEmpty()) {


last = null;


}


else {


first.pre = null;


}


return item;


}



public Item removeLast() {


if(isEmpty()) throw new java.util.NoSuchElementException();


Item item = last.item;


last = last.pre;


N--;


if(isEmpty()) first = null;


else last.next = null;


return item;


}



public Iterator<Item> iterator() {


return new ListIterator();


}



private class ListIterator implements Iterator<Item> {


private Node current = first;


public boolean hasNext() {


return current != null;


}


public void remove() {


throw new java.lang.UnsupportedOperationException();


}


public Item next() {


if(!hasNext()) throw new java.util.NoSuchElementException();


Item item = current.item;


current = current.next;


return item;


}


}



public static void main(String[] args) {
	Deque<String> deque = new Deque<String>();
	Iterator<String> iter = deque.iterator();
	String[] test = {"hello", "1", "2", "3", "4"};
	String empty = null;
	//deque.addFirst(empty); //test NoSuchElementException();
	for (int i = 0; i < 5; i++) {
		deque.addFirst(test[i]);
		deque.addLast(test[i]);
		}
	StdOut.println(deque.size());
	while (iter.hasNext()) {
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


}