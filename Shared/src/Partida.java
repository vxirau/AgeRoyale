package src;

import src.Controller.RoomsController;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objecte que representa les dades de la partida
 */
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

    //private GameView gameView;
    private static volatile boolean gameIsRunning = false;

    /**
     * Constructor de la classe partida
     */
    public Partida() {

    }

    /**
     * Constructor secundari de la classe partida
     * @param idPartida indica el id de la partida
     * @param publiques indica les partides publiques que té
     * @param nom indica el nom de la partida
     * @param host indica qui és el host de la partida
     */
    public Partida(int idPartida, boolean publiques, String nom, String host) {
        this.idPartida = idPartida;
        this.publiques = publiques;
        this.name = nom;
		this.host = host;
    }

    /**
     * Constructor secundari de la classe partida
     * @param name indica el nom de la partida
     * @param date indica la data que es va crear la partida
     * @param publiques indica les partides publiques que té
     * @param host indica qui és el host de la partida
     * @param jugadors indica els jugadors de la partida
     * @param espectadors indica els espectadors que té la partida
     *
     */
    public Partida(String name, String date, Boolean publiques, String host, ArrayList<Usuari> jugadors, ArrayList<Usuari> espectadors) {
        this.name = name;
        this.data = date;
        this.publiques = publiques;
        this.host = host;
        this.duracio = 0;
        this.jugadors = jugadors;
        this.espectadors = espectadors;
    }

    /**
     * Constructor secundari de la classe partida
     * @param name indica el nom de la partida
     * @param date indica la data que es va crear la partida
     * @param publiques indica les partides publiques que té
     * @param host indica qui és el host de la partida
     */
    public Partida(String name, String date, Boolean publiques, String host) {
        this.name = name;
        this.data = date;
        this.publiques = publiques;
        this.host = host;
        this.duracio = 0;
        this.idPartida = 10;
    }

    /**
     * Constructor secundari de la classe partida
     * @param idPartida indica el id de la partida
     * @param data indica la data que es va crear la partida
     * @param duracio indica el temps que dura la partida
     * @param jugadors indica els jugadors de cada partida
     * @param espectadors indica els espectadors que té cada partida
     */
    public Partida(int idPartida, String data, int duracio, ArrayList<Usuari> jugadors, ArrayList<Usuari> espectadors) {
        this.idPartida = idPartida;
        this.data = data;
        this.duracio = duracio;
        this.jugadors = jugadors;
        this.espectadors = espectadors;
    }

    /**
     * Retorna el id de la partida
     * @return idPartida indica el id de la partida
     */
    public int getIdPartida() {
        return idPartida;
    }

    /**
     * Retorna si la partida és publica o no
     * @return publiques indica si es publica o no
     */
    public boolean isPublic() {
        return publiques;
    }

    /**
     * Assigna si la partida és publica o no
     * @param publiques indica si es publica o no
     */
    public void setPubliques(boolean publiques) {
        this.publiques = publiques;
    }

    /**
     * Retorna el nom de la partida
     * @return name indica el nom de la partida
     */
    public String getName() {
        return name;
    }

    /**
     * Assigna el nom de la partida
     * @param  name indica el nom de la partida
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna el host de la partida
     * @return host indica el host de la partida
     */
    public String getHost() {
        return host;
    }

    /**
     * Assigna el host de la partida
     * @param  host indica el host de la partida
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * Assigna el id de la partida
     * @param  idPartida indica el id de la partida
     */
    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    /**
     * Retorna la data de creacio de la partida
     * @return data indica la data de la partida
     */
    public String getData() {
        return data;
    }

    /**
     * Assigna la data de creacio de la partida
     * @param  data indica la data de la partida
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     * Retorna la duracio de la partida
     * @return duracio indica la duracio de la partida
     */
    public int getDuracio() {
        return duracio;
    }

    /**
     * Assigna la duracio de la partida
     * @param  duracio indica la duracio de la partida
     */
    public void setDuracio(int duracio) {
        this.duracio = duracio;
    }

    /**
     * Retorna els jugadors de la partida
     * @return jugadors indica els jugadors de la partida
     */
    public ArrayList<Usuari> getJugadors() {
        return jugadors;
    }

    /**
     * Asisgna els jugadors de la partida
     * @param  jugadors indica els jugadors de la partida
     */
    public void setJugadors(ArrayList<Usuari> jugadors) {
        this.jugadors = jugadors;
    }

    /**
     * Assigna els jugadors de la partida
     * @param jugador1 indica el primer jugador de la partida
     * @param jugador2 indica el segon jugador de la partida
     */
    public void setJugadors(Usuari jugador1, Usuari jugador2){
        if(this.jugadors == null) {
            this.jugadors = new ArrayList<>();
        }
        if (jugador1.getIdUsuari() != 0) this.jugadors.add(jugador1);
        if (jugador2.getIdUsuari() != 0) this.jugadors.add(jugador2);
    }

    /**
     * Retorna els espectadors de la partida
     * @return  espectadors indica els espectadors de la partida
     */
    public ArrayList<Usuari> getEspectadors() {
        return espectadors;
    }

    /**
     * Assigna el id de la partida
     * @param idPartida indica l'id de la partida
     */
    public void setIdPartida(Integer idPartida) {
        this.idPartida = idPartida;
    }

    /**
     * Indica si les partides son publiques
     * @return  publiques indica si les partides son publiques
     */
    public boolean isPubliques() {
        return publiques;
    }

    /**
     * Assigna els espectadors de la partida
     * @param espectadors indica els espectadors de la partida
     */
    public void setEspectadors(ArrayList<Usuari> espectadors) {
        this.espectadors = espectadors;
    }

    /**
     * Indica si la partida ha finalitzat
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Asisgna si la partida ha finalitzat
     * @param finished indica si la partida ha acabat
     */
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
