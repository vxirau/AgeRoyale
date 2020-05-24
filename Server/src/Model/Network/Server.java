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

/**
 * Classe encarregada de rebre les connexions de tots els clients. Hereda de thread ja que s'executa paralelament a l'execució del programa per poder-se fer servir en qualsevol moment
 */
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

	/**
	 * Constructor de la classe
	 * @param vista pantalla gràfica del servidor
	 */
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

	/**
	 * Inicialitza el servidor
	 */
	public void startServer() {
		// iniciem el thread del servidor
		isOn = true;
		this.start();
	}

	/**
	 * Para l'execució del servidor
	 */
	public void stopServer() {
		// aturem el thread del servidor
		System.out.println("***** STOP *****");
		isOn = false;
		this.interrupt();
	}

	/**
	 * Mostra els clients que han establert connexió amb el servidor
	 */
	public void showClients() {
		System.out.println("***** SERVER ***** (" + dServers.size() +" clients / dedicated servers running)");
	}

	/**
	 * El servidor accepta les peticions dels clients
	 */
	public void run()  {
		while (isOn) {
			try {
				// acceptem peticions de connexio dels clients
				// BLOQUEJA EXECUCIO DEL THREAD
				Socket sClient = sSocket.accept();
				// creem un nou servidor dedicat per atendre les
				// peticions del client
				DedicatedServer pwClient = new DedicatedServer(sClient, dServers, this);
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

	/**
	 * Realitza un broadcast a tots els clients, per tal de que tots tinguin la mateix infomació (Waiting room, friends, running games)
	 */
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

	/**
	 * realitza un broadcast de les invitacions, per tal de que la invitació arribi al client destí i al origen
	 * @param invite invitació per part d'un client a un altre
	 */
	public void broadcastInvite(Invite invite) {
		for (DedicatedServer dServer : dServers) {
			dServer.inviteMessage(invite);
		}
	}

	/**
	 * Elimina un dedicatedServer, la connexió entre un client i el server
	 * @param dedicatedServer
	 */
	public void removeDedicated(DedicatedServer dedicatedServer) {
		dServers.remove(dedicatedServer);
	}

	/**
	 * Realitza un broadcast del començament del joc, per tal de que s'actualitzi en tots els clients
	 * @param partida partida que s'està jugant
	 */
	public void broadcastStartGame(Partida partida) {
		for (DedicatedServer dServer : dServers) {
			dServer.startGameMessage(partida);
		}
	}

	/**
	 * Indica si el servidor està encès
	 * @return
	 */
	public boolean isOn() {
		return isOn;
	}


}
