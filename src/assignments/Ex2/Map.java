package assignments.Ex2;
import java.io.Serializable;
import java.util.*;

/**
 * This class represents a 2D map (int[w][h]) as a "screen" or a raster matrix or maze over integers.
 * This is the main class needed to be implemented.
 *
 * @author boaz.benmoshe
 *
 */
public class Map implements Map2D, Serializable{
    private int [][] map;

    // edit this class below
	/**
	 * Constructs a w*h 2D raster map with an init value v.
	 * @param w
	 * @param h
	 * @param v
	 */
	public Map(int w, int h, int v) {
        init(w, h, v);}
	/**
	 * Constructs a square map (size*size).
	 * @param size
     *
     *
	 */
	public Map(int size) {this(size,size, 0);}

	/**
	 * Constructs a map from a given 2D array.
	 * @param data
	 */
	public Map(int[][] data) {
		init(data);
	}

    /**
     * @param w,h,v
     *
     * initizes map with dimentions w*h  fills all the ceel xith the value v
     * uses nested loop to iterate through every pixcel
     */
	@Override
	public void init(int w, int h, int v) {
        map = new int [w][h]; //creat 2D array
        for (int x=0;x<w;x++) {  // loop through columns
            for (int y = 0; y < h; y++) {  // loop through rows
                map[x][y] = v; // set  each pixcel to value
            }
        }
	}
    /**
     * copied data from 2D array into the map
     * we use deep copy cuz changes to orginal arry won't affect our map
     */
	@Override
	public void init(int[][] arr) {
        int w = arr.length; //get width from array
        int h = arr[0].length; // get height from the first row
        map = new int [w][h]; // creat new map array
        for (int x=0;x<w;x++) { // loop columns
            for (int y = 0; y < h; y++) { // loop row
                map[x][y] = arr[x][y]; // copy eachvalue
            }
        }

	}
    /**
     * a copy ot the internal map
     * prevent extternal code from modfing internal data directly
     */
	@Override
	public int[][] getMap() {
		int w=getWidth(); // get crrent width
        int h=getHeight(); // get current height
        int[][] ans = new int[w][h]; // creat new arry
        for (int x=0;x<w;x++) { // loop col
            for (int y = 0; y < h; y++) { // loop row
                ans[x][y] = map[x][y]; // copy each value
            }
        }

		return ans; // return copy
	}
	@Override
	public int getWidth() {
        return map.length;
    } // arry first dimeantion
	@Override
	public int getHeight() {
        return map[0].length;
    } // array second dimantion
	@Override
	public int getPixel(int x, int y) {
        return  map[x][y] ;
    } // access array element
	@Override
	public int getPixel(Pixel2D p) {
        return getPixel(p.getX(), p.getY());
	}
	@Override
	public void setPixel(int x, int y, int v) {
     map[x][y] = v;
    } //update arry element
	@Override
	public void setPixel(Pixel2D p, int v) {
     setPixel(p.getX(), p.getY(), v);
	}
    /**
     * check x&y are both non negative and less than demension
     * return true if pixcel p is withen map boundari
     */
    @Override
    public boolean isInside(Pixel2D p) {
        int x = p.getX(); // get x cordenet
        int y = p.getY(); // get y cordenet
        return x >= 0 &&  y >= 0   && x<getWidth()&&  y < getHeight(); // cheak non negative and chick withen bound
    }
    /**
     * check if bothe maps have identical dimention
     */

    @Override
    public boolean sameDimensions(Map2D p) {
        return this.getWidth() == p.getWidth() && //same width
                this.getHeight() == p.getHeight() ; // same height
    }
    /**
     * adding corresponding pixels from map p to this map
     * throe exception if dimention don't match
     */

    @Override
    public void addMap2D(Map2D p) {
        if (!sameDimensions(p)) {
    throw new IllegalArgumentException("same dimensions.");// check dimantion match
}
        int w = getWidth(); // get width
        int h = getHeight(); // get height
        for (int x=0; x<w;x++){ //loop col
            for (int y=0; y<h;y++){ // loop row
                int sum= this.getPixel(x,y)+p.getPixel(x,y);// add value
                this.setPixel(x,y,sum); // update this map
            }
        }
    }
    /**
     *multiple every pixel value by scalar
     * result is cast to int( decimals are truncated)
     */

