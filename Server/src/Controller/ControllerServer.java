package src.Controller;

import src.Model.Database.DAO.partidaDAO;
import src.Model.Database.DAO.statsDAO;
import src.Model.Database.DAO.usuariDAO;
import src.Model.Network.DedicatedServer;
import src.Model.Network.Server;
import src.Partida;
import src.Usuari;
import src.View.ViewServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class ControllerServer implements ActionListener {

    private Server server;
    private static ViewServer view;

    public ControllerServer(Server server, ViewServer view) {
        this.server = server;
        this.view = view;
        statsDAO sDAO = new statsDAO();
        partidaDAO aDAO = new partidaDAO();
        ArrayList<Usuari> r = sDAO.getTopUsers();
        ArrayList<Partida> p = aDAO.getAllPartides();
        view.setAllGames(p);
        view.setTableContents(r);
        view.initAll();
    }

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
            String botoClicked = ((JButton)e.getSource()).getText();
            if(botoClicked.equals("Start")){
                JOptionPane.showMessageDialog(view, "Server started!", "Server", JOptionPane.PLAIN_MESSAGE);
                server.startServer();
            }else if(botoClicked.equals("Stop")){
                server.stopServer();
            }
        }



    }

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


    public static int checkLogin(String name, String password) {
        usuariDAO u = new usuariDAO();
        //Existeix el login i la contrasenya
        if (u.existsLogin(name, password) != null) {
            return 1;
        }
        return 2;
    }
}
