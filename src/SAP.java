import java.util.Arrays;
//import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
	private Digraph myDigraph;
	private int V;

   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G) {
		if(G == null) {
			throw new IllegalArgumentException();
		}
	   myDigraph = new Digraph(G);
	   V = myDigraph.V();
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
		if(v < 0 || v >=V) {
			throw new IllegalArgumentException();
		}
		if(w < 0 || w >=V) {
			throw new IllegalArgumentException();
		}
	   BreadthFirstDirectedPaths bfdp1=new BreadthFirstDirectedPaths(myDigraph,v);
	   BreadthFirstDirectedPaths bfdp2=new BreadthFirstDirectedPaths(myDigraph,w);
	   int mindist = 0;
//	   int minNode = 0;
	   int count = 0;
	   int tmpdist = 0;
//	   int tmpNode = 0;
	   for(int i = 0;i < V;i++) {
		   if(bfdp1.hasPathTo(i) && bfdp2.hasPathTo(i)) {
			   if (count == 0) {
				   mindist = bfdp1.distTo(i) + bfdp2.distTo(i);
//				   minNode = i;
				   count += 1;
			   }
			   else {
				   tmpdist = bfdp1.distTo(i) + bfdp2.distTo(i);
//				   tmpNode = i;
				   if (tmpdist < mindist) {
					   mindist = tmpdist;
//					   minNode = tmpNode;
				   }
				   count += 1;
			   }
		   }
	   }
	   if(count > 0) {
		   return mindist;
	   }
	   else {
		   return -1;
	   }
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
		if(v < 0 || v >=V) {
			throw new IllegalArgumentException();
		}
		if(w < 0 || w >=V) {
			throw new IllegalArgumentException();
		}
	   BreadthFirstDirectedPaths bfdp1=new BreadthFirstDirectedPaths(myDigraph,v);
	   BreadthFirstDirectedPaths bfdp2=new BreadthFirstDirectedPaths(myDigraph,w);
	   int mindist = 0;
	   int minNode = 0;
	   int count = 0;
	   int tmpdist = 0;
	   int tmpNode = 0;
	   for(int i = 0;i < V;i++) {
		   if(bfdp1.hasPathTo(i) && bfdp2.hasPathTo(i)) {
			   if (count == 0) {
				   mindist = bfdp1.distTo(i) + bfdp2.distTo(i);
				   minNode = i;
				   count += 1;
			   }
			   else {
				   tmpdist = bfdp1.distTo(i) + bfdp2.distTo(i);
				   tmpNode = i;
				   if (tmpdist < mindist) {
					   mindist = tmpdist;
					   minNode = tmpNode;
				   }
				   count += 1;
			   }
		   }
	   }
	   if(count > 0) {
		   return minNode;
	   }
	   else {
		   return -1;
	   }
   }

   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
		if(v == null || w == null) {
			throw new IllegalArgumentException();
		}
		
//		int counter = 0;
//		for (Object i : v) {
//		    counter++;
//		}
//		int counter2 = 0;
//		for (Object i : w) {
//		    counter2++;
//		}
//		if(counter == 0 || counter2 == 0) {
//			throw new IllegalArgumentException();
//		}
		
		for(Integer e:v) {
			if(e < 0 || e >=V || e == null) {
				throw new IllegalArgumentException();
			}
		}
		for(Integer e:w) {
			if(e < 0 || e >=V || e == null) {
				throw new IllegalArgumentException();
			}
		}
	   BreadthFirstDirectedPaths bfdp1=new BreadthFirstDirectedPaths(myDigraph,v);
	   BreadthFirstDirectedPaths bfdp2=new BreadthFirstDirectedPaths(myDigraph,w);
	   int mindist = 0;
//	   int minNode = 0;
	   int count = 0;
	   int tmpdist = 0;
//	   int tmpNode = 0;
	   for(int i = 0;i < V;i++) {
		   if(bfdp1.hasPathTo(i) && bfdp2.hasPathTo(i)) {
			   if (count == 0) {
				   mindist = bfdp1.distTo(i) + bfdp2.distTo(i);
//				   minNode = i;
				   count += 1;
			   }
			   else {
				   tmpdist = bfdp1.distTo(i) + bfdp2.distTo(i);
//				   tmpNode = i;
				   if (tmpdist < mindist) {
					   mindist = tmpdist;
//					   minNode = tmpNode;
				   }
				   count += 1;
			   }
		   }
	   }
	   if(count > 0) {
		   return mindist;
	   }
	   else {
		   return -1;
	   }
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		if(v == null || w == null) {
			throw new IllegalArgumentException();
		}

//		int counter = 0;
//		for (Object i : v) {
//		    counter++;
//		}
//		int counter2 = 0;
//		for (Object i : w) {
//		    counter2++;
//		}
//		if(counter == 0 || counter2 == 0) {
//			throw new IllegalArgumentException();
//		}

		for(Integer e:v) {
			if(e < 0 || e >=V || e == null) {
				throw new IllegalArgumentException();
			}
		}
		for(Integer e:w) {
			if(e < 0 || e >=V || e == null) {
				throw new IllegalArgumentException();
			}
		}
	   BreadthFirstDirectedPaths bfdp1=new BreadthFirstDirectedPaths(myDigraph,v);
	   BreadthFirstDirectedPaths bfdp2=new BreadthFirstDirectedPaths(myDigraph,w);
	   int mindist = 0;
	   int minNode = 0;
	   int count = 0;
	   int tmpdist = 0;
	   int tmpNode = 0;
	   for(int i = 0;i < V;i++) {
		   if(bfdp1.hasPathTo(i) && bfdp2.hasPathTo(i)) {
			   if (count == 0) {
				   mindist = bfdp1.distTo(i) + bfdp2.distTo(i);
				   minNode = i;
				   count += 1;
			   }
			   else {
				   tmpdist = bfdp1.distTo(i) + bfdp2.distTo(i);
				   tmpNode = i;
				   if (tmpdist < mindist) {
					   mindist = tmpdist;
					   minNode = tmpNode;
				   }
				   count += 1;
			   }
		   }
	   }
	   if(count > 0) {
		   return minNode;
	   }
	   else {
		   return -1;
	   }
   }

   // do unit testing of this class
   public static void main(String[] args) {
//	    In in = new In("C:\\Users\\Pendragon\\Downloads\\wordnet-testing\\wordnet\\digraph1.txt");
	    In in = new In("C:\\Users\\Arthur Lance\\Downloads\\wordnet-testing\\wordnet\\digraph25.txt");
	    Digraph G = new Digraph(in);
	    SAP sap = new SAP(G);
//	    while (!StdIn.isEmpty()) {
//	        int v = StdIn.readInt();
//	        int w = StdIn.readInt();
//	        int length   = sap.length(v, w);
//	        int ancestor = sap.ancestor(v, w);
//	        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//	    }
	    Integer arr[] = {13,23,24};
	    Set<Integer> v = new HashSet<>(Arrays.asList(arr));
	    Integer arr2[] = {6,16,17};
	    Set<Integer> w = new HashSet<>(Arrays.asList(arr2));
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
   }
}
