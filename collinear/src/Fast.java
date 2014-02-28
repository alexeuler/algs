import java.security.InvalidParameterException;
import java.util.Arrays;

/**
 * Created by Алексей Карасев on 28.02.14.
 */
public class Fast {

    private static Point[] data;

    private static Point parse(String s) {
        String[] coords = s.split(" ");
        Point p = new Point(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]));
        return p;
    }

    private static void findAndPrint() {
        if (data.length < 4) {
            throw new InvalidParameterException();
        }
        for (Point p : data) {
            Arrays.sort(data, p.SLOPE_ORDER);
            printEqual(p);
        }
    }

    private static void printEqual(Point p) {
        int n = 1;
        double slope = p.slopeTo(data[0]);
        for (int i = 1; i < data.length; i++) {
            if (p.slopeTo(data[i]) == slope) {
                n++;
                continue;
            } else {
                if (n >= 4) {
                    Point[] collinear = new Point[n];
                    for (int j = 0; j < n; j++) {
                        collinear[j] = data[i-1-j];
                    }
                    print(collinear);
                }
                n = 1;
                slope = p.slopeTo(data[i]);
            }
        }
        if (n >= 4) {
            Point[] collinear = new Point[n];
            for (int j = 0; j < n; j++) {
                collinear[j] = data[data.length-1-j];
            }
            print(collinear);
        }

    }

    private static void print(Point[] points) {
        int i = 0;
        Arrays.sort(points);
        for (Point p : points) {
            i++;
            if (i != points.length) {
                System.out.print(p.toString());
                System.out.print(" -> ");
            }
        }
        System.out.print(points[points.length - 1].toString() + "\n");
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = Integer.parseInt(in.readLine());
        data = new Point[n];
        for (int i = 0; i < n; i++) {
            data[i] = parse(in.readLine());
        }
        findAndPrint();
    }

}
