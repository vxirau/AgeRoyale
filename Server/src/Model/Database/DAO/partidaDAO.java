package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Partida;
import src.Usuari;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe que representa les partides jugades
 */
public class partidaDAO {

    /**
     * Retorna la partida buscada per un id en concret
     * @param idPartida id de la partida
     * @return partida representa la partida corresponent amb el id
     */
    //OBTENIR INFORMACIO
    public Partida getPartida(int idPartida){
        usuariDAO usuariDAO = new usuariDAO();
        spectatorDAO sDAO = new spectatorDAO();
        Partida partida = new Partida();
        String query = "SELECT par.* FROM AgeRoyale.partida AS par WHERE idPartida = " + idPartida + ";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            if (rs.next()){
                partida.setIdPartida(rs.getInt("idPartida"));
                partida.setDuracio(rs.getInt("duration"));
                partida.setData(rs.getString("date"));
                partida.setName(rs.getString("name"));
                partida.setFinished(rs.getBoolean("finished"));
                partida.setHost(rs.getString("host"));
                partida.setPubliques(rs.getBoolean("publica"));
                partida.setJugadors(usuariDAO.getUserFromId(rs.getInt("player1")) , usuariDAO.getUserFromId(rs.getInt("player2")));
                partida.setEspectadors(sDAO.getAllSpectatorInGame(partida));
            } else {
                partida = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partida;
    }

