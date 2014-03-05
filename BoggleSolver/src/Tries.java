
public class Tries {
    private static final int R = 26;        // extended upper case A to Z

    private Node root = new Node();

    private static class Node {
        private Object end;
        private Node[] next = new Node[R];
    }

   /****************************************************
    * Is the key in the symbol table?
    ****************************************************/
    public boolean contains(String key) {
        Node x = get(key);
        if(x== null) return false;
        return (boolean)x.end == true;
    }
    
    public boolean isPrefix(String key) {
        return get(key) != null;
    }

    public Node get(String key) {
        Node x = get(root, key, 0);
        if (x == null) return null;
        return  x;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c-'A'], key, d+1);
    }

   /****************************************************
    * Insert key-value pair into the symbol table.
    ****************************************************/
    public void put(String key) {
        root = put(root, key, 0);
    }

    private Node put(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            x.end = true;
            return x;
        }
        char c = key.charAt(d);
        if(x.end == null) x.end = false;
        x.next[c-'A'] = put(x.next[c-'A'], key, d+1);
        return x;
    }

    /****************************************************
     * delete the key from the symbol table.
     ****************************************************/
    public void delete(String key) {
        root = delete(root, key, 0);
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) x.end = null;
        else {
            char c = key.charAt(d);
            x.next[c-'A'] = delete(x.next[c-'A'], key, d+1);
        }
        if (x.end != null) return x;
        for (int c = 0; c < R; c++)
            if (x.next[c] != null)
                return x;
        return null;
    }


    // test client
    public static void main(String[] args) {

        // build symbol table from standard input
        TrieST<Integer> st = new TrieST<Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print results
        for (String key : st.keys()) {
            StdOut.println(key + " " + st.get(key));
        }
    }
}
