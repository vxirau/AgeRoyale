package src;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private String type;
    private Object object;
    private ArrayList<Partida> objectArray;

    public Message(Object user, String type){
        this.type = type;
        this.object =  user;
    }

    public Message(int a, ArrayList<Partida> array, String type){
        this.type = type;
        this.objectArray =  array;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public ArrayList<Partida> getObjectArray() {
        return objectArray;
    }

    public void setObjectArray(ArrayList<Partida> objectArray) {
        this.objectArray = objectArray;
    }
}
