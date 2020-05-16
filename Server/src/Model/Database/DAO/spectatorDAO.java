package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Partida;
import src.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class spectatorDAO {


    public boolean isSpectating(Partida p, Usuari user){
        boolean is = false;
        String query = "SELECT es.* FROM AgeRoyale.espectators as es WHERE es.idPartida = "+ p.getIdPartida() +" AND es.idUsuari = " + user.getIdUsuari() + ";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if(rs.next()){
                is = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return is;
    }

    public void addSpectator(Partida p, Usuari user){
        String query = "INSERT INTO AgeRoyale.espectators (idPartida, idUsuari) VALUE ("+ p.getIdPartida() +", "+user.getIdUsuari()+");";
        DBConnector.getInstance().insertQuery(query);
    }

    public ArrayList<Usuari> getAllSpectatorInGame(Partida p){
        ArrayList<Usuari> spectators = new ArrayList<>();
        usuariDAO uDAO  = new usuariDAO();
        String query = "SELECT es.idUsuari FROM AgeRoyale.espectators as es WHERE es.idPartida = "+ p.getIdPartida() +";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);

        try{
            while (rs.next()){
                spectators.add(uDAO.getUserFromId(rs.getInt("idUsuari")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return spectators;
    }

    public void removeSpectator (Usuari user){
        String query = "DELETE FROM AgeRoyale.espectators WHERE AgeRoyale.espectators.idUsuari = " + user.getIdUsuari() + ";";
        DBConnector.getInstance().deleteQuery(query);
    }
}
