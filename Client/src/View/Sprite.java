package src.View;

import java.io.Serializable;

public class Sprite implements Serializable {


    //coordenades x i y de l'sprite que escollim i pixels de l'sprite
    private int x;
    private int y;
    public int[] pixels;

    private final int side;
    private SpritesSheet sheet;

    //Sprites tropes
    public static final Sprite GOBLIN_BACK = new Sprite(32, 4, 0, 0, SpritesSheet.troops);
    public static final Sprite GOBLIN_BACK_RIGHT_FOOT = new Sprite(32, 5, 0, 0, SpritesSheet.troops);
    public static final Sprite GOBLIN_BACK_LEFT_FOOT = new Sprite(32, 6, 0, 0, SpritesSheet.troops);
    public static final Sprite GOBLIN_LEFT = new Sprite(32, 4, 1, 0, SpritesSheet.troops);
    public static final Sprite GOBLIN_LEFT_RIGHT_FOOT = new Sprite(32, 5, 1, 0, SpritesSheet.troops);
    public static final Sprite GOBLIN_LEFT_LEFT_FOOT = new Sprite(32, 6, 1, 0, SpritesSheet.troops);
    public static final Sprite GOBLIN_FRONT = new Sprite(32, 4, 2, 0, SpritesSheet.troops);
    public static final Sprite GOBLIN_FRONT_LEFT_FOOT = new Sprite(32, 5, 2, 0, SpritesSheet.troops);
    public static final Sprite GOBLIN_FRONT_RIGHT_FOOT = new Sprite(32, 6, 2, 0, SpritesSheet.troops);
    public static final Sprite GOBLIN_RIGHT = new Sprite(32, 4, 3, 0, SpritesSheet.troops);
    public static final Sprite GOBLIN_RIGHT_LEFT_FOOT = new Sprite(32, 5, 3, 0, SpritesSheet.troops);
    public static final Sprite GOBLIN_RIGHT_RIGHT_FOOT = new Sprite(32, 6, 3, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_BACK = new Sprite(32, 0, 5, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_BACK_LEFT_FOOT = new Sprite(32, 1, 5, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_BACK_RIGHT_FOOT = new Sprite(32, 2, 5, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_LEFT = new Sprite(32, 0, 6, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_LEFT_RIGHT_FOOT = new Sprite(32, 1, 6, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_LEFT_LEFT_FOOT = new Sprite(32, 2, 6, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_FRONT = new Sprite(32, 0, 7, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_FRONT_LEFT_FOOT = new Sprite(32, 1, 7, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_FRONT_RIGHT_FOOT = new Sprite(32, 2, 7, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_RIGHT = new Sprite(32, 0, 8, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_RIGHT_LEFT_FOOT = new Sprite(32, 1, 8, 0, SpritesSheet.troops);
    public static final Sprite SKELETON_RIGHT_RIGHT_FOOT = new Sprite(32, 2, 8, 0, SpritesSheet.troops);
    public static final Sprite MAGIC_TOWER = new Sprite(32, 8, 0, 0, SpritesSheet.troops);
    public static final Sprite BOMB = new Sprite(32, 9, 0, 0, SpritesSheet.troops);
    public static final Sprite BOMB_PHASE_1 = new Sprite(32, 9, 1, 0, SpritesSheet.troops);
    public static final Sprite BOMB_PHASE_2 = new Sprite(32, 9, 2, 0, SpritesSheet.troops);
    public static final Sprite BOMB_PHASE_3 = new Sprite(32, 9, 3, 0, SpritesSheet.troops);
    public static final Sprite BOMB_PHASE_4 = new Sprite(32, 9, 4, 0, SpritesSheet.troops);



    //Sprites de l'arena
    public static  Sprite GRASS = new Sprite(32, 0, 0, 0, SpritesSheet.arena);
    public static Sprite WATER = new Sprite(32, 1, 0, 0, SpritesSheet.arena);
    public static Sprite BRIDGE = new Sprite(32, 2, 0, 0, SpritesSheet.arena);
    public static Sprite CASTLE_WALL = new Sprite(32, 3, 0, 0, SpritesSheet.arena);
    public static Sprite TOWER_DOWN_LEFT = new Sprite(32, 4, 0, 1, SpritesSheet.arena);
    public static Sprite TOWER_DOWN_LEFT_ENEMY = new Sprite(32, 4, 0, 0, SpritesSheet.arena);
    public static Sprite TOWER_DOWN_RIGHT = new Sprite(32, 5, 0, 1, SpritesSheet.arena);
    public static Sprite TOWER_DOWN_RIGHT_ENEMY = new Sprite(32, 5, 0, 0, SpritesSheet.arena);
    public static Sprite TOWER_UP_LEFT = new Sprite(32, 6, 0, 1, SpritesSheet.arena);
    public static Sprite TOWER_UP_LEFT_ENEMY = new Sprite(32, 6, 0, 0, SpritesSheet.arena);
    public static Sprite TOWER_UP_RIGHT = new Sprite(32, 7, 0, 1, SpritesSheet.arena);
    public static Sprite TOWER_UP_RIGHT_ENEMY = new Sprite(32, 7, 0, 0, SpritesSheet.arena);
    public static Sprite CASTLE_UP = new Sprite(32, 8, 0, 1, SpritesSheet.arena);
    public static Sprite CASTLE_UP_ENEMY = new Sprite(32, 8, 0, 0, SpritesSheet.arena);
    public static Sprite CASTLE_DOWN_LEFT = new Sprite(32, 9, 0, 1, SpritesSheet.arena);
    public static Sprite CASTLE_DOWN_LEFT_ENEMY = new Sprite(32, 9, 0, 0, SpritesSheet.arena);
    public static Sprite CASTLE_DOWN_RIGHT = new Sprite(32, 0, 1, 1, SpritesSheet.arena);
    public static Sprite CASTLE_DOWN_RIGHT_ENEMY = new Sprite(32, 0, 1, 0, SpritesSheet.arena);


    public Sprite(final int side, final int column, final int row, final int orientation, final SpritesSheet sheet){
        this.side = side;
        this.sheet = sheet;
        pixels = new int[side * side];


        this.x = column * side;
        this.y = row * side;

        loadSprite(orientation);

    }
    //Escollirem si carregar l'sprite normal o rotat amb el metode loadSprite
    private void loadSprite(int orientation){
        //type ens indicara si volem carregar el sprite de forma normal o rotat 180 graus, donat que a algunes parts del mapa reutilitzem spritespero necessitem invertirlos
        if(orientation == 0){
            loadNormalSprite();
        }else{
            loadRotatedSprite();
        }
    }

    //Carrega l'sprite sense rotar
    private void loadNormalSprite(){
        //Iterem a traves del full de sprites i obtenim els pixels de cada sprite
        for(int i = 0; i < side; i++){
            for(int j = 0; j < side; j++){
                //Obtenim els pixels de cada sprite
                pixels[i + j * side] = sheet.pixels[(j + this.x) + (i + this.y) * sheet.getWidth()];
            }
        }
    }

    //Carrega l'sprite sense rotar
    private void loadRotatedSprite(){
        int[] tempPixels = loadTempPixels();
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = tempPixels[tempPixels.length - 1 - i];
        }
    }

    //Copiem els pixels normals a un array temporal de pixels
    private int[] loadTempPixels(){
        int[] tempPixels = new int[side * side];

        //Iterem a traves del full de sprites i obtenim els pixels de cada sprite
        for(int i = 0; i < side; i++){
            for(int j = 0; j < side; j++){
                //Obtenim els pixels de cada sprite
                tempPixels[i + j * side] = sheet.pixels[(j + this.x) + (i + this.y) * sheet.getWidth()];
            }
        }
        return tempPixels;
    }


    public int getSide() {
        return side;
    }

    public Sprite getRight(Sprite sprite){

        return sprite;
    }
}
