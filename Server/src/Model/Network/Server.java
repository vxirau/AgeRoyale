package src.Model.Network;


import src.Controller.NetworkConfiguration;
import src.View.ViewServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server extends Thread {

	private boolean isOn;
	private ServerSocket sSocket;
	// relacio amb els servidors dedicats
	private LinkedList<DedicatedServer> dServers;
	// relacio amb la vista
	// podriem mantenir una relacio amb el controlador
	// en el cas de que aquest existis, i delegar les
	// tasques dactualitzacio de la vista al controlador
	private ViewServer view;


	public Server(ViewServer vista) {
		try {
			this.isOn = false;
			this.view = vista;
			this.sSocket = new ServerSocket(NetworkConfiguration.staticPort);
			this.dServers = new LinkedList<DedicatedServer>();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startServer() {
		// iniciem el thread del servidor
		isOn = true;
		this.start();
	}

	public void stopServer() {
		// aturem el thread del servidor
		isOn = false;
		this.interrupt();
	}

	public void showClients() {
		System.out.println("***** SERVER ***** (" + dServers.size() +" clients / dedicated servers running)");
	}

	public void run()  {
		while (isOn) {
			try {
				// acceptem peticions de connexio dels clients
				// BLOQUEJA EXECUCIO DEL THREAD
				Socket sClient = sSocket.accept();
				// creem un nou servidor dedicat per atendre les
				// peticions del client
				DedicatedServer pwClient = new DedicatedServer(sClient, view, dServers, this);
				dServers.add(pwClient);
				// engegem el servidor dedicat
				pwClient.startDedicatedServer();
				showClients();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// aturem tots els servidors dedicats creats
		// quan ja no atenem mes peticions de clients
		for (DedicatedServer dServer : dServers) {
			dServer.startDedicatedServer();
		}
	}

}