import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
	private Node root;
	private Queue<Point2D> points;
	private Queue<Node> nodes;
	private int pointNum;
	
	private class Node {
	   private Point2D p;      // the point
	   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
	   private Node lb;        // the left/bottom subtree
	   private Node rt;        // the right/top subtree
	   private Node parent;        // 
	   
       public Node(Point2D p){
           this.p = p;
       }
       
       public void setRect(double xmin, double ymin, double xmax, double ymax) {
    	   this.rect = new RectHV(xmin,ymin,xmax,ymax); 
       }
	}
	
	// construct an empty set of points 
   public KdTree() {
	   this.points = new Queue<Point2D>();
	   this.nodes = new Queue<Node>();
	   pointNum = 0;
   }   
   
   // is the set empty?
   public boolean isEmpty() {
	   return size() == 0;
   }   
   
   // number of points in the set
   public int size() {
	   return pointNum;
   }   

   // add the point to the set (if it is not already in the set)
   public void insert(Point2D p) {
		if(p == null) {
			throw new IllegalArgumentException();
		}
	   
	   if (!this.contains(p)) {
		   root = put(root, p,0,0,1,1);
		   pointNum += 1;
	   }
   }   
   
   private Node put(Node x, Point2D p, double xmin, double ymin, double xmax, double ymax) {
       if (x == null) { 
    	   Node resNode = new Node(p);
    	   resNode.setRect(xmin, ymin, xmax, ymax);
    	   points.enqueue(p);
    	   nodes.enqueue(resNode);
    	   return resNode;
       }
       boolean isHoriz = isHorizontal(x);
       int cmp = compareIn2d(x,p,isHoriz);
	   double xminNew = xmin;
	   double yminNew = ymin;
	   double xmaxNew = xmax;
	   double ymaxNew = ymax;
       if (isHoriz && cmp < 0) { ymaxNew = x.p.y(); }
       else if (isHoriz && cmp >= 0) { yminNew = x.p.y(); }
       else if (!isHoriz && cmp < 0) { xmaxNew = x.p.x(); }
       else if (!isHoriz && cmp >= 0) { xminNew = x.p.x(); }
       if (cmp < 0) {
    	   x.lb = put(x.lb, p,xminNew, yminNew, xmaxNew, ymaxNew);
    	   x.lb.parent = x;
       }
       else {
    	   x.rt = put(x.rt, p,xminNew, yminNew, xmaxNew, ymaxNew);
    	   x.rt.parent = x;
       }
       return x;
   }
   
   private boolean isHorizontal(Node x) {
	   int d = depth(x);
	   boolean isHoriz = false;
	   if (d % 2 == 0) { isHoriz = true; }
	   else { isHoriz = false; }
	   return isHoriz;
   }

   private int compareIn2d(Node x,Point2D p, boolean isHoriz) {
	   double c1 = 0;
	   double c2 = 0;
	   if (isHoriz) {
		   c1 = p.y();
		   c2 = x.p.y();
	   }
	   else {
		   c1 = p.x();
		   c2 = x.p.x();
	   }

       if (c1 == c2) { return 0; }
       else if (c1 < c2) { return -1; }
       else { return 1; }
   }   
   
   // does the set contain point p?
   public boolean contains(Point2D p) {
		if(p == null) {
			throw new IllegalArgumentException();
		}
	   
	   return get(p) != null;
   }   

   private Node get(Point2D p) {
       return get(root, p);
   }

   private Node get(Node x, Point2D p) {
       if (p == null) throw new IllegalArgumentException("calls get() with a null key");
       if (x == null) return null;
       boolean isHoriz = isHorizontal(x);
       int cmp = compareIn2d(x,p,isHoriz);
       int cmp2 = p.compareTo(x.p);
       if      (cmp < 0) return get(x.lb, p);
       else if (cmp > 0) return get(x.rt, p);
       else if (cmp == 0 && cmp2 != 0) return get(x.rt, p);
       else              return x;
   }
   
   private int depth(Node x) {
	   if (x == null) return 0;
	   return 1 + depth(x.parent);
   }
   
   // draw all points to standard draw
   public void draw() {
	   for (Node x : nodes) {
    	   StdDraw.setPenColor(StdDraw.BLACK);
           StdDraw.setPenRadius(0.01);
    	   x.p.draw();
           StdDraw.setPenRadius();
           if (isHorizontal(x)) {
        	   StdDraw.setPenColor(StdDraw.BLUE);
        	   Point2D p1 = new Point2D(x.rect.xmin(), x.p.y());
        	   Point2D p2 = new Point2D(x.rect.xmax(), x.p.y());
        	   p1.drawTo(p2);
           }
           else {
        	   StdDraw.setPenColor(StdDraw.RED);
        	   Point2D p1 = new Point2D(x.p.x(), x.rect.ymin());
        	   Point2D p2 = new Point2D(x.p.x(), x.rect.ymax());
        	   p1.drawTo(p2);
           }
       }
   }   
   
   // all points that are inside the rectangle (or on the boundary)
   public Iterable<Point2D> range(RectHV rect) {
		if(rect == null) {
			throw new IllegalArgumentException();
		}
	   
	   Queue<Point2D> points = new  Queue<Point2D>();
	   check(root,rect,points);
	   return points;
   }   
   
   private void check(Node x,RectHV rect,Queue<Point2D> points) {
	   if (x == null) { return; }
	   
       if (x.rect.intersects(rect)) {
    	   if (rect.contains(x.p)) {
    		   points.enqueue(x.p);
    	   }
    	   
    	   check(x.lb,rect,points);
    	   check(x.rt,rect,points);
       }
   }
   
   // a nearest neighbor in the set to point p; null if the set is empty
   public Point2D nearest(Point2D p) {
		if(p == null) {
			throw new IllegalArgumentException();
		}
	   
	   Point2D nrtPoint = null;
	   nrtPoint = findnearest(root,p,nrtPoint);
	   return nrtPoint;
   }        
   
   private Point2D findnearest(Node x,Point2D p,Point2D nrtPoint) { 
	   if (x == null) { return nrtPoint; }
	   
	   if (nrtPoint == null) { nrtPoint = x.p; }

	   if (nrtPoint.distanceTo(p) <= x.rect.distanceTo(p)) { return nrtPoint; }
	   else {
		   if (x.p.distanceTo(p) < nrtPoint.distanceTo(p)) { nrtPoint = x.p; }
		   
	       boolean isHoriz = isHorizontal(x);
	       int cmp = compareIn2d(x,p,isHoriz);
		   if (cmp < 0) {			   
			   nrtPoint = findnearest(x.lb,p,nrtPoint);
			   nrtPoint = findnearest(x.rt,p,nrtPoint);
			   return nrtPoint;
		   }
		   else {
			   nrtPoint = findnearest(x.rt,p,nrtPoint);
			   nrtPoint = findnearest(x.lb,p,nrtPoint);
			   return nrtPoint;
		   }
	   }

   }

   // unit testing of the methods (optional)
   public static void main(String[] args) {
       // initialize the two data structures with point from file
//       String filename = "C:\\Users\\Pendragon\\Downloads\\kdtree-testing\\kdtree\\circle10.txt";
//       String filename = "C:\\Users\\Pendragon\\Downloads\\kdtree-testing\\kdtree\\circle100.txt";
       String filename = "C:\\Users\\Arthur Lance\\Downloads\\kdtree-testing\\kdtree\\input10.txt";
       In in = new In(filename);
       KdTree kdtree = new KdTree();
       while (!in.isEmpty()) {
           double x = in.readDouble();
           double y = in.readDouble();
           Point2D p = new Point2D(x, y);
           kdtree.insert(p);
       }
       
       kdtree.draw();
//       StdOut.println(kdtree.levelOrder());
//       StdOut.println(kdtree.points);
//       StdOut.println(kdtree.root.p);
//       StdOut.println(kdtree.root.lb.p);
//       StdOut.println(kdtree.isHorizontal(kdtree.root.lb));
//       StdOut.println(kdtree.depth(kdtree.root.lb));
       
       StdOut.println(kdtree.size());
       
//       StdOut.println(kdtree.range(new RectHV(0,0,1,1)));
//       StdOut.println(kdtree.range(new RectHV(0,0,0.3,0.3)));
//       StdOut.println(kdtree.range(new RectHV(0.7,0.7,1,1)));
//       StdOut.println(kdtree.range(new RectHV(0.3,0.7,1,1)));
//       StdOut.println(kdtree.range(new RectHV(0.2,0.7,1,1)));
//       
//       StdOut.println(kdtree.nearest(new Point2D(0.81, 0.30)));
       StdOut.println(kdtree.nearest(new Point2D(1, 1)));
   }                  
}
