package src.Model.Database;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import src.NetworkConfiguration;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Classe que correspon a la connexió amb la base de dades
 */
public class DBConnector {

    private String url = NetworkConfiguration.staticDPip;
    public Connection conn;
    private static Statement s;
    private static DBConnector instance;

    /**
     * Contructor de la classe
     */
    private DBConnector() {
        this.instance = this;
    }

    /**
     * C
     * @return
     */
    public static DBConnector getInstance(){
        if(instance == null){
            instance = new DBConnector();
            instance.connect();
        }
        return instance;
    }


    /**
     * Es connecta amb la base de dades de mySQl
     */
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

    /**
     * Query que serveix per a inserir dades en una taula
     * @param query query a realitzar
     */
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

    /**
     * realitza la query d'inserir
     * @param preparedStatement serveix per a "precompila" la query
     */
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

    /**
     * Query que actualitza una taula
     * @param query sentència de la query
     */
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

    /**
     * Query que elimina alguna dada d'una taula
     * @param query sentència de la query
     */
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

    /**
     * Query que filtra informació d'una taula
     * @param query sentència de la query
     * @return retorna el resultat de la búsqueda de la query
     */
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

    /**
     * Es desconnecta de la base de dades
     */
    public void disconnect(){
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println("Problema al tancar la connexió --> " + e.getSQLState());
        }
    }

}