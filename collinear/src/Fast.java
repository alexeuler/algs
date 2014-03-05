import java.util.Arrays;

/**
 * Created by Алексей Карасев on 28.02.14.
 */
public class Fast {

    private static Point[] data;
    private static Point[] sorted_data;
    private static Node printed;

    private static class Node {
        private Node next;
        private Node last;
        private String data;

        public Node(String init) {
            this.data = init;
            this.last = this;
        }

        public void push(String item) {
            last.next = new Node(item);
            last = last.next;
        }

        public boolean has(String item) {
            Node pointer = this;
            while (pointer != null) {
                if (pointer.data.equals(item)) {
                    return true;
                }
                pointer = pointer.next;
            }
            return false;
        }
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

    private static void findAndPrint() {
        printed = new Node("");
        for (Point p : data) {
            Arrays.sort(sorted_data, p.SLOPE_ORDER);
            printEqual(p);
        }
    }

    private static void printEqual(Point p) {
        int n = 1;
        double slope = p.slopeTo(sorted_data[0]);
        for (int i = 1; i < sorted_data.length; i++) {
            if (p.slopeTo(sorted_data[i]) == slope) {
                n++;
                continue;
            } else {
                if (n >= 3) {
                    Point[] collinear = new Point[n + 1];
                    for (int j = 0; j < n; j++) {
                        collinear[j] = sorted_data[i - 1 - j];
                    }
                    collinear[n] = p;
                    print(collinear);
                }
                n = 1;
                slope = p.slopeTo(sorted_data[i]);
            }
        }
        if (n >= 3) {
            Point[] collinear = new Point[n + 1];
            for (int j = 0; j < n; j++) {
                collinear[j] = sorted_data[sorted_data.length - 1 - j];
            }
            collinear[n] = p;
            print(collinear);
        }

    }

    private static void print(Point[] points) {
        int i = 0;
        Arrays.sort(points);

        String result = "";
        for (Point p : points) {
            i++;
            if (i != points.length) {
                result += p.toString() + " -> ";
            }
        }
        result += points[points.length - 1].toString() + "\n";
        if (!printed.has(result)) {
            StdOut.print(result);
            points[0].drawTo(points[points.length - 1]);
            printed.push(result);
        }
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        In in = new In(args[0]);
        String line = in.readLine();
        line = line.replace(" ", "");
        int n = Integer.parseInt(line);
        data = new Point[n];
        sorted_data = new Point[n];
        int i = 0;
        while (i < n) {
            line = in.readLine();
            if ((line == null) || line.equals("")) {
                continue;
            }
            data[i] = parse(line);
            sorted_data[i] = data[i];
            i++;
        }
        findAndPrint();
        StdOut.print("Done");
    }

}
