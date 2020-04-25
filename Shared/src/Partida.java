package src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Partida implements Serializable {
    private int idPartida;
    private Date data;
    private int duracio;
		private boolean publiques;
		private String name;
		private String host;
    private ArrayList<Usuari> jugadors;
    private ArrayList<Usuari> espectadors;

    public Partida() {

    }

    public Partida(int idPartida, boolean publiques, String nom, String host) {
        this.idPartida = idPartida;
        this.publiques = publiques;
        this.name = nom;
		this.host = host;
    }

    public Partida(int idPartida, Date data, int duracio, ArrayList<Usuari> jugadors, ArrayList<Usuari> espectadors) {
        this.idPartida = idPartida;
        this.data = data;
        this.duracio = duracio;
        this.jugadors = jugadors;
        this.espectadors = espectadors;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public boolean isPublic() {
        return publiques;
    }

    public void setPubliques(boolean publiques) {
        this.publiques = publiques;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getDuracio() {
        return duracio;
    }

    public void setDuracio(int duracio) {
        this.duracio = duracio;
    }

    public ArrayList<Usuari> getJugadors() {
        return jugadors;
    }

    public void setJugadors(ArrayList<Usuari> jugadors) {
        this.jugadors = jugadors;
    }

    public void setJugadors(Usuari jugador1, Usuari jugador2){
        if(this.jugadors == null) {
            this.jugadors = new ArrayList<>();
        }
        this.jugadors.add(jugador1);
        this.jugadors.add(jugador2);
    }

    public ArrayList<Usuari> getEspectadors() {
        return espectadors;
    }

    public void setEspectadors(ArrayList<Usuari> espectadors) {
        this.espectadors = espectadors;
    }
}
