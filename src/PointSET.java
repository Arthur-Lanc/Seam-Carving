import java.util.LinkedList;
import java.util.Queue;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class PointSET {
	private SET<Point2D> myTree;
	
	// construct an empty set of points
   public PointSET() {
	   myTree = new SET<Point2D>();
   }   
   
   // is the set empty?
   public boolean isEmpty() {
	   return myTree.isEmpty();
   }   
   
   // number of points in the set
   public int size() {
	   return myTree.size();
   }   
   
   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
		if(p == null) {
			throw new IllegalArgumentException();
		}
   	
	   if (!myTree.contains(p)) {
		   myTree.add(p);
	   }
   }              
   
   // does the set contain point p?
   public boolean contains(Point2D p) {
		if(p == null) {
			throw new IllegalArgumentException();
		}
		
	   return myTree.contains(p);
   }   
   
   // draw all points to standard draw
   public void draw() {
//       StdDraw.enableDoubleBuffering();
//       StdDraw.setXscale(0, 1);
//       StdDraw.setYscale(0, 1);
       StdDraw.setPenRadius(0.01);
       for (Point2D p : myTree) {
           p.draw();
       }
//       StdDraw.show();
   }   
   
   // all points that are inside the rectangle (or on the boundary)
   public Iterable<Point2D> range(RectHV rect){
		if(rect == null) {
			throw new IllegalArgumentException();
		}
	   
	   Queue<Point2D> q = new LinkedList<Point2D>();
       for (Point2D p : myTree) {
           if (rect.contains(p)) {
        	   q.add(p);
           }
       }
       return q;
   }   
	   
   // a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
		if(p == null) {
			throw new IllegalArgumentException();
		}
		
	   if (myTree.isEmpty()) {
		   return null;
	   }
	   else {
		   double minDist = Double.POSITIVE_INFINITY;
		   Point2D nearestPoint = null;
		   for (Point2D p1 : myTree) {
			   double tempDist = p.distanceTo(p1);
			   if (tempDist < minDist) {
				   minDist = tempDist;
				   nearestPoint = p1;
			   }
		   }
		   return nearestPoint;
	   }
   }              

   // unit testing of the methods (optional)
   public static void main(String[] args) {
       // initialize the two data structures with point from file
//       String filename = "C:\\Users\\Pendragon\\Downloads\\kdtree-testing\\kdtree\\circle10.txt";
       String filename = "C:\\Users\\Arthur Lance\\Downloads\\kdtree-testing\\kdtree\\input10.txt";
       In in = new In(filename);
       PointSET brute = new PointSET();
       while (!in.isEmpty()) {
           double x = in.readDouble();
           double y = in.readDouble();
           Point2D p = new Point2D(x, y);
           brute.insert(p);
       }
       
       brute.draw();
//       StdOut.println(brute.size());
       StdOut.println(brute.nearest(new Point2D(0,0)));
       StdOut.println(brute.nearest(new Point2D(1,0)));
       StdOut.println(brute.range(new RectHV(0,0,1,1)));
       StdOut.println(brute.range(new RectHV(0,0,0.3,0.3)));
       StdOut.println(brute.range(new RectHV(0.7,0.7,1,1)));
       StdOut.println(brute.range(new RectHV(0.3,0.7,1,1)));
   }                   
}
