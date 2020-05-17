package src.View;

import java.awt.geom.Point2D;
import java.io.Serializable;

//La classe Tile representara cada quadre on es carreagara cada sprite del joc
public class Tile implements Serializable {

    private int x1;
    private int x2;
    private int y1;
    private int y2;
    private Point2D tileCenter;
    private Sprite sprite;
    private boolean isSolid;

    public static final int SIDE = 32;


    //Diferents sprites carregats als tiles del joc
    public static final Tile GRASS = new Tile(Sprite.GRASS);
    public static final Tile WATER = new Tile(Sprite.WATER, true);
    public static final Tile BRIDGE = new Tile(Sprite.BRIDGE);
    public static final Tile CASTLE_WALL = new Tile(Sprite.CASTLE_WALL, true);
    public static final Tile TOWER_DOWN_LEFT = new Tile(Sprite.TOWER_DOWN_LEFT, true);
    public static final Tile TOWER_DOWN_RIGHT = new Tile(Sprite.TOWER_DOWN_RIGHT, true);
    public static final Tile TOWER_UP_LEFT = new Tile(Sprite.TOWER_UP_LEFT, true);
    public static final Tile TOWER_UP_RIGHT = new Tile(Sprite.TOWER_UP_RIGHT, true);
    public static final Tile CASTLE_UP = new Tile(Sprite.CASTLE_UP, true);
    public static final Tile CASTLE_DOWN_LEFT = new Tile(Sprite.CASTLE_DOWN_LEFT, true);
    public static final Tile CASTLE_DOWN_RIGHT = new Tile(Sprite.CASTLE_DOWN_RIGHT, true);
    public static final Tile TOWER_DOWN_LEFT_ENEMY = new Tile(Sprite.TOWER_DOWN_LEFT_ENEMY, true);
    public static final Tile TOWER_DOWN_RIGHT_ENEMY = new Tile(Sprite.TOWER_DOWN_RIGHT_ENEMY, true);
    public static final Tile TOWER_UP_LEFT_ENEMY = new Tile(Sprite.TOWER_UP_LEFT_ENEMY, true);
    public static final Tile TOWER_UP_RIGHT_ENEMY = new Tile(Sprite.TOWER_UP_RIGHT_ENEMY, true);
    public static final Tile CASTLE_UP_ENEMY = new Tile(Sprite.CASTLE_UP_ENEMY, true);
    public static final Tile CASTLE_DOWN_LEFT_ENEMY = new Tile(Sprite.CASTLE_DOWN_LEFT_ENEMY, true);
    public static final Tile CASTLE_DOWN_RIGHT_ENEMY = new Tile(Sprite.CASTLE_DOWN_RIGHT_ENEMY, true);


    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getX2() {
        return x2;
    }

    public void setX2(int x2) {
        this.x2 = x2;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getY2() {
        return y2;
    }

    public void setY2(int y2) {
        this.y2 = y2;
    }
    public Tile(){
    }

    public Tile(Sprite sprite){
        this.sprite = sprite;
        isSolid = false;

    }
    public Tile(Sprite sprite, boolean isSolid){
        this.sprite = sprite;
        this.isSolid = isSolid;
    }

    public boolean isSolid() {
        return isSolid;
    }

    public Point2D getTileCenter() {
        return tileCenter;
    }

    public void setTileCenter(Point2D tileXCenter) {
        this.tileCenter = tileXCenter;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public boolean isInsideTile(double x, double y){
        if (x >= this.getX1() && x < this.getX2() && y>= this.getY1() && y < this.getY2()){
            return true;
        }else{
            return false;
        }
    }

    public void drawTile(int x, int y, GameView gameView){
        //Hem de tornar a passar x i y a tiles, ja que al mapa ho haviem passsat a pixels
        gameView.drawTile(x << 5, y << 5, this);
    }

    public boolean tileIsSolid(){
        return false;
    }
}
