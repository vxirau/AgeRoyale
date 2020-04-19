package src;

import src.Controller.LoginViewController;
import src.Controller.RegisterViewController;
import src.Model.Network.UserService;
import src.View.LoginView;
import src.View.ViewRegistre;

import javax.swing.*;
import java.io.IOException;

public class MainClient{

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
						UserService userService = new UserService();
						LoginView loginview = new LoginView();
						LoginViewController controller = new LoginViewController(loginview, userService);
						loginview.loginViewsetListener(controller);
						loginview.setVisible(true);
          }
        });
    }
}
