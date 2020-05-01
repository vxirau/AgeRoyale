package src;

import src.View.GameMap;
import src.View.GameView;
import src.View.Sprite;

import java.io.Serializable;

public class Tropa extends Entity implements Serializable {

    private Sprite sprite;
    private char troopDirection = 'n';
    private boolean isMoving = false;
    private GameMap gameMap;
    private int xVariation;
    private int yVariation;

    private int idTropa;
    private int vida;
    private int cost;
    private int atac;
    private int alcance;
    private boolean ofensiva;
    private boolean isPlaying = true;

    public Sprite getSprite() {
        return sprite;
    }

    public Tropa() {

    }

    public Tropa(GameMap gameMap, int xPosition, int yPosition, Sprite sprite) {
        this.gameMap = gameMap;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.sprite = sprite;
        xVariation = 0;

        yVariation = 1;
    }



    public Tropa(GameMap gameMap, int idTropa, int vida, int cost, int atac, int alcance, boolean ofensiva) {
        this.gameMap = gameMap;
        this.idTropa = idTropa;
        this.vida = vida;
        this.cost = cost;
        this.atac = atac;
        this.alcance = alcance;
        this.ofensiva = ofensiva;
    }

    public void update(){
        if(isPlaying){
            moveTroop(xVariation, yVariation);
        }
    }

    public void show(GameView gameView){
        gameView.drawTroop(xPosition, yPosition, this);
    }

    private void moveTroop(int xVariation, int yVariation){
        //Es mou cap a la dreta (east)
        if(xVariation > 0){
            troopDirection = 'e';
            this.xPosition += xVariation;
        }
        //Es mou cap a l'esquerra (west)
        if(xVariation < 0){
            troopDirection = 'w';
            this.xPosition += xVariation;
        }
        //Es mou cap abaix (south)
        if(yVariation > 0){
            troopDirection = 's';
            this.yPosition += yVariation;
        }
        //Es mou cap adalt (north)
        if(yVariation < 0){
            troopDirection = 'n';
            this.yPosition += yVariation;
        }



        //Si la tropa no ha estat destruida, la movem
        if(!entityIsDestroyed()){
            if(!onCollision(xVariation,0)){
                updatexPosition(xVariation);
            }else{
                xVariation = 0;
            }
            if(!onCollision(0,yVariation)){
                updateyPosition(yVariation);
            }else{
                yVariation = 0;
            }
        }
    }

    //Metode per detectar si col·lisionem amb alguna cosa al mapa
    private boolean onCollision(int xVariation, int yVariation){
        boolean collision = false;

        int xPosition = this.getxPosition() + xVariation;
        int yPosition = this.getyPosition() + yVariation;

        int leftMargin = -6;
        int rightMargin = 18;
        int supMargin = -4;
        int infMargin = 31;

        int leftBorder = (xPosition + rightMargin) / sprite.getSide();
        int rightBorder = (xPosition + rightMargin + leftMargin) / sprite.getSide();
        int supBorder = (yPosition + infMargin) / sprite.getSide();
        int infBorder = (yPosition + infMargin + supMargin) / sprite.getSide();

        if(gameMap.getSpecificTile(leftBorder + supBorder * gameMap.getMapWidth()).isSolid()){
            collision = true;
            yVariation = 0;
        }
        if(gameMap.getSpecificTile(leftBorder + infBorder * gameMap.getMapWidth()).isSolid()){
            yVariation = 0;
            collision = true;
        }
        if(gameMap.getSpecificTile(rightBorder + supBorder * gameMap.getMapWidth()).isSolid()){
            yVariation = 0;
            collision = true;
        }
        if(gameMap.getSpecificTile(rightBorder + infBorder * gameMap.getMapWidth()).isSolid()){
            yVariation = 0;
            collision = true;
        }

        return collision;
    }






    public int getIdTropa() {
        return idTropa;
    }

    public void setIdTropa(int idTropa) {
        this.idTropa = idTropa;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAtac() {
        return atac;
    }

    public void setAtac(int atac) {
        this.atac = atac;
    }

    public int getAlcance() {
        return alcance;
    }

    public void setAlcance(int alcance) {
        this.alcance = alcance;
    }

    public boolean isOfensiva() {
        return ofensiva;
    }

    public void setOfensiva(boolean ofensiva) {
        this.ofensiva = ofensiva;
    }
}
