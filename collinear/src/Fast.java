import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Алексей Карасев on 28.02.14.
 */
public class Fast {

    private static Point[] data;
    private static Point[] sorted_data;
    private static List<List<Point>> lines = new List<List<Point>>();

    private static class List<Item extends Comparable<Item>> implements Comparable<List<Item>> {
        public Object[] data;
        public int size;

        public List() {
            data = new Object[4];
            size = 0;
        }

        public Item get(int i) {
            return (Item) data[i];
        }

        public void push(Item item) {
            if (size == data.length) {
                resize(2 * data.length);
            }
            data[size++] = item;
        }

        public int compareTo(List<Item> that) {
            if (this.size < that.size) {
                return -1;
            }
            if (this.size > that.size) {
                return 1;
            }
            for (int i = 0; i < this.size; i++) {
                Item thisItem = this.get(i);
                Item thatItem = that.get(i);
                if (thisItem.compareTo(thatItem) == 0) {
                    continue;
                }
                return thisItem.compareTo(thatItem);
            }
            return 0;
        }

        public void sort() {
            resize(size);
            Arrays.sort(data);
        }

        public <T> T[] toArray(T[] a) {
            return (T[]) Arrays.copyOf(data, size, a.getClass());
        }

        private void resize(int capacity) {
            Object[] copy = new Object[capacity];
            for (int i = 0; i < size; i++)
                copy[i] = data[i];
            data = copy;
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
        for (Point p : data) {
            Arrays.sort(sorted_data, p.SLOPE_ORDER);
            findCollinear(p);
        }
        printLines();
    }

    private static void findCollinear(Point p) {
        int n = 1;
        double slope = p.slopeTo(sorted_data[0]);
        for (int i = 1; i < sorted_data.length + 1; i++) {
            if ((i != sorted_data.length) && (p.slopeTo(sorted_data[i]) == slope)) {
                n++;
                continue;
            } else {
                if (n >= 3) {
                    List<Point> collinear = new List<Point>();
                    for (int j = 0; j < n; j++) {
                        collinear.push(sorted_data[i - 1 - j]);
                    }
                    collinear.push(p);
                    collinear.sort();
                    lines.push(collinear);
                }
                n = 1;
                if (i != sorted_data.length) {
                    slope = p.slopeTo(sorted_data[i]);
                }
                ;
            }
        }
    }

    private static void printLines() {
        lines.sort();
        List<Point> a = lines.get(0);
        printLine(a);
        for (int i = 1; i < lines.data.length; i++) {
            if (lines.get(i).compareTo(lines.get(i - 1)) == 0) {
                continue;
            }
            printLine(lines.get(i));
        }
    }

    private static void printLine(List<Point> list) {
        Point[] array = list.toArray(new Point[0]);
        String result = "";
        result += array[0].toString();
        array[0].drawTo(array[array.length - 1]);
        for (int i = 1; i < array.length; i++) {
            result += " -> ";
            result += array[i].toString();
        }
        StdOut.println(result);
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