    @Override
    public void mul(double scalar) {
        int w = getWidth(); // get wedith
        int h = getHeight(); // get height
        for (int x=0;x<w;x++){ // loop col
            for (int y=0;y<h;y++){ // loop row
                int newValue=(int)(scalar*this.getPixel(x,y)); //multiply by cast
                this.setPixel(x,y,newValue);// update value
            }
        }

    }
    /**
     *resize the map by scaling factor sx and sy
     * creat new map and maps each pixel to corresponding old pixel
     * ex:- if sx = 2.0 a 10*10 map becom 20*10
     */

    @Override
    public void rescale(double sx, double sy) {
        int newWidth=(int)(getWidth()*sx); // calculate new width
        int newHeight=(int)(getHeight()*sy); // calculate new height
        int[][]newMap=new int [newWidth][newHeight];//creat new arry
        for (int x=0;x<newWidth;x++){ // loop new col
            for (int y=0;y<newHeight;y++){ // loop new row
                int oldX=(int)(x/sx); // map to old x
                int oldY=(int)(y/sy); // map to old y
                newMap[x][y]=map[oldX][oldY]; // copy value
            }
        }
        this.map=newMap; //replace map

    }
    /**
     * fill all pixel with radius rad from center
     * algoritum : checks every pixel distance from center
     * O(width*height)
     */
    @Override
    public void drawCircle(Pixel2D center, double rad, int color) {
        int w = getWidth();// get width
        int h = getHeight(); // get heigh
        for (int x=0;x<w;x++){ // loop col
            for (int y=0;y<h;y++){ // loop row
                Index2D current=new Index2D(x,y); // creat pixel
                double distane=center.distance2D(current); // calculate distunce

                if(distane<=rad){ // if  inside circle
                    setPixel(x,y,color);// colour it
                }
            }
        }

    }
    /**
     *algorthem:-bresenham line algorithm-efficient pixel perfect line drawing
     * incrementally steap from p1 deciding wheter to move horizontally or virtically based on accumalted error
     *
     */

    @Override
    public void drawLine(Pixel2D p1, Pixel2D p2, int color) {
        int x0=p1.getX(); // starting x
        int y0=p1.getY(); // starting y
        int x1=p2.getX(); // end x
        int y1=p2.getY(); // end y

        int dx=Math.abs(x1-x0); // herizonal distance
        int dy=Math.abs(y1-y0); // vertical distance

        int sx=x0<x1? 1:-1; // step direction for x
        int sy=y0<y1? 1:-1; // step direction for y

        int err=dx-dy; // error accumulator

        while(true){  // infinty loop
            if(x0>=0 && x0 < getWidth() && y0 >=0 && y0 < getHeight()){
                setPixel(x0,y0,color); // draw pixel  if in bound

            }
            if (x0==x1 && y0==y1) break; // reached the end point
            int e2=2*err; // double the error
            if( e2 > -dy){ // step in x direction
                err-=dy;
                x0+=sx;
            }
            if( e2 < dx) { // step in y direction
                err += dx;
                y0 += sy;
            }
        }
    }
    /**
     *fill a rectangle with cornor at p1 & p2
     * dosen't matter which is top-left or bottom-rhght
     */
    @Override
    public void drawRect(Pixel2D p1, Pixel2D p2, int color) {
        int minX= Math.min(p1.getX(), p2.getX()); // leaft edge
        int maxX= Math.max(p1.getX(), p2.getX()); // right edge
        int minY= Math.min(p1.getY(), p2.getY()); // bootom
        int maxY= Math.max(p1.getY(), p2.getY()); // top

        for (int x=minX;x<=maxX;x++){ // loop from left to right
            for (int y=minY;y<=maxY;y++){ // loop from bootom to top
                if(x>=0 && x<getWidth() && y>=0 && y<getHeight()){
                    setPixel(x,y,color); // fill pixcelif in bound
                }
            }
        }

    }
    /**
     *cheak if maps have same dimantion and all pixel  value match
     */
    @Override
    public boolean equals(Object ob) {
        if (ob == null) return false; // null check
        if ( this==ob) return true; // same reference
        if (!(ob instanceof Map2D)) return false; // type check

        Map2D other = (Map2D)ob; // cast to map2D

        if(!sameDimensions(other)) return false; // cheack dimension
        int w = getWidth();
        int h = getHeight();
        for (int x=0;x<w;x++){ // loop all pixel
            for (int y=0;y<h;y++){
                if(this.getPixel(x,y)!=other.getPixel(x,y)) {
                    return false; // found difference
                }
            }
        }

        return true; // all pixel match
    }


