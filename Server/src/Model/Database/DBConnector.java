package src.Model.Database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import src.NetworkConfiguration;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class DBConnector {

    private String url = NetworkConfiguration.staticDPip;
    public Connection conn;
    private static Statement s;
    private static DBConnector instance;

    private DBConnector() {
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
            conn = (Connection) DriverManager.getConnection(NetworkConfiguration.staticDPip, NetworkConfiguration.staticDBUser, NetworkConfiguration.staticDBPass);
            s =(Statement) conn.createStatement();
            s.executeUpdate("USE AgeRoyale;");
            if (conn != null) {
                System.out.println("Connexió a base de dades "+url+" ... Ok");
            }
        }
        catch(SQLException ex) {
            System.out.println("Problema al connecta-nos a la BBDD --> "+url);
            System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getSQLState());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
        catch(ClassNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public void insertQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
            System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getSQLState());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    public void insertQuery(PreparedStatement preparedStatement){
        try {
            System.out.println(preparedStatement.toString());
            preparedStatement.execute();
        } catch (SQLException ex) {
            System.out.println("Problema al Inserir --> " + ex.getSQLState());
            System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getSQLState());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    public void updateQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Modificar --> " + ex.getSQLState());
            System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getSQLState());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }
    }

    public void deleteQuery(String query){
        try {
            s =(Statement) conn.createStatement();
            s.executeUpdate(query);

        } catch (SQLException ex) {
            System.out.println("Problema al Eliminar --> " + ex.getSQLState());
            System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getSQLState());
            System.out.println(Arrays.toString(ex.getStackTrace()));
        }

    }

    public ResultSet selectQuery(String query){
        ResultSet rs = null;
        try {
            s =(Statement) conn.createStatement();
            rs = s.executeQuery (query);

        } catch (SQLException ex) {
            System.out.println("Problema al Recuperar les dades --> " + ex.getSQLState());
            System.out.println(ex.getMessage());
            System.out.println(ex.getErrorCode());
            System.out.println(ex.getSQLState());
            System.out.println(Arrays.toString(ex.getStackTrace()));
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