package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Tropa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe que representa la taula de tropes de la base de dades
 */
public class tropesDAO {

    /**
     * Retorna totes les tropes de la base de dades
     * @return tropas llista de tropes
     */
    //OBTENIR
    public ArrayList<Tropa> getAllTropes (){
        ArrayList<Tropa> tropas = new ArrayList<>();
        String query = "SELECT tropa.* FROM AgeRoyale.tropa;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            while(rs.next()){
                Tropa tropa = new Tropa();
                tropa.setIdTropa(rs.getInt("idTropa"));
                tropa.setAtac(rs.getInt("atac"));
                tropa.setVida(rs.getInt("vida"));
                tropa.setCost(rs.getInt("cost"));
                tropa.setOfensiva(rs.getBoolean("tipus"));

                tropas.add(tropa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tropas;
    }

    /**
     * Retorna les tropes d'un usuari, buscant-lo per l'id
     * @param idUser id del usuari
     * @return tropas llista de tropes corresponent amb l'id del usuari
     */
    public ArrayList<Tropa> getTropesFromUserId (Integer idUser) {
        ArrayList<Tropa> tropas = new ArrayList<>();
        String query = "SELECT tr.* FROM AgeRoyale.tropa as tr, AgeRoyale.usuaritropa as ustr WHERE ustr.idUser = " + idUser + " and ustr.idTropa = tr.idTropa;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            while(rs.next()){
                Tropa tropa = new Tropa();
                tropa.setIdTropa(rs.getInt("idTropa"));
                tropa.setAtac(rs.getInt("atac"));
                tropa.setVida(rs.getInt("vida"));
                tropa.setCost(rs.getInt("cost"));
                tropa.setOfensiva(rs.getBoolean("tipus"));

                tropas.add(tropa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tropas;
    }
}
