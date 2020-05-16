package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Partida;
import src.Usuari;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class partidaDAO {

    //OBTENIR INFORMACIO
    public Partida getPartida(int idPartida){
        usuariDAO usuariDAO = new usuariDAO();
        Partida partida = new Partida();
        String query = "SELECT par.* FROM AgeRoyale.partida AS par WHERE idPartida = " + idPartida + ";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            if (rs.next()){
                partida.setDuracio(rs.getInt("duration"));
                partida.setData(rs.getString("date"));
                partida.setFinished(rs.getBoolean("finished"));
                partida.setJugadors(usuariDAO.getUserFromId(rs.getInt("player1")), usuariDAO.getUserFromId(rs.getInt("player2")));
            } else {
                partida = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partida;
    }

    public ArrayList<Partida> getPartidesFromUserId (Usuari usuari){
        usuariDAO usuariDAO = new usuariDAO();
        ArrayList<Partida> partides = new ArrayList<>();
        String query = "SELECT par.* FROM AgeRoyale.partida AS par WHERE player1 = " + usuari.getIdUsuari() + " OR player2 = " + usuari.getIdUsuari() + " ;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            while (rs.next()){
                Partida partida = new Partida();
                partida.setIdPartida(rs.getInt("idPartida"));
                partida.setDuracio(rs.getInt("duration"));
                partida.setData(rs.getString("date"));
                partida.setName(rs.getString("name"));
                partida.setFinished(rs.getBoolean("finished"));
                partida.setHost(rs.getString("host"));
                partida.setPubliques(rs.getBoolean("publica"));
                partida.setJugadors(usuariDAO.getUserFromId(rs.getInt("player1")), usuariDAO.getUserFromId(rs.getInt("player2")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partides;
    }

    public ArrayList<Partida> getAllPartides (){
        usuariDAO usuariDAO = new usuariDAO();
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
                partides.add(partida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partides;
    }

    public ArrayList<Partida> getRunningPartides(){
        usuariDAO usuariDAO = new usuariDAO();
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
                partides.add(partida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partides;
    }

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

    //BORRAR PARTIDA
    public void removePartida (Partida partida){
        String query = "DELETE FROM AgeRoyale.partida WHERE idPartida = " + partida.getIdPartida() + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

    public void removePartida (int idPartida){
        String query = "DELETE FROM AgeRoyale.partida WHERE idPartida = " + idPartida + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

    public void removePartida (Usuari user){
        String query = "DELETE FROM AgeRoyale.partida WHERE player1 = " + user.getIdUsuari() + " OR player2 = " + user.getIdUsuari() + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

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

    public void addPlayerTwo(Partida p, Usuari usuari) {
        String query = "UPDATE AgeRoyale.partida SET AgeRoyale.partida.player2 = "+usuari.getIdUsuari()+" WHERE AgeRoyale.partida.idPartida = " + p.getIdPartida() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    public void addPlayerOne(Partida p, Usuari usuari) {
        String query = "UPDATE AgeRoyale.partida SET AgeRoyale.partida.player1 = "+usuari.getIdUsuari()+" WHERE AgeRoyale.partida.idPartida = " + p.getIdPartida() + ";";
        DBConnector.getInstance().updateQuery(query);
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


}
