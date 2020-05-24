package src;

import src.View.GameView;

import java.io.Serializable;

/**
 * Classe que representa les entitats de cada tropa
 */
public class Entity implements Serializable {

    protected float xPosition;
    protected float yPosition;
    private int entityLife;

    /**
     * Retorna la vida de una entitat
     * @return entityLife indica la vida de la entitat
     */
    public int getEntityLife() {
        return entityLife;
    }

    /**
     * Assigna la vida a una entitat
     * @param entityLife indica la vida d'una entitat
     */
    public void setEntityLife(int entityLife) {
        this.entityLife = entityLife;
    }

    /**
     * Retorna si la entitat ha estat destruida
     * @return entityIsDestroyed indica si ha estat destruida o no
     */
    public boolean isEntityIsDestroyed() {
        return entityIsDestroyed;
    }

    private boolean entityIsDestroyed = false;

    /**
     * Constructor de la classe
     */
    public Entity(){

    }

    /**
     * Actualitza les entitats
     */
    public void update(){

    }

    /**
     * Mostra el joc
     * @param gameView representa la vista del joc
     */
    public void show(GameView gameView){

    }

    /**
     * Actualitza la posicio de la x
     * @param xVariation indica la variacio en x
     */
    public void updatexPosition(float xVariation){
        xPosition += xVariation;
    }

    /**
     * Actualitza la posicio de la y
     * @param yVariation indica la variacio en y
     */
    public void updateyPosition(float yVariation){
        yPosition += yVariation;
    }

    /**
     * Destrueix una entitat
     */
    public void destroyEntity(){
        entityIsDestroyed = true;
    }

    /**
     * Retorna la posicio en x
     * @return xPosition indica la posicio de la x
     */
    public float getxPosition() {
        return xPosition;
    }

    /**
     * Assigna la posicio en x
     * @param xPosition idnica la posicio de la x
     */
    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    /**
     * Retorna la posició de la y
     * @return yPosition indica la posicio de la y
     */
    public float getyPosition() {
        return yPosition;
    }

    /**
     * Assigna la posicio de la y
     * @param yPosition indica la posicio de la y
     */
    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    /**
     * Indica si la entitat ha estat destruida
     * @return entityIsDestroyed indica si la entitat ha estat destruida
     */
    public boolean entityIsDestroyed() {
        return entityIsDestroyed;
    }

    /**
     * Assigna si la entitat ha estat destruida
     * @param entityIsDestroyed idnica si la entitat ha estat destruida
     */
    public void setEntityIsDestroyed(boolean entityIsDestroyed) {
        this.entityIsDestroyed = entityIsDestroyed;
    }
}
