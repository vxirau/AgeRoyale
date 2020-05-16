package src.View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public class ImageMap extends GameMap implements Serializable {

    private int[] pixelArray;

    public ImageMap(String path) throws IOException {
        super(path);
    }
    //Metode per llegir el mapa creat amb pixels, mitjancant el qual introduim els valors dels colors del mapa a pixelArray
    public void loadMap(String path) throws IOException {
        BufferedImage image = ImageIO.read(ImageMap.class.getResource(path));

        mapWidth = image.getWidth();
        mapHeight = image.getHeight();

        tilesArray = new Tile[mapWidth * mapHeight];
        pixelArray = new int[mapWidth * mapHeight];

        image.getRGB(0, 0, mapWidth, mapHeight, pixelArray,0,  mapWidth);
    }

    //Metode per crear el mapa. Llegim els colors guardats a pixelArray, i en funcio del color que trobi el pixel, carregara l'sprite pertinent al tile
    public void generateGameMap(){
        for(int i = 0; i < pixelArray.length; i++){
            //Obtenim el color guardat a la posicio i de l'array de pixels
            switch(pixelArray[i]){
                case 0xff386003:
                    tilesArray[i] = Tile.GRASS;
                    continue;
                case 0xff0f239b:
                    tilesArray[i] = Tile.WATER;
                    continue;
                case 0xff6d442b:
                    tilesArray[i] = Tile.BRIDGE;
                    continue;
                case 0xff000000:
                    tilesArray[i] = Tile.CASTLE_WALL;
                    continue;
                case 0xffd77940:
                    tilesArray[i] = Tile.TOWER_DOWN_LEFT_ENEMY;
                    continue;
                case 0xff9a4c35:
                    tilesArray[i] = Tile.TOWER_DOWN_RIGHT_ENEMY;
                    continue;
                case 0xff5c200d:
                    tilesArray[i] = Tile.TOWER_UP_LEFT_ENEMY;
                    continue;
                case 0xff320e02:
                    tilesArray[i] = Tile.TOWER_UP_RIGHT_ENEMY;
                    continue;
                case 0xff3b3b3b:
                    tilesArray[i] = Tile.CASTLE_UP_ENEMY;
                    continue;
                case 0xff5e5e5e:
                    tilesArray[i] = Tile.CASTLE_DOWN_LEFT_ENEMY;
                    continue;
                case 0xff888888:
                    tilesArray[i] = Tile.CASTLE_DOWN_RIGHT_ENEMY;
                    continue;
                case 0xff20164b:
                    tilesArray[i] = Tile.TOWER_DOWN_LEFT;
                    continue;
                case 0xff2d1c79:
                    tilesArray[i] = Tile.TOWER_DOWN_RIGHT;
                    continue;
                case 0xff1e2c07:
                    tilesArray[i] = Tile.TOWER_UP_LEFT;
                    continue;
                case 0xff3ac5a5:
                    tilesArray[i] = Tile.TOWER_UP_RIGHT;
                    continue;
                case 0xff12994f:
                    tilesArray[i] = Tile.CASTLE_UP;
                    continue;
                case 0xff553b04:
                    tilesArray[i] = Tile.CASTLE_DOWN_LEFT;
                    continue;
                case 0xff867550:
                    tilesArray[i] = Tile.CASTLE_DOWN_RIGHT;
                    continue;
                default:
                    tilesArray[i] = null;
            }
        }
    }
}
