package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe que representa els amics de cada usuari
 */
public class amicDAO {

    /**
     * retorna els amics d'un usuari pel seu id
     * @param idUser id de l'usuari
     * @return amics retorna la llista d'amics d'aquell usuari
     */
    //OBTENIR INFORMACIO
    public ArrayList<Usuari> getAmics(int idUser){
        statsDAO statsDAO = new statsDAO();
        tropesDAO tropesDAO = new tropesDAO();
        ArrayList<Usuari> amics = new ArrayList<>();

        String query = "SELECT us.* FROM AgeRoyale.usuari as us, AgeRoyale.amic as am WHERE (us.idUser = am.id_u1 and " + idUser + " = am.id_u2) OR (us.idUser = am.id_u2 and " + idUser + " = am.id_u1);";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);

        try{
            while (rs.next()){
                Usuari amic = new Usuari();
                amic.setIdUsuari(rs.getInt("idUser"));
                amic.setNickName(rs.getString("nickname"));
                amic.setEmail(rs.getString("email"));
                amic.setPassword(rs.getString("password"));
                amic.setTropes(tropesDAO.getTropesFromUserId(amic.getIdUsuari()));
                amic.setStats(statsDAO.getStatsFromStatsId(rs.getInt("idStats")));
                amic.setOnline(rs.getBoolean("isOnline"));

                amics.add(amic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amics;
    }

    /**
     * retorna els amics d'un usuari
     * @param user indica l'usuari que té els amics
     * @return amics retorna la llista d'amics de l'usuari
     */
    public ArrayList<Usuari> getAmics(Usuari user){
        statsDAO statsDAO = new statsDAO();
        tropesDAO tropesDAO = new tropesDAO();
        ArrayList<Usuari> amics = new ArrayList<>();

        String query = "SELECT us.* FROM AgeRoyale.usuari as us, AgeRoyale.amic as am WHERE (us.idUser = am.id_u1 and " + user.getIdUsuari() + " = am.id_u2) OR (us.idUser = am.id_u2 and " + user.getIdUsuari() + " = am.id_u1);";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);

        try{
            while (rs.next()){
                Usuari amic = new Usuari();
                amic.setIdUsuari(rs.getInt("idUser"));
                amic.setNickName(rs.getString("nickname"));
                amic.setEmail(rs.getString("email"));
                amic.setPassword(rs.getString("password"));
                amic.setTropes(tropesDAO.getTropesFromUserId(amic.getIdUsuari()));
                amic.setStats(statsDAO.getStatsFromStatsId(rs.getInt("idStats")));
                amic.setOnline(rs.getBoolean("isOnline"));

                amics.add(amic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amics;
    }

    /**
     * S'afegeix un amic a la base de dades pels seus id's
     * @param idUser id de l'usuari
     * @param idAmic id de l'amic a afegir
     */
    //AFEGIR INFORMACIO
    public void addAmic (int idUser, int idAmic){
        String query = "INSERT INTO AgeRoyale.amic (id_u1, id_u2) VALUE (" + idUser + ", " + idAmic + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    /**
     * S'afegeix un amic a la base de dades
     * @param usuari indica l'usuari al que se li afegeix l'amic
     * @param amic representa l'amic a afegir
     */
    public void addAmic (Usuari usuari, Usuari amic){
        String query = "INSERT INTO AgeRoyale.amic (id_u1, id_u2) VALUE (" + usuari.getIdUsuari() + ", " + amic.getIdUsuari() + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    /**
     * S'afegeix un amic a la base de dades
     * @param idUsuari id del usuari a afegir
     * @param amics llista dels amics de l'usuari
     */
    public void addAmic (int idUsuari, ArrayList<Usuari> amics){
        for (Usuari usr: amics) {
            addAmic( idUsuari, usr.getIdUsuari());
        }
    }

    /**
     * S'afegeix un amic a la base de dades
     * @param usuari usuari a afegir
     * @param amics llista dels amics de l'usuari
     */
    public void addAmic (Usuari usuari, ArrayList<Usuari> amics){
        for (Usuari usr: amics) {
            addAmic(usuari, usr);
        }
    }

    /**
     * Eliminar un amic
     * @param usuari usuari al que se li elimina una amistat
     * @param amic usuari que s'elimina d'amistat
     */
    //BORRAR INFORMACIO
    public void removeAmic (Usuari usuari, Usuari amic){
        String query = "DELETE FROM AgeRoyale.amic WHERE (" + usuari.getIdUsuari() + " = id_u1 AND " + amic.getIdUsuari() + " = id_u2) OR (" + amic.getIdUsuari() + " = id_u1 and " + usuari.getIdUsuari() + " = id_u2);";
        DBConnector.getInstance().deleteQuery(query);
    }

    /**
     * Elimar totts els amics d'un usuari
     * @param usuari usuari al que se li eliminen els amics
     */
    public void removeAmics (Usuari usuari){
        String query = "DELETE FROM AgeRoyale.amic WHERE (" + usuari.getIdUsuari() + " = id_u1 OR " + usuari.getIdUsuari() + " = id_u2);";
        DBConnector.getInstance().deleteQuery(query);
    }
}
