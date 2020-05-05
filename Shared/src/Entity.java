package src;

import src.View.GameView;

public class Entity {

    protected float xPosition;
    protected float yPosition;
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
