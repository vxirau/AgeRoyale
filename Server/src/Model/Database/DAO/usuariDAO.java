package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Stats;
import src.Tropa;
import src.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class usuariDAO {
    private usuariTropaDAO usuariTropaDAO;
    private tropesDAO tropesDAO;
    private statsDAO statsDAO;
    private amicDAO amicDAO;
    private partidaDAO partidaDAO;

    //OBTENIR INFORMACIO
    public ArrayList<Usuari> getAllUsers(){
        ArrayList<Usuari> usuaris = new ArrayList<>();
        String query = "SELECT us.* FROM AgeRoyale.usuari AS us;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            while(rs.next()){
                Usuari usuari = new Usuari();
                usuari.setIdUsuari(rs.getInt("idUser"));
                usuari.setNickName(rs.getString("nickname"));
                usuari.setEmail(rs.getString("email"));
                usuari.setPassword(rs.getString("password"));
                usuari.setTropes(tropesDAO.getTropesFromUserId(usuari.getIdUsuari()));
                usuari.setStats(statsDAO.getStatsFromStatsId(rs.getInt("idStats")));
                usuari.setAmics(amicDAO.getAmics(usuari.getIdUsuari()));

                usuaris.add(usuari);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuaris;
    }

    public Usuari getUserFromId(Integer idUser) {
        Usuari usuari = new Usuari();
        String query = "SELECT us.* FROM AgeRoyale.usuari as us WHERE idUser = " + idUser + ";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            if (rs.next()) {
                usuari.setIdUsuari(idUser);
                usuari.setNickName(rs.getString("nickname"));
                usuari.setEmail(rs.getString("email"));
                usuari.setPassword(rs.getString("password"));
                usuari.setTropes(tropesDAO.getTropesFromUserId(usuari.getIdUsuari()));
                usuari.setStats(statsDAO.getStatsFromStatsId(rs.getInt("idStats")));
                usuari.setAmics(amicDAO.getAmics(usuari.getIdUsuari()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuari;
    }

    //ACTUALITZAR INFORMACIO
    public void updateUsuari (Usuari usuari){
        statsDAO.updateStats(usuari.getStats());

        usuariTropaDAO.onRemoveUsuari(usuari);
        usuariTropaDAO.addTropesToUsuari(usuari, usuari.getTropes());

        amicDAO.removeAmics(usuari);
        amicDAO.addAmic(usuari, usuari.getAmics());

        String query = "UPDATE AgeRoyale.usuari SET nickname = '" + usuari.getNickName() + "' and email = '" + usuari.getEmail() + "' and password = '" + usuari.getPassword() + "' WHERE idUser = " + usuari.getIdUsuari() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    //AFEGIR INFORMACIO
    public synchronized int addUser(String nickname, String email, String password, Stats stats, ArrayList<Tropa> tropas, ArrayList<Usuari> amics){
        int newUserPK = nextUserPK();
        int statPK = statsDAO.nextStatPK();

        if (stats != null) {
            stats.setIdStat(statPK);
            statsDAO.addStats(stats);
        } else {
            statsDAO.addStats(new Stats(statPK));
        }

        if (tropas != null)
            usuariTropaDAO.addTropesToUsuari(newUserPK, tropas);

        if (amics != null)
            amicDAO.addAmic(newUserPK, amics);

        String query = "INSERT INTO AgeRoyale.usuari (nickname, email, password, idStats) VALUE ('" + nickname + "', '" + email + "', '" + password + "', " + statPK + ");";
        DBConnector.getInstance().insertQuery(query);

        return newUserPK;
    }

    //BORRAR INFORMACIO
    public void removeUsuari(Usuari usuari){
        amicDAO.removeAmics(usuari);
        usuariTropaDAO.onRemoveUsuari(usuari);
        statsDAO.removeStats(usuari);
        partidaDAO.removePartida(usuari);

        String query = "DELETE FROM AgeRoyale.usuari WHERE idUser = " + usuari.getIdUsuari() + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

    //GESTIO DE PK
    public int nextUserPK(){
        int nextPk = -1;
        String query = "SELECT us.idUser FROM AgeRoyale.usuari as us ORDER BY us.idUser DESC LIMIT 1;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                nextPk = rs.getInt("idUser");
            } else {
                nextPk = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ++nextPk;
    }
}
