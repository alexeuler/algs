import static junit.framework.Assert.*;

/**
 * Created by Алексей Карасев on 27.03.14.
 */
public class WordNetTest {
    @org.junit.Test
    public void testIsNoun() throws Exception {
        WordNet wn = new WordNet("test/synsets.txt", "test/hypernyms.txt");
        assertTrue(wn.isNoun("'hood"));
        assertTrue(wn.isNoun("public_speaking"));
        assertTrue(wn.isNoun("1530s"));
        assertFalse(wn.isNoun("dfgdfjlgkjlhood"));
    }


}
