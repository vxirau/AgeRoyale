package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.LoginView;
import src.View.MenuView;
import src.View.ViewRegistre;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class LoginViewController implements ActionListener {
    private LoginView view;
    private UserService uService;
    private Usuari user;

    public LoginViewController(LoginView view, UserService userService) {
        this.view = view;
        this.uService = userService;
        if(!userService.serviceStarted()){
            userService.startServerComunication();
        }
    }

    public void loginSuccessful(Usuari usr){
        this.user = usr;
        Message m = new Message(usr, "getRequests");
        uService.sendObject(m);
    }

    public void onRequestsRecieved(ArrayList<Usuari> requests){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuView rView = new MenuView();
                view.setVisible(false);
                rView.setVisible(true);
                try {
                    MenuController controlador = new MenuController(rView, uService, LoginViewController.this.user, requests);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loginNotSuccessful(){
        System.out.println("No existeix. Registra't!");

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String boto = ((JButton) e.getSource()).getText();

        if(boto.equals("INICIAR SESSIÓ")){
            Message message = new Message(new Usuari(view.getUsuari(), view.getPassword()), "Login");
            uService.sendLogin(message, this);

        } else if(boto.equals("Registra't ara")){
            SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                            ViewRegistre rView = new ViewRegistre();
                            RegisterViewController controlador = new RegisterViewController(rView,uService);
                            rView.registerController(controlador);
                            view.setVisible(false);
                rView.setVisible(true);
            }
            });
        }
    }
}
