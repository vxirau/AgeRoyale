package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Tropa;
import src.Usuari;

import java.util.ArrayList;

/**
 * Classe que representa la taula de tropa de la base de dades corresponent a un usuari
 */
public class usuariTropaDAO {

    /**
     * Afegeix una tropa en la base de dades del usuari
     * @param idUsuari id del usuari
     * @param idTropa id de la tropa
     */
    //AFEGIR INFORMACIO
    public void addTropaToUsuari(int idUsuari, int idTropa){
        String query = "INSERT INTO AgeRoyale.usuaritropa (idUser, idTropa) VALUES (" + idUsuari + ", " + idTropa + ");";
        DBConnector.getInstance().insertQuery(query);
    }
}
