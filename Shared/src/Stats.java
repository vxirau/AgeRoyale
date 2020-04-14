package src;

import java.io.Serializable;

public class Stats implements Serializable {
    private int idStat;
    private int totalPartides;
    private int totalVictories;
    private Float winrate;
    private Float avgDurationVictories;

    public Stats() {

    }

    public Stats(int idStat) {
        this.idStat = idStat;
        this.totalPartides = 0;
        this.totalVictories = 0;
        this.winrate = 0f;
        this.avgDurationVictories = 0f;
    }

    public Stats(int idStat, int totalPartides, int totalVictories, Float avgDurationVictories) {
        this.idStat = idStat;
        this.totalPartides = totalPartides;
        this.totalVictories = totalVictories;
        this.setWinrate();
        this.avgDurationVictories = avgDurationVictories;
    }

    public Stats(int idStat, int totalPartides, int totalVictories, Float winrate, Float avgDurationVictories) {
        this.idStat = idStat;
        this.totalPartides = totalPartides;
        this.totalVictories = totalVictories;
        this.winrate = winrate;
        this.avgDurationVictories = avgDurationVictories;
    }

    public int getIdStat() {
        return idStat;
    }

    public void setIdStat(int idStat) {
        this.idStat = idStat;
    }

    public int getTotalPartides() {
        return totalPartides;
    }

    public void setTotalPartides(int totalPartides) {
        this.totalPartides = totalPartides;
    }

    public int getTotalVictories() {
        return totalVictories;
    }

    public void setTotalVictories(int totalVictories) {
        this.totalVictories = totalVictories;
    }

    public Float getWinrate() {
        return winrate;
    }

    public void setWinrate() {
        if (totalPartides != 0) {
            this.winrate = (Float) (((float) this.totalVictories / (float) this.totalPartides) * (float) 100.0);
        } else {
            this.winrate = 0f;
        }
    }

    public void setWinrate(Float winrate){
        this.winrate = winrate;
    }

    public Float getAvgDurationVictories() {
        return avgDurationVictories;
    }

    public void setAvgDurationVictories(Float avgDurationVictories) {
        this.avgDurationVictories = avgDurationVictories;
    }
}

