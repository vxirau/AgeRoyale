package src.View;

public class GameMap {

    private int mapWidth;
    private int mapHeight;

    private int[] mapTiles;

    public GameMap(int mapWidth, int mapHeight){
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;

        this.mapTiles = new int[mapWidth * mapHeight];
        generateGameMap();
    }


    public GameMap(String path){
        loadMap(path);
    }

    private void generateGameMap(){

    }

    private void loadMap(String path){

    }
    public void updateMap(){

    }

    public void showMap(GameView gameView){

    }


}
