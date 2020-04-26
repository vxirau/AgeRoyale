package src;

import src.Controller.GameController;
import src.Controller.LoginViewController;
import src.Controller.MenuController;
import src.Controller.RegisterViewController;
import src.Model.Network.UserService;
import src.View.GameView;
import src.View.LoginView;
import src.View.MenuView;
import src.View.ViewRegistre;

import javax.swing.*;
import java.io.IOException;

public class MainClient{

    private static boolean game = false;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {

              if (!game) {
                  UserService userService = new UserService();
                  LoginView loginview = new LoginView();
                  LoginViewController controller = new LoginViewController(loginview, userService);
                  loginview.loginViewsetListener(controller);
                  loginview.setVisible(true);
              } else {

                 /* GameView gView = new GameView();
                  gView.startGame();
                  GameController controller = new GameController(gView);
                  gView.registerController(controller);
                  gView.setVisible(true);*/
              }
          }

        });

    }
}
