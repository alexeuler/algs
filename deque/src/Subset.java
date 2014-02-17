/**
 * Created by Алексей Карасев on 17.02.14.
 */
public class Subset {

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        String line = StdIn.readLine();
        for (String data : line.split(" ")) {
            queue.enqueue(data);
        }
        int k = Integer.parseInt(args[0]);
        for (int i = 0; i < k; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
