import java.io.IOException;
import java.nio.file.Paths;
import java.util.Scanner;

//import java.util.Iterator;

class SAP {
    
    private MyDigraph sap;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(MyDigraph G) {
        sap = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v > sap.V()-1 || w < 0 || w > sap.V()-1)
            throw new IndexOutOfBoundsException();
        int ancestor = ancestor(v, w);
        if (ancestor == -1) return -1;
        return shortestLength(v, ancestor) + shortestLength(w, ancestor);
    }
    
    private int shortestLength(int v, int w) {
        if (v < 0 || v > sap.V()-1 || w < 0 || w > sap.V()-1)
            throw new IndexOutOfBoundsException();
        boolean[] marked = new boolean[sap.V()];
        int[] pre = new int[sap.V()];
        Queue<Integer> queue = new Queue<Integer>();
        queue.enqueue(v);
        while(!queue.isEmpty()) {
            int p = queue.dequeue();
            for(int q : sap.adj(p)) {
                if(q == w) {
                    pre[w] = p;
                    break;
                }
                else if(!marked[q]) {
                    marked[q] = true;
                    pre[q] = p;
                    queue.enqueue(q);
                }
            }
        }
        int length = 1;
        int pw = w;
        while(pre[pw] != v) {
            pw = pre[pw];
            length++;
        }
        return length;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v > sap.V()-1 || w < 0 || w > sap.V()-1)
            throw new IndexOutOfBoundsException();
        if (v == w)
            return v;
        int[] route = new int[sap.V()];
        Queue<Integer> vqueue = new Queue<Integer>();
        Queue<Integer> wqueue = new Queue<Integer>();
        route[v] = v;
        route[w] = w;
        vqueue.enqueue(v);
        wqueue.enqueue(w);
        int p, q;
        while (!vqueue.isEmpty() || !wqueue.isEmpty()) {
            if(!vqueue.isEmpty()){
                p = vqueue.dequeue();
                for (int m : sap.adj(p)) {
                    if (route[m] == w) return m;
                    else if(route[m] != v) {
                        vqueue.enqueue(m);
                        route[m] = v;
                    }
                }
            }
            if(!wqueue.isEmpty()) {
                q = wqueue.dequeue();
                for (int n : sap.adj(q)) {
                    if (route[n] == v) return n;
                    else if(route[n] != w) {
                        wqueue.enqueue(n);
                        route[n] = w;
                    }
                }
            }
        }
        return -1;
    }


    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            return -1;
        int minlength = Integer.MAX_VALUE;
        int temp;
        for (int mv : v)
            for (int mw : w) {
                temp = length(mv, mw);
                if (temp < minlength) {
                    minlength = temp;
                }
            }
        return minlength;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        if (v == null || w == null)
            return -1;
        int minlength = Integer.MAX_VALUE;
        int ancestor = -1;
        int temp;
        for (int mv : v)
            for (int mw : w) {
                temp = length(mv, mw);
                if (temp < minlength) {
                    minlength = temp;
                    ancestor = ancestor(mv, mw);
                }
            }
        return ancestor;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) throws IOException {
        //In in = new In(args[0]);
        //In in = new In(file);
        Scanner in = new Scanner(Paths.get(args[0]));
        MyDigraph G = new MyDigraph(in);
        SAP sap = new SAP(G);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}