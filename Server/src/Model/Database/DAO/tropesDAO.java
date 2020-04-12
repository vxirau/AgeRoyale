package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Tropa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class tropesDAO {
    private usuariTropaDAO usuariTropaDAO;

    //OBTENIR INFORMACIO
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

    public Tropa getTropaFromIdTropa (int idTropa){
        Tropa tropa = new Tropa();
        String query = "SELECT tr.* FROM AgeRoyale.tropa as tr WHERE tr.idTropa = " + idTropa + ";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            if (rs.next()) {
                tropa.setIdTropa(rs.getInt("idTropa"));
                tropa.setAtac(rs.getInt("atac"));
                tropa.setVida(rs.getInt("vida"));
                tropa.setCost(rs.getInt("cost"));
                tropa.setOfensiva(rs.getBoolean("tipus"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tropa;
    }

    //ACTUALITZAR INFORMACIO
    public void updateTropa (int idTropa, int newAtac, int newVida, int newCost, int newTipus){
        String query = "UPDATE AgeRoyale.tropa SET AgeRoyale.tropa.atac = " + newAtac +", AgeRoyale.tropa.vida = " + newVida + ", AgeRoyale.tropa.cost = " + newCost + ", AgeRoyale.tropa.tipus = " + newTipus + " WHERE AgeRoyale.tropa.idTropa = " + idTropa + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    public void updateTropa (Tropa tropa){
        String query = "UPDATE AgeRoyale.tropa SET AgeRoyale.tropa.atac = " + tropa.getAtac() +", AgeRoyale.tropa.vida = " + tropa.getVida() + ", AgeRoyale.tropa.cost = " + tropa.getCost() + ", AgeRoyale.tropa.tipus = " + tropa.isOfensiva() + " WHERE AgeRoyale.tropa.idTropa = " + tropa.getIdTropa() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    //AFEGIR INFORMACIO
    public void addTropa (int dany, int vida, int cost, boolean atac){
        String query = "INSERT INTO AgeRoyale.tropa (atac, vida, cost, tipus) VALUE (" + dany + ", " + vida + ", " + cost + ", " + atac +");";
        DBConnector.getInstance().insertQuery(query);
    }

    public void addTropa (Tropa tropa){
        String query = "INSERT INTO AgeRoyale.tropa (atac, vida, cost, tipus) VALUE (" + tropa.getAtac() + ", " + tropa.getVida() + ", " + tropa.getCost() + ", " + tropa.isOfensiva() +");";
        DBConnector.getInstance().insertQuery(query);
    }

    //BORRAR INFORMACIO
    public void removeTropa (int idTropa){
        usuariTropaDAO.onRemoveTropa(idTropa);
        String query = "DELETE FROM AgeRoyale.tropa WHERE idTropa = " + idTropa + ";";
        DBConnector.getInstance().deleteQuery(query);
    }

    public void removeTropa (Tropa tropa){
        usuariTropaDAO.onRemoveTropa(tropa);
        String query = "DELETE FROM AgeRoyale.tropa WHERE idTropa = " + tropa.getIdTropa() + ";";
        DBConnector.getInstance().deleteQuery(query);
    }
}
