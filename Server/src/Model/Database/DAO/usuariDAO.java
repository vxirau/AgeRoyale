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

/**
 * Classe que representa la taula usuari de la base de dades
 */
public class usuariDAO {

    /**
     * Funció que retorna tots els usuaris de la base de dades
     * @return usuaris representa la llista de tots els usuaris
     */
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

    /**
     * Retorna els usuaris de la base de dades cercant-los per un nom
     * @param name nom corresponent a l'usuari
     * @return usuaris retorna la llista d'usuaris corresponent amb el nom
     */
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

    /**
     * Retorna un usuari corresponent al id que li passem per referència
     * @param idUser id del usuari
     * @return usuari retorna el usuari que correspon amb el id
     */
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

    /**
     * Actualiza l'estat del usuari
     * @param usuari usuari a actualitzar
     * @param online indica si l'usuari està online o no
     */
    //ACTUALITZAR INFORMACIO
    public void updateState(Usuari usuari, boolean online){
        String query = "UPDATE AgeRoyale.usuari SET isOnline = " + Boolean.toString(online) + " WHERE idUser = " + usuari.getIdUsuari() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Actualitza la contrasenya d'un usuari
     * @param usuari usuari que vol actualitzar la contrasenya
     */
    public void updatePass(Usuari usuari) {
        String query = "UPDATE AgeRoyale.usuari SET password = '"  + usuari.getPassword() + "' WHERE idUser = " + usuari.getIdUsuari() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Actualitza el nickname i el email d'un usuari
     * @param usuari usuari a actualitzar
     */
    public void updateNickEmail (Usuari usuari) {
        String query = "UPDATE AgeRoyale.usuari SET nickname = '"  + usuari.getNickName() + "' , email = '" + usuari.getEmail() + "' WHERE idUser = " + usuari.getIdUsuari() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Afegeix un usuari a la base de dades
     * @param usuari usuari a afegir
     * @return newUserPk retorna la Pk del usuari
     */
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

    /**
     * Genera una PK per un usuari
     * @return nextPk representa la PK (tipus serial) del usuari
     */
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

    /**
     * Comprova si existeix un usuari
     * @param usr usuari a comprovar
     * @return Usuari retorna l'usuari en cas d'existir, i sinó retorna null
     */
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

    /**
     * Comprova si existeix un usuari pel seu nickname i contrasenya
     * @param nickname nom de l'usuari
     * @param password contrasenya de l'usuari
     * @return Usuari retorna l'usuari en cas d'existir, i sinó retorna null
     */
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

    /**
     * Comprova si ja existeix aquell registre, en cas de que l'usuari ja s'hagi registrat anteriorment
     * @param usr usuari a comprovar
     * @return boolean indica si ja s'ha registrat o no
     */
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

    /**
     * Es bannejar a l'usuari, es prohibeix l'accés a la app durant 24 hores
     * @param u usuari a bannejar
     */
    public void banUser(Usuari u){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        String query = "UPDATE AgeRoyale.usuari SET AgeRoyale.usuari.banned = 1, AgeRoyale.usuari.banDate = '" + dateFormat.format(cal.getTime()) + "'  WHERE AgeRoyale.usuari.idUser = " + u.getIdUsuari() + " AND AgeRoyale.usuari.nickname = '" + u.getNickName() + "';";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Comprova si ja exiteix un usuari amb un nickname o email
     * @param usuari usuari a comprovar
     * @return boolean indica si existeix un usuari amb aquell nickname o email
     */
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

    /**
     * Comprova si aquell usuari està bannejat
     * @param usr usuari a comprovar
     * @return boolean indica si està bannejat o no
     */
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

    /**
     * Retorna la data en la que va estar bannejat aquell usuari
     * @param usr usuari bannejat
     * @return s representa la data en la que va estra bannejat
     */
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

    /**
     * Desbanneja a un usuari, li permet el accés a la app després de 24 hores
     * @param u usuari a desbannejar
     */
    public void unBan(Usuari u) {
        String query = "UPDATE AgeRoyale.usuari SET AgeRoyale.usuari.banned = 0, AgeRoyale.usuari.banDate = " + null + "WHERE AgeRoyale.usuari.idUser = " + u.getIdUsuari() + " AND AgeRoyale.usuari.nickname = '" + u.getNickName() + "';";
        DBConnector.getInstance().updateQuery(query);
    }
}
