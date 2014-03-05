
public class CircularSuffixArray {
    
    private String suffixes;
    private int length;
    private int[] index;
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        suffixes = s;
        length = s.length();
        index = new int[length];
        int[] indext = new int[length];
        for (int i = 0; i < length; i++) {
            String ss = suffixes.substring(i, length) + suffixes.substring(0, i);
            indext[i] = 0;
            for (int j = 0; j < length; j++) {
                if (ss.compareTo(suffixes.substring(j, length) + suffixes.substring(0, j)) > 0) {
                    indext[i]++;
                }
            }
            index[indext[i]] = i;
        }
    }
    // length of s
    public int length() {
        return length;
    }
    // returns index of ith sorted suffix
    public int index(int i) {
        return index[i];
    }
    
    public static void main (String[] args) {
        String s = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(s);
        int length = s.length();
        for (int i = 0; i < length; i++) {
            System.out.println(csa.index(i));
        }
    }
}
