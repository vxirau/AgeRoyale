package src;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objecte on es guarda tota la informació de l'usuari
 * */
public class Usuari implements Serializable {
    private int idUsuari;
    private String nickName;
    private String email;
    private String password;
    private Stats stats;
    private Boolean accepted;
    private ArrayList<Tropa> tropes;
    private ArrayList<Usuari> amics;
    private boolean isOnline;

    /**
     * Constructor de la classe Usuari
     * */
    public Usuari() {
    }

    /**
     * Constructor sobrecarregat de la classe Usuari
     * @param idUsuari representa el id del usuari
     * @param nickName representa el nom del usuari
     * @param email representa l'email del usuari
     * @param password representa la contrasenya del usuari
     * @param stats representa les estadístiques de l'usuari
     * @param tropes representa les tropes de l'usuari
     * @param amics representa els amics de l'usuari
     * @param isOnline representa si l'usuari està online
     * */
    public Usuari(int idUsuari, String nickName, String email, String password, Stats stats, ArrayList<Tropa> tropes, ArrayList<Usuari> amics, boolean isOnline) {
        this.idUsuari = idUsuari;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.stats = stats;
        this.tropes = tropes;
        this.amics = amics;
        this.isOnline = isOnline;
    }

    /**
     * Constructor sobrecarregat de la classe Usuari
     * @param idUsuari representa el id del usuari
     * @param nickName representa el nom del usuari
     * @param email representa l'email del usuari
     * @param password representa la contrasenya del usuari
     * @param stats representa les estadístiques de l'usuari
     * @param tropes representa les tropes de l'usuari
     * @param amics representa els amics de l'usuari
     * */
    public Usuari(int idUsuari, String nickName, String email, String password, Stats stats, ArrayList<Tropa> tropes, ArrayList<Usuari> amics) {
        this.idUsuari = idUsuari;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.stats = stats;
        this.tropes = tropes;
        this.amics = amics;
    }

    /**
     * Constructor sobrecarregat de la classe Usuari
     * @param nickName representa el nom del usuari
     * @param password representa la contrasenya del usuari
     * */
    public Usuari(int id, String nickName, String password) {
        this.idUsuari = id;
        this.amics = amics;
        this.nickName = nickName;
    }

    /**
     * Constructor sobrecarregat de la classe Usuari
     * @param nickName representa el nom del usuari
     * @param password representa la contrasenya del usuari
     * @param amics representa els amics de l'usuari
     * */
    public Usuari(int id, String nickName, String password, ArrayList<Usuari> amics) {
        this.idUsuari = id;
        this.amics = amics;
        this.nickName = nickName;
        this.password = password;
    }

    /**
     * Constructor sobrecarregat de la classe Usuari
     * @param nickName representa el nom del usuari
     * @param password representa la contrasenya del usuari
     * */
    public Usuari(String nickName, String password) {
        this.nickName = nickName;
        this.password = password;
    }

    /**
     * Constructor sobrecarregat de la classe Usuari
     * @param nickName representa el nom del usuari
     * @param email representa l'email del usuari
     * @param password representa la contrasenya del usuari
     * */
    public Usuari(String nickName, String email, String password){
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }

    /**
     * Retorna l'id de l'usuari
     * @return idUsuari representa l'id de l'usuari
     * */
    public int getIdUsuari() {
        return idUsuari;
    }

    /**
     * Assigna l'id de l'usuari
     * @param idUsuari representa l'id de l'usuari
     * */
    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
    }

    /**
     * Retorna el nom de l'usuari
     * @return nickName representa el nom de l'usuari
     * */
    public String getNickName() {
        return nickName;
    }

    /**
     * Assigna el nom de l'usuari
     * @param  nickName representa el nom de l'usuari
     * */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Retorna l'email de l'usuari
     * @return email representa l'email de l'usuari
     * */
    public String getEmail() {
        return email;
    }

    /**
     * Assigna l'email de l'usuari
     * @param  email representa l'email de l'usuari
     * */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna la contrasenya de l'usuari
     * @return password representa la contrasenya de l'usuari
     * */
    public String getPassword() {
        return password;
    }

    /**
     * Assigna la contrasenya de l'usuari
     * @param  password representa la contrasenya de l'usuari
     * */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retorna les estadístiques de l'usuari
     * @return stats representa les estadístiques de l'usuari
     * */
    public Stats getStats() {
        return stats;
    }

    /**
     * Assigna les estadístiques de l'usuari
     * @param stats representa les estadístiques de l'usuari
     * */
    public void setStats(Stats stats) {
        this.stats = stats;
    }

    /**
     * Retorna les tropes de l'usuari
     * @return tropes representa les tropes de l'usuari
     * */
    public ArrayList<Tropa> getTropes() {
        return tropes;
    }

    /**
     * Assigna les tropes de l'usuari
     * @param tropes representa les tropes de l'usuari
     * */
    public void setTropes(ArrayList<Tropa> tropes) {
        this.tropes = tropes;
    }

    /**
     * Retorna esl amics de l'usuari
     * @return amics representa els amics de l'usuari
     * */
    public ArrayList<Usuari> getAmics() {
        return amics;
    }

    /**
     * Assigna els amics de l'usuari
     * @param  amics representa els amics de l'usuari
     * */
    public void setAmics(ArrayList<Usuari> amics) {
        this.amics = amics;
    }

    /**
     * Verifica si l'usuari està online
     * @return isOnline representa si l'usuari està online
     * */
    public boolean isOnline() {
        return isOnline;
    }

    /**
     * Assigna si l'usuari està online o no
     * @param  online representa si l'usuari està online o no
     * */
    public void setOnline(boolean online) {
        isOnline = online;
    }

    /**
     * Verifica si l'usuari és acceptat
     * @return accepted representa si l'usuari ha estat acceptat o no
     * */
    public Boolean isAccepted() {
        return accepted;
    }

    /**
     * Assigna si l'usuari és acceptat o no
     * @param  accepted representa si l'usuari està acceptat o no
     * */
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Usuari{" +
                "idUsuari=" + idUsuari +
                ", nickName='" + nickName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", stats=" + stats +
                ", tropes=" + tropes +
                ", amics=" + amics +
                '}';
    }

}
