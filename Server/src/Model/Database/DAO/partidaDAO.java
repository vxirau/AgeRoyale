package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Partida;
import src.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class partidaDAO {
    private usuariDAO usuariDAO;
    //OBTENIR INFORMACIO
    public Partida getPartida(int idPartida){
        Partida partida = new Partida();
        String query = "SELECT par.* FROM AgeRoyale.partida AS par WHERE idPartida = " + idPartida + ";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            if (rs.next()){
                partida.setDuracio(rs.getInt("duration"));
                partida.setData(rs.getDate("date"));
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
        ArrayList<Partida> partides = new ArrayList<>();
        String query = "SELECT par.* FROM AgeRoyale.partida AS par WHERE player1 = " + usuari.getIdUsuari() + " OR player2 = " + usuari.getIdUsuari() + " ;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            while (rs.next()){
                Partida partida = new Partida();
                partida.setIdPartida(rs.getInt("idPartida"));
                partida.setDuracio(rs.getInt("duration"));
                partida.setData(rs.getDate("date"));
                partida.setJugadors(usuariDAO.getUserFromId(rs.getInt("player1")), usuariDAO.getUserFromId(rs.getInt("player2")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partides;
    }

    public ArrayList<Partida> getAllPartides (){
        ArrayList<Partida> partides = new ArrayList<>();
        String query = "SELECT par.* FROM AgeRoyale.partida AS par;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            while (rs.next()){
                Partida partida = new Partida();
                partida.setIdPartida(rs.getInt("idPartida"));
                partida.setDuracio(rs.getInt("duration"));
                partida.setData(rs.getDate("date"));
                partida.setJugadors(usuariDAO.getUserFromId(rs.getInt("player1")), usuariDAO.getUserFromId(rs.getInt("player2")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partides;
    }

    //AFEGIR INFORMACIO
    public synchronized void addPatida (Partida partida){
        int newPartidaPK = nextPartidaPK();
        partida.setIdPartida(newPartidaPK);

        String query = "INSERT INTO AgeRoyale.partida (idPartida, duration, date, player1, player2) VALUE (" + partida.getIdPartida() + ", " + partida.getDuracio() + ", '" + partida.getData() + "', " + partida.getJugadors().get(0).getIdUsuari() + ", " + partida.getJugadors().get(1).getIdUsuari() +");";
        DBConnector.getInstance().insertQuery(query);
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
}
