import java.io.IOException;
//import java.util.Iterator;
//import java.util.Iterator;
//import java.io.InputStream;
//import java.io.PipedInputStream;
//import java.io.PipedOutputStream;

public class Subset {
    public static void main(String[] args) throws IOException {
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();
        int N = Integer.parseInt(args[0]);
        //String add;
        while (!StdIn.isEmpty()) {
            randomizedQueue.enqueue(StdIn.readString());
            }
        //Iterator<String> iter = randomizedQueue.iterator();
        //while (iter.hasNext()) {
            //StdOut.println(iter.next());
            //}
        //StdOut.println(randomizedQueue.size());
        //StdOut.println();
        for (int i = 0; i < N; i++)
            StdOut.println(randomizedQueue.dequeue());
        }
}