import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

//import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {
//	private static final int INFINITY = Integer.MAX_VALUE;
	private HashMap<Integer, LinkedList<String>> synsetMap;
	private HashMap<String, LinkedList<Integer>> synsetMap2;
	private final int V;           // number of vertices in this digraph
	private Digraph myDigraph;
    private LinkedList<String> nounsll;
    private HashSet<String> nounsset;
    private HashSet<Integer> nodeSynset;
    private HashSet<Integer> nodeHypernymset;
    private SAP mysap;
	
   // constructor takes the name of the two input files
   public WordNet(String synsets, String hypernyms) {
		if(synsets == null || hypernyms == null) {
			throw new IllegalArgumentException();
		}
	   
	   nounsll = new LinkedList<String>();
	   nounsset = new HashSet<String>();
	   synsetMap = new HashMap<Integer, LinkedList<String>>();
	   synsetMap2 = new HashMap<String, LinkedList<Integer>>();
	   nodeSynset = new HashSet<Integer>();
	   nodeHypernymset = new HashSet<Integer>();
	   
	   
	      In synsetsIn = new In(synsets);
	      In hypernymsIn = new In(hypernyms);
	      
          String[] lines1 = synsetsIn.readAllLines();
          this.V = lines1.length;
          for (int i = 0;i < V;i++) {
          	  String line = lines1[i];
        	  String[] fields = line.split(",");
        	  int nodeNum = Integer.parseInt(fields[0]);
        	  LinkedList<String> synsetll = new LinkedList<String>(Arrays.asList(fields[1].split(" ")));
        	  synsetMap.put(nodeNum, synsetll);
        	  nounsll.addAll(synsetll);
        	  nounsset.addAll(synsetll);
        	  nodeSynset.add(nodeNum);
        	  for(String word:synsetll) {
        		  if(synsetMap2.containsKey(word)) {
        			  synsetMap2.get(word).add(i);
        		  }
        		  else {
        			  LinkedList<Integer> tmpll = new LinkedList<Integer>();
        			  tmpll.add(i);
        			  synsetMap2.put(word, tmpll);
        		  }
        	  }
        	  
          }
          
          myDigraph = new Digraph(V);
          String[] lines = hypernymsIn.readAllLines();
          for (int i = 0; i < lines.length; i++) {
              String line = lines[i];
              String[] fields = line.split(",");
              int nodeNum = Integer.parseInt(fields[0]);
              nodeHypernymset.add(nodeNum);
              for (int j = 1; j < fields.length; j++) {
            	  myDigraph.addEdge(nodeNum, Integer.parseInt(fields[j]));
              }
          }
          
          mysap = new SAP(myDigraph);
          
          DirectedCycle dc  = new DirectedCycle(myDigraph);
          if(nodeSynset.size() - nodeHypernymset.size() > 1) {
        	  throw new IllegalArgumentException();
          }
          if(dc.hasCycle()) {
        	  throw new IllegalArgumentException();
          }
          
   }

   // returns all WordNet nouns
   public Iterable<String> nouns(){
	   return nounsset;
   }

   // is the word a WordNet noun?
   public boolean isNoun(String word) {
		if(word == null) {
			throw new IllegalArgumentException();
		}
	   return nounsset.contains(word);
   }

   // distance between nounA and nounB (defined below)
   public int distance(String nounA, String nounB) {
		if(nounA == null || nounB == null) {
			throw new IllegalArgumentException();
		}
		if(!isNoun(nounA) ||!isNoun(nounB) ) {
			throw new IllegalArgumentException();
		}
	   LinkedList<Integer> tmpll1=synsetMap2.get(nounA);
	   LinkedList<Integer> tmpll2=synsetMap2.get(nounB);
	   return mysap.length(tmpll1, tmpll2);
   }

   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
   // in a shortest ancestral path (defined below)
   public String sap(String nounA, String nounB) {
		if(nounA == null || nounB == null) {
			throw new IllegalArgumentException();
		}
		if(!isNoun(nounA) ||!isNoun(nounB) ) {
			throw new IllegalArgumentException();
		}
	   LinkedList<Integer> tmpll1=synsetMap2.get(nounA);
	   LinkedList<Integer> tmpll2=synsetMap2.get(nounB);
	   int minNode = mysap.ancestor(tmpll1, tmpll2);
	   LinkedList<String> tmpll = synsetMap.get(minNode);
	   return String.join(" ", tmpll);

   }

   // do unit testing of this class
   public static void main(String[] args) {
//	   WordNet wn = new WordNet("C:\\Users\\Arthur Lance\\Downloads\\wordnet-testing\\wordnet\\synsets3.txt",
//			   "C:\\Users\\Arthur Lance\\Downloads\\wordnet-testing\\wordnet\\hypernyms3InvalidCycle.txt");
//	   WordNet wn = new WordNet("C:\\Users\\Pendragon\\Downloads\\wordnet-testing\\wordnet\\synsets3.txt",
//			   "C:\\Users\\Pendragon\\Downloads\\wordnet-testing\\wordnet\\hypernyms3InvalidCycle.txt");
//	   WordNet wn = new WordNet("C:\\Users\\Pendragon\\Downloads\\wordnet-testing\\wordnet\\synsets100-subgraph.txt",
//			   "C:\\Users\\Pendragon\\Downloads\\wordnet-testing\\wordnet\\hypernyms100-subgraph.txt");
	   WordNet wn = new WordNet("C:\\Users\\Pendragon\\Downloads\\wordnet-testing\\wordnet\\synsets.txt",
			   "C:\\Users\\Pendragon\\Downloads\\wordnet-testing\\wordnet\\hypernyms.txt");
	   
//	   StdOut.println("myDigraph: "+wn.myDigraph);
//	   StdOut.println("synsetMap: "+wn.synsetMap);
//	   StdOut.println("nouns: "+wn.nouns());
	   
//	   StdOut.println("isNoun: "+wn.isNoun("a"));
//	   StdOut.println("isNoun: "+wn.isNoun("d"));
//	   StdOut.println("distance: "+wn.distance("a","b"));
//	   StdOut.println("distance: "+wn.distance("a","c"));
//	   StdOut.println("sap: "+wn.sap("a","b"));
//	   StdOut.println("sap: "+wn.sap("a","c"));
	   
//	   StdOut.println("isNoun: "+wn.isNoun("fff"));
//	   StdOut.println("isNoun: "+wn.isNoun("Hageman_factor"));
//	   StdOut.println("distance: "+wn.distance("Christmas_factor","plasma_protein"));
//	   StdOut.println("distance: "+wn.distance("Christmas_factor","clotting_factor"));
//	   StdOut.println("sap: "+wn.sap("CRP","Hageman_factor"));
//	   StdOut.println("sap: "+wn.sap("CRP","actin"));
	   
	   StdOut.println("isNoun: "+wn.isNoun("horse"));
	   StdOut.println("isNoun: "+wn.isNoun("zebra"));
	   StdOut.println("isNoun: "+wn.synsetMap2.get("horse"));
	   StdOut.println("isNoun: "+wn.synsetMap2.get("zebra"));
	   StdOut.println("distance: "+wn.distance("horse","horse"));
	   StdOut.println("distance: "+wn.distance("horse","zebra"));
	   StdOut.println("sap: "+wn.sap("horse","horse"));
	   StdOut.println("sap: "+wn.sap("horse","zebra"));
   }
}