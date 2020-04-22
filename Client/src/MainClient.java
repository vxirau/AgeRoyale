package src;

import src.Controller.LoginViewController;
import src.Controller.RegisterViewController;
import src.Model.Network.UserService;
import src.View.LoginView;
import src.View.RoomListView;
import src.View.ViewRegistre;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

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


              //NO TOCAR PER ARA, QUAN ESTIGUI EL MENU IMPLEMENTAT COMENTAR AMB XI I LIDIA, QUE ADAPTARAN EL JPANEL :D
              //JA FUNCIONA TOT
              /*ArrayList<Partida> partides = new ArrayList<>();
            partides.add(new Partida(2, true, "Guerra", "Marin"));
            partides.add(new Partida(4, false, "Holi", "Adrian"));
            partides.add(new Partida(7, true, "Xirau", "Lily"));
						RoomListView loginview = new RoomListView(partides);
						loginview.setVisible(true);*/
          }
        });
    }
}
