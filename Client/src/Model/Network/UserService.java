package src.Model.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import src.Controller.LoginViewController;
import src.Controller.NetworkConfiguration;
import src.Controller.RegisterViewController;
import src.Message;
import src.Partida;
import src.Usuari;
import src.View.LoginView;
import src.View.MenuView;
import src.View.ViewRegistre;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UserService extends Thread{
  	private Socket socket;
	private ObjectOutputStream doStream;
	private ObjectInputStream doInput;
	private DataInputStream dInput;
	private boolean isOn = false;
	private Object[] options = {"Entèsos"};
  	private Path current = Paths.get("./Server/resources/config.json");
  	private String arxiu = current.toAbsolutePath().toString();
    private ViewRegistre vregistre;


	public UserService() {
		try {
			this.isOn = false;
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

	public void setRegisterView(ViewRegistre vregistre){
		this.vregistre = vregistre;
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

	public boolean serviceStarted(){
		return isOn;
	}


	public void run() {
		//while (isOn) {
			try {

				Message jelow = (Message) doInput.readObject();
				System.out.println(jelow.getType());
				if(jelow.getType().equals("REGISTER_OK")){
					JOptionPane.showOptionDialog(new JFrame(), "DE SUPER PUTA MARE SOCI" , "Congratulacions", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null,options,options[0]);
					LoginView lview = new LoginView();
					LoginViewController controller = new LoginViewController(lview, UserService.this);
					lview.loginViewsetListener(controller);
					vregistre.setVisible(false);
					lview.setVisible(true);
					Usuari user = (Usuari) jelow.getObject();
					lview.setUsuari(user.getNickName());
					lview.setPassword(user.getPassword());
				}else if(jelow.getType().equals("allGamesReply")){
					ArrayList<Partida> p = jelow.getObjectArray();
					System.out.println("Arriba Resposta");
					MenuView.setAllGames(p);
				}else{
					JOptionPane.showOptionDialog(new JFrame(), "LOKO HI HA QUELCOM MALAMENT" , "Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
				}
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
				stopServerComunication();
				System.out.println("*** ESTA EL SERVIDOR EN EXECUCIO? ***");
			}
		//}
		stopServerComunication();
	}

	public void sendRegister(Object user) {
		try{
			this.doStream.reset();
			this.doStream.writeObject(user);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void sendPartida(Object partida) {
		try{
			this.doStream.reset();
			this.doStream.writeObject(partida);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void sendGetPartides(Object message) {
		try{
			this.doStream.reset();
			this.doStream.writeObject(message);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}



}
