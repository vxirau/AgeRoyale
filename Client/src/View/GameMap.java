package src.View;

import java.awt.*;
import java.io.IOException;
import java.io.Serializable;

/**
* Vista del mapa de la partida, el mapa del joc. Implementa serializable perque s'ha de enviar al servidor de tant en quant
* */
public class GameMap implements Serializable {

    protected int mapWidth;
    protected int mapHeight;
    public static final int SIDE = 32;
    private int xIncrement;
    private int yIncrement;
    private Tile[][] mapTiles;
    protected Tile[] tilesArray;


    /**
    * Constructor de la classe
     * @param path directori de la imatge del mapa
    * */
    public GameMap(String path) throws IOException {
        loadMap(path);
        generateGameMap();
        mapTiles = new Tile[20][10];
        assignTilePixels();
    }

    /**
    * --
    * */
    protected void generateGameMap(){

    }

    /**
    * Retorna el grossor del mapa
     * @return maWidth enter amb el valor del grossor
    * */
    public int getMapWidth() {
        return mapWidth;
    }

    /**
    * Retorna un Tile que es trobia  al posició indicada
     * @param position posició de la que es vol el tile
     * @return variable de tipus Tile corresponent al tile que està en la posició donada
    * */
    public Tile getSpecificTile(int position) {
        return tilesArray[position];
    }

    /**
    * Retorna el array de Tiles de la partida
     * @return tilesArray variable de tipus Tile[] amb totes les Tiles de la partida
    * */
    public Tile[] getTilesArray() {
        return tilesArray;
    }

    /**
    * Retorna la matri de tiles de la partida
     * @return mapTiles variable de tipus Tile[][] amb la informació de totes les tiles de la partida
    * */
    public Tile[][] getMapTiles() {
        return mapTiles;
    }

    /**
    * Setter que assigna la matriu de tiles a lo que rep per valor
     * @param mapTiles variable de tipus Tile[][]
    * */
    public void setMapTiles(Tile[][] mapTiles) {
        this.mapTiles = mapTiles;
    }

    /**
    * Construeix la graella de la partida mitjançant tiles
    * */
    private void assignTilePixels(){
        xIncrement = 0;
        yIncrement = 0;

        //Assignem pixels a cada tile
        for(int i = 0; i < 20; i ++){ //640
            xIncrement = 0;
            for (int j = 0; j < 10; j++){ //320
                mapTiles[i][j] = new Tile();
                mapTiles[i][j].setX1(xIncrement);
                mapTiles[i][j].setX2(xIncrement + SIDE);
                mapTiles[i][j].setY1(yIncrement);
                mapTiles[i][j].setY2(yIncrement + SIDE);
                mapTiles[i][j].setTileCenter(new Point((mapTiles[i][j].getX1() + (SIDE/2)),(mapTiles[i][j].getY1() + (SIDE/2))));
                xIncrement += SIDE;

            }
            yIncrement += SIDE;
        }
    }

    /**
     * --
    * */
    protected void loadMap(String path) throws IOException {

    }

    /**
     * --
     * */
    public void updateMap(){

    }

    /**
     * Traduira el conjunt de tiles del nostre mapa a pixels
     * @param compensX posició X
     * @param compensY posició Y
     * @param gameView vista de la partida
     * */
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
