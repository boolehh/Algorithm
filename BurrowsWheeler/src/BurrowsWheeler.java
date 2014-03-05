import java.util.Arrays;
import java.util.Scanner;


public class BurrowsWheeler {
    
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        @SuppressWarnings("resource")
        Scanner in = new Scanner(System.in);
        String s;
        int index = 0;
        
        while (in.hasNext()) {
            s = in.nextLine();
            int length = s.length();
            char[] encoded = new char[s.length()];
            CircularSuffixArray CSA = new CircularSuffixArray(s);
            for (int i = 0; i < CSA.length(); i++) {
                if (CSA.index(i) == 0)
                    index = i;
                encoded[i] = s.charAt((CSA.index(i)+length-1)%length);;
                //System.out.print(CSA.index(i));
                }
            //System.out.println();
            System.out.println(index);
            System.out.println(encoded);
        }
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {
        
        @SuppressWarnings("resource")
        Scanner in = new Scanner(System.in);
        if(!in.hasNext())
            throw new IllegalArgumentException();
        
        int first = Integer.parseInt(in.nextLine());
        String lastColum = in.nextLine();
        //System.out.println(first);
        //System.out.println(lastColum);
        char[] firstColum = lastColum.toCharArray();
        Arrays.sort(firstColum);
        //System.out.println(firstColum);
        int[] next = new int[lastColum.length()];
        
        //calculate the next[]
        for (int i = 0; i < firstColum.length;) {
            char c = firstColum[i];
            int j = 0;
            while (firstColum[i] == c) {
                //System.out.printf("%c %d ", c, i);
                for (; j < lastColum.length(); j++) {
                    if (lastColum.charAt(j) == c) {
                        next[i] = j;
                        //System.out.printf("%d\n", j);
                        j++;
                        break;
                    }
                }
                if(++i >= firstColum.length)
                    break;
            }
        }
        
        //deduce the original string
        char[] originalS = new char[lastColum.length()];
        int index = first;
        for (int i = 0; i < originalS.length; i++) {
            originalS[i] = firstColum[index];
            index = next[index];
        }
        System.out.println(originalS);
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if (args == null)
            throw new IllegalArgumentException();
        if (args[0].equals("-")) {
            encode();
        }
        if (args[0].equals("+")) {
            decode();
        }
    }
}
