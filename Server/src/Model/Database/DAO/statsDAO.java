package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Stats;
import src.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe que representa les estadístiques de l'usuari
 */
public class statsDAO {

    /**
     * Retorna les estadistiques corresponents a un id
     * @param idStat id de les estadistiques
     * @return stat retorna les estadistiques corresponents
     */
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
                stat.setTropaMesUtilitzada(getMostUsedTroop(idStat));
            } else {
                stat = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stat;
    }

    /**
     * Retorna el top d'usuaris
     * @return topUsers retorna la llista dels usuaris top
     */
    public ArrayList<Usuari> getTopUsers(){
        ArrayList<Usuari> topUsers = new ArrayList<>();

        String query = "SELECT * FROM stats as s ORDER BY totalVictories DESC LIMIT 10;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            while (rs.next()) {
                Stats stat = new Stats();
                stat.setIdStat(rs.getInt("idStat"));
                stat.setTotalPartides(rs.getInt("totalPartides"));
                stat.setTotalVictories(rs.getInt("totalVictories"));
                stat.setWinrate(rs.getFloat("winrate"));
                stat.setAvgDurationVictories(rs.getFloat("avgDurationVictories"));
                stat.setTropaMesUtilitzada(getMostUsedTroop(stat.getIdStat()));

                String query2 = "SELECT u.* FROM usuari as u, stats as s WHERE "+ stat.getIdStat() + " = u.idStats ORDER BY s.totalVictories DESC LIMIT 10;";
                ResultSet rs2 = DBConnector.getInstance().selectQuery(query2);
                if (rs2.next()) {
                    Usuari u = new Usuari();
                    u.setStats(stat);
                    u.setIdUsuari(rs2.getInt("idUser"));
                    u.setNickName(rs2.getString("nickname"));
                    u.setEmail(rs2.getString("email"));
                    u.setPassword(rs2.getString("password"));
                    u.setOnline(rs2.getBoolean("isOnline"));
                    topUsers.add(u);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topUsers;
    }

    /**
     * Retorna la tropa més utilitzada
     * @param idStat indica el id de la estadistica
     * @return String retorna la tropa més usada
     */
    public String getMostUsedTroop (int idStat){
        String query = "SELECT CASE GREATEST(Skeleton, Goblin, Wizard, Bomb) WHEN Skeleton THEN 'Skeleton' WHEN Goblin THEN 'Goblin' WHEN Wizard THEN 'Wizard' WHEN Bomb THEN 'Bomb' END AS 'TROPA' FROM AgeRoyale.stats as st WHERE st.idStat = " + idStat +" ;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Actualitza les estadistqiues
     * @param stats estadistiques
     */
    //ACTUALITZR INFORMACIO
    public void updateStats(Stats stats) {
        String query = "UPDATE AgeRoyale.stats SET AgeRoyale.stats.totalPartides = " + stats.getTotalPartides() + ", AgeRoyale.stats.totalVictories = " + stats.getTotalVictories() + ", AgeRoyale.stats.winrate = " + stats.getWinrate() + ", AgeRoyale.stats.avgDurationVictories = " + stats.getAvgDurationVictories() + " WHERE AgeRoyale.stats.idStat = " + stats.getIdStat() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Actualiatza la tropa més usada per part de l'usuari
     * @param idUsuari id de l'usuari
     * @param tropa tropa més usada
     */
    public void updateUsedTroop(int idUsuari, String tropa){
        int count = 0;
        String queryGet = "SELECT st." + tropa + " FROM AgeRoyale.stats as st, AgeRoyale.usuari as us WHERE us.idUser = " + idUsuari + " and us.idStats = st.idStat;";
        ResultSet rs = DBConnector.getInstance().selectQuery(queryGet);

        try{
            if (rs.next()){
                count = rs.getInt(tropa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        count++;
        String query = "UPDATE AgeRoyale.stats as st, AgeRoyale.usuari as us SET st." + tropa + " = " + count + " WHERE us.idUser = " + idUsuari + " and us.idStats = st.idStat";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Afegeix estadistiques (victories, derrotes, etc)
     * @param stat representa les estadistques
     */
    //AFEGIR INFORMACIO
    public void addStats (Stats stat) {
        String query = "INSERT INTO AgeRoyale.stats (idStat, totalPartides, totalVictories, winrate, avgDurationVictories) VALUE (" + stat.getIdStat() + ", " + stat.getTotalPartides() + ", " + stat.getTotalVictories() + ", " + stat.getWinrate() + ", " + stat.getAvgDurationVictories() + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    /**
     * Eliminar informació de l'usuari (estadistiques)
     * @param usuari usuari a la que s'actualitza la informació
     */
    //BORRAR INFORMACIO
    public void removeStats (Usuari usuari){
        String query = "DELETE FROM AgeRoyale.stats where AgeRoyale.usuari.idUser = " + usuari.getIdUsuari() + " AND AgeRoyale.usuari.idStats = AgeRoyale.stats.idStat;";
        DBConnector.getInstance().deleteQuery(query);
    }

    /**
     * Es reseteja la informació
     * @param idStats id de les estadistiques
     */
    //REINICIAR STATS
    public void resetStats (int idStats) {
        String query = "UPDATE AgeRoyale.stats SET AgeRoyale.stats.totalPartides = 0, AgeRoyale.stats.totalVictories = 0, AgeRoyale.stats.winrate = 0, AgeRoyale.stats.avgDurationVictories = 0 WHERE AgeRoyale.stats.idStat = " + idStats + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Genera una PK
     * @return nextPk PK generada
     */
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