	@Override
	/**
	 * Fills this map with the new color (new_v) starting from p.
	 * https://en.wikipedia.org/wiki/Flood_fill
     * fill connected region with new color
     *      *  we use BFS method start from intial pixcel, add in to queue, while queue not empty
     *      *  take pixel from queue , if it has old color change it to new color ,add all 4 neightbors(top,bottom,left ,right) to queue
     *      *  countinue untile queue is empty( all connected pixel processed)
     *      *  if true map wraps arround edge
	 */
	public int fill(Pixel2D xy, int new_v,  boolean cyclic) {
        // step1 :check if starting  point is valid
		if (!isInside(xy)&&!cyclic) {
            return 0; // outside bounds and not cyclic
        }
        //step 2: wrapp arround if cyclic mode
        Pixel2D actualStart= cyclic ? wrapAround(xy) :xy;

        if(!isInside(actualStart)){
            return 0; //still invalid
        }
        //step 3 : get color we're replace
        int old_v= getPixel(actualStart);
        if (old_v == new_v) {
            return 0;}  // already the target color
        int counter=0; // count filled pixel
        //step 4: initialize BFS
        Queue<Pixel2D> queue = new LinkedList<>(); //BFS queue
        Set<String>  viseted = new HashSet<>(); //track visited

        queue.add(actualStart); //add starting pixel
        viseted.add(pixelToString(actualStart )); // mark as visited

        while (!queue.isEmpty()) {
            Pixel2D current = queue.poll(); // get next pixel
            if (!isInside(current)) {
                continue; //skip if out of bond
            }
            if (getPixel(current) != old_v) {
                continue; // wrong color skip
            }
            setPixel(current, new_v); // fill with new color
            counter++; // increase counter
            // add neightbornto queue
            Pixel2D[] neighbor = getNeightborns(current, cyclic);

            for (Pixel2D neighbors : neighbor) {
                Pixel2D actualNightbor = cyclic ? wrapAround(neighbors) : neighbors;

                if (!cyclic && !isInside(actualNightbor)) {
                    continue; //skip if out of bound
                }
                if (!isInside(actualNightbor)) {
                    continue;
                }
                String key = pixelToString(actualNightbor);
                if (!viseted.contains(key)) { // not visited yet
                    viseted.add(key); // mark viseted
                    queue.add(actualNightbor);// add to queue
                }
            }

        }
		return counter; // reutern num of filled pixels
	}

