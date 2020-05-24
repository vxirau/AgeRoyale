package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Stats;
import src.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
                usuari.setOnline(rs.getBoolean("isOnline"));

                usuaris.add(usuari);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuaris;
    }

    public ArrayList<Usuari> getUsersByName(String name){
        tropesDAO tropesDAO = new tropesDAO();
        statsDAO statsDAO = new statsDAO();
        amicDAO amicDAO = new amicDAO();
        ArrayList<Usuari> usuaris = new ArrayList<>();

        String query = "SELECT us.* FROM AgeRoyale.usuari AS us WHERE nickname LIKE '%" + name + "%' OR email LIKE '%" + name + "';";
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
                usuari.setOnline(rs.getBoolean("isOnline"));

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
                usuari.setOnline(rs.getBoolean("isOnline"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuari;
    }

    //ACTUALITZAR INFORMACIO
    public void updateState(Usuari usuari, boolean online){
        String query = "UPDATE AgeRoyale.usuari SET isOnline = " + Boolean.toString(online) + " WHERE idUser = " + usuari.getIdUsuari() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    public void updatePass(Usuari usuari) {
        String query = "UPDATE AgeRoyale.usuari SET password = '"  + usuari.getPassword() + "' WHERE idUser = " + usuari.getIdUsuari() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    public void updateNickEmail (Usuari usuari) {
        String query = "UPDATE AgeRoyale.usuari SET nickname = '"  + usuari.getNickName() + "' , email = '" + usuari.getEmail() + "' WHERE idUser = " + usuari.getIdUsuari() + ";";
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

        if (usuari.getAmics() != null) {
            amicDAO.addAmic(newUserPK, usuari.getAmics());
        }

        String query = "INSERT INTO AgeRoyale.usuari (nickname, email, password, idStats, isOnline, banned) VALUE ('" + usuari.getNickName() + "', '" + usuari.getEmail() + "', '" + usuari.getPassword() + "', " + statPK + ", 0, 0);";
        DBConnector.getInstance().insertQuery(query);

        for (int i = 1; i <= 4 ; i++) {
            usuariTropaDAO.addTropaToUsuari(newUserPK, i);
        }

        return newUserPK;
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

    public void banUser(Usuari u){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        String query = "UPDATE AgeRoyale.usuari SET AgeRoyale.usuari.banned = 1, AgeRoyale.usuari.banDate = '" + dateFormat.format(cal.getTime()) + "'  WHERE AgeRoyale.usuari.idUser = " + u.getIdUsuari() + " AND AgeRoyale.usuari.nickname = '" + u.getNickName() + "';";
        DBConnector.getInstance().updateQuery(query);
    }

    public boolean existsUsuariOnChange(Usuari usuari){
        String query = "SELECT if(COUNT(*) > 1, 1, -1) as exist FROM AgeRoyale.usuari AS us WHERE us.nickname = '" + usuari.getNickName() + "' OR us.email = '" + usuari.getEmail() + "';";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                int result = rs.getInt("exist");
                if (result == 1){
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isBanned(Usuari usr) {
        String query = "SELECT us.banned FROM AgeRoyale.usuari AS us WHERE us.idUser = " + usr.getIdUsuari() + ";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        Boolean r = null;
        try {
            if(rs.next()){
                r = (Boolean) rs.getObject("banned");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(r == false || r == null){
            return false;
        }else{
            return true;
        }
    }

    public String getDateBan(Usuari usr) {
        String query = "SELECT us.banDate FROM AgeRoyale.usuari AS us WHERE us.idUser = " + usr.getIdUsuari() + ";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        String s = "";
        try {
            if(rs.next()){
                s =  rs.getString("banDate");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return s;
    }

    public void unBan(Usuari u) {
        String query = "UPDATE AgeRoyale.usuari SET AgeRoyale.usuari.banned = 0, AgeRoyale.usuari.banDate = " + null + "WHERE AgeRoyale.usuari.idUser = " + u.getIdUsuari() + " AND AgeRoyale.usuari.nickname = '" + u.getNickName() + "';";
        DBConnector.getInstance().updateQuery(query);
    }
}
