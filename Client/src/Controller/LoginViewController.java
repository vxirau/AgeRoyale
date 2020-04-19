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

    public LoginViewController(LoginView view, UserService userService) {

        this.view = view;
        this.uService = userService;
				if(!userService.serviceStarted()){
					userService.startServerComunication();
				}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String boto = ((JButton) e.getSource()).getText();

        if(boto.equals("INICIAR SESSIÓ")){
            System.out.println("HOLA");
            int estat = ControllerServer.checkLogin(view.getName(), view.getPassword());
            switch (estat){
                case 1:
                    System.out.println("Existeix usuari");
                    break;
                case 2:
                    System.out.println("No existeix. Registra't!");
                    break;
            }
        } else if(boto.equals("Registra't ara")){
          SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
	            ViewRegistre rView = null;
							rView = new ViewRegistre();
							RegisterViewController controlador = new RegisterViewController(rView,uService);
							rView.registerController(controlador);
							view.setVisible(false);
	            rView.setVisible(true);
            }
          });
        }
    }
}
