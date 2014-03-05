import java.util.Scanner;

/*
 * 
 * 
 */
public class MoveToFront {
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        char[] alphbet = new char[256];
        alphbet[0] = 0x00;
        for (int i = 1; i < 256; i++) {
            alphbet[i] = (char) (alphbet[i-1] + 1);
        }
        
        Scanner in = new Scanner(System.in);
        
        while (in.hasNext()) {
            String s = in.nextLine();
            for (int i = 0; i < s.length(); i++)
                System.out.println(indexOf(alphbet, s.charAt(i)));
        }
        in.close();
    }
    
    private static int indexOf(char[] s, char c) {
        int l = s.length;
        int index;
        for (index = 0; index < l; index++) {
            if (s[index] == c)
                break;
        }
        for (int i = index; i > 0; i--) {
            s[i] = s[i-1];
        }
        s[0] = c;
        return index;
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] alphbet = new char[256];
        alphbet[0] = 0x00;
        for (int i = 1; i < 256; i++) {
            alphbet[i] = (char) (alphbet[i-1] + 1);
        }
        Scanner in = new Scanner(System.in);
        int c;
        char temp;
        while (in.hasNext()) {
            c = in.nextInt();
            System.out.println(alphbet[c]);
            temp = alphbet[c];
            for (int i = c; i > 0; i--) {
                alphbet[i] = alphbet[i-1];
            }
            alphbet[0] = temp; 
        }
        in.close();
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if (args == null)
            throw new IllegalArgumentException();
        if (args[0].equals("-")) {
            //System.out.println("start to encode");
            encode();
        }
        if (args[0].equals("+")) {
            //System.out.println("start to decode");
            decode();
        }
    }
}
