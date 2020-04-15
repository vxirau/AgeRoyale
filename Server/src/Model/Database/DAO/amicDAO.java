package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class amicDAO {
    private statsDAO statsDAO = new statsDAO();
    private tropesDAO tropesDAO = new tropesDAO();

    //OBTENIR INFORMACIO
    public ArrayList<Usuari> getAmics(int idUser){
        ArrayList<Usuari> amics = new ArrayList<>();
        String query = "SELECT us.* FROM AgeRoyale.usuari as us, AgeRoyale.amic as am WHERE (us.idUser = am.id_u1 and " + idUser + " = am.id_u2) OR (us.idUser = am.id_u2 and " + idUser + " = am.id_u1);";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);

        try{
            while (rs.next()){
                Usuari amic = new Usuari();
                amic.setIdUsuari(rs.getInt(1));
                amic.setNickName(rs.getString(2));
                amic.setEmail(rs.getString(3));
                amic.setPassword(rs.getString(4));
                amic.setStats(statsDAO.getStatsFromStatsId(rs.getInt(5)));
                amic.setTropes(tropesDAO.getTropesFromUserId(idUser));

                //TODO: gestionar amics dels amics : de moment no existeixen

                amics.add(amic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amics;
    }

    public ArrayList<Usuari> getAmics(Usuari user){
        ArrayList<Usuari> amics = new ArrayList<>();
        String query = "SELECT us.* FROM AgeRoyale.usuari as us, AgeRoyale.amic as am WHERE (us.idUser = am.id_u1 and " + user.getIdUsuari() + " = am.id_u2) OR (us.idUser = am.id_u2 and " + user.getIdUsuari() + " = am.id_u1);";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);

        try{
            while (rs.next()){
                Usuari amic = new Usuari();
                amic.setIdUsuari(rs.getInt(1));
                amic.setNickName(rs.getString(2));
                amic.setEmail(rs.getString(3));
                amic.setPassword(rs.getString(4));
                amic.setStats(statsDAO.getStatsFromStatsId(rs.getInt(5)));
                amic.setTropes(tropesDAO.getTropesFromUserId(amic.getIdUsuari()));

                //TODO: gestionar amics dels amics : de moment no existeixen

                amics.add(amic);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amics;
    }

    //AFEGIR INFORMACIO
    public void addAmic (int idUser, int idAmic){
        String query = "INSERT INTO AgeRoyale.amic (id_u1, id_u2) VALUE (" + idUser + ", " + idAmic + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    public void addAmic (Usuari usuari, Usuari amic){
        String query = "INSERT INTO AgeRoyale.amic (id_u1, id_u2) VALUE (" + usuari.getIdUsuari() + ", " + amic.getIdUsuari() + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    public void addAmic (int idUsuari, ArrayList<Usuari> amics){
        for (Usuari usr: amics) {
            addAmic( idUsuari, usr.getIdUsuari());
        }
    }

    public void addAmic (Usuari usuari, ArrayList<Usuari> amics){
        for (Usuari usr: amics) {
            addAmic(usuari, usr);
        }
    }

    //BORRAR INFORMACIO
    public void removeAmic (int idUser, int idAmic){
        String query = "DELETE FROM AgeRoyale.amic WHERE (" + idUser + " = id_u1 AND " + idAmic + " = id_u2) OR (" + idAmic + " = id_u1 and " + idUser + " = id_u2);";
        DBConnector.getInstance().deleteQuery(query);
    }

    public void removeAmic (Usuari usuari, Usuari amic){
        String query = "DELETE FROM AgeRoyale.amic WHERE (" + usuari.getIdUsuari() + " = id_u1 AND " + amic.getIdUsuari() + " = id_u2) OR (" + amic.getIdUsuari() + " = id_u1 and " + amic.getIdUsuari() + " = id_u2);";
        DBConnector.getInstance().deleteQuery(query);
    }

    public void removeAmics (int idUser){
        String query = "DELETE FROM AgeRoyale.amic WHERE (" + idUser + " = id_u1 OR " + idUser + " = id_u2);";
        DBConnector.getInstance().deleteQuery(query);
    }

    public void removeAmics (Usuari usuari){
        String query = "DELETE FROM AgeRoyale.amic WHERE (" + usuari.getIdUsuari() + " = id_u1 OR " + usuari.getIdUsuari() + " = id_u2);";
        DBConnector.getInstance().deleteQuery(query);
    }
}
