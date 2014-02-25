import java.util.Iterator;
 
public class RandomizedQueue2<Item> implements Iterable<Item> {
    private Item[] rq;
    private int N = 0;
    public RandomizedQueue2() {
        rq = (Item[]) new Object[2];
        }
    public boolean isEmpty() {
        return N == 0;
        }
    public int size() {
        return N;
        }
    private void resize(int Max) {
        assert Max >= N;
        Item[] temp = (Item[]) new Object[Max];
        for(int i=0; i<N; i++){
            temp[i] = rq[i];
            }
        rq = temp;
        }
    public void enqueue(Item item) {
        if(item == null) throw new java.lang.NullPointerException();
        if(N == rq.length) resize(2*rq.length);
        rq[N++] = item;
        }
    public Item dequeue() {
        if(isEmpty()) throw new java.util.NoSuchElementException();
        int index = (int) (Math.random()*N);
        Item item = rq[index];
        if(index != N-1) rq[index] = rq[N-1];
        rq[N-1] = null;
        N--;
        if(N>0 && N==rq.length/4) resize(rq.length/2);
        return item;
        }
    public Item sample() {
        if(isEmpty()) throw new java.util.NoSuchElementException();
        int index = (int) (Math.random()*N);
        Item item = rq[index];
        return item;
        }
    public Iterator<Item> iterator() {
        return new RQIterator();
        }
    private class RQIterator implements Iterator<Item> {
        private int index = 0;
        private Item[] r;
        public RQIterator() {
            r = (Item[]) new Object[N];
            for(int i=0; i<N; i++)
            r[i] = rq[i];
            StdRandom.shuffle(r);
            }
        public boolean hasNext() {
            return index < N;
            }
        public void remove() {
            throw new java.lang.UnsupportedOperationException();
            }
        public Item next() {
            if(!hasNext()) throw new java.util.NoSuchElementException();
            Item item = r[index++];
            return item;
            }
        }
}