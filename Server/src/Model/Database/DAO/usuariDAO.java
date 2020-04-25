package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Stats;
import src.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class usuariDAO {

    //OBTENIR INFORMACIO
    public ArrayList<Usuari> getAllUsers(){
        tropesDAO tropesDAO = new tropesDAO();
        statsDAO statsDAO = new statsDAO();
        amicDAO amicDAO = new amicDAO();
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
        tropesDAO tropesDAO = new tropesDAO();
        statsDAO statsDAO = new statsDAO();
        amicDAO amicDAO = new amicDAO();
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
        statsDAO statsDAO = new statsDAO();
        usuariTropaDAO usuariTropaDAO = new usuariTropaDAO();
        amicDAO amicDAO = new amicDAO();

        statsDAO.updateStats(usuari.getStats());

        usuariTropaDAO.onRemoveUsuari(usuari);
        usuariTropaDAO.addTropesToUsuari(usuari, usuari.getTropes());

        amicDAO.removeAmics(usuari);
        amicDAO.addAmic(usuari, usuari.getAmics());

        String query = "UPDATE AgeRoyale.usuari SET nickname = '" + usuari.getNickName() + "' and email = '" + usuari.getEmail() + "' and password = '" + usuari.getPassword() + "' WHERE idUser = " + usuari.getIdUsuari() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    //AFEGIR INFORMACIO
    public synchronized int addUser(Usuari usuari){
        statsDAO statsDAO = new statsDAO();
        usuariTropaDAO usuariTropaDAO = new usuariTropaDAO();
        amicDAO amicDAO = new amicDAO();

        int newUserPK = nextUserPK();
        int statPK = statsDAO.nextStatPK();

        if (usuari.getStats() != null) {
            usuari.getStats().setIdStat(statPK);
            statsDAO.addStats(usuari.getStats());
        } else {
            statsDAO.addStats(new Stats(statPK));
            statsDAO.resetStats(statPK);
        }

        if (usuari.getTropes() != null)
            usuariTropaDAO.addTropesToUsuari(newUserPK, usuari.getTropes());

        if (usuari.getAmics() != null)
            amicDAO.addAmic(newUserPK, usuari.getAmics());

        String query = "INSERT INTO AgeRoyale.usuari (nickname, email, password, idStats) VALUE ('" + usuari.getNickName() + "', '" + usuari.getEmail() + "', '" + usuari.getPassword() + "', " + statPK + ");";
        DBConnector.getInstance().insertQuery(query, null);

        return newUserPK;
    }

    //BORRAR INFORMACIO
    public void removeUsuari(Usuari usuari){
        statsDAO statsDAO = new statsDAO();
        usuariTropaDAO usuariTropaDAO = new usuariTropaDAO();
        amicDAO amicDAO = new amicDAO();
        partidaDAO partidaDAO = new partidaDAO();

        String query = "DELETE FROM AgeRoyale.usuari WHERE idUser = " + usuari.getIdUsuari() + ";";
        DBConnector.getInstance().deleteQuery(query);

        amicDAO.removeAmics(usuari);
        usuariTropaDAO.onRemoveUsuari(usuari);
        statsDAO.removeStats(usuari);
        partidaDAO.removePartida(usuari);
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

    //COMPROVAR EXISTENCIA
    public Usuari existsLogin (Usuari usr){
        String query = "SELECT if(COUNT(*) = 1, us.idUser, -1) as exist FROM AgeRoyale.usuari AS us WHERE us.nickname = '" + usr.getNickName() + "' AND us.password = '" + usr.getPassword() + "';";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                int id = rs.getInt("exist");
                if (id != -1){
                    return getUserFromId(id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Usuari existsLogin (String nickname, String password){
        String query = "SELECT if(COUNT(*) = 1, us.idUser, -1) as exist FROM AgeRoyale.usuari AS us WHERE us.nickname = '" + nickname + "' AND us.password = '" + password + "';";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                int id = rs.getInt("exist");
                if (id != -1){
                    return getUserFromId(id);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean existsRegistre (Usuari usr){
        String query = "SELECT if(COUNT(*) = 1, 1, -1) as exist FROM AgeRoyale.usuari AS us WHERE us.nickname = '" + usr.getNickName() + "' OR us.email = '" + usr.getEmail() + "';";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                int id = rs.getInt("exist");
                if (id == 1){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsRegistre (String nickname, String email){
        String query = "SELECT if(COUNT(*) = 1, 1, -1) as exist FROM AgeRoyale.usuari AS us WHERE us.nickname = '" + nickname + "' OR us.email = '" + email + "';";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                int id = rs.getInt("exist");
                if (id == 1){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
