package src;

import src.Controller.*;
import src.Model.Network.UserService;
import src.View.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

public class MainClient {



    private static int game = 1;
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel( "javax.swing.plaf.nimbus.NimbusLookAndFeel" );
        } catch( Exception e ) {
            e.printStackTrace();
        }
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
                  GameController gameController = null;
                  TroopController troopController = null;
                 try {
                      gameController = new GameController(gView,userService);
                      troopController = new TroopController(gView,userService);
                  } catch (IOException e) {
                      e.printStackTrace();
                 }

                 GameView finalGView = gView;
                 GameController finalGameController = gameController;
                 TroopController finalTroopController = troopController;
                 SwingUtilities.invokeLater(new Runnable() {
                      @Override
                      public void run() {
                          finalGView.registerController(finalGameController, null);
                          finalGView.setTroopController(finalTroopController);

                          finalGView.setVisible(true);
                      }
                  });

              }else if(game == 8){
                  ArrayList<Usuari> amics = new ArrayList<>();
                  amics.add(new Usuari("amigo1", "password1"));
                  amics.add(new Usuari("amigo2", "password2"));
                  amics.add(new Usuari("amigo3", "password3"));
                  amics.add(new Usuari("amigo4", "password4"));
                  amics.add(new Usuari("amigo5", "password5"));
                  amics.add(new Usuari("amigo6", "password6"));
                  amics.add(new Usuari("amigo7", "password7"));
                  amics.add(new Usuari("amigo8", "password8"));
                  amics.add(new Usuari("amigo9", "password9"));
                  amics.add(new Usuari("amigo10", "password10"));
                  Usuari u  = new Usuari(0, "Victor", "password", amics);

                  ArrayList<Usuari> jugadors = new ArrayList<>();
                  jugadors.add(new Usuari("amigo1", "password1"));
                  jugadors.add(new Usuari("amigo2", "password2"));

                  ArrayList<Usuari> espectadors = new ArrayList<>();
                  espectadors.add(new Usuari("amigo4", "password1"));
                  espectadors.add(new Usuari("amigo78", "password2"));

                  WaitingRoomView view = new WaitingRoomView(new Partida("Partidaza", null, false, "Victor", jugadors, espectadors), u);
                  view.setVisible(true);
              }else{
                  SwingUtilities.invokeLater(new Runnable() {
                      @Override
                      public void run() {
                          MenuView rView = new MenuView();
                          ArrayList<Usuari> amics = new ArrayList<>();
                          amics.add(new Usuari("amigo1", "password1"));
                          amics.add(new Usuari("amigo2", "password2"));
                          amics.add(new Usuari("amigo3", "password3"));
                          amics.add(new Usuari("amigo4", "password4"));
                          amics.add(new Usuari("amigo5", "password5"));
                          amics.add(new Usuari("amigo6", "password6"));
                          amics.add(new Usuari("amigo7", "password7"));
                          amics.add(new Usuari("amigo8", "password8"));
                          amics.add(new Usuari("amigo9", "password9"));
                          amics.add(new Usuari("amigo10", "password10"));
                          Usuari u  = new Usuari(0, "victor", "password", amics);
                         /* try {
                              //MenuController controlador = new MenuController(rView, userService, u, null, null);
                          } catch (InterruptedException e) {
                              e.printStackTrace();
                          }*/
                          rView.setVisible(true);
                      }
                  });
              }
          }
        });
    }

}
