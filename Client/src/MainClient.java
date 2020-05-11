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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

public class MainClient {

    private static int game = 1;
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
                  GameController gameController = new GameController(gView);
                  GameView finalGView = gView;
                  SwingUtilities.invokeLater(new Runnable() {
                      @Override
                      public void run() {
                          finalGView.registerController(gameController);

                          finalGView.setVisible(true);
                      }
                  });

              }else{
                  SwingUtilities.invokeLater(new Runnable() {
                      @Override
                      public void run() {
                          MenuView rView = new MenuView();
                          ArrayList<Usuari> amics = new ArrayList<>();
                          amics.add(new Usuari(5, "Bernat", "1234"));
                          amics.add(new Usuari(4, "Lidia", "1234"));
                          amics.add(new Usuari(3, "Marti", "1234"));
                          amics.add(new Usuari(2, "Adri", "1234"));
                          amics.add(new Usuari(5, "Bernat", "1234"));
                          amics.add(new Usuari(4, "Lidia", "1234"));
                          amics.add(new Usuari(3, "Marti", "1234"));
                          amics.add(new Usuari(2, "Adri", "1234"));
                          MenuController controlador = new MenuController(rView, userService, new Usuari(1, "Victor", "1234", amics));
                          rView.setVisible(true);
                      }
                  });
              }
          }
        });
    }

}
