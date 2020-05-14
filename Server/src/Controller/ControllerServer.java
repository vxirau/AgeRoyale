package src.Controller;

import src.Model.Database.DAO.statsDAO;
import src.Model.Database.DAO.usuariDAO;
import src.Model.Network.DedicatedServer;
import src.Model.Network.Server;
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
    private ViewServer view;

    public ControllerServer(Server server, ViewServer view) {
        this.server = server;
        this.view = view;
        statsDAO sDAO = new statsDAO();
        ArrayList<Usuari> r = sDAO.getTopUsers();
        view.setTableContents(r);
        view.initAll();
    }

    @Override
    public void actionPerformed(ActionEvent e) {


        if(e.getSource() instanceof  JComboBox){
            JComboBox cb = (JComboBox)e.getSource();
            String item = (String)cb.getSelectedItem();
            view.setTableRange(item);
        }else if(e.getSource() instanceof  JButton){
            String botoClicked = ((JButton)e.getSource()).getText();
            if(botoClicked.equals("Start")){
                server.startServer();
            }else if(botoClicked.equals("Stop")){
                server.stopServer();
            }
        }



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
