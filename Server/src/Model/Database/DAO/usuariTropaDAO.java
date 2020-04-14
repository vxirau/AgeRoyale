package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Tropa;
import src.Usuari;

import java.util.ArrayList;

public class usuariTropaDAO {

    //AFEGIR INFORMACIO
    public void addTropaToUsuari(Usuari usuari, Tropa tropa){
        String query = "INSERT INTO AgeRoyale.UsuariTropa (idUser, idTropa) VALUES (" + usuari.getIdUsuari() + ", " + tropa.getIdTropa() + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    public void addTropaToUsuari(int idUsuari, int idTropa){
        String query = "INSERT INTO AgeRoyale.UsuariTropa (idUser, idTropa) VALUES (" + idUsuari + ", " + idTropa + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    public void addTropesToUsuari(Usuari usuari, ArrayList<Tropa> tropas){
        for (Tropa tropa: tropas) {
            addTropaToUsuari(usuari, tropa);
        }
    }

    public void addTropesToUsuari(int idUsuari, ArrayList<Tropa> tropas){
        for (Tropa tropa: tropas) {
            addTropaToUsuari(idUsuari, tropa.getIdTropa());
        }
    }

    //BORRAR INFORMACIO
    public void onRemoveTropa (int idTropa){
        String query = "DELETE FROM AgeRoyale.usuaritropa WHERE idTropa = " + idTropa + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

    public void onRemoveTropa (Tropa tropa){
        String query = "DELETE FROM AgeRoyale.usuaritropa WHERE idTropa = " + tropa.getIdTropa() + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

    public void onRemoveUsuari (int idUser){
        String query = "DELETE FROM AgeRoyale.usuaritropa WHERE idUser = " + idUser + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

    public void onRemoveUsuari (Usuari usuari){
        String query = "DELETE FROM AgeRoyale.usuaritropa WHERE idUser = " + usuari.getIdUsuari() + ";";
        DBConnector.getInstance().deleteQuery(query);
    }
}
