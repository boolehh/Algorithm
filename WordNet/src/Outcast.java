

class Outcast {
    private WordNet ws;
 // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        ws = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        if (nouns == null)
            throw new NullPointerException();
        int distance;
        int maxDistance = Integer.MIN_VALUE;
        String leastRelated = nouns[0];
        for (int i = 0; i < nouns.length; i++) {
            distance = 0;
            for (int j = 0; j < nouns.length; j++)
                distance += ws.distance(nouns[i], nouns[j]);
            if (maxDistance < distance)
                leastRelated = nouns[i];
        }
        return leastRelated;
    }

    // for unit testing of this class (such as the one below)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            @SuppressWarnings("deprecation")
            String[] nouns = In.readStrings(args[t]);
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}