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

    private static int game = 2;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
          @Override
          public void run() {
              UserService userService = new UserService();
              if (game == 1) {
                  LoginView loginview = new LoginView();
                  LoginViewController controller = new LoginViewController(loginview, userService);
                  loginview.loginViewsetListener(controller);
                  loginview.setVisible(true);
              } else if(game == 0){
                  GameView gView = null;
                  try {
                      gView = new GameView();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
                  gView.startGame();
                  GameController controller = new GameController(gView);
                  gView.registerController(controller);
                  gView.setVisible(true);
              }else{
                  SwingUtilities.invokeLater(new Runnable() {
                      @Override
                      public void run() {
                          MenuView rView = new MenuView();
                          Usuari user = new Usuari(5, "bernat", "bernat@gmail.com", "1234", null, null, null);
                          MenuController controlador = new MenuController(rView, userService, user);
                          rView.setVisible(true);
                      }
                  });
              }
          }
        });
    }
}
