package src;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    private String type;
    private Object object;

    public Message(Object obj, String type){
        this.type = type;
        this.object =  obj;
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

}
