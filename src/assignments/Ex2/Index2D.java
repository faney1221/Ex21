package assignments.Ex2;
/* represent a singl pixcel with x,y corrdenet
* use x,y prameter
*/
public class Index2D implements Pixel2D {
    private int x;
    private int y;
    public Index2D(int x, int y) {
        this.x = x; // store x cordenet
        this.y = y; // store y cordenet


    }
    /*
    *creat new pixcel at position(x,y)
  * use x,y prameter
 * x horitzonal position
 * y vertical position
     */
    public Index2D(Pixel2D other) {
        this.x = other.getX(); // copy x from other pixcel
        this.y = other.getY(); // copy y from other pixcel
        ;
    }
    /*
    return x(horizontal) corrdenet of the pixcel
     */
    @Override
    public int getX() {

        return  this.x; //return x coordenet
    }
    /*
    return y(vertical) corrdenet of the pixcel
     */

    @Override
    public int getY() {

        return this.y; //return x coordent
    }
    /*
      *  calculate stright line distance to another pixcel
       * i used the formula root((x1-x2)^2+(y1-y2)^2) pythagoraeam theory
       * return the distance as double
         */
    @Override
    public double distance2D(Pixel2D p2) {
        int dx =this.x - p2.getX(); // calculat x
        int dy =this.y - p2.getY(); // calculate y

        return  Math.sqrt((dx*dx)+(dy*dy)); // pythagorean  theory
    }
    /*
        return  string representation
         */
    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")"; // format as (x,y)
    }
    /*
       * cheak if two pixcel have the same cordntes
       * return true if both x&y match
         */
    @Override
    public boolean equals(Object p) {
        if(this==p){return true;} // cheak if same object refarence
        if(!(p instanceof Index2D)){return false;} // wrong types

        Pixel2D other = (Pixel2D)p; // cast to pixcel2D
        return this.x == other.getX() && this.y == other.getY(); // comper the cordenet
    }
}