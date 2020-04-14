package src.Model.Network;

import src.View.ViewServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class DedicatedServer extends Thread {

	private boolean isOn;
	private Socket sClient;
	private ObjectInputStream dataInput;
	private ObjectOutputStream objectOut;
	private LinkedList<DedicatedServer> clients;
	private ViewServer vista;
	private Server server;

	public DedicatedServer(Socket sClient, ViewServer vista, LinkedList<DedicatedServer> clients, Server server) {
		this.isOn = false;
		this.sClient = sClient;
		this.vista = vista;
		this.clients = clients;
		this.server = server;
	}

	public void startDedicatedServer() {
		// iniciem el servidor dedicat
		isOn = true;
		this.start();
	}

	public void stopDedicatedServer() {
		// aturem el servidor dedicat
		this.isOn = false;
		this.interrupt();
	}

	public void run() {
		String in;
		String[] aux;
		try {
			// creem els canals de comunicacio
			dataInput = new ObjectInputStream(sClient.getInputStream());
			objectOut = new ObjectOutputStream(sClient.getOutputStream());
			// enviem estat de la grid al client
			// NO ENVIEM EL MODEL, SINO EL SEU ESTAT, ALLO QUE ES RELLEVANT
			// PELS CLIENTS (OBSERVAR Grid != GridState)
			objectOut.writeObject("");
			while (isOn) {
				// esperem rebre dades del client, es dona quan selecciona
				// una cella de la graella
				//in = dataInput.readUTF();
				// recuperem la fila i la columna i actualizem el model
				//aux = in.split("-"); // fila - columna
				//model.checkCell(Integer.valueOf(aux[0]), Integer.valueOf(aux[1]));
				// actualitzem la vista, si existis un controlador podriem
				// delegar latualitzacio de la vista al controlador
				//vista.updateGrid(model.getMatrix());
				// actualitzem tots els clients (veure el metode privat mes avall)
				// aquesta tasca la podriem delegar al servidor, donat que aquest
				// tambe mante una relacio amb tots els servidors dedicats
				updateAllClients();
			}
		} catch (IOException e1) {
			// en cas derror aturem el servidor dedicat
			stopDedicatedServer();
			// eliminem el servidor dedicat del conjunt de servidors dedicats
			clients.remove(this);
			// invoquem el metode del servidor que mostra els servidors dedicats actuals
			server.showClients();
		}
	}

	private ObjectOutputStream getOutChannel() {
		return objectOut;
	}

	private void updateAllClients() {
		ObjectOutputStream outStream;
		for (DedicatedServer dServer : clients) {
			// recuperem el canal de sortida del servidor dedicat
			// per tal de contactar amb el client
			outStream = dServer.getOutChannel();
			try {
				// netejem el canal de sortida
				outStream.reset();
				// NO ENVIEM EL MODEL, SINO EL SEU ESTAT, ALLO QUE ES RELLEVANT
				// PELS CLIENTS (OBSERVAR Grid != GridState)
				outStream.writeObject("");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
