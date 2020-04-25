package src.Model.Database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import src.Partida;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnector {
    private static String userName;
    private static String password;
    private static String db = "AgeRoyale";
    private static int port = 3306;
    private String url = "jdbc:mysql://localhost";
    private static Connection conn;
    private static Statement s;
    private static DBConnector instance;

    private DBConnector() {
        this.url += ":"+port+"/";
        this.url += db + "?verifyServerCertificate=false&useSSL=false&serverTimezone=UTC";
        DBConnector.userName = "root";
        DBConnector.password = "Codepin1";
        this.instance = this;
    }

    public static DBConnector getInstance(){
        if(instance == null){
            instance = new DBConnector();
            instance.connect();
        }
        return instance;
    }

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Connection");
            conn = (Connection) DriverManager.getConnection(url, userName, password);
            if (conn != null) {
                System.out.println("Connexió a base de dades "+url+" ... Ok");
            }
        }
        catch(SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> "+url);
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public void insertQuery(String query, Object p){
        try {
            if(p!=null){
                Partida part = (Partida)p;
                PreparedStatement preparedStmt = conn.prepareStatement(query);

                preparedStmt.setInt (1, part.getIdPartida());
                preparedStmt.setBoolean (2, part.isPubliques());
                preparedStmt.setString (3, part.getName());
                preparedStmt.setString (4, part.getHost());
                preparedStmt.setInt (5, part.getDuracio());
                preparedStmt.setString (6, part.getData());
                System.out.println(preparedStmt.toString());
                preparedStmt.execute();
            }



        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
        }
    }

    public void updateQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Modificar --> " + ex.getSQLState());
        }
    }

    public void deleteQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
        }

    }

    public ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
        }
        return rs;
    }

    public void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
        }
    }

}