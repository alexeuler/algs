import java.io.*;

import static org.junit.Assert.*;


/**
 * Created by Алексей Карасев on 24.02.14.
 */
public class BruteTest {

    private final ByteArrayOutputStream outContent =  new ByteArrayOutputStream();
    private final PrintStream stdout = System.out;
    private File f;
    private OutputStream s;
    @org.junit.Before
    public void setUp() throws Exception {
        f = File.createTempFile("brute", ".txt");
        f.deleteOnExit();
        s = new FileOutputStream(f);
        System.setOut(new PrintStream(outContent, true, "UTF-8"));
    }

    @org.junit.After
    public void tearDown() throws Exception {
        s.close();
        System.setOut(stdout);
    }

    @org.junit.Test
    public void testInput6() throws Exception {
        String in = "6\n";
        in+= "19000 10000\n";
        in+= "18000 10000\n";
        in+= "32000 10000\n";
        in+= "21000 10000\n";
        in+= "1234 5678\n";
        in+= "14000 10000\n";
        s.write(in.getBytes());
        String[] args = new String [1];
        args[0] = f.getAbsolutePath();
        Brute.main(args);
        String r="";
        //String newLine = System.getProperty("line.separator");
        String newLine = "\n";
        r+="(18000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000)"+ newLine;
        r+="(14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (32000, 10000)"+ newLine;
        r+="(14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (21000, 10000)"+ newLine;
        r+="(14000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000)"+ newLine;
        r+="(14000, 10000) -> (18000, 10000) -> (21000, 10000) -> (32000, 10000)"+ newLine;
        assertArrayEquals(r.getBytes(), outContent.toByteArray());
    }
    @org.junit.Test
    public void testInput8() throws Exception {
        String in = "8\n";
        in+= "10000 0\n";
        in+= "0 10000\n";
        in+= "3000 7000\n";
        in+= "7000 3000\n";
        in+= "20000 21000\n";
        in+= "3000 4000\n";
        in+= "14000 15000\n";
        in+= "6000 7000\n";
        s.write(in.getBytes());
        String[] args = new String [1];
        args[0] = f.getAbsolutePath();
        Brute.main(args);
        String r="";
        //String newLine = System.getProperty("line.separator");
        String newLine = "\n";
        r+="(10000, 0) -> (7000, 3000) -> (3000, 7000) -> (0, 10000)"+ newLine;
        r+="(3000, 4000) -> (6000, 7000) -> (14000, 15000) -> (20000, 21000)"+ newLine;
        assertArrayEquals(r.getBytes(), outContent.toByteArray());
    }

}
