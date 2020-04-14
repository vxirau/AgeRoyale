package src.Controller;

import src.Model.ComprovaClient;
import src.Usuari;
import src.View.ViewRegistre;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterViewController implements ActionListener {
	private ViewRegistre view;

    public RegisterViewController(ViewRegistre view) {
        this.view = view;
    }

		public void actionPerformed(ActionEvent event) {
			String boto = ((JButton) event.getSource()).getText();

			if(boto.equals("Registra't")){
				int mal = ComprovaClient.checkRegistre(view.getName(), view.getEmail(), view.getPassword(), view.getRePass());
				Object[] options = {"Entèsos"};
				switch(mal){
					case 1:
						JOptionPane.showOptionDialog(new JFrame(), "L'adreça electrònica no és vàlida","Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
						break;
					case 2:
						JOptionPane.showOptionDialog(new JFrame(), "Les contrasenyes no coincideixen ","Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
						break;
					case 3:
						JOptionPane.showOptionDialog(new JFrame(), "Els parametres no son vàlids","Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
						break;
					case 4:
						break;
					case 5:
						break;
					case 6:
						break;
					case 7:
						break;
					case 8:
						break;
					case 9:
						Usuari registro = new Usuari(view.getName(), view.getEmail(), view.getPassword());
						break;
				}
			}else{
      	System.out.println("pa tu casa");
			}

    }
}
