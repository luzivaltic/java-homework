import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;

public class WordNet {
    private Digraph wordNet;
    private HashMap<String , List<Integer> > nouns;
    private HashMap<Integer , String> hypernymMap;
    private SAP sapWordNet;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms){
        if( synsets == null || hypernyms == null ){
            throw new IllegalArgumentException();
        }

        nouns = new HashMap<String , List<Integer>>();
        hypernymMap = new HashMap<Integer , String>();
        In in = new In(synsets);
        
        while(in.hasNextLine()) {
            String[] synsetStrings = in.readLine().split(",");   
            String[] words = synsetStrings[1].split(" ");
            int id = Integer.parseInt( synsetStrings[0] );            
            hypernymMap.put(id,synsetStrings[1]);
            for(String word : words) {
                if( !nouns.containsKey(word) ){
                    nouns.put(word, new ArrayList<>());
                } 
                nouns.get(word).add(id);  
            }
        }

        wordNet = new Digraph( hypernymMap.size() );
        in = new In(hypernyms);
        while( in.hasNextLine() ) {
            String[] idConnect = in.readLine().split(",");   
            int id = Integer.parseInt(idConnect[0]);
            for(int i = 1; i < idConnect.length; i++){
                wordNet.addEdge(id, Integer.parseInt(idConnect[i]));   
            }
        }
        sapWordNet = new SAP(wordNet);

        if( ( new DirectedCycle(wordNet) ).hasCycle() ){
            throw new IllegalArgumentException();   
        }

        int numRoot = 0;
        for(int i = 0; i < wordNet.V(); i++){
            if( !wordNet.adj(i).iterator().hasNext() ){
                numRoot++;
            }
        }

        if( numRoot != 1 ) {
            throw new IllegalArgumentException();
        }
    }
 
    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return nouns.keySet();
    }
 
    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if( word == null ){
            throw new IllegalArgumentException();
        }
        return nouns.containsKey(word);
    }
 
    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if( !isNoun(nounA) || !isNoun(nounB) ){
            throw new IllegalArgumentException();
        }
        return sapWordNet.length(nouns.get(nounA) ,nouns.get(nounB));
    }
 
    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if( !isNoun(nounA) || !isNoun(nounB) ){
            throw new IllegalArgumentException();
        }
        return hypernymMap.get( sapWordNet.ancestor( nouns.get(nounA) , nouns.get(nounB) ) );
    }
 
    // do unit testing of this class
    public static void main(String[] args) {
        WordNet tester = new WordNet("synsets.txt", "hypernyms.txt");
    }
}