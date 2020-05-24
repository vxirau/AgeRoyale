package src.View;

import java.awt.geom.Point2D;
import java.io.Serializable;

/**
* La classe Tile representara cada quadre on es carreagara cada sprite del joc
* */
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

    /**
    * Retorna la coordenaxa x1 de la Tile
     * @return enter amb el valor de la x1
    * */
    public int getX1() {
        return x1;
    }

    /**
    * Assigna el valor de la x1 a la variable de la classe
     * @param x1 enter a assignar
    * */
    public void setX1(int x1) {
        this.x1 = x1;
    }

    /**
     * Retorna la coordenaxa x2 de la Tile
     * @return enter amb el valor de la x2
     * */
    public int getX2() {
        return x2;
    }

    /**
     * Assigna el valor de la x2 a la variable de la classe
     * @param x2 enter a assignar
     * */
    public void setX2(int x2) {
        this.x2 = x2;
    }

    /**
     * Retorna la coordenaxa y1 de la Tile
     * @return enter amb el valor de la y1
     * */
    public int getY1() {
        return y1;
    }

    /**
     * Assigna el valor de la y1 a la variable de la classe
     * @param y1 enter a assignar
     * */
    public void setY1(int y1) {
        this.y1 = y1;
    }

    /**
     * Retorna la coordenaxa y2 de la Tile
     * @return enter amb el valor de la y2
     * */
    public int getY2() {
        return y2;
    }

    /**
     * Assigna el valor de la y2 a la variable de la classe
     * @param y2 enter a assignar
     * */
    public void setY2(int y2) {
        this.y2 = y2;
    }

    /**
    * Constructor de la classe buit
    * */
    public Tile(){
    }

    /**
    * Constructor sobrecarregat
     * @param sprite a assignar a la tile
    */
    public Tile(Sprite sprite){
        this.sprite = sprite;
        isSolid = false;

    }

    /**
     * Constructor sobrecarregat
     * @param sprite a assignar a la tile
     * @param isSolid boolean que indica si la tile es solida o no
     */
    public Tile(Sprite sprite, boolean isSolid){
        this.sprite = sprite;
        this.isSolid = isSolid;
    }

    /**
    * Ens indica si la tile actual es solida o no
     * @return isSolid boolea que indica si es solid
    * */
    public boolean isSolid() {
        return isSolid;
    }

    /**
     * Assigna el valor de solidesa a la tile
     * @param solid boolea que indica si es solid
     * */
    public void setSolid(boolean solid) {
        isSolid = solid;
    }

    /**
     * Retorna el punt2D de la Tile
     * @return tileCenter punt de l'espai de la tile
     * */
    public Point2D getTileCenter() {
        return tileCenter;
    }

    /**
    * Encarregada de assignar el punt 2d del centre de la tile
     * @param tileXCenter punt on es vol ubicar el centre de la tile
    * */
    public void setTileCenter(Point2D tileXCenter) {
        this.tileCenter = tileXCenter;
    }

    /**
    * Retorna el sprite de la tile
     * @return sprite variable de tipus Sprite
    * */
    public Sprite getSprite() {
        return sprite;
    }

    /**
    * Retorna si el punt que s'ha enviat esta dins de la tile
     * @param x coordenada x
     * @param y coordenada y
     * @return boolea que indica si esta dins o no
    * */
    public boolean isInsideTile(double x, double y){
        if (x >= this.getX1() && x < this.getX2() && y>= this.getY1() && y < this.getY2()){
            return true;
        }else{
            return false;
        }
    }

    /**
    * Dibuixa la tile pertinent
     * @param x coordenada x de la tile a dibuixar
     * @param y coordenada y de la tile a dibuixar
     * @param gameView vista del joc
    * */
    public void drawTile(int x, int y, GameView gameView){
        //Hem de tornar a passar x i y a tiles, ja que al mapa ho haviem passsat a pixels
        gameView.drawTile(x << 5, y << 5, this);
    }

    /**
    * Retorna si la tile en questió és sòlida o no.
    * @return boolea que indica la solidesa
    * */
    public boolean tileIsSolid(){
        return false;
    }
}
