package src.Controller;

import src.Model.Network.DedicatedServer;
import src.Model.Network.Server;
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
			String botoClicked = ((JButton)e.getSource()).getText();
			if(botoClicked.equals("Start")){
				server.startServer();
			}else if(botoClicked.equals("Stop")){
				server.stopServer();
			}
    }
}
