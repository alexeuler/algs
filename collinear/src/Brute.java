import java.util.Arrays;

/**
 * Created by Алексей Карасев on 24.02.14.
 */
public class Brute {

    private static Point[] data;

    private static void find_and_print() {
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
        Point[] out = new Point[]{p, q, r, s};
        Arrays.sort(out);
        String result = "";
        for (int i = 0; i < 3; i++) {
            result += out[i].toString();
            result += " -> ";
        }
        result += out[3].toString() + "\n";
        StdOut.print(result);
        out[0].drawTo(out[3]);
    }

    private static Point parse(String s) {
        String[] coords = s.split(" ");
        String[] refined = new String[2];
        int i = 0;
        for (String str : coords) {
            if (!str.equals("")) {
                refined[i] = str;
                i++;
            }
        }
        Point p = new Point(Integer.parseInt(refined[0]), Integer.parseInt(refined[1]));
        p.draw();
        return p;
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        In in = new In(args[0]);
        String line = in.readLine();
        line = line.replace(" ", "");
        int n = Integer.parseInt(line);
        data = new Point[n];
        for (int i = 0; i < n; i++) {
            String str = in.readLine();
            data[i] = parse(str);
        }
        find_and_print();
    }

}
