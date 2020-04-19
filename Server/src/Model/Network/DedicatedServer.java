package src.Model.Network;

import src.Message;
import src.Model.Database.DAO.usuariDAO;
import src.Usuari;
import src.View.ViewServer;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class DedicatedServer extends Thread {

	private boolean isOn;
	private Socket sClient;
	private ObjectInputStream dataInput;
	private DataOutputStream dataOut;
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
			//dataOut = new DataOutputStream(sClient.getOutputStream());

			// enviem estat de la grid al client
			// NO ENVIEM EL MODEL, SINO EL SEU ESTAT, ALLO QUE ES RELLEVANT
			// PELS CLIENTS (OBSERVAR Grid != GridState)
			Message m = (Message) dataInput.readObject();
			if (m.getType().equals("register")){
				Usuari u = (Usuari)m.getObject();

				System.out.println(u.toString());

				objectOut.reset();
				usuariDAO uDAO = new usuariDAO();
				if(!uDAO.existsRegistre(u)){
					objectOut.writeObject(new Message(u, "REGISTER_OK"));
					uDAO.addUser(u);
				}else{
					objectOut.writeObject(new Message(u, "REGISTER_KO"));
				}
			}

			// NO ENVIEM EL MODEL, SINO EL SEU ESTAT, ALLO QUE ES RELLEVANT
			// PELS CLIENTS (OBSERVAR Grid != GridState)
			//while (isOn) {
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
				//updateAllClients();
			//}
		} catch (IOException | ClassNotFoundException e1) {
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
			if(dServer!=null){
				outStream = dServer.getOutChannel();
				try {
					// netejem el canal de sortida
					outStream.reset();
					// NO ENVIEM EL MODEL, SINO EL SEU ESTAT, ALLO QUE ES RELLEVANT
					// PELS CLIENTS (OBSERVAR Grid != GridState)
					//dataOut.writeUTF("hola");
					outStream.writeObject(new Message(null, "pilotes"));
					//outStream.writeObject("");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
