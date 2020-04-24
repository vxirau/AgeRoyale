package src.View;

public class Sprite {


    private final int side;
    private final SpritesSheet sheet;

    //coordenades x i y de l'sprite que escollim
    private int x;
    private int y;


    private int[] pixels;


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

}
