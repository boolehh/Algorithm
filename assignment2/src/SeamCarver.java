import java.awt.Color;
import java.awt.image.BufferedImage;

public class SeamCarver {
   
    private int height;
    private int width;
    private Picture picture;

    public SeamCarver(Picture picture) {
        this.picture = picture;
        this.height = picture.height();
        this.width = picture.width();
        }
    // current picture
    public Picture picture() {
        return picture;
        }
    // width  of current picture
    public int width() {
        return width;
        }
    // height of current picture
    public int height() {
        return height;
        }
    // energy of pixel at column x and row y in current picture
    public double energy(int x, int y) {
        if (x < 0 || x > width - 1 || y < 0 || y > height - 1)
            throw new IndexOutOfBoundsException();
        if (x == 0 || x == width - 1 || y == 0 || y == height - 1)
            return  195075;
        Color left = picture.get(x - 1, y);
        Color right = picture.get(x + 1, y);
        Color above = picture.get(x, y - 1);
        Color below = picture.get(x, y + 1);
        double redx = right.getRed() - left.getRed();
        double greenx = right.getGreen() - left.getGreen();
        double bluex = right.getBlue() - left.getBlue();
        double redy = above.getRed() - below.getRed();
        double greeny =  above.getGreen() - below.getGreen();
        double bluey =  above.getBlue() - below.getBlue();
        double xenergy = redx*redx + bluex*bluex + greenx*greenx;
        double yenergy = redy*redy + greeny*greeny + bluey*bluey;
        return xenergy + yenergy;
        }
    // sequence of indices for horizontal seam in current picture
    public int[] findHorizontalSeam() {
        int []hseam = new int[width-2];
        long[][] penergy = new long[height][width];
        int[][] prev = new int[height][width];
        
        for (int i = 0; i < height; i++)
            penergy[i][1] = (long) energy(1, i);
        for(int j = 2; j < width - 1; j++) {
            penergy[0][j-1] = penergy[height-1][j-1] = Long.MAX_VALUE;
            for (int i = 1; i < height - 1; i++) {
                if (penergy[i-1][j-1] <= penergy[i][j-1])
                    if (penergy[i-1][j-1] <= penergy[i+1][j-1])
                        prev[i][j] = i-1;
                    else
                        prev[i][j] = i+1;
                else if (penergy[i][j-1] <= penergy[i+1][j-1])
                    prev[i][j] = i;
                else
                    prev[i][j] = i+1;
                penergy[i][j] = (long) (energy(j, i) + penergy[prev[i][j]][j-1]);       
            }
        }
        long temp = Long.MAX_VALUE;
        for (int i = 1; i < height - 1; i++)
            if (temp > penergy[i][height-2]) {
                temp = penergy[i][height-2];
                hseam[width-2] = i;
            }
        for (int i = width-3; i > 0; i++) {
            hseam[i] = prev[hseam[i+1]][i+1];
        }
        hseam[0] = hseam[1];
        hseam[width-1]= hseam[width-2];
        return hseam;
        }
    // sequence of indices for vertical   seam in current picture
    public int[] findVerticalSeam() {
        int[] vseam = new int[height];
        long[][] penergy = new long[height][width];
        int[][] prev = new int[height][width];
        
        for (int i = 1; i < width-1; i++)
            penergy[1][i] = (long) energy(i, 1);
        //calculate row by row
        for (int i = 2; i < height - 1; i++) {
            penergy[i-1][0] = penergy[i-1][width-1] = Long.MAX_VALUE;
            for (int j = 1; j < width - 1; j++) {
                if (penergy[i-1][j-1] <= penergy[i-1][j]) {
                    if (penergy[i-1][j-1] <= penergy[i-1][j+1])
                        prev[i][j] = j-1;
                }
                else if (penergy[i-1][j] <= penergy[i-1][j+1])
                    prev[i][j] = j;
                else
                    prev[i][j] = j+1;
                penergy[i][j] = (long) (energy(j, i) + penergy[i-1][prev[i][j]]);       
            }
        }
        //find the smallest energy pixel of the second last row
        long temp = Long.MAX_VALUE;
        for (int i = 1; i < width - 1; i++)
            if (temp > penergy[height-2][i]) {
                temp = penergy[height-2][i];
                vseam[height-2] = i;
            }
        //fill the vseam
        for (int i = height-3; i > 0; i--) {
            vseam[i] = prev[i+1][vseam[i+1]];
        }
        vseam[0] = vseam[1];
        vseam[height-1]= vseam[height-2];
        return vseam;
        }
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] a) {
        if (a == null)
            throw new java.lang.IllegalArgumentException();
        if (a.length != width || width < 1)
            throw new java.lang.IllegalArgumentException();
        if (!checkserials(a))
            throw new IllegalArgumentException();
        //define a new picture, and copy the image from old picture to this new one
        Picture newPicture = new Picture(width, height-1);
        for(int i = 0; i < width; i++)
            for(int j = 0; j < height - 1; j++)
            {
                if(j < a[i])
                    newPicture.set(i, j, picture.get(i, j));
                else
                    newPicture.set(i, j, picture.get(i, j+1));
            }
        picture = newPicture;
        height--;
    }
   // remove vertical   seam from current picture
    public void removeVerticalSeam(int[] a) {
        if (a == null)
            throw new java.lang.IllegalArgumentException();
        if (a.length != height || height < 1)
            throw new java.lang.IllegalArgumentException();
        if (!checkserials(a))
            throw new IllegalArgumentException();
        //define a new picture, and copy the image from old picture to this new one
        Picture newPicture = new Picture(width-1, height);
        for(int i = 0; i < height; i++)
            for(int j = 0; j < width - 1; j++)
            {
                if(j < a[i])
                    newPicture.set(j, i, picture.get(j, i));
                else
                    newPicture.set(j, i, picture.get(j+1, i));
            }
        picture = newPicture;
        width--;
        }
    
    private boolean checkserials(int[] s) {
        if (s == null) return false;
        for (int i = 0; i < s.length - 1; i++) {
            if (Math.abs(s[i] - s[i+1]) > 1)
                return false;
        }
        return true;
    }
    public static void main(String[] args) {
        Picture p = new Picture("/home/boolee/workspace/assignment1/HJoceanSmall.png");
        SeamCarver sc = new SeamCarver(p);
        sc.picture().show();
        int[] a;
        for (int i = 0; i < 50; i++) {
            a = sc.findVerticalSeam();
            sc.removeVerticalSeam(a);
        }
        sc.picture().show();
        sc.picture().save("/home/boolee/workspace/assignment1/HJoceanSmall_V.png");
    }
}