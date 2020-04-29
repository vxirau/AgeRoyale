package src.View;

public class Sprite {


    //coordenades x i y de l'sprite que escollim i pixels de l'sprite
    private int x;
    private int y;
    public int[] pixels;

    private final int side;
    private SpritesSheet sheet;

    //Sprites tropes
    public static final Sprite PERSONA_FRONT = new Sprite(32, 0, 0, 0, SpritesSheet.troops);
    public static final Sprite PERSONA_BACK = new Sprite(32, 1, 0, 0, SpritesSheet.troops);
    public static final Sprite PERSONA_RIGHT = new Sprite(32, 2, 0, 0, SpritesSheet.troops);
    public static final Sprite PERSONA_LEFT = new Sprite(32, 3, 0, 0, SpritesSheet.troops);


    //Sprites de l'arena
    public static Sprite GRASS = new Sprite(32, 0, 0, 0, SpritesSheet.arena);
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
}
