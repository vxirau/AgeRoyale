package src;

import src.Controller.RegisterViewController;
import src.Model.Network.UserService;
import src.View.ViewRegistre;

import javax.swing.*;
import java.io.IOException;

public class MainClient{

	private static boolean login_registre = true;

    public static void main(String[] args) {

				if(login_registre){
          SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ViewRegistre vista = null;
                try {
                    vista = new ViewRegistre();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UserService userService = new UserService();
                RegisterViewController controlador = new RegisterViewController(vista,userService);
                vista.registerController(controlador);
                vista.setVisible(true);
            }
          });
				}else{
					//obre finestra login
				}


    }
}
