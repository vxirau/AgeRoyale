package src.View;

import java.io.IOException;

public class GameMap {

    protected int mapWidth;
    protected int mapHeight;

    private int[] mapTiles;
    protected Tile[] tilesArray;


    public GameMap(String path) throws IOException {
        loadMap(path);
        generateGameMap();
    }

    //Metode que omplira el conjunt de tiles del mapa en funcio del tipus de tile que li correspon
    protected void generateGameMap(){

    }

    protected void loadMap(String path) throws IOException {

    }
    public void updateMap(){

    }
    //Metode que traduira el conjunt de tiles del nostre mapa a pixels
    public void showMap(int compensX, int compensY, GameView gameView){
        //Utilitzarem bit shifting per tal d'optimitzar memoria (movem cinc bits cap a la dreta, es a dir, >> 5 es el mateix que / 32), i aixi passarem els tiles a pixels
        int north = compensY >> 5;
        int south = (compensY + gameView.getHeight() + Tile.SIDE) >> 5;
        int east = (compensX + gameView.getWidth() + Tile.SIDE) >> 5;
        int west = compensX >> 5;
        //Recorrem el mapa d'adalt abaix (north a south) i d'esquerra a dreta (west a east)
        for(int i = north; i < south; i++){
            for(int j = west; j < east; j++){
                //En aquest sentit, dibuixem cada tile que toca cridant al metode drawTile()

                if(j < 0 || i < 0 || j >= mapWidth ||  i >= mapHeight){
                    Tile.GRASS.drawTile(j, i, gameView);
                }else{
                    tilesArray[j + i * mapWidth].drawTile(j, i, gameView);
                }
            }
        }
    }



}
