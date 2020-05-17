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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class RegisterViewController implements ActionListener, WindowListener {
	private ViewRegistre view;
	private UserService uService;
	private Object[] options = {"Entèsos"};


    public RegisterViewController(ViewRegistre view, UserService uService) {
        this.view = view;
        this.uService = uService;
				if(!uService.serviceStarted()){
					uService.startServerComunication();
				}
    }

	public void showMessage(String alerta){
		JOptionPane.showOptionDialog(new JFrame(), alerta,"Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
	}

	public void actionPerformed(ActionEvent event) {
		String boto = ((JButton) event.getSource()).getText();

		if(boto.equals("REGISTRAR-SE")){
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
					Message missatge = new Message(registro, "register");
					uService.sendRegister(missatge, this);
					break;
				default:
					JOptionPane.showOptionDialog(new JFrame(), "Hi ha hagut algún error en processar la teva solicitud","Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
					break;
			}
		}else if(boto.equals("ATRAS")){
			SwingUtilities.invokeLater(new Runnable() {
	  @Override
	  public void run() {
					LoginView lview = null;
					lview = new LoginView();
					LoginViewController controller = new LoginViewController(lview, uService);
					lview.loginViewsetListener(controller);
					view.setVisible(false);
					lview.setVisible(true);
				}
			});

		}
    }

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {
		uService.stopServerComunication();
	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	public void setView(boolean b) {
    	view.setVisible(b);
	}
}
