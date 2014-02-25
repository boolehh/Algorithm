import java.util.ArrayList;


//java.lang.IllegalArgumentException
class WordNet {
    //private int N;
    private mWord word;
    //private Bag<Integer>[] hypernyms;
    //SAP sap;
    private Digraph wordD;
    private class mWord {
        
        private ArrayList<String> synonym;
        private ArrayList<String> defination;

        mWord() {
            synonym =  new ArrayList<String>();
            defination = new ArrayList<String>();
        }
        private void add(String syn, String def) {
            synonym.add(syn);
            defination.add(def);
        }
        private int indexOf(String s) {
            return synonym.indexOf(s);
        }
        private boolean contains(String s) {
            return synonym.contains(s);
        }
    }
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        
        In inSynsets = new In(synsets);
        In inHypernyms = new In(hypernyms);
        word = new mWord();
        wordD = new Digraph(inHypernyms);
        String line;
        while (inSynsets.hasNextLine()) {
            line = inSynsets.readLine();
            String[] tokens = line.split(",");
            word.add(tokens[1], tokens[2]);
        }
        //N = word.size();
       //this.hypernyms = (Bag<Integer>[]) new Bag[N];
       //
       //while (inHypernyms.hasNextLine()) {
       //    line = inSynsets.readLine();
       //    String[] tokens = line.split(",");
       //    int id = Integer.parseInt(tokens[0]);
       //    this.hypernyms[id] = new Bag<Integer>();
       //    for (int j = 1; j < tokens.length; j++)
       //        this.hypernyms[id].add(Integer.parseInt(tokens[j]));
       //}
    }

    // the set of nouns (no duplicates), returned as an Iterable
    public Iterable<String> nouns() {
        return word.synonym;
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return this.word.contains(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB ==null)
            throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        SAP sap = new SAP(wordD);
        int indexA = word.indexOf(nounA);
        int indexB = word.indexOf(nounB);
        return sap.length(indexA, indexB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB ==null)
            throw new IllegalArgumentException();
        if (!isNoun(nounA) || !isNoun(nounB))
            throw new IllegalArgumentException();
        SAP sap = new SAP(wordD);
        int indexA = word.indexOf(nounA);
        int indexB = word.indexOf(nounB);
        int index = sap.ancestor(indexA, indexB);
        return word.synonym.get(index);
    }

    // for unit testing of this class
    public static void main(String[] args) {
        
    }
}