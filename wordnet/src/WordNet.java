import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * Created by Алексей Карасев on 27.03.14.
 */
public class WordNet {
    private static final int INFINITY = Integer.MAX_VALUE;
    private Hashtable<String, Integer> dictionary;
    private ArrayList<String> synsets;
    private Digraph graph;

    // constructor takes the name of the two input files
    public WordNet(String synsetsFilename, String hypernymsFilename) {
        dictionary = new Hashtable<String, Integer>();
        In in = new In(synsetsFilename);
        String line;
        Integer id = -1;
        while ((line = in.readLine()) != null) {
            String[] tokens = line.split(",");
            id = Integer.parseInt(tokens[0]);
            synsets.add(tokens[0]);
            String[] words = tokens[1].split(" ");
            for (String word : words)
                dictionary.put(word, id);
        }

        graph = new Digraph(id + 1);
        in = new In(hypernymsFilename);
        while ((line = in.readLine()) != null) {
            String[] tokens = line.split(",");
            for (int i = 1; i < tokens.length; i++)
                graph.addEdge(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[i]));
        }
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        return dictionary.containsKey(word);
    }

    // distance between nounA and nounB
    public int distance(String nounA, String nounB) {
        Integer idA = dictionary.get(nounA);
        Integer idB = dictionary.get(nounB);
        if ((idA == null) || (idB == null)) throw new java.lang.IllegalArgumentException();
        SAP sap = new SAP(graph);
        return sap.length(idA, idB);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path
    public String sap(String nounA, String nounB) {
        Integer idA = dictionary.get(nounA);
        Integer idB = dictionary.get(nounB);
        if ((idA == null) || (idB == null)) throw new java.lang.IllegalArgumentException();
        SAP sap = new SAP(graph);
        int id_synset = sap.ancestor(idA, idB);
        return synsets.get(id_synset);
    }

}
