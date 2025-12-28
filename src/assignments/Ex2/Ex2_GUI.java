
package assignments.Ex2;
import java.awt.Color;
import java.io.*;
/**
 * Intro2CS_2026A
 * This class represents a Graphical User Interface (GUI) for Map2D.
 * The class has save and load functions, and a GUI draw function.
 * You should implement this class, it is recommender to use the StdDraw class, as in:
 * https://introcs.cs.princeton.edu/java/stdlib/javadoc/StdDraw.html
 *
 * visualized map2D using stDraw library
 *  each map  pixel bicom a colored square on screen
 */
public class Ex2_GUI {
    public static void drawMap(Map2D map) {
        //

        if (map==null) {
            System.out.println("err,map is null");
            return;
        }
        int width=map.getWidth();
        int height=map.getHeight();

        StdDraw.setCanvasSize(800,800);
        StdDraw.setXscale(0,width);
        StdDraw.setYscale(0,height);
        StdDraw.enableDoubleBuffering();

        StdDraw.clear(StdDraw.WHITE);

        for(int x=0;x<width;x++) {
            for(int y=0;y<height;y++) {
                int pixelValue =map.getPixel(x, y);

                Color color=getColorFromValue(pixelValue);
                StdDraw.setPenColor(color);
                StdDraw.filledSquare( x+0.5,(height-y-1)+0.5,0.5);
            }
        }
StdDraw.show();

    }

    /**
     * @param mapFileName
     * @return
     *
     */
    public static Map2D loadMap(String mapFileName) {
        Map2D ans = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(mapFileName))) {
            ans = (Map2D) ois.readObject();
            System.out.println("Map loaded successfully from: " + mapFileName);
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + mapFileName);
            // Create a default map if file doesn't exist
            ans = createDefaultMap();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading map: " + e.getMessage());
            ans = createDefaultMap();
        }

        return ans;
    }

    /**
     *
     * @param map
     * @param mapFileName
     */
    public static void saveMap(Map2D map, String mapFileName) {
        if (map==null) {
            System.out.println("err,map is null");
            return;
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(mapFileName))) {
            oos.writeObject(map);
            System.out.println("Map saved successfully to: " + mapFileName);
        } catch (IOException e) {
            System.err.println("Error saving map: " + e.getMessage());
        }


    }
    public static void main(String[] a) {
        String mapFile = "map.txt";
        Map2D map = loadMap(mapFile);
        saveMap(map, mapFile);
        drawMap(map);
    }
    /// ///////////// Private functions ///////////////

    private static Color getColorFromValue(int value) {
        switch (value) {
            case 0:
                return StdDraw.WHITE;
            case 1:
                return StdDraw.BLACK;
            case 2:
                return StdDraw.RED;
            case 3:
                return StdDraw.GREEN;
            case 4:
                return StdDraw.BLUE;
            case 5:
                return StdDraw.YELLOW;
            case 6:
                return StdDraw.ORANGE;
            case 7:
                return StdDraw.PINK;
            case 8:
                return StdDraw.CYAN;
            case 9:
                return StdDraw.MAGENTA;
            default:
                // For values > 9, create a grayscale color
                int gray = Math.min(255, Math.abs(value) % 256);
                return new Color(gray, gray, gray);
        }
    }

    /**
     * Creates a default map for testing
     * @return Map2D with some default content
     */
    private static Map2D createDefaultMap() {
        System.out.println("Creating default map with '100' displayed...");
        Map2D map = new Map(100, 100, 0);
        //draw 1
        map.drawLine(new Index2D(20,30),new Index2D(20,70),1);

        // Draw the first "0" circle
        Index2D center1 = new Index2D(45, 50);
        map.drawCircle(center1, 15.0, 4);
        map.drawCircle(center1, 10.0, 3);

        // draw the second "0"
        Index2D center2 = new Index2D(75, 50);
        map.drawCircle(center2, 15.0, 4);
        map.drawCircle(center2, 10.0, 3);

        // add decorated border
        map.drawRect(new Index2D(2,2),new Index2D(98,5),7);
        map.drawRect(new Index2D(2,95),new Index2D(98,98),7);
        map.drawRect(new Index2D(2,2),new Index2D(5,98),7);
        map.drawRect(new Index2D(95,2),new Index2D(98,98),7);


        return map;
    }

    /**
     * Example function to draw some shapes on the map
     * @param map - the map to draw on
     */
    private static void drawSomeShapes(Map2D map) {
        if (map == null) return;

        // Draw a circle
        Index2D center = new Index2D(map.getWidth() / 2, map.getHeight() / 2);
        map.drawCircle(center, 8.0, 4);

        // Draw a rectangle
        Index2D rectP1 = new Index2D(10, 10);
        Index2D rectP2 = new Index2D(20, 20);
        map.drawRect(rectP1, rectP2, 2);

        // Draw a line
        Index2D lineP1 = new Index2D(5, 5);
        Index2D lineP2 = new Index2D(map.getWidth() - 5, map.getHeight() - 5);
        map.drawLine(lineP1, lineP2, 1);

        // Fill an area
        Index2D fillStart = new Index2D(30, 30);
        map.fill(fillStart, 5, false);
    }
}
