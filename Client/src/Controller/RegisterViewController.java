package src.Controller;

import src.Model.ComprovaClient;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.ViewRegistre;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterViewController implements ActionListener {
	private ViewRegistre view;
	private Object[] options = {"Entèsos"};


    public RegisterViewController(ViewRegistre view) {
        this.view = view;
    }

		public void showMessage(String alerta){
			JOptionPane.showOptionDialog(new JFrame(), alerta,"Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
		}

		public void actionPerformed(ActionEvent event) {
			String boto = ((JButton) event.getSource()).getText();

			if(boto.equals("Registra't")){
				int mal = ComprovaClient.checkRegistre(view.getName(), view.getEmail(), view.getPassword(), view.getRePass());
				switch(mal){
					case 1:
						JOptionPane.showOptionDialog(new JFrame(), "Hi ha un o més paràmetres buits","Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
						break;
					case 2:
						JOptionPane.showOptionDialog(new JFrame(), "L'adreça electrònica no és vàlida","Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
						break;
					case 3:
						JOptionPane.showOptionDialog(new JFrame(), "La contrasenya no és vàlida. Ha tenir almenys:\n - Una majúscula\n - Una minúscula\n - 8 caràcters\n - 1 valor numèric","Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
						break;
					case 4:
					JOptionPane.showOptionDialog(new JFrame(), "Les contrasenyes no coincideixen","Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
						break;
					case 5:
						Usuari registro = new Usuari(view.getName(), view.getEmail(), view.getPassword());
      			UserService message = new UserService(this);
						message.sendRegister(registro);

						break;
					default:
						JOptionPane.showOptionDialog(new JFrame(), "Hi ha hagut algun error en processar la teva solicitud","Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
						break;
				}
			}else{
      	System.out.println("pa tu PUTA casa");
			}

    }
}
