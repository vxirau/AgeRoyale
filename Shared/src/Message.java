package src;

import java.io.Serializable;

/**
 * Classe per tal de generar el missatge, implementa la classe Serializable
 */
public class Message implements Serializable {
    private String type;
    private Object object;

    /**
     * Constructor de la classe
     * @param obj objecte del missatge
     * @param type tipus de missatge
     */
    public Message(Object obj, String type){
        this.type = type;
        this.object =  obj;
    }

    /**
     * Retorna el tipus de missatge
     * @return type indica el tipus de missatge
     */
    public String getType() {
        return type;
    }

    /**
     * Assigna el tipus de missatge
     * @param type indica el tipus de missatge
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Retorna el objecte del missatge
     * @return object indica l'objecte del missatge
     */
    public Object getObject() {
        return object;
    }

    /**
     * Assigna l'objecte del missatge
     * @param object indica l'objecte del missatge
     */
    public void setObject(Object object) {
        this.object = object;
    }

}
