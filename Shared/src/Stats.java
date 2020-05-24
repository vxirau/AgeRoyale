package src;

import src.View.TropesView;

import java.io.Serializable;

/**
 * Objecet que representa les estadístiques de cada jugador
 */
public class Stats implements Serializable {
    private int idStat;
    private int totalPartides;
    private int totalVictories;
    private Float winrate;
    private Float avgDurationVictories;
    private String tropaMesUtilitzada;

    /**
     * Constructor de la classe Stats
     */
    public Stats() {

    }

    /**
     * Constructor secundari de la classe Stats
     * @param idStat representa el id de una estadistica
     */
    public Stats(int idStat) {
        this.idStat = idStat;
        this.totalPartides = 0;
        this.totalVictories = 0;
        this.winrate = 0f;
        this.avgDurationVictories = 0f;
    }

    /**
     * Constructor secundari de la classe Stats
     * @param idStat representa el id de una estadistica
     * @param totalPartides indica el total de partides jugades
     * @param totalVictories indica el total de victories
     * @param avgDurationVictories indica la mitjana de la durada de cada partida on s'ha fet una victoria
     */
    public Stats(int idStat, int totalPartides, int totalVictories, Float avgDurationVictories) {
        this.idStat = idStat;
        this.totalPartides = totalPartides;
        this.totalVictories = totalVictories;
        this.setWinrate();
        this.avgDurationVictories = avgDurationVictories;
    }

    /**
     * Constructor secundari de la classe Stats
     * @param idStat representa el id de una estadistica
     * @param totalPartides indica el total de partides jugades
     * @param totalVictories indica el total de victories
     * @param winrate indica el ratio de victories
     * @param avgDurationVictories indica la mitjana de la durada de cada partida on s'ha fet una victoria
     */
    public Stats(int idStat, int totalPartides, int totalVictories, Float winrate, Float avgDurationVictories) {
        this.idStat = idStat;
        this.totalPartides = totalPartides;
        this.totalVictories = totalVictories;
        this.winrate = winrate;
        this.avgDurationVictories = avgDurationVictories;
    }

    /**
     * Retorna el id de la estadistica
     * @return idStat indica el id de la estadistica
     */
    public int getIdStat() {
        return idStat;
    }

    /**
     * Assigna el id de la estadistica
     * @param idStat indica el id de la estadistica
     */
    public void setIdStat(int idStat) {
        this.idStat = idStat;
    }

    /**
     * Retorna el total de partides
     * @return totalPartides indica el total de partides
     */
    public int getTotalPartides() {
        return totalPartides;
    }

    /**
     * Assigna el total de partides
     * @param  totalPartides indica el total de partides
     */
    public void setTotalPartides(int totalPartides) {
        this.totalPartides = totalPartides;
    }

    /**
     * Retorna el total de victories
     * @return totalVictories indica el total de victories
     */
    public int getTotalVictories() {
        return totalVictories;
    }

    /**
     * Assigna el total de victories
     * @param  totalVictories indica el total de victories
     */
    public void setTotalVictories(int totalVictories) {
        this.totalVictories = totalVictories;
    }

    /**
     * Retorna el ratio de victories
     * @return winrate indica el ratio de victories
     */
    public Float getWinrate() {
        return winrate;
    }

    /**
     * Assigna el ratio de victories
     */
    public void setWinrate() {
        if (totalPartides != 0) {
            this.winrate = (Float) (((float) this.totalVictories / (float) this.totalPartides) * (float) 100.0);
        } else {
            this.winrate = 0f;
        }
    }

    /**
     * Assigna el ratio de victories
     * @param winrate indica el ratio de victories
     */
    public void setWinrate(Float winrate){
        this.winrate = winrate;
    }

    /**
     * Retorna la mitjana de temps que ha durat una partida quan ha realitzat una victoria
     * @return avgDurationVictories indica el temps que ha durat una partida quan ha realitzat una victoria
     */
    public Float getAvgDurationVictories() {
        return avgDurationVictories;
    }

    /**
     * Assigna la mitjana de temps que ha durat una partida quan ha realitzat una victoria
     * @param avgDurationVictories indica el temps que ha durat una partida quan ha realitzat una victoria
     */
    public void setAvgDurationVictories(Float avgDurationVictories) {
        this.avgDurationVictories = avgDurationVictories;
    }

    /**
     * Retorna la tropa més utilitzada
     * @return tropaMesUtilitzada tropa mes utilitzada
     */
    public String getTropaMesUtilitzada() {
        return tropaMesUtilitzada;
    }

    /**
     * Assigna la tropa més utilitzada
     * @param tropaMesUtilitzada tropa mes utilitzada
     */
    public void setTropaMesUtilitzada(String tropaMesUtilitzada) {
        this.tropaMesUtilitzada = tropaMesUtilitzada;
    }
}

