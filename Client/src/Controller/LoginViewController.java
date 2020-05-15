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
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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

    public void userIsBanned(Usuari s){
        JOptionPane.showMessageDialog(view, "Estas bannejat!\nEt queden: " + s.getNickName()+ " dies per poder entrar");
    }

    public void onRequestsRecieved(ArrayList<Usuari> requests){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuView rView = new MenuView();
                view.setVisible(false);
                rView.setVisible(true);
                try {
                    ArrayList<Usuari> r = separateRequests(requests, 0);
                    ArrayList<Usuari> rq = separateRequests(requests, 1);
                    checkForAcceptedRequests(rq);
                    MenuController controlador = new MenuController(rView, uService, LoginViewController.this.user, r, rq);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void checkForAcceptedRequests(ArrayList<Usuari> rq){
        ArrayList<Usuari> r = new ArrayList<>();
        for(Usuari y : rq){
            if(y.isAccepted()!=null){
                r.add(y);
                if(y.isAccepted()){
                    JOptionPane.showMessageDialog(view, "Una solicitud teva ha estat acceptada!\nConsulta els teus amics.");
                }
            }
        }
        for(Usuari e : r){
            ArrayList<Usuari> h = new ArrayList<>();
            h.add(e);
            h.add(this.user);
            Message m = new Message(h, "removeRequest");
            uService.sendLogin(m, this);
        }
    }

    public ArrayList<Usuari> separateRequests(ArrayList<Usuari> requests, int criteri){
        ArrayList<Usuari> users = new ArrayList<>();
        int i=0;
        boolean ok = true;
        if(criteri==0){
            for(i=0; ok; i++){
                if(requests.get(i).getIdUsuari()==-20 && requests.get(i).getNickName().equals("SEPARATOR")){
                    ok = false;
                }else{
                    users.add(requests.get(i));
                }
            }
        }else if(criteri==1){
            for(i=0; ok; i++){
                if(requests.get(i).getIdUsuari()==-20 && requests.get(i).getNickName().equals("SEPARATOR")){
                    ok = false;
                }
            }
            for(int j=i; j<requests.size() ;j++){
                users.add(requests.get(j));
            }
        }

        return users;
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
