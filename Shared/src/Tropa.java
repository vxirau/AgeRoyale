package src;

import src.View.GameMap;
import src.View.GameView;
import src.View.Sprite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class Tropa extends Entity implements Serializable {

    private Sprite sprite;
    private char troopDirection = 'n';
    private boolean isMoving = false;
    private GameMap gameMap;
    private float xVariation;
    private float yVariation;

    private int troopType;
    private int idTropa;
    private int vida;
    private int cost;
    private int atac;
    private int alcance;
    private boolean ofensiva;

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

    private boolean isPlaying = true;
    private static int cont = 0;
    private ArrayList<Sprite> mov = new ArrayList<>();



    public Sprite getSprite() {
        return sprite;
    }

    public Tropa() {

    }

    public Tropa(GameMap gameMap, float xPosition, float yPosition, Sprite sprite) {
        this.gameMap = gameMap;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.sprite = sprite;
        this.troopType = -1;
        xVariation = 0;
        yVariation = (float) -2;

        if(sprite.equals(Sprite.SKELETON_BACK)){
            this.troopType = 0;
            mov.add(Sprite.SKELETON_RIGHT);
            mov.add(Sprite.SKELETON_RIGHT_LEFT_FOOT);
            mov.add(Sprite.SKELETON_RIGHT_RIGHT_FOOT);
            mov.add(Sprite.SKELETON_BACK);
            mov.add(Sprite.SKELETON_BACK_LEFT_FOOT);
            mov.add(Sprite.SKELETON_BACK_RIGHT_FOOT);

        } else if(sprite.equals(Sprite.GOBLIN_BACK)){
            this.troopType = 1;
            mov.add(Sprite.GOBLIN_RIGHT);
            mov.add(Sprite.GOBLIN_RIGHT_LEFT_FOOT);
            mov.add(Sprite.GOBLIN_RIGHT_RIGHT_FOOT);
            mov.add(Sprite.GOBLIN_BACK);
            mov.add(Sprite.GOBLIN_BACK_LEFT_FOOT);
            mov.add(Sprite.GOBLIN_BACK_RIGHT_FOOT);

        }else if(sprite.equals(Sprite.BOMB)){
            this.troopType = 2;
            mov.add(Sprite.BOMB);
            mov.add(Sprite.BOMB_PHASE_1);
            mov.add(Sprite.BOMB_PHASE_2);
            mov.add(Sprite.BOMB_PHASE_3);
            mov.add(Sprite.BOMB_PHASE_4);


        }else{

        }
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
}
