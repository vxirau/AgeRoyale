package src.Model.Database.DAO;

import src.Model.Database.DBConnector;
import src.Stats;
import src.Usuari;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Classe que representa les solicituds que té un usuari
 */
public class requestsDAO {

    /**
     * Retorna les solicituds que té un usuari en concret
     * @param u usuari que té les solicituds
     * @return allRequests representa les solicituds d'usuaris d'un usuari
     */
    public ArrayList<Usuari> getFriendRequests(Usuari u){
        usuariDAO uDAO = new usuariDAO();
        ArrayList<Usuari> allRequests = new ArrayList<>();
        String query = "SELECT if(COUNT(*) > 0, req.originId, -1) as exist FROM AgeRoyale.requests AS req WHERE destinationId = " + u.getIdUsuari()+ " AND accepted IS NULL;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                int id = rs.getInt("exist");
                if (id != -1){
                    String query2 = "SELECT req.originId, req.accepted FROM AgeRoyale.requests AS req WHERE destinationId = " + u.getIdUsuari() + " AND accepted IS NULL;";
                    ResultSet rs2 = DBConnector.getInstance().selectQuery(query2);
                    while(rs2.next()){
                        Usuari g = uDAO.getUserFromId(rs2.getInt("originId"));
                        g.setAccepted(rs2.getBoolean("accepted"));
                        allRequests.add(g);
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        addRequested(allRequests, u);
        return allRequests;
    }

    /**
     * Afegeix una solicitud a un usuari
     * @param r arraylist que representa les solicituds de l'usuari
     * @param u usuari a qui se li afegeix la solicitud
     */
    public void addRequested(ArrayList<Usuari> r, Usuari u){
        ArrayList<Usuari> allRequests = getRequested(u);
        r.add(new Usuari(-20, "SEPARATOR", ""));
        r.addAll(allRequests);
    }

    /**
     * Retorna una solicitud per part d'un usuari
     * @param u usuari que ha fet la solicitud
     * @return allRequests representa les solicituds realitzades per aquell usuari
     */
    public ArrayList<Usuari> getRequested(Usuari u){
        usuariDAO uDAO = new usuariDAO();
        ArrayList<Usuari> allRequests = new ArrayList<>();
        String query = "SELECT if(COUNT(*) > 0, req.destinationId, -1) as exist FROM AgeRoyale.requests AS req WHERE originId = " + u.getIdUsuari()+ " AND accepted IS NOT NULL;";
        ResultSet rs = DBConnector.getInstance().selectQuery(query);
        try {
            if (rs.next()) {
                int id = rs.getInt("exist");
                if (id != -1){
                    String query2 = "SELECT req.destinationId, req.accepted FROM AgeRoyale.requests AS req WHERE originId = " + u.getIdUsuari() + " AND accepted IS NOT NULL;";
                    ResultSet rs2 = DBConnector.getInstance().selectQuery(query2);
                    while(rs2.next()){
                        Usuari g = uDAO.getUserFromId(rs2.getInt("destinationId"));

                        Boolean b = (Boolean) rs2.getObject("accepted");
                        g.setAccepted(b);
                        allRequests.add(g);
                    }

                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allRequests;
    }

    /**
     * Elimina una solicitud
     * @param u1 usuari destí (qui rep la solicitud)
     * @param u2 usuari origen (qui envia la solictud)
     */
    public void removeRequest(Usuari u1, Usuari u2){
        String query = "DELETE FROM AgeRoyale.requests WHERE originId=" + u2.getIdUsuari() + " AND destinationId =" + u1.getIdUsuari() +";";
        DBConnector.getInstance().deleteQuery(query);
    }

    /**
     * Afegeix una solicitud
     * @param u1 usuari origen (usuari que envia solicitud)
     * @param u2 usuari destí (usuari que rep solicitud)
     */
    public void addRequest(Usuari u1, Usuari u2){
        String query = "INSERT INTO AgeRoyale.requests (originId, destinationId) VALUE ("+ u1.getIdUsuari() + ", "+ u2.getIdUsuari() + ");";
        DBConnector.getInstance().insertQuery(query);
    }

    /**
     * Accepta la solicitud
     * @param u1 usuari destí (usuari que rep solicitud)
     * @param u2 usuari origen (usuari que envia solicitud)
     */
    public void acceptRequest(Usuari u1, Usuari u2){
        String query = "UPDATE AgeRoyale.requests SET AgeRoyale.requests.accepted = 1 WHERE AgeRoyale.requests.destinationId = " + u1.getIdUsuari() + " AND AgeRoyale.requests.originId = " + u2.getIdUsuari() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

    /**
     * Denega una solicitud
     * @param u1 usuari destí (usuari que rep solicitud)
     * @param u2 usuari origen (usuari que envia solicitud)
     */
    public void denyRequest(Usuari u1, Usuari u2){
        String query = "UPDATE AgeRoyale.requests SET AgeRoyale.requests.accepted = 0 WHERE AgeRoyale.requests.destinationId = " + u1.getIdUsuari() + " AND AgeRoyale.requests.originId = " + u2.getIdUsuari() + ";";
        DBConnector.getInstance().updateQuery(query);
    }

} 
