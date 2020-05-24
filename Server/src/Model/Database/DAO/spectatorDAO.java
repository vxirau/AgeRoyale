package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Partida;
import src.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe que representa els espectadors de la partida
 */
public class spectatorDAO {

    /**
     * Comprova si l'usuari està espectant la partida
     * @param p partida que esta espectant
     * @param user usuari que pot ser espectador
     * @return is indica si és un espectador o no
     */
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

    /**
     * Afegeix un espectador a la partida
     * @param p partida a la que s'afegeix
     * @param user usuari a afegir
     */
    public void addSpectator(Partida p, Usuari user){
        String query = "INSERT INTO AgeRoyale.espectators (idPartida, idUsuari) VALUE ("+ p.getIdPartida() +", "+user.getIdUsuari()+");";
        DBConnector.getInstance().insertQuery(query);
    }

    /**
     * Retorna tots els espectadors de la partida
     * @param p partida on estan els espectadors
     * @return spectators llista d'usuaris que són espectadors
     */
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

    /**
     * S'elimina un espectador de la partida
     * @param user usuari a eliminar com a espectador
     */
    public void removeSpectator (Usuari user){
        String query = "DELETE FROM AgeRoyale.espectators WHERE AgeRoyale.espectators.idUsuari = " + user.getIdUsuari() + ";";
        DBConnector.getInstance().deleteQuery(query);
    }
}
