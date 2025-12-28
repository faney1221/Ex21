
package assignments.Ex2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Intro2CS, 2026A, this is a very
 */
class MapTest {
    /**
     */
    private int[][] _map_3_3 = {{0,1,0}, {1,0,1}, {0,1,0}};
    private int[][] _map_5_5={
            {0,0,0,0,0},
            {0,1,1,1,0},
            {0,0,0,0,0},
            {0,1,1,1,0},
            {0,0,0,0,0},

    };
    private Map2D _m0, _m1, _m3_3 , _m5_5;
    @BeforeEach
    public void setuo() {
        _m0=new Map(5,5,0);
        _m1=new Map(5,5,0);

        _m3_3 = new Map(_map_3_3);
        _m5_5=new Map(_map_5_5);
    }
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void init() {
        int[][] bigarr = new int [500][500];
        _m1.init(bigarr);
        assertEquals(bigarr.length, _m1.getWidth());
        assertEquals(bigarr[0].length, _m1.getHeight());
        Pixel2D p1 = new Index2D(3,2);
        _m1.fill(p1,1, true);
    }

    @Test
    void testInit() {
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0, _m1);
    }
    @Test
    void testEquals() {
        assertEquals(_m0,_m1);
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        assertEquals(_m0,_m1);
    }
    @Test
    void testConstructorWithDimensions() {
        Map2D map= new Map(10,20,5);
        assertEquals(10,map.getWidth());
        assertEquals(20,map.getHeight());
        assertEquals(5,map.getPixel(0,0));
        assertEquals(5,map.getPixel(9,19));

    }
    @Test
    void testConstructorSquare() {
        Map2D map= new Map(7);
        assertEquals(7,map.getWidth());
        assertEquals(7,map.getHeight());
        assertEquals(0,map.getPixel(0,0));
    }
    @Test
    void testConstructorFromArray() {
        Map2D map= new Map(_map_3_3);
        assertEquals(3,map.getWidth());
        assertEquals(3,map.getHeight());
        assertEquals(0,map.getPixel(0,0));
        assertEquals(1,map.getPixel(0,1));
    }
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testInitLargeArray() {
        int[][] bigarr = new int [500][500];
        _m1.init(bigarr);
        assertEquals(500, _m1.getWidth());
        assertEquals(500, _m1.getHeight());
    }
    @Test
    void testInitWithValues() {
        _m0.init(4,6,7);
        assertEquals(4,_m0.getWidth());
        assertEquals(6,_m0.getHeight());
        assertEquals(7,_m0.getPixel(0,0));
        assertEquals(7,_m0.getPixel(3,5));
    }
    @Test
    void testGetPixel() {
        assertEquals(0,_m3_3.getPixel(0,0));
        assertEquals(1,_m3_3.getPixel(0,1));
        assertEquals(0,_m3_3.getPixel(1,1));
    }
    @Test
    void testGetPixelWithIndex2D() {
        Index2D p = new Index2D(1,2);
        assertEquals(1,_m3_3.getPixel(p));
    }
    @Test
    void testSetPixel() {
        _m0.setPixel(2,3,9);
        assertEquals(9,_m0.getPixel(2,3));
    }
    @Test
    void SetPixelWithIndex2D() {
        Index2D p = new Index2D(2,3);
        _m0.setPixel(p,9);
        assertEquals(9,_m0.getPixel(p));
    }
    @Test
    void testGetMap() {
        int[][] mapCopy=_m3_3.getMap();
        assertEquals(3,mapCopy.length);
        assertEquals(3,mapCopy[0].length);
        assertEquals(0,mapCopy[0][0]);

        mapCopy[0][0]=999;
        assertEquals(0,_m3_3.getPixel(0,0));
    }
    @Test
    void testIsInside(){
        Index2D p1= new Index2D(0,0);
        Index2D p2= new Index2D(2,2);
        Index2D p3= new Index2D(-1,0);
        Index2D p4= new Index2D(3,0);
        Index2D p5= new Index2D(0,3);

        assertTrue(_m3_3.isInside(p1));
        assertTrue(_m3_3.isInside(p2));
        assertFalse(_m3_3.isInside(p3));
        assertFalse(_m3_3.isInside(p4));
        assertFalse(_m3_3.isInside(p5));
    }
    @Test
    void testSameDimensions(){
        Map2D map1= new Map(5,7,0);
        Map2D map2= new Map(5,7,1);
        Map2D map3= new Map(5,8,0);

        assertTrue(map1.sameDimensions(map2));
        assertFalse(map1.sameDimensions(map3));
    }
    @Test
    void testEqualsWithDifferentValues(){
        _m0.init(_map_3_3);
        _m1.init(_map_3_3);
        _m1.setPixel(0,0,5);
        assertNotEquals(_m0,_m1);
    }
    @Test
    void testEqualsWithDifferentDimensions(){
        Map2D map1= new Map(5,5,0);
        Map2D map2= new Map(5,6,0);
        assertNotEquals(map1,map2);
    }
    @Test
    void testEqualsWithNull(){
        assertNotEquals(_m0,null);

    }
    @Test
    void testEqualsWithSelf(){
        assertEquals(_m0,_m1);
    }
    @Test
    void testAddMap2D(){
        int[][] data1={{1,2},{3,4}};
        int[][] data2={{5,6},{7,8}};

        Map2D map1= new Map(data1);
        Map2D map2= new Map(data2);

        map1.addMap2D(map2);

        assertEquals(6,map1.getPixel(0,0));  //1+5
        assertEquals(8,map1.getPixel(0,1));  //2+6
        assertEquals(10,map1.getPixel(1,0)); //3+7
        assertEquals(12,map1.getPixel(1,1));  //4+8
    }
    @Test
    void testAddMap2DWithDifferentDimensions(){
        Map2D map1= new Map(3,3,0);
        Map2D map2= new Map(4,4,0);

        assertThrows(IllegalArgumentException.class, () ->{
            map1.addMap2D(map2);
        });
    }
    @Test
    void testmul(){
        int[][] data={{2,4},{6,8}};
        Map2D map= new Map(data);
        map.mul(2.5);

        assertEquals(5,map.getPixel(0,0)); //2*2.5=5
        assertEquals(10,map.getPixel(0,1)); //4*2.5=10
        assertEquals(15,map.getPixel(1,0)); //6*2.5=15
        assertEquals(20,map.getPixel(1,1)); //8*2.5=20
    }
    @Test
    void testRescale(){
        Map2D map= new Map(4,4,0);
        map.setPixel(1,1,5);

        map.rescale(2.0,2.0);

        assertEquals(8,map.getWidth());
        assertEquals(8,map.getHeight());
    }
    @Test
    void testDrawCircle(){
        Map2D map= new Map(10,10,0);
        Index2D center= new Index2D(5,5);

        map.drawCircle(center, 2.0 ,1);

        assertEquals(1,map.getPixel(5,5)); //center
        assertEquals(1,map.getPixel(5,3)); //within redius
        assertEquals(0,map.getPixel(5,0)); //outside redius
    }
    @Test
    void testDrawLine(){
        Map2D map= new Map(10,10,0);
        Index2D p1= new Index2D(2,2);
        Index2D p2= new Index2D(5,5);

        map.drawLine(p1,p2,1);

        assertEquals(1,map.getPixel(2,2));
        assertEquals(1,map.getPixel(5,5));
        assertEquals(1,map.getPixel(3,3));
    }
    @Test
    void testDrawRect(){
        Map2D map= new Map(10,10,0);
        Index2D p1= new Index2D(2,2);
        Index2D p2= new Index2D(5,5);

        map.drawRect(p1,p2,1);

        assertEquals(1,map.getPixel(2,2));
        assertEquals(1,map.getPixel(5,5));
        assertEquals(1,map.getPixel(3,3));
        assertEquals(0,map.getPixel(0,0));
        assertEquals(0,map.getPixel(6,6));
    }
    @Test
    void testFillSimple(){
        Map2D map= new Map(5,5,0);
        Index2D start= new Index2D(0,0);

        int filled=map.fill(start , 5,false);

        assertEquals(25,filled); //all pixel should be filled
        assertEquals(5,map.getPixel(0,0));
        assertEquals(5,map.getPixel(4,4));
    }
    @Test
    void testFillWithObstacles(){
        //_m5_5 has obstacle(is)
        Index2D start= new Index2D(0,0);

        int filled= _m5_5.fill(start , 5,false);

        assertTrue(filled>0);
        assertEquals(5,_m5_5.getPixel(0,0));
        assertEquals(1,_m5_5.getPixel(1,1)); //obstacle unchanged
    }
    @Test
    void testFillSameColor(){
        Map2D map= new Map(3,3,0);
        Index2D start= new Index2D(0,0);

        int filled= map.fill(start , 0,false);

        assertEquals(0,filled); //no change if same color
    }
    @Test
    void testFillOutsideBounds(){
        Index2D outside= new Index2D(-1,-1);

        int filled= _m5_5.fill(outside , 5,false);
        assertEquals(0,filled);
    }
    @Test
    @Timeout(value = 1, unit = SECONDS)
    void testFillLargeMap(){
        Map2D map= new Map(100,100,0);
        Index2D start= new Index2D(50,50);

        int filled= map.fill(start , 1,false);

        assertEquals(10000,filled);
    }
    @Test
    void testShortestPathSimple() {
        Map2D map = new Map(5, 5, 0);
        Index2D start = new Index2D(0, 0);
        Index2D end = new Index2D(4, 4);

        Pixel2D[] path = map.shortestPath(start, end, 1, false);

        assertNotNull(path);
        assertTrue(path.length >= 9);
        assertEquals(start.getX(), path[0].getX());
        assertEquals(start.getY(), path[0].getY());
        assertEquals(end.getX(), path[path.length - 1].getX());
        assertEquals(end.getY(), path[path.length - 1].getY());
    }
    @Test
    void testShortestPathWithObstacles(){
        Index2D start= new Index2D(0,0);
        Index2D end= new Index2D(4,4);

        Pixel2D[] path = _m5_5.shortestPath(start, end, 1, false);

        assertNotNull(path);
        assertTrue(path.length > 0);

        for(Pixel2D p : path){
            assertNotEquals(1,_m5_5.getPixel(p));
        }
    }
    @Test
    void testShortestPathNoPath(){
        int[][] blocked={
                {0,1,0},
                {1,1,1},
                {0,1,0},
        };
        Map2D map= new Map(blocked);

        Index2D start= new Index2D(0,0);
        Index2D end= new Index2D(2,2);

        Pixel2D[] path = map.shortestPath(start, end, 1, false);
        assertNull(path);
    }
    @Test
    void testShortestPathStartOnObstacle(){
        Index2D start= new Index2D(1,1);
        Index2D end= new Index2D(4,4);

        Pixel2D[] path = _m5_5.shortestPath(start, end, 1, false);
        assertNull(path);
    }
    @Test
    void testShortestPathEndOnObstacle(){
        Index2D start= new Index2D(0,0);
        Index2D end= new Index2D(1,1);

        Pixel2D[] path = _m5_5.shortestPath(start, end, 1, false);

        assertNull(path);
    }
    @Test
    void testShortestPathOutOfBounds(){
        Index2D start= new Index2D(0,0);
        Index2D end= new Index2D(10,10);

        Pixel2D[] path = _m5_5.shortestPath(start, end, 1, false);

        assertNull(path);
    }
    @Test
    @Timeout(value=2 , unit = SECONDS)
    void testShortestPathLargeMap(){
      Map2D map= new Map(100,100,0);
      Index2D start= new Index2D(0,0);
      Index2D end= new Index2D(99,99);

      Pixel2D[] path = map.shortestPath(start, end, 1, false);

      assertNotNull(path);
      assertTrue(path.length > 0);
    }
    @Test
    void testAllDistanceSimple(){
        Map2D map= new Map(5,5,0);
        Index2D start= new Index2D(0,0);

        Map2D disMap=map.allDistance(start,1,false);

        assertNotNull(disMap);
        assertEquals(0,disMap.getPixel(0,0));
        assertEquals(1,disMap.getPixel(1,0));
        assertEquals(1,disMap.getPixel(0,1));
        assertEquals(8,disMap.getPixel(4,4));
    }
    @Test
    void testAllDistanceWithObstacles(){
        Index2D start= new Index2D(0,0);

        Map2D disMap=_m5_5.allDistance(start,1,false);

        assertEquals(0,disMap.getPixel(0,0));
        assertEquals(-1,disMap.getPixel(1,1));
    }
    @Test
    void testAllDistanceStartOnObstacles(){
        Index2D start= new Index2D(1,1);

        Map2D disMap=_m5_5.allDistance(start,1,false);
        assertEquals(-1,disMap.getPixel(1,1));
    }
    @Test
    void testFillCyclic(){
        Map2D map= new Map(5,5,0);
        map.setPixel(0,0,1);
        map.setPixel(0,4,1);
        map.setPixel(4,0,1);

        Index2D start= new Index2D(0,0);
        int filled= map.fill(start , 5,true);

        assertTrue(filled>=3);
    }
    @Test
    void testShortestPathCyclic(){
        Map2D map= new Map(10,10,0);
        Index2D start= new Index2D(0,0);
        Index2D end= new Index2D(9,9);

        Pixel2D[] pathNormal = map.shortestPath(start, end, 1, false);
        Pixel2D[] pathCyclic = map.shortestPath(start, end, 1, true);

        assertNotNull(pathCyclic);
        assertNotNull(pathNormal);

        assertTrue(pathCyclic.length <= pathNormal.length);
    }
    @Test
    void testCompleteWorkflow(){
        Map2D map= new Map(20,20,0);

        Index2D p1= new Index2D(5,5);
        Index2D p2= new Index2D(15,15);
        map.drawRect(p1,p2,1);

        Index2D c1=new Index2D(10,10);
        map.drawCircle(c1,2.0,1);

        Index2D start=new Index2D(0,0);
        Index2D end=new Index2D(19,19);
        Pixel2D[] path = map.shortestPath(start, end, 1, false);

        assertNotNull(path);

        int filled= map.fill(new Index2D(0,0), 5,false);
        assertTrue(filled>0);
    }

}
