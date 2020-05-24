package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Tropa;
import src.Usuari;

import java.util.ArrayList;

public class usuariTropaDAO {

    //AFEGIR INFORMACIO
    public void addTropaToUsuari(int idUsuari, int idTropa){
        String query = "INSERT INTO AgeRoyale.usuaritropa (idUser, idTropa) VALUES (" + idUsuari + ", " + idTropa + ");";
        DBConnector.getInstance().insertQuery(query);
    }
}
