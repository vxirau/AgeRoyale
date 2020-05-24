package src;

import java.io.Serializable;

/**
 * Classe que representa els edificis de la tropa
 */
public class Edifici extends Entity implements Serializable{

    Integer[][] tiles;

    /**
     * Constructor de la classe
     */
    public Edifici(){
        this.tiles = new Integer[4][2];
    }

    /**
     * Retorna el que hi ha en el quadrat
     * @return tiles indica el que hi ha en aquell quadrat
     */
    public Integer[][] getTiles() {
        return tiles;
    }

    /**
     * Assigna el que hi ha en aquell quadrat
     * @param tiles indica el que hi ha en aquell quadrat
     */
    public void setTiles(Integer[][] tiles) {
        this.tiles = tiles;
    }
}
