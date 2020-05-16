package src;

import src.Controller.RoomsController;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Partida implements Serializable{
    private Integer idPartida;
    private String data;
    private int duracio;
    private boolean publiques;
    private String name;
    private String host;
    private boolean finished;
    private ArrayList<Usuari> jugadors;
    private ArrayList<Usuari> espectadors;
    private static volatile boolean gameIsRunning = false;

    public Partida() {

    }

    public Partida(int idPartida, boolean publiques, String nom, String host) {
        this.idPartida = idPartida;
        this.publiques = publiques;
        this.name = nom;
		this.host = host;
    }

    public Partida(String name, String date, Boolean publiques, String host, ArrayList<Usuari> jugadors, ArrayList<Usuari> espectadors) {
        this.name = name;
        this.data = date;
        this.publiques = publiques;
        this.host = host;
        this.duracio = 0;
        this.jugadors = jugadors;
        this.espectadors = espectadors;
    }

    public Partida(String name, String date, Boolean publiques, String host) {
        this.name = name;
        this.data = date;
        this.publiques = publiques;
        this.host = host;
        this.duracio = 0;
    }

    public Partida(int idPartida, String data, int duracio, ArrayList<Usuari> jugadors, ArrayList<Usuari> espectadors) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
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

    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    public boolean isPubliques() {
        return publiques;
    }

    public void setEspectadors(ArrayList<Usuari> espectadors) {
        this.espectadors = espectadors;
    }

    public void startPartida() throws IOException {
        RoomsController.startGame(0, 0, this, null);
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    /*
    //Actualitzem l'estat de les tropes
    public void update(){
        if(tropes.size() > 0){
            for(int i = 0; i < tropes.size(); i++){
                tropes.get(i).update();
            }
        }

    }*/



}