	@Override
	/**(
	 * BFS like shortest the computation based on iterative raster implementation of BFS, see:
	 * https://en.wikipedia.org/wiki/Breadth-first_search
     *
     * find the shortest path between two point ,avoiding obstacles
     * explor all pixel at distance 1 and distance 2 e.t.c
     * return array of pixel forming path, or null if no path exist
	 */
	public Pixel2D[] shortestPath(Pixel2D p1, Pixel2D p2, int obsColor, boolean cyclic) {
        //step 1: validat input
            if(!cyclic && (!isInside(p1) || !isInside(p2))) {
                return null; // point outside
            }
// srep 2: handel cyclic wrpping
            Pixel2D start = cyclic ? wrapAround(p1) : p1;
            Pixel2D end = cyclic ? wrapAround(p2) : p2;

            if (!isInside(start) || !isInside(end)) {
                return null;
            } // invalid after wrpping
//step 3: check if start/end are obstacles
            if (getPixel(start) == obsColor || getPixel(end) == obsColor) {
                return null; //can't start/end on obstacle
            }
// step 4: initialize BFS
            Queue<Pixel2D> queue = new LinkedList<>();
            java.util.Map<String, Pixel2D> parent = new HashMap<>(); //track path
            Set<String> visited = new HashSet<>(); // track visited

            queue.add(start);
            visited.add(pixelToString(start));
            parent.put(pixelToString(start),null); // start has no parent
// step 5: BFS search
            while (!queue.isEmpty()) {
                Pixel2D current = queue.poll(); // get next pixel
//steo 6: cheak if reached destination
                if (pixelsEqual(current, end)) {
                    return reconstructPath(parent, start, current); // build path
                }
// step 7: expoler neighbors
                Pixel2D[] neighbors = getNeightborns(current, cyclic);
                for (Pixel2D neighbor : neighbors) {
                    Pixel2D actualNeighbor = cyclic ? wrapAround(neighbor) : neighbor;

                    if (!cyclic && !isInside(actualNeighbor)) {
                        continue; //out of bond
                    }

                    if (!isInside(actualNeighbor)) {
                        continue;
                    }

                    if (getPixel(actualNeighbor) == obsColor) {
                        continue; //obstacle,skip
                    }

                    String key = pixelToString(actualNeighbor);
                    if (!visited.contains(key)) {
                        visited.add(key); //mark visited
                        parent.put(key, current);// remeber parent
                        queue.add(actualNeighbor); //add to queue
                    }
                }
            }

            return null; //  no path found
        }
    /*
     *calculate distance from start to all reachablepixel
     * map where each pixel contain its distance from start
     * value-1=unreacheble
     * value 0= start pixel
     * value n=n step away from start
     */
    @Override
    public Map2D allDistance(Pixel2D start, int obsColor, boolean cyclic) {
           // step 1: creat distance map(initialize to-1=unreachebel)
            Map2D distanceMap = new Map(getWidth(), getHeight(), -1);
           //step 2: validate start point
            if (!cyclic && !isInside(start)) {
                return distanceMap;  //all-1
            }

            Pixel2D actualStart = cyclic ? wrapAround(start) : start;

            if (!isInside(actualStart)) {
                return distanceMap;
            }

            if (getPixel(actualStart) == obsColor) {
                return distanceMap; //start is obstacle
            }
           //step 3: initialize BFS
            Queue<Pixel2D> queue = new LinkedList<>();
            Set<String> visited = new HashSet<>();

            queue.add(actualStart);
            visited.add(pixelToString(actualStart));
            distanceMap.setPixel(actualStart, 0); // Distance to self is 0
             // step 4: BFS to all reachable pixel
            while (!queue.isEmpty()) {
                Pixel2D current = queue.poll();
                int currentDist = distanceMap.getPixel(current); //get current distance

                Pixel2D[] neighbors = getNeightborns(current, cyclic);

                for (Pixel2D neighbor : neighbors) {
                    Pixel2D actualNeighbor = cyclic ? wrapAround(neighbor) : neighbor;

                    if (!cyclic && !isInside(actualNeighbor)) {
                        continue;
                    }

                    if (!isInside(actualNeighbor)) {
                        continue;
                    }

                    if (getPixel(actualNeighbor) == obsColor) {
                        continue; // obstacle
                    }

                    String key = pixelToString(actualNeighbor);
                    if (!visited.contains(key)) {
                        visited.add(key);
                        distanceMap.setPixel(actualNeighbor, currentDist + 1); // distance +1
                        queue.add(actualNeighbor);
                    }
                }
            }

            return distanceMap; // return distance map
        }

	////////////////////// Private Methods ///////////////////////
    /**
     *implement cyclic wrapping
     * formula ((value%size)+size)%size
     * handeles negative number correctly
     */
    private Pixel2D wrapAround(Pixel2D p) {
        int x= p.getX();
        int y=p.getY();
        int w=getWidth();
        int h=getHeight();

        x= ((x%w)+w)%w; // wrap x cordnet
        y= ((y%h)+h)%h; // wrap y coedenet

        return new Index2D(x , y);
    }
    /**
     *dosen't cheak bounds -caller must validate
     * return  array of 4 neirgtboring pixels
     */
    private Pixel2D[] getNeightborns(Pixel2D p, boolean cyclic) {
        int x=p.getX();
        int y=p.getY();

        return new Pixel2D[]{
                new Index2D(x-1 , y), //left
                new Index2D(x+1 , y),// right
                new Index2D(x , y-1), // down
                new Index2D(x , y+1) //up

        };

    }
    /**
     *creat unique string key for hashmap
     */
    private String pixelToString(Pixel2D p) {
        return p.getX() +","+p.getY();
    }
    /**
     *compare coordintes
     */
    private boolean pixelsEqual(Pixel2D p1, Pixel2D p2) {
        return p1.getX() == p2.getX() && p1.getY() == p2.getY();

    }
    /**
     * reconstruct path from parent links(BFS result)
     * start at end follow parents back to start then reveres
     */
    private Pixel2D[] reconstructPath(java.util.Map< String ,Pixel2D> parent, Pixel2D start , Pixel2D end ) {
        List<Pixel2D> path = new ArrayList<>();
        Pixel2D current =end;

        while (current!=null) { // backtrack to start
            path.add(new Index2D(current)); // add to pth
            String key = pixelToString(current);
            current= parent.get(key);//move to parent

        }
        Collections.reverse(path); // reveres
        return path.toArray(new Pixel2D[0]); // covert to array
    }

}