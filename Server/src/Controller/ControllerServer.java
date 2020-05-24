package src.Controller;

import src.Model.Database.DAO.partidaDAO;
import src.Model.Database.DAO.statsDAO;
import src.Model.Database.DAO.usuariDAO;
import src.Model.Network.Server;
import src.Partida;
import src.Usuari;
import src.View.ViewServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * Aquesta classe és el controller destinat a la vista del server.
 */
public class ControllerServer implements ActionListener, WindowListener {

    private Server server;
    private static ViewServer view;
    private boolean flag;

    /**
     * Contructor de la classe
     * @param view vista del server
     */
    public ControllerServer(ViewServer view) {
        this.view = view;
        statsDAO sDAO = new statsDAO();
        partidaDAO aDAO = new partidaDAO();
        ArrayList<Usuari> r = sDAO.getTopUsers();
        ArrayList<Partida> p = aDAO.getAllPartides();
        view.setAllGames(p);
        view.setTableContents(r);
        view.initAll();
    }

    /**
     * Funció que mostra les dades desitjades segons on s'ha premut
     * @param e indica on s'ha premut
     */
    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getSource() instanceof  JComboBox){
            JComboBox cb = (JComboBox)e.getSource();
            String item = (String)cb.getSelectedItem();
            view.setDades(true);
            int index=0;
            if(item.equals("Mes")){
                index = 1;
            }else if(item.equals("Any")){
                index = 2;
            }
            JTabbedPane p = view.getTabbedPane();
            p.removeTabAt(2);
            p.addTab("Partides Jugades", view.makeGraph(index));
            p.setSelectedIndex(2);
            view.refresh(p);
        }else if(e.getSource() instanceof  JButton){
            //String botoClicked = ((JButton)e.getSource()).getText();
            //if(botoClicked.equals("Start")){
            if (!flag) {
                this.server = new Server(view);
                JOptionPane.showMessageDialog(view, "Server started!", "Server", JOptionPane.PLAIN_MESSAGE);
                server.startServer();
            }
            flag = true;
            //}
            /*else if(botoClicked.equals("Stop")){
                server.stopServer();
            }*/
        }



    }

    /**
     * Funcio que actualitza la taula de les estadistidiques
     */
    public static void updateTable(){
        statsDAO sDAO = new statsDAO();
        partidaDAO aDAO = new partidaDAO();
        ArrayList<Usuari> r = sDAO.getTopUsers();
        ArrayList<Partida> p = aDAO.getAllPartides();
        view.setAllGames(p);
        view.setTableContents(r);
        view.initAll();
        JTabbedPane h = view.getTabbedPane();
        h.removeTabAt(2);
        h.addTab("Partides Jugades", view.makeGraph(0));
        h.setSelectedIndex(2);
        view.refresh(h);
    }

    /**
     * Funció que comprova el login
     * @param name nom de l'usuari
     * @param password contrasenya de l'usuari
     * @return int si és 1 vol dir que existeix i si és 2 no exiteix
     */
    public static int checkLogin(String name, String password) {
        usuariDAO u = new usuariDAO();
        //Existeix el login i la contrasenya
        if (u.existsLogin(name, password) != null) {
            return 1;
        }
        return 2;
    }

    /**
     * --
     */
    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * Quan s'esta tancant una finestra
     * @param e finestra que es tanca
     */
    @Override
    public void windowClosing(WindowEvent e) {
        if(server.isOn())    server.stopServer();
    }

    /**
     * Es tanca per complet una finestra
     * @param e finestra que es tanca
     */
    @Override
    public void windowClosed(WindowEvent e) {
        if(server.isOn())    server.stopServer();

    }

    /**
     * --
     */
    @Override
    public void windowIconified(WindowEvent e) {

    }

    /**
     * --
     */
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    /**
     * --
     */
    @Override
    public void windowActivated(WindowEvent e) {

    }

    /**
     * --
     */
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
