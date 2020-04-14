package src.Model.Network;

import src.Controller.NetworkConfiguration;
import src.Controller.RegisterViewController;
import src.Usuari;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class UserService {
  private Socket socket;
	private ObjectOutputStream doStream;
	private RegisterViewController controller;


	public UserService(RegisterViewController controller) {
		this.controller = controller;
	}

	public static void sendRegister(Usuari user) {
		try {
			// Establim la connexio amb el servidor i enviem el missatge
			socket = new Socket(NetworkConfiguration.staticIP, NetworkConfiguration.staticPort);
			doStream = new ObjectOutputStream(socket.getOutputStream());
			doStream.writeObject(user);
			// Tanquem el socket
			socket.close();
		} catch (IOException e) {
			// Si hi ha algut algun problema informem al controlador, ell
			// informara a la vista

			controller.showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}
}
