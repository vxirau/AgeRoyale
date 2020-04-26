package src.View;
//La classe Tile representara cada quadre on es carreagara cada sprite del joc
public class Tile {

    private int x;
    private int y;

    public Sprite getSprite() {
        return sprite;
    }

    private Sprite sprite;

    //Diferents sprites carregats als tiles del joc
    public static final Tile GRASS = new Tile(Sprite.GRASS);


    public Tile(Sprite sprite){
        this.sprite = sprite;
    }


    private void showTile(int x, int y, GameView gameView){
        gameView.showTile(x, y, this);
    }

    public boolean tileIsSolid(){
        return false;
    }
}
