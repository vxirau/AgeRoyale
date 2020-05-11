package src.Model.Network;

import src.Message;
import src.Model.Database.DAO.amicDAO;
import src.Model.Database.DAO.partidaDAO;
import src.Model.Database.DAO.usuariDAO;
import src.Partida;
import src.Usuari;
import src.View.ViewServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
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

	public DedicatedServer(Socket sClient, ViewServer vista, LinkedList<DedicatedServer> clients, Server server) throws IOException {
		this.isOn = false;
		this.sClient = sClient;
		this.vista = vista;
		this.clients = clients;
		this.server = server;
		dataInput = new ObjectInputStream(sClient.getInputStream());
		objectOut = new ObjectOutputStream(sClient.getOutputStream());
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

			while(isOn) {

				Message m = (Message) dataInput.readObject();
				System.out.println("ARRIBA: " + m.getType());
				if (m.getType().equals("register")) {
					Usuari u = (Usuari) m.getObject();
					System.out.println(u.toString());

					objectOut.reset();
					usuariDAO uDAO = new usuariDAO();
					if (!uDAO.existsRegistre(u)) {
						objectOut.writeObject(new Message(u, "REGISTER_OK"));
						uDAO.addUser(u);
					} else {
						objectOut.writeObject(new Message(u, "REGISTER_KO"));
					}
				} else if (m.getType().equals("roomCreate")) {
					partidaDAO pDao = new partidaDAO();
					pDao.addPartida((Partida) m.getObject());
				} else if (m.getType().equals("getAllGames")) {
					objectOut.reset();
					partidaDAO pDao = new partidaDAO();
					ArrayList<Partida> p = pDao.getAllPartides();
					objectOut.writeObject(new Message(p, "allGamesReply"));
				} else if (m.getType().equals("Login")) {
					Usuari usuari = (Usuari) m.getObject();
					usuariDAO uDAO = new usuariDAO();
					Usuari usr = uDAO.existsLogin(usuari);
					Message messageResposta = new Message(usr, "Login resposta");
					objectOut.writeObject(messageResposta);
					if (usr != null) {
						uDAO.updateState(usr, true);
					}
				} else if (m.getType().equals("Logout")) {
					Usuari usuari = (Usuari) m.getObject();
					usuariDAO uDAO = new usuariDAO();
					uDAO.updateState(usuari, false);
				} else if (m.getType().equals("PasswordUpdate")) {
					Usuari usuari = (Usuari) m.getObject();
					usuariDAO uDAO = new usuariDAO();
					uDAO.updatePass(usuari);
				} else if (m.getType().equals("UserPKUpdates")) {
					Usuari usuari = (Usuari) m.getObject();
					usuariDAO uDAO = new usuariDAO();
					String resposta = "";
					if (uDAO.existsUsuariOnChange(usuari)) {
						resposta = "UserPKUpdates_KO";
					} else {
						uDAO.updateNickEmail(usuari);
						resposta = "UserPKUpdates_OK";
					}
					Message messageResposta = new Message(resposta, "UserPKUpdatesResposta");
					objectOut.writeObject(messageResposta);
				} else if (m.getType().equals("Friends")){
					Usuari usuari = (Usuari) m.getObject();
					amicDAO aDAO = new amicDAO();
					ArrayList<Usuari> a = aDAO.getAmics(usuari);
					Message messageResposta = new Message(a, "FriendsResposta");
					objectOut.writeObject(messageResposta);
				}
			}
		} catch (IOException | ClassNotFoundException e1){
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