    /**
     * Retorna totes les partides
     * @return partides representa la llista de totes les partides creades
     */
    public ArrayList<Partida> getAllPartides (){
        usuariDAO usuariDAO = new usuariDAO();
        spectatorDAO sDAO = new spectatorDAO();
        ArrayList<Partida> partides = new ArrayList<>();
        String query = "SELECT par.* FROM AgeRoyale.partida AS par;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            while (rs.next()){
                Partida partida = new Partida();
                partida.setIdPartida(rs.getInt("idPartida"));
                partida.setName(rs.getString("name"));
                partida.setHost(rs.getString("host"));
                partida.setPubliques(rs.getBoolean("publica"));
                partida.setDuracio(rs.getInt("duration"));
                partida.setFinished(rs.getBoolean("finished"));
                partida.setData(rs.getString("date"));
                partida.setJugadors(usuariDAO.getUserFromId(rs.getInt("player1")), usuariDAO.getUserFromId(rs.getInt("player2")));
                partida.setEspectadors(sDAO.getAllSpectatorInGame(partida));
                partides.add(partida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partides;
    }

    /**
     * Retorna les partides que estan en joc
     * @return partides representa la llista de les partides en joc
     */
    public ArrayList<Partida> getRunningPartides(){
        usuariDAO usuariDAO = new usuariDAO();
        spectatorDAO sDAO = new spectatorDAO();
        ArrayList<Partida> partides = new ArrayList<>();
        String query = "SELECT par.* FROM AgeRoyale.partida AS par WHERE par.finished = false;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            while (rs.next()){
                Partida partida = new Partida();
                partida.setIdPartida(rs.getInt("idPartida"));
                partida.setName(rs.getString("name"));
                partida.setHost(rs.getString("host"));
                partida.setPubliques(rs.getBoolean("publica"));
                partida.setDuracio(rs.getInt("duration"));
                partida.setFinished(rs.getBoolean("finished"));
                partida.setData(rs.getString("date"));
                partida.setJugadors(usuariDAO.getUserFromId(rs.getInt("player1")), usuariDAO.getUserFromId(rs.getInt("player2")));
                partida.setEspectadors(sDAO.getAllSpectatorInGame(partida));
                partides.add(partida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partides;
    }

    /**
     * Afegeix una partida a la base de dades
     * @param partida partida a afegir
     */
    //AFEGIR INFORMACIO
    public synchronized void addPartida (Partida partida)  {
        int newPartidaPK = nextPartidaPK();
        partida.setIdPartida(newPartidaPK);

        String query = "INSERT INTO AgeRoyale.partida (idPartida, publica, name, host, duration, date, finished)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement preparedStmt = DBConnector.getInstance().conn.prepareStatement(query);

            preparedStmt.setInt (1, partida.getIdPartida());
            preparedStmt.setBoolean (2, partida.isPubliques());
            preparedStmt.setString (3, partida.getName());
            preparedStmt.setString (4, partida.getHost());
            preparedStmt.setInt (5, partida.getDuracio());
            preparedStmt.setString (6, partida.getData());
            preparedStmt.setBoolean (6+1, partida.isFinished());

            DBConnector.getInstance().insertQuery(preparedStmt);

        } catch (SQLException ex) {
            System.out.println("Problema al preparar la insercio de --> " + ex.getSQLState());
        }
    }

    /**
     * Elimina una partida de la base de dades
     * @param user usuari a la que se li elimina la partida
     */
    //BORRAR PARTIDA
    public void removePartida (Usuari user){
        String query = "DELETE FROM AgeRoyale.partida WHERE player1 = " + user.getIdUsuari() + " OR player2 = " + user.getIdUsuari() + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

    /**
     * Generar una PK (serial) per cada partida
     * @return nextPK representa la Pk de la partida
     */
    //GESTIO DE PK
    public int nextPartidaPK(){
        int nextPk = -1;
        String query = "SELECT par.idPartida FROM AgeRoyale.partida as par ORDER BY par.idPartida DESC LIMIT 1;\n";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                nextPk = rs.getInt("idPartida");
            } else {
                nextPk = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ++nextPk;
    }

    /**
     * Afegir segon jugador a la partida
     * @param p partida a la que s'afegeix l'usuari
     * @param usuari usuari a afegir
     */
    public void addPlayerTwo(Partida p, Usuari usuari) {
        String query = "UPDATE AgeRoyale.partida SET AgeRoyale.partida.player2 = "+usuari.getIdUsuari()+" WHERE AgeRoyale.partida.idPartida = " + p.getIdPartida() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Afegir primer jugador a la partida
     * @param p partida a afegir l'usuari
     * @param usuari usuari a afegir
     */
    public void addPlayerOne(Partida p, Usuari usuari) {
        String query = "UPDATE AgeRoyale.partida SET AgeRoyale.partida.player1 = "+usuari.getIdUsuari()+" WHERE AgeRoyale.partida.idPartida = " + p.getIdPartida() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Retorna la partida en la que esta jugant l'usuari
     * @param u usuari que està jugant
     * @return partida retorna la partida en la que està jugant l'usuari
     */
    public Partida userIsPlayer(Usuari u){
        Partida partida = null;
        String query = "SELECT par.idPartida FROM AgeRoyale.partida AS par WHERE par.player1 = " + u.getIdUsuari() + " OR par.player2 = " + u.getIdUsuari() +";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            if (rs.next()){
                partida = getPartida(rs.getInt("idPartida"));
             }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partida;
    }

    /**
     * Elimina un jugador de la partida
     * @param p partida a la que se li elimina un jugador
     * @param usuari usuari que s'elimina
     */
    public void removePlayer(Partida p, Usuari usuari) {
        int playerNum = getPlayerNum(p, usuari);
        String query = "UPDATE AgeRoyale.partida SET AgeRoyale.partida.player"+playerNum+" = "+null+" WHERE AgeRoyale.partida.idPartida = " + p.getIdPartida() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Retorna el número de jugador d'aquell usuari
     * @param p partida en la que està jugant
     * @param usuari usuari que està jugant
     * @return has retorna el número de jugador que és
     */
    private int getPlayerNum(Partida p, Usuari usuari) {
        Integer has=null;
        String query = "SELECT par.player1 FROM AgeRoyale.partida as par WHERE idPartida = " + p.getIdPartida();
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                has = (Integer)rs.getObject("player1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(has!=null && has == usuari.getIdUsuari()){
            has = 1;
        }else{
            has = 2;
        }
        return has;
    }

    /**
     * Comprova si la partida té el primer jugador
     * @param p partida que té el primer jugador
     * @return boolean indica si té el primer jugador
     */
    public boolean hasPlayerOne(Partida p) {
        Integer has = null;
        String query = "SELECT par.player1 FROM AgeRoyale.partida as par WHERE idPartida = " + p.getIdPartida();
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                has = (Integer) rs.getObject("player1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(has == null){
            return false;
        }else{
            return true;
        }
    }
    public Integer getPlayerOne(Partida p){
        Integer has = null;
        String query = "SELECT par.player1 FROM AgeRoyale.partida as par WHERE idPartida = " + p.getIdPartida();
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                has = (Integer) rs.getObject("player1");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return has;
    }

    public Integer getPlayerTwo(Partida p){
        Integer has = null;
        String query = "SELECT par.player2 FROM AgeRoyale.partida as par WHERE idPartida = " + p.getIdPartida();
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                has = (Integer) rs.getObject("player2");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return has;
    }
    /**
     * Comprova si la partida té un segon jugador
     * @param p partida que té el segon jugador
     * @return boolean indica si té el segon jugador
     */
    public boolean hasPlayerTwo(Partida p) {
        Integer has = null;
        String query = "SELECT par.player2 FROM AgeRoyale.partida as par WHERE idPartida = " + p.getIdPartida();
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                has = (Integer) rs.getObject("player2");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(has == null){
            return false;
        }else{
            return true;
        }
    }

    public void updateTime(int idPartida, int time){
        String query = "UPDATE AgeRoyale.partida SET AgeRoyale.partida.duration = " + time + ", AgeRoyale.partida.finished = true WHERE idPartida = " + idPartida  + ";";
        DBConnector.getInstance().updateQuery(query);
    }
}
