package src;

import src.View.GameView;

import java.io.Serializable;

public class Entity implements Serializable {

    protected float xPosition;
    protected float yPosition;
    private int entityLife;

    public int getEntityLife() {
        return entityLife;
    }

    public void setEntityLife(int entityLife) {
        this.entityLife = entityLife;
    }

    public boolean isEntityIsDestroyed() {
        return entityIsDestroyed;
    }

    private boolean entityIsDestroyed = false;


    public Entity(){

    }
    public void update(){

    }

    public void show(GameView gameView){

    }

    public void updatexPosition(float xVariation){
        xPosition += xVariation;
    }

    public void updateyPosition(float yVariation){
        yPosition += yVariation;
    }

    public void destroyEntity(){
        entityIsDestroyed = true;
    }

    public float getxPosition() {
        return xPosition;
    }

    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public boolean entityIsDestroyed() {
        return entityIsDestroyed;
    }

    public void setEntityIsDestroyed(boolean entityIsDestroyed) {
        this.entityIsDestroyed = entityIsDestroyed;
    }
}
