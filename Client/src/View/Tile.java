package src.View;
//La classe Tile representara cada quadre on es carreagara cada sprite del joc
public class Tile {

    private int x;
    private int y;
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

    public Sprite getSprite() {
        return sprite;
    }

    public void drawTile(int x, int y, GameView gameView){
        //Hem de tornar a passar x i y a tiles, ja que al mapa ho haviem passsat a pixels
        gameView.drawTile(x << 5, y << 5, this);
    }

    public boolean tileIsSolid(){
        return false;
    }
}
