package src;

import java.io.Serializable;

public class Edifici extends Entity implements Serializable{

    Integer[][] tiles;

    public Edifici(){
        this.tiles = new Integer[4][2];
    }

    public Integer[][] getTiles() {
        return tiles;
    }

    public void setTiles(Integer[][] tiles) {
        this.tiles = tiles;
    }
}
