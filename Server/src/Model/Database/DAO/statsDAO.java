package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Stats;
import src.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;

public class statsDAO {
    //OBTENIR INFORMACIO
    public Stats getStatsFromStatsId(int idStat) {
        Stats stat = new Stats();
        String query = "SELECT st.* FROM AgeRoyale.stats AS st where idStat = " + idStat +";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            if (rs.next()) {
                stat.setIdStat(rs.getInt("idStat"));
                stat.setTotalPartides(rs.getInt("totalPartides"));
                stat.setTotalVictories(rs.getInt("totalVictories"));
                stat.setWinrate(rs.getFloat("winrate"));
                stat.setAvgDurationVictories(rs.getFloat("avgDurationVictories"));
            } else {
                stat = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stat;
    }

    public Stats getStatsFromUserId(int idUser) {
        Stats stat = new Stats();
        String query = "SELECT st.* FROM AgeRoyale.stats as st, AgeRoyale.usuari as us WHERE us.idUser = " + idUser + " and us.idStats = st.idStat;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            if (rs.next()){
                stat.setIdStat(rs.getInt("idStat"));
                stat.setTotalPartides(rs.getInt("totalPartides"));
                stat.setTotalVictories(rs.getInt("totalVictories"));
                stat.setWinrate(rs.getFloat("winrate"));
                stat.setAvgDurationVictories(rs.getFloat("avgDurationVictories"));
            } else {
                stat = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stat;
    }

    //ACTUALITZR INFORMACIO
    public void updateStats(Stats stats) {
        String query = "UPDATE AgeRoyale.stats SET AgeRoyale.stats.totalPartides = " + stats.getTotalPartides() + ", AgeRoyale.stats.totalVictories = " + stats.getTotalVictories() + ", AgeRoyale.stats.winrate = " + stats.getWinrate() + ", AgeRoyale.stats.avgDurationVictories = " + stats.getAvgDurationVictories() + " WHERE AgeRoyale.stats.idStat = " + stats.getIdStat() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    public void updateStats(int idStat, int totalPartides, int totalVictories, float winrate, int avgDurationVic) {
        String query = "UPDATE AgeRoyale.stats SET AgeRoyale.stats.totalPartides = " + totalPartides + ", AgeRoyale.stats.totalVictories = " + totalVictories + ", AgeRoyale.stats.winrate = " + winrate + ", AgeRoyale.stats.avgDurationVictories = " + avgDurationVic + " WHERE AgeRoyale.stats.idStat = " + idStat + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    //AFEGIR INFORMACIO
    public void addStats (Stats stat) {
        String query = "INSERT INTO AgeRoyale.stats (idStat, totalPartides, totalVictories, winrate, avgDurationVictories) VALUE (" + stat.getIdStat() + ", " + stat.getTotalPartides() + ", " + stat.getTotalVictories() + ", " + stat.getWinrate() + ", " + stat.getAvgDurationVictories() + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    public void addStats (int idStat, int totalPartides, int totalVictories, float winrate, int avgDurationVic) {
        String query = "INSERT INTO AgeRoyale.stats (idStat, totalPartides, totalVictories, winrate, avgDurationVictories) VALUE (" + idStat + ", " + totalPartides + ", " + totalVictories + ", " + winrate + ", " + avgDurationVic + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    //BORRAR INFORMACIO
    public void removeStats (Stats stats){
        String query = "DELETE FROM AgeRoyale.stats WHERE idStat = " + stats.getIdStat() + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

    public void removeStats (int idStats){
        String query = "DELETE FROM AgeRoyale.stats WHERE idStat = " + idStats + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

    public void removeStats (Usuari usuari){
        String query = "DELETE FROM AgeRoyale.stats where AgeRoyale.usuari.idUser = " + usuari.getIdUsuari() + " AND AgeRoyale.usuari.idStats = AgeRoyale.stats.idStat;";
        DBConnector.getInstance().deleteQuery(query);
    }

    //REINICIAR STATS
    public void resetStats (Stats stats) {
        String query = "UPDATE AgeRoyale.stats SET AgeRoyale.stats.totalPartides = 0, AgeRoyale.stats.totalVictories = 0, AgeRoyale.stats.winrate = 0, AgeRoyale.stats.avgDurationVictories = 0 WHERE AgeRoyale.stats.idStat = " + stats.getIdStat() + ";";
        DBConnector.getInstance().updateQuery(query);
    }
    public void resetStats (int idStats) {
        String query = "UPDATE AgeRoyale.stats SET AgeRoyale.stats.totalPartides = 0, AgeRoyale.stats.totalVictories = 0, AgeRoyale.stats.winrate = 0, AgeRoyale.stats.avgDurationVictories = 0 WHERE AgeRoyale.stats.idStat = " + idStats + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    //GESTIO DE PK
    public int nextStatPK(){
        int nextPk = -1;
        String query = "SELECT st.idStat FROM AgeRoyale.stats as st ORDER BY st.idStat DESC LIMIT 1;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                nextPk = rs.getInt("idStat");
            } else {
                nextPk = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ++nextPk;
    }

}
