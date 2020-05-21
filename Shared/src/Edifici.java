package src;

import src.View.Tile;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Edifici extends Entity implements Serializable{

    ArrayList<Point2D> tiles;

    public Edifici(){
       tiles = new ArrayList<>();
    }

    public ArrayList<Point2D> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Point2D> tiles) {
        this.tiles = tiles;
    }
}
