package src;

import src.View.GameMap;
import src.View.GameView;
import src.View.Sprite;
import src.View.Tile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class Tropa extends Entity implements Serializable, Runnable {

    private Sprite sprite;
    private char troopDirection = 'n';
    private boolean isMoving = false;
    private GameMap gameMap;
    private float xVariation;
    private float yVariation;
    private static final int SIDE = 32;
    private boolean isFighting;
    private boolean isOn;
    private int idPartida;
    private int idUsuariInvoke;




    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked = false;


    public int getIdUsuariInvoke() {
        return idUsuariInvoke;
    }

    public void setIdUsuariInvoke(int idUsuariInvoke) {
        this.idUsuariInvoke = idUsuariInvoke;
    }


    private String whichSprite;
    private Usuari usr;
    private int defaultY;
    private float initialX;
    private float initialY;
    private int numTorre;
    private int hasDestroyedCastle;


    public int getDestroyedCastle() {
        return hasDestroyedCastle;
    }

    public void setdestroyedCastle(int hasDestroyedCastle) {
        this.hasDestroyedCastle = hasDestroyedCastle;
    }

    private int troopType;
    private int idTropa;
    private int vida;
    private int cost;
    private int atac;
    private int alcance;
    private boolean ofensiva;
    private Tile tile;

    public float getInitialX() {
        return initialX;
    }

    public void setInitialX(float initialX) {
        this.initialX = initialX;
    }

    public float getInitialY() {
        return initialY;
    }

    public void setInitialY(float initialY) {
        this.initialY = initialY;
    }

    private boolean isPlaying = true;
    private static int cont = 0;
    private ArrayList<Sprite> mov = new ArrayList<>();


    public Tile getTroopTile() {
        for(int i = 0; i < 20; i ++){
            for(int j = 0; j < 10; j++){
                if(this.xPosition >= this.getGameMap().getMapTiles()[i][j].getX1() && this.xPosition < this.getGameMap().getMapTiles()[i][j].getX2()
                    && this.yPosition >= this.getGameMap().getMapTiles()[i][j].getY1() && this.yPosition < this.getGameMap().getMapTiles()[i][j].getY2()){
                    //Trobem el tile al que es troba la nostra tropa i el retornem.
                    return this.getGameMap().getTilesArray()[i];

                }
            }
        }
        return null;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }



    public int getTroopType() {
        return troopType;
    }

    public void setTroopType(int troopType) {
        this.troopType = troopType;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public Tropa() {

    }

    public boolean isFighting() {
        return isFighting;
    }

    public void setFighting(boolean fighting) {
        isFighting = fighting;
    }

    public Tropa(GameMap gameMap, float xPosition, float yPosition, Sprite sprite, Usuari user) {
        this.gameMap = gameMap;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.sprite = sprite;
        this.troopType = -1;
        xVariation = 0;
        yVariation = (float) -2;
        this.isFighting = false;
        this.defaultY = 500;
        this.idUsuariInvoke = user.getIdUsuari();
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

    /*public void update(){
        if(isPlaying) {
            if(this.troopType == 0 || this.troopType == 1){
                moveOffensiveTroop(xVariation, yVariation, cont);
            }else if(troopType == 2){
                bombExplosion(cont);
            }else{

            }
            cont++;
        }
    }*/

    public void setSprites(Sprite sprite){

        if(sprite.equals(Sprite.SKELETON_BACK)){

            this.troopType = 0;
            mov.add(Sprite.SKELETON_RIGHT);
            mov.add(Sprite.SKELETON_RIGHT_LEFT_FOOT);
            mov.add(Sprite.SKELETON_RIGHT_RIGHT_FOOT);
            mov.add(Sprite.SKELETON_BACK);
            mov.add(Sprite.SKELETON_BACK_LEFT_FOOT);
            mov.add(Sprite.SKELETON_BACK_RIGHT_FOOT);
            mov.add(Sprite.SKELETON_LEFT);
            mov.add(Sprite.SKELETON_LEFT_LEFT_FOOT);
            mov.add(Sprite.SKELETON_LEFT_RIGHT_FOOT);
            mov.add(Sprite.SKELETON_FRONT);
            mov.add(Sprite.SKELETON_FRONT_LEFT_FOOT);
            mov.add(Sprite.SKELETON_FRONT_RIGHT_FOOT);


        } else if(sprite.equals(Sprite.GOBLIN_BACK)){
            this.troopType = 1;

            mov.add(Sprite.GOBLIN_RIGHT);
            mov.add(Sprite.GOBLIN_RIGHT_LEFT_FOOT);
            mov.add(Sprite.GOBLIN_RIGHT_RIGHT_FOOT);
            mov.add(Sprite.GOBLIN_BACK);
            mov.add(Sprite.GOBLIN_BACK_LEFT_FOOT);
            mov.add(Sprite.GOBLIN_BACK_RIGHT_FOOT);
            mov.add(Sprite.GOBLIN_LEFT);
            mov.add(Sprite.GOBLIN_LEFT_LEFT_FOOT);
            mov.add(Sprite.GOBLIN_LEFT_RIGHT_FOOT);
            mov.add(Sprite.GOBLIN_FRONT);
            mov.add(Sprite.GOBLIN_FRONT_LEFT_FOOT);
            mov.add(Sprite.GOBLIN_FRONT_RIGHT_FOOT);

        }else if(sprite.equals(Sprite.BOMB)){
            this.troopType = 2;

            mov.add(Sprite.BOMB);
            mov.add(Sprite.BOMB_PHASE_1);
            mov.add(Sprite.BOMB_PHASE_2);
            mov.add(Sprite.BOMB_PHASE_3);
            mov.add(Sprite.BOMB_PHASE_4);


        }else {
            this.troopType = 3;
            mov.add(Sprite.MAGIC_TOWER);
        }
    }



    public void show(GameView gameView){
        gameView.drawTroop(xPosition, yPosition, this);
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public char getTroopDirection() {
        return troopDirection;
    }

    public void setTroopDirection(char troopDirection) {
        this.troopDirection = troopDirection;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public float getxVariation() {
        return xVariation;
    }

    public void setxVariation(float xVariation) {
        this.xVariation = xVariation;
    }

    public float getyVariation() {
        return yVariation;
    }

    public void setyVariation(float yVariation) {
        this.yVariation = yVariation;
    }

    public static int getCont() {
        return cont;
    }

    public static void setCont(int cont) {
        Tropa.cont = cont;
    }

    public ArrayList<Sprite> getMov() {
        return mov;
    }

    public void setMov(ArrayList<Sprite> mov) {
        this.mov = mov;
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

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getWhichSprite() {
        return whichSprite;
    }

    public void setWhichSprite(String whichSprite) {
        this.whichSprite = whichSprite;
    }

    public int getDefaultY() {
        return defaultY;
    }

    public void setDefaultY(int defaultY) {
        this.defaultY = defaultY;
    }

    public int getNumTorre() {
        return numTorre;
    }

    public void setNumTorre(int numTorre) {
        this.numTorre = numTorre;
    }

    @Override
    public String toString() {
        return "Tropa{" +
                "sprite=" + sprite +
                ", troopDirection=" + troopDirection +
                ", isMoving=" + isMoving +
                ", gameMap=" + gameMap +
                ", xVariation=" + xVariation +
                ", yVariation=" + yVariation +
                ", isFighting=" + isFighting +
                ", isOn=" + isOn +
                ", idPartida=" + idPartida +
                ", whichSprite='" + whichSprite + '\'' +
                ", defaultY=" + defaultY +
                ", troopType=" + troopType +
                ", idTropa=" + idTropa +
                ", vida=" + vida +
                ", cost=" + cost +
                ", atac=" + atac +
                ", alcance=" + alcance +
                ", ofensiva=" + ofensiva +
                ", tile=" + tile +
                ", isPlaying=" + isPlaying +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }

    @Override
    public void run() {

    }
}
