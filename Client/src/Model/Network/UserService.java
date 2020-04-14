package src.Model.Network;

import src.Controller.NetworkConfiguration;
import src.Controller.RegisterViewController;
import src.Usuari;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class UserService extends Thread{
  //private Socket socket;
	private ObjectOutputStream doStream;
	private RegisterViewController controller;


	public UserService(RegisterViewController controller) {
		this.controller = controller;
	}

	public void sendRegister(Object user) {
		try(Socket socket = new Socket(NetworkConfiguration.staticIP, NetworkConfiguration.staticPort)) {
			// Establim la connexio amb el servidor i enviem el missatge


			this.doStream = new ObjectOutputStream(socket.getOutputStream());
			this.doStream.writeObject(user);
			// Tanquem el socket
			this.doStream.flush();
			socket.close();
		} catch (IOException e) {
			// Si hi ha algut algun problema informem al controlador, ell
			// informara a la vista

			controller.showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}
}
