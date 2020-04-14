package src;

import java.util.ArrayList;

public class Usuari {
    private int idUsuari;
    private String nickName;
    private String email;
    private String password;
    private Stats stats;
    private ArrayList<Tropa> tropes;
    private ArrayList<Usuari> amics;

    public Usuari() {

    }

    public Usuari(int idUsuari, String nickName, String email, String password, Stats stats, ArrayList<Tropa> tropes, ArrayList<Usuari> amics) {
        this.idUsuari = idUsuari;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.stats = stats;
        this.tropes = tropes;
        this.amics = amics;
    }

    public int getIdUsuari() {
        return idUsuari;
    }

    public void setIdUsuari(int idUsuari) {
        this.idUsuari = idUsuari;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public ArrayList<Tropa> getTropes() {
        return tropes;
    }

    public void setTropes(ArrayList<Tropa> tropes) {
        this.tropes = tropes;
    }

    public ArrayList<Usuari> getAmics() {
        return amics;
    }

    public void setAmics(ArrayList<Usuari> amics) {
        this.amics = amics;
    }
}
