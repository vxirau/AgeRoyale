package src.Model.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import src.Controller.NetworkConfiguration;
import src.Controller.RegisterViewController;
import src.Message;
import src.Usuari;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserService extends Thread{
  	private Socket socket;
	private ObjectOutputStream doStream;
	private ObjectInputStream doInput;
	private DataInputStream dInput;
	private boolean isOn = false;
	private Object[] options = {"Entèsos"};
  	private Path current = Paths.get("./Server/resources/config.json");
  	private String arxiu = current.toAbsolutePath().toString();


	public UserService() {
		try {
			this.isOn = false;
			// connectem amb el servidor i obrim els canals de comunicacio
			if(!NetworkConfiguration.Setup){
				BufferedReader reader = new BufferedReader(new FileReader(arxiu));
				Gson gson = new GsonBuilder().create();
				NetworkConfiguration config = gson.fromJson(reader, NetworkConfiguration.class);
				config.ompleStatic();
				NetworkConfiguration.Setup = true;
			}
			this.socket = new Socket(NetworkConfiguration.staticIP, NetworkConfiguration.staticPort);
			//this.socket = new Socket("192.168.86.31", 2003);
			this.doStream = new ObjectOutputStream(socket.getOutputStream());
      this.doInput = new ObjectInputStream(socket.getInputStream());
      this.dInput = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("*** ESTA EL SERVIDOR EN EXECUCIO? ***");
		}
	}

	public void startServerComunication() {
		// iniciem la comunicacio amb el servidor
		isOn = true;
		this.start();
	}

	public void stopServerComunication() {
		// aturem la comunicacio amb el servidor
		this.isOn = false;
		this.interrupt();
	}

	public void showMessage(String alerta){
		JOptionPane.showOptionDialog(new JFrame(), alerta,"Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
	}


	public void run() {
		//while (isOn) {
			try {
				// escolta les actualizacions de lestat del model
				// que envia el servidor quan algun client fa clic
				// a alguna casella
				Message jelow = (Message) doInput.readObject();
				System.out.println(jelow.getType());

				// actualiza la vista.
				// Tambe es podria delegar la tasca dactualizar la vista
				// al controlador.
				//view.updateGrid(p.getMatrix());
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				// si hi ha algun problema satura la comunicacio amb el servidor
				stopServerComunication();
				System.out.println("*** ESTA EL SERVIDOR EN EXECUCIO? ***");
			}
		//}
		stopServerComunication();
	}

	public void sendRegister(Object user) {
		try{
			// Establim la connexio amb el servidor i enviem el missatge
			this.doStream.writeObject(user);
			// Tanquem el socket

		} catch (IOException e) {
			// Si hi ha algut algun problema informem al controlador, ell
			// informara a la vista
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}




}
