import java.security.InvalidParameterException;
import java.util.Arrays;

/**
 * Created by Алексей Карасев on 24.02.14.
 */
public class Brute {

    private static Point[] data;

    private static void find_and_print() {
        if (data.length < 4) {
            throw new InvalidParameterException();
        }
        for (int i = 0; i < data.length; i++) {
            for (int j = i + 1; j < data.length; j++) {
                for (int k = j + 1; k < data.length; k++) {
                    for (int l = k + 1; l < data.length; l++) {
                        if (isLine(data[i], data[j], data[k], data[l])) {
                            print(data[i], data[j], data[k], data[l]);
                        }
                    }
                }
            }
        }
    }

    private static boolean isLine(Point p, Point q, Point r, Point s) {
        if ((p.slopeTo(q) == p.slopeTo(r)) && (p.slopeTo(q) == p.slopeTo(s))) {
            return true;
        }
        return false;
    }

    private static void print(Point p, Point q, Point r, Point s) {
        Point[] out = new Point[] {p,q,r,s};
        Arrays.sort(out);
        for (int i=0; i<3; i++) {
            System.out.print(out[i].toString());
            System.out.print(" -> ");
        }
        System.out.print(out[3].toString() + "\n");
    }

    private static Point parse(String s) {
        String[] coords = s.split(" ");
        Point p = new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
        return p;
    }

    public static void main(String[] args) {
        In in=new In(args[0]);
        int n = Integer.parseInt(in.readLine());
        data = new Point[n];
        for (int i=0; i < n; i++) {
            data[i] = parse(in.readLine());
        }
        find_and_print();
    }

}
