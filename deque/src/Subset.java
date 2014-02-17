/**
 * Created by Алексей Карасев on 17.02.14.
 */
public class Subset {

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        String line;
        while ((line = StdIn.readString()) != null) {
            queue.enqueue(line);
        }
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
