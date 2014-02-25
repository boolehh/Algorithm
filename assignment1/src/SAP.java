//import java.util.Iterator;

class SAP {
    
    private Digraph sap;
 // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        sap = G;
    }
   //public SAP(String file) {
   //    In in = new In(file);
   //    sap = new Digraph(in);
   //}

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (v < 0 || v > sap.V() || w < 0 || w > sap.V())
            throw new IndexOutOfBoundsException();
        int ancestor = ancestor(v, w);
        if (ancestor == -1) return -1;
        int vlength = 0;
        int wlength = 0;
        if (v != ancestor) {
            boolean[] marked = new boolean[sap.V()];
            Queue<Integer> vqueue = new Queue<Integer>();
            vqueue.enqueue(v);
            int[][] q = new int[sap.V()][2]; //contains the previous vertex and its distance
            q[v][0] = v;
            q[v][1] = 0;
            while (!vqueue.isEmpty()) {
                    int qq = vqueue.dequeue();
                    for (int p : sap.adj(qq)) {
                        if (p == ancestor) {
                            vlength = q[qq][1] + 1;
                            break;
                        }
                        if (!marked[p]) {
                            q[p][0] = qq;
                            q[p][1] = q[qq][1] + 1;
                            marked[p] = true;
                            vqueue.enqueue(p);
                        }
                    }
                }
        }
        if (w != ancestor) {
            boolean[] marked = new boolean[sap.V()];
            Queue<Integer> wqueue = new Queue<Integer>();
            wqueue.enqueue(w);
            int[][] q = new int[sap.V()][2];
            q[w][0] = v;
            q[w][1] = 0;
            while (!wqueue.isEmpty()) {
                    int qq = wqueue.dequeue();
                    for (int p : sap.adj(qq)) {
                        if (p == ancestor) {
                            vlength = q[qq][1] + 1;
                            break;
                        }
                        if (!marked[p]) {
                            q[p][0] = qq;
                            q[p][1] = q[qq][1] + 1;
                            marked[p] = true;
                            wqueue.enqueue(p);
                        }
                    }
                }
        }
        return vlength + wlength;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (v < 0 || v > sap.V() || w < 0 || w > sap.V())
            throw new IndexOutOfBoundsException();
        if (v == w)
            return v;
        int[] route = new int[sap.V()];
        Queue<Integer> vqueue = new Queue<Integer>();
        Queue<Integer> wqueue = new Queue<Integer>();
        route[v] = 1;
        route[w] = 2;
        vqueue.enqueue(v);
        wqueue.enqueue(w);
        int q;
        while (vqueue.isEmpty() && wqueue.isEmpty()) {
            if (!vqueue.isEmpty()) {
                q = vqueue.dequeue();
                for (int p : sap.adj(q)) {
                    if (route[p] == 2) return p;
                    vqueue.enqueue(p);
                    route[p] = 1;
                    }
                }
            if (!wqueue.isEmpty()) {
                q = wqueue.dequeue();
                for (int p : sap.adj(q)) {
                    if (route[p] == 1) return p;
                    wqueue.enqueue(p);
                    route[p] = 2;
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
    public static void main(String[] args) {
        In in = new In(args[0]);
        Digraph G = new Digraph(in);
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