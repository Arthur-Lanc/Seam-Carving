import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private WordNet myWordnet;
	
	// constructor takes a WordNet object
   public Outcast(WordNet wordnet) {
	   myWordnet = wordnet;
   }         
   
   // given an array of WordNet nouns, return an outcast
   public String outcast(String[] nouns) {
	   int count = 0;
	   int maxdist = 0;
	   int tmpdist = 0;
	   String maxoc = null;
	   String tmpoc = null;
	   for(String tmpstr1:nouns) {
		   if(count == 0) {
			   for(String tmpstr2:nouns) {
				   int dist1 = myWordnet.distance(tmpstr1, tmpstr2);
				   maxdist += dist1;
				   maxoc = tmpstr1;
//				   StdOut.println("tmpstr1,tmpstr2: "+tmpstr1+","+tmpstr2);
//				   StdOut.println("dist1: "+dist1);
//				   StdOut.println("maxdist: "+maxdist);
			   }
			   count += 1;
		   }
		   else {
			   tmpdist = 0;
			   for(String tmpstr2:nouns) {
				   tmpdist += myWordnet.distance(tmpstr1, tmpstr2);
				   tmpoc = tmpstr1;
//				   StdOut.println("tmpstr1,tmpstr2: "+tmpstr1+","+tmpstr2);
//				   StdOut.println("maxdist: "+maxdist);
			   }
			   count += 1;
			   if(tmpdist > maxdist) {
				   maxdist = tmpdist;
				   maxoc = tmpoc;
			   }
		   }
	   }
//	   StdOut.println("maxdist: "+maxdist);
	   return maxoc;
   }
   
   // see test client below
   public static void main(String[] args) {
	   String[] inputstr = {"synsets.txt", "hypernyms.txt", "outcast5.txt", "outcast8.txt", "outcast11.txt"};
	   String addrstr = "C:\\Users\\Pendragon\\Downloads\\wordnet-testing\\wordnet\\";
	    WordNet wordnet = new WordNet(addrstr+inputstr[0], addrstr+inputstr[1]);
	    Outcast outcast = new Outcast(wordnet);
	    for (int t = 2; t < inputstr.length; t++) {
	        In in = new In(addrstr+inputstr[t]);
	        String[] nouns = in.readAllStrings();
	        StdOut.println(inputstr[t] + ": " + outcast.outcast(nouns));
	    }
   }  
}
