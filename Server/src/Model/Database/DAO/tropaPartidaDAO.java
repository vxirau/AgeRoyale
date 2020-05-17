package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Tropa;
import src.Usuari;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class tropaPartidaDAO {

    public ArrayList<Tropa> getTropesPartida (int idPartida){
        ArrayList<Tropa> tropas = new ArrayList<>();
        String query = "SELECT trp.* FROM AgeRoyale.tropapartida as trp WHERE trp.idPartida = " + idPartida + ";";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try{
            while(rs.next()){
                Tropa tropa = new Tropa();
                tropa.setIdTropa(rs.getInt("idTropa"));
                tropa.setIdPartida(rs.getInt("idPartida"));
                tropa.setWhichSprite(rs.getString("sprite"));
                char[] buf = new char[1];
                rs.getCharacterStream("troopDirection").read(buf);
                char direction = buf[0];
                tropa.setTroopDirection(direction);
                tropa.setMoving(rs.getBoolean("isMoving"));
                tropa.setxVariation(rs.getFloat("xVariation"));
                tropa.setyVariation(rs.getFloat("yVariation"));
                tropa.setxPosition(rs.getFloat("xPosition"));
                tropa.setyPosition(rs.getFloat("yPosition"));
                tropa.setAtac(rs.getInt("atac"));
                tropa.setVida(rs.getInt("vida"));
                tropa.setCost(rs.getInt("cost"));
                tropa.setOfensiva(rs.getBoolean("tipus"));
                tropas.add(tropa);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return tropas;
    }

    public void addTropa (Tropa tropa){
        String query = "INSERT INTO AgeRoyale.tropapartida (idPartida, sprite, troopDirection, isMoving, xVariation, yVariation, xPosition, yPosition, atac, vida, cost, tipus) VALUE ( " + tropa.getIdPartida() + ", '" + tropa.getWhichSprite() + "', '" + tropa.getTroopDirection() + "', " + tropa.isMoving() + ", " + tropa.getxVariation() + ", " + tropa.getyVariation() + ", " + tropa.getxPosition() + ", " + tropa.getyPosition() + ", " +  tropa.getAtac() + ", " + tropa.getVida() + ", " + tropa.getCost() + ", " + tropa.isOfensiva() +");";
        DBConnector.getInstance().insertQuery(query);
    }
}
