package src;

import src.View.GameMap;
import src.View.GameView;
import src.View.Sprite;

import java.io.Serializable;

public class Tropa extends Entity implements Serializable {

    private Sprite sprite;
    private char troopDirection = 'n';
    private boolean isMoving = false;

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

    public Tropa(int xPosition, int yPosition, Sprite sprite) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.sprite = sprite;
    }



    public Tropa(int idTropa, int vida, int cost, int atac, int alcance, boolean ofensiva) {
        this.idTropa = idTropa;
        this.vida = vida;
        this.cost = cost;
        this.atac = atac;
        this.alcance = alcance;
        this.ofensiva = ofensiva;
    }

    public void update(){
        if(isPlaying){
            moveTroop(0, 0);
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
        /*if (xVariation != 0 || yVariation != 0){
            if(troopDirection == 'n'){
                sprite = Sprite.PERSONA_BACK;
            }
            if(troopDirection == 's'){
                sprite = Sprite.PERSONA_FRONT;
            }
            if(troopDirection == 'w'){
                sprite = Sprite.PERSONA_LEFT;
            }
            if(troopDirection == 'e'){
                sprite = Sprite.PERSONA_RIGHT;
            }

        }*/


        //Si la tropa no ha estat destruida, la movem
        if(!entityIsDestroyed()){
            updatexPosition(xVariation);
            updateyPosition(yVariation);
        }
    }

    //Metode per detectar si col·lisionem amb alguna cosa al mapa
    private boolean onCollision(){
        return false;
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
