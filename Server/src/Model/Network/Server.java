package src.Model.Network;


import src.Invite;
import src.NetworkConfiguration;
import src.Partida;
import src.Tropa;
import src.View.ViewServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server extends Thread {

	private boolean isOn;
	private ServerSocket sSocket;
	// relacio amb els servidors dedicats
	private CopyOnWriteArrayList<DedicatedServer> dServers;
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
			this.dServers = new CopyOnWriteArrayList<>();
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
		System.out.println("***** STOP *****");
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

    public void broadcastClients() {
		for (DedicatedServer dServer : dServers) {
			try {
				dServer.privateMessage("updateWaitingRoom");
				dServer.privateMessage("Friends");
				dServer.privateMessage("getAllRunningGames");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

	public void broadcastInvite(Invite invite) {
		for (DedicatedServer dServer : dServers) {
			dServer.inviteMessage(invite);
		}
	}

	public void removeDedicated(DedicatedServer dedicatedServer) {
		dServers.remove(dedicatedServer);
	}

	public void broadcastStartGame(Partida partida) {
		for (DedicatedServer dServer : dServers) {
			dServer.startGameMessage(partida);
		}
	}

	public boolean isOn() {
		return isOn;
	}


}
