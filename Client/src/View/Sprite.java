package src.View;

public class Sprite {


    //coordenades x i y de l'sprite que escollim i pixels de l'sprite
    private int x;
    private int y;
    public int[] pixels;

    private final int side;
    private SpritesSheet sheet;



    //Sprites
    public static Sprite GRASS = new Sprite(32, 0, 0, SpritesSheet.arena);
    public static Sprite KING;
    public static Sprite TOWER;
    public static Sprite RIVER;
    public static Sprite BRIDGE;


    public Sprite(final int side, final int column, final int row, final SpritesSheet sheet){
        this.side = side;
        this.sheet = sheet;
        pixels = new int[side * side];


        this.x = column * side;
        this.y = row * side;

        //Iterem a traves dels sprites i obtenim els pixels de cada sprite
        for(int i = 0; i < side; i++){
            for(int j = 0; j < side; j++){
                //Obtenim els pixels de cada sprite
                pixels[i + j * side] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.getWidth()];
            }
        }

    }


    public int getSide() {
        return side;
    }
}
