import java.awt.Color;


import edu.princeton.cs.algs4.Picture;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.StdOut;

public class SeamCarver {
	private Picture myPicture;
	private boolean isHorizonTrans;
	private boolean isFindHorizontalSeamCall;
	private boolean isRemoveHorizontalSeamCall;
	
	private boolean isFindVerticalSeamCall;
	
    // create a seam carver object based on the given picture
   public SeamCarver(Picture picture) {
		if(picture == null) {
			throw new IllegalArgumentException();
		}
	   myPicture = new Picture(picture);
	   isHorizonTrans = false;
	   isFindHorizontalSeamCall = false;
	   isRemoveHorizontalSeamCall = false;
	   isFindVerticalSeamCall = false;
   }   
   
   // current picture
   public Picture picture() {
	   if (isHorizonTrans == true) {
		   trasposePicture();
	   }
	   return new Picture(myPicture);
   }                          
   
   // width of current picture
   public     int width() {
	   if (isHorizonTrans == true) {
		   return myPicture.height();
	   }
	   return myPicture.width();
   }   
   
   // height of current picture
   public     int height() {
	   if (isHorizonTrans == true) {
		   return myPicture.width();
	   }
	   return myPicture.height();
   }                          
   
   // energy of pixel at column x and row y
   public  double energy(int x, int y) {
	   if (isHorizonTrans == true && isFindVerticalSeamCall == false) {
		   trasposePicture();
	   }
	   isFindVerticalSeamCall = false;
	   
//	   StdOut.println("x, y: "+x+", "+y);
//	   StdOut.println("myPicture.width(): "+myPicture.width());
//	   StdOut.println("myPicture.height(): "+myPicture.height());
		if(x < 0 || x > myPicture.width() - 1 || y < 0 || y > myPicture.height() - 1) {
			throw new IllegalArgumentException();
		}
       if (x == 0 || x == myPicture.width()-1 || y == 0 || y == myPicture.height()-1 ) {
    	   return 1000;
       }
       else {
    	   Color color1 = myPicture.get(x+1, y);
    	   Color color2 = myPicture.get(x-1, y);
    	   double deltaXSquare = Math.pow(color1.getRed()-color2.getRed(),2)+Math.pow(color1.getGreen()-color2.getGreen(),2)+Math.pow(color1.getBlue()-color2.getBlue(),2);
    	   color1 = myPicture.get(x, y+1);
    	   color2 = myPicture.get(x, y-1);
    	   double deltaYSquare = Math.pow(color1.getRed()-color2.getRed(),2)+Math.pow(color1.getGreen()-color2.getGreen(),2)+Math.pow(color1.getBlue()-color2.getBlue(),2);
    	   return Math.sqrt(deltaXSquare+deltaYSquare);
           
       }
   }              
   
   // sequence of indices for horizontal seam
   public   int[] findHorizontalSeam() {
	   int[] horiArr = new int[myPicture.width()];
	   if (isHorizonTrans == false) {
		   trasposePicture();
	   }
	   isFindHorizontalSeamCall = true;
	   horiArr = findVerticalSeam();
	   
	   return horiArr;
   }
   
   private void trasposePicture() {
	    Picture newPicture = new Picture(myPicture.height(),myPicture.width());
	    
	    for(int j=0; j<myPicture.height(); j++) {
	    	for(int i=0; i<myPicture.width(); i++) {
	    		newPicture.set(j,i,myPicture.get(i,j));
	        }
	    }
	    
	    myPicture = newPicture;
	    isHorizonTrans = !isHorizonTrans;
	}
   
   // sequence of indices for vertical seam
   public   int[] findVerticalSeam() {
	   if (isHorizonTrans == true && isFindHorizontalSeamCall == false) {
		   trasposePicture();
	   }
	   isFindHorizontalSeamCall = false;
	   
	    double[][] energy = new double[myPicture.width()][myPicture.height()];
	    int[][] edgeTo = new int[myPicture.width()][myPicture.height()];
	    double[][] distTo = new double[myPicture.width()][myPicture.height()];
	    for(int j=0; j<myPicture.height(); j++) {
	    	for(int i=0; i<myPicture.width(); i++) {
	    		isFindVerticalSeamCall = true;
	        	energy[i][j] = energy(i, j);
	        	if (j == 0) {
	        		distTo[i][j] = 0.0;
	        	}
	        	else {
	        		distTo[i][j] = Double.POSITIVE_INFINITY;
	        	}
	        }
	    }
	    
	    int[] ranges  =  { -1,0,1 };
	    for(int j=0; j<myPicture.height()-1; j++) {
	    	for(int i=0; i<myPicture.width(); i++) {
	        	 for(int k : ranges) {
	        		 if (i+k<0 || i+k>=myPicture.width()) {
	        			 continue;
	        		 }
	        		 else {
	        			   if (distTo[i+k][j+1] > distTo[i][j] + energy[i][j]){
	        				   distTo[i+k][j+1] = distTo[i][j] + energy[i][j];
	        				   edgeTo[i+k][j+1] = i;
//	        				   StdOut.println("edgeTo: "+edgeTo[i+k][j+1]);
	        			   }
//	        			   StdOut.println("edgeTo: "+edgeTo[i+k][j+1]);
	        		 }
	        	 }
	        }
	    } 
	    
	    double minDist = Double.POSITIVE_INFINITY;
	    int minCol = 0;
	    for (int i=0; i<myPicture.width(); i++) {
	    	if(distTo[i][myPicture.height()-1] < minDist) {
	    		minDist = distTo[i][myPicture.height()-1];
	    		minCol = i;
	    	}
	    }
	    
	    int[] vertArr = new int[myPicture.height()];
	    for (int j=myPicture.height()-1;j>=0;j--) {
	    	vertArr[j] = minCol;
	    	minCol = edgeTo[minCol][j];
	    }
	    
	   return vertArr;
   }                
   
   // remove horizontal seam from current picture
   public    void removeHorizontalSeam(int[] seam) {		
	   if (isHorizonTrans == false) {
		   trasposePicture();
	   }
	   isRemoveHorizontalSeamCall = true;
	   removeVerticalSeam(seam);
   }  
   
   // remove vertical seam from current picture
   public    void removeVerticalSeam(int[] seam) {
	   if (isHorizonTrans == true && isRemoveHorizontalSeamCall == false) {
		   trasposePicture();
	   }
	   isRemoveHorizontalSeamCall = false;
	   
	   
		if(seam == null) {
			throw new IllegalArgumentException();
		}
		if(seam.length != myPicture.height()) {
			throw new IllegalArgumentException();
		}
		for(int i=0;i<seam.length;i++) {
			if(seam[i]<0 || seam[i]>myPicture.width()) {
				throw new IllegalArgumentException();
			}
			if(i > 0) {
				if(Math.abs(seam[i]-seam[i-1])>1) {
					throw new IllegalArgumentException();
				}
			}
		}
		if(myPicture.width()<=1) {
			throw new IllegalArgumentException();
		}
	   

	    Picture newPicture = new Picture(myPicture.width()-1,myPicture.height());
	    for(int j=0; j<myPicture.height(); j++) {
	    	int removeCol = seam[j];
	    	for(int i=0; i<myPicture.width(); i++) {
	    		if (i < removeCol) {
	    			newPicture.set(i,j,myPicture.get(i,j));
	    		}
	    		else if (i > removeCol) {
	    			newPicture.set(i-1,j,myPicture.get(i,j));
	    		}
	        }
	    }
	    myPicture = newPicture;
   }    
}
