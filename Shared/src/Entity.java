package src;

import src.View.GameView;

public class Entity {

    protected int xPosition;
    protected int yPosition;
    private boolean entityIsDestroyed = false;


    public Entity(){

    }
    public void update(){

    }

    public void show(GameView gameView){

    }

    public void updatexPosition(int xVariation){
        xPosition += xVariation;
    }

    public void updateyPosition(int yVariation){
        yPosition += yVariation;
    }

    public void destroyEntity(){
        entityIsDestroyed = true;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public boolean entityIsDestroyed() {
        return entityIsDestroyed;
    }

    public void setEntityIsDestroyed(boolean entityIsDestroyed) {
        this.entityIsDestroyed = entityIsDestroyed;
    }
}
