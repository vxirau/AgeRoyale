package src.Controller;

import src.Message;
import src.Model.ComprovaClient;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.LoginView;
import src.View.ViewRegistre;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginViewController implements ActionListener {
    private LoginView view;
    private UserService uService;

    public LoginViewController(LoginView view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String boto = ((JButton) e.getSource()).getText();

        if(boto.equals("INICIAR SESSIÓ")){
            int estat = ComprovaClient.checkLogin(view.getName(), view.getPassword());
            switch (estat){
                case 1:
                    System.out.println("Existeix usuari");
                    break;
                case 2:
                    System.out.println("No existeix. Registra't!");
                    break;
            }
        } else if(boto.equals("Registra't ara")){
            System.out.println("holi");
        }
    }
}

