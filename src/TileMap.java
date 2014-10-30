import java.awt.image.BufferedImage;

/**
 * Created by danielmacario on 14-10-29.
 */
public class TileMap {


    private double x;
    private double y;

    private int xmin;
    private int ymin;
    private int xmax;
    private int ymax;

    //for smooth scrolling?
    private double tween;

    //map
    private int[][] map;
    private int tileSize;
    private int numRows;
    private int numCols;
    private int width;
    private int height;

    //tileset
    private BufferedImage tileset;
    private int numTilesAcross;
    //private Tile[][] tiles;

    //drawing - limit what we draw to the tiles that are onscreen
    private int rowOffSet;
    private int colOffSet;
    private int numRowsToDraw;
    private int numColsToDraw;

    public TileMap(int tileSize) {
        this.tileSize = tileSize;
        numRowsToDraw = GamePanel.HEIGHT / tileSize + 2;
        numColsToDraw = GamePanel.WIDTH / tileSize + 2;
        tween = 0.07;
    }

}
