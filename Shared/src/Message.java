package src;

import java.io.Serializable;

public class Message implements Serializable {
    private String type;
    private Object object;

    public Message(Object user, String type){
        this.type = type;
        this.object =  user;
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
