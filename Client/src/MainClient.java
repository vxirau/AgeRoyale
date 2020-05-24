package src;

import src.Controller.*;
import src.Model.Network.UserService;
import src.View.*;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

/**
* Classe destinada a executar el main del client.
* */
public class MainClient {

    /**
    * Main del client desde on comença l'execució del programa
    * */
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
              LoginView loginview = new LoginView();
              LoginViewController controller = new LoginViewController(loginview, userService);
              loginview.loginViewsetListener(controller);
              loginview.setVisible(true);
          }
        });
    }

}
