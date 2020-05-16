package src.Model.Network;

import src.Controller.ControllerServer;
import src.Controller.TroopSController;
import src.Message;
import src.Model.Database.DAO.*;
import src.Partida;
import src.Tropa;
import src.Usuari;
import src.View.ViewServer;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class DedicatedServer extends Thread {

	private boolean isOn;
	private Socket sClient;
	private ObjectInputStream dataInput;
	private DataOutputStream dataOut;
	private ObjectOutputStream objectOut;
	private LinkedList<DedicatedServer> clients;
	private ViewServer vista;
	private TroopSController troopSController;
	private Usuari clientUser;
	private Server server;
	public static int cont = 0;


	public DedicatedServer(Socket sClient, ViewServer vista, LinkedList<DedicatedServer> clients, Server server) throws IOException {
		this.isOn = false;
		this.sClient = sClient;
		this.vista = vista;
		this.clients = clients;
		this.server = server;
		dataInput = new ObjectInputStream(sClient.getInputStream());
		objectOut = new ObjectOutputStream(sClient.getOutputStream());
		this.troopSController = new TroopSController();
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
					ControllerServer.updateTable();
					pDao.addPartida((Partida) m.getObject());
					ArrayList<Partida> p = pDao.getRunningPartides();
					objectOut.writeObject(new Message(p, "allGamesReply"));
				} else if (m.getType().equals("getAllGames")) {
					objectOut.reset();
					partidaDAO pDao = new partidaDAO();
					ArrayList<Partida> p = pDao.getAllPartides();
					objectOut.writeObject(new Message(p, "allGamesReply"));
				} else if (m.getType().equals("getAllRunningGames")) {
					objectOut.reset();
					partidaDAO pDao = new partidaDAO();
					ArrayList<Partida> p = pDao.getRunningPartides();
					objectOut.writeObject(new Message(p, "allGamesReply"));
				} else if (m.getType().equals("Login")) {
					Usuari usuari = (Usuari) m.getObject();
					usuariDAO uDAO = new usuariDAO();
					Usuari usr = uDAO.existsLogin(usuari);
					Message messageResposta;
					if(usr != null && uDAO.isBanned(usr)){
						SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Date date = format.parse(uDAO.getDateBan(usr));

						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Calendar cal = Calendar.getInstance();
						Date currentDate = format.parse(dateFormat.format(cal.getTime()));

						long secs = (currentDate.getTime() - date.getTime()) / 1000;
						int hours = (int) (secs / 3600);
						int minuts = (int) (secs/60) - hours*60;

						if(hours>=24){
							uDAO.unBan(usr);
							messageResposta = new Message(usr, "Login resposta");
						}else{
							int remainingH = 23-hours;
							int remainingMin = 60-minuts;
							Usuari not = new Usuari(-30, remainingH+"h " + remainingMin + "min ", "BANNED");
							messageResposta = new Message(not, "Login resposta");
						}
					}else{
						clientUser = usr;
						messageResposta = new Message(usr, "Login resposta");
					}
					objectOut.writeObject(messageResposta);
					if (usr != null) {
						uDAO.updateState(usr, true);
					}
				} else if (m.getType().equals("Logout")) {
					Usuari usuari = (Usuari) m.getObject();
					usuariDAO uDAO = new usuariDAO();
					uDAO.updateState(usuari, false);
					isOn = false;
					Message messageResposta = new Message(null, "Logout_OK");
					objectOut.writeObject(messageResposta);
					stopDedicatedServer();
					clients.remove(this);
					server.showClients();
				} else if (m.getType().equals("PasswordUpdate")) {
					Usuari usuari = (Usuari) m.getObject();
					usuariDAO uDAO = new usuariDAO();
					uDAO.updatePass(usuari);
				} else if (m.getType().equals("getRequests")) {
					Usuari usuari = (Usuari) m.getObject();
					requestsDAO aDAO = new requestsDAO();
					Message messageResposta = new Message(aDAO.getFriendRequests(usuari), "requestsReply");
					objectOut.writeObject(messageResposta);
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
				} else if (m.getType().equals("removeFriend")){
					ArrayList<Usuari> users = (ArrayList<Usuari>) m.getObject();
					amicDAO aDAO = new amicDAO();
					aDAO.removeAmic(users.get(0), users.get(1));
					ArrayList<Usuari> a = aDAO.getAmics(users.get(0));
					Message messageResposta = new Message(a, "FriendsResposta");
					objectOut.writeObject(messageResposta);
				} else if(m.getType().equals("Tropa update")){
					Tropa t = (Tropa) m.getObject();
					t = troopSController.moveOffensiveTroop(t,t.getxVariation(),t.getyVariation(),cont);
					cont++;
                    objectOut.reset();
					Message mresposta = new Message(t,"Tropa resposta");
					objectOut.writeObject(mresposta);
				} else if (m.getType().equals("FindFriend")){
					String nom = (String) m.getObject();
					usuariDAO uDAO = new usuariDAO();
					ArrayList<Usuari> auz = uDAO.getUsersByName(nom);
					Message messageResposta = new Message(auz, "FindFriendResposta");
					objectOut.writeObject(messageResposta);
				}else if(m.getType().equals("acceptRequest")){
					ArrayList<Usuari> users = (ArrayList<Usuari>) m.getObject();
					requestsDAO rDAO = new requestsDAO();
					amicDAO aDAO = new amicDAO();
					rDAO.acceptRequest(users.get(0), users.get(1));
					aDAO.addAmic(users.get(0), users.get(1));
					ArrayList<Usuari> a = aDAO.getAmics(users.get(0));
					Message messageResposta = new Message(a, "FriendsResposta");
					objectOut.writeObject(messageResposta);
					Message messageResposta2 = new Message(rDAO.getFriendRequests(users.get(0)), "requestsReply");
					objectOut.writeObject(messageResposta2);
				}else if(m.getType().equals("removeRequest")){
					ArrayList<Usuari> users = (ArrayList<Usuari>) m.getObject();
					requestsDAO rDAO = new requestsDAO();
					rDAO.removeRequest(users.get(0), users.get(1));
				}else if(m.getType().equals("denyRequest")){
					ArrayList<Usuari> users = (ArrayList<Usuari>) m.getObject();
					requestsDAO rDAO = new requestsDAO();
					rDAO.denyRequest(users.get(0), users.get(1));
				}else if(m.getType().equals("sendRequest")){
					ArrayList<Usuari> users = (ArrayList<Usuari>) m.getObject();
					requestsDAO rDAO = new requestsDAO();
					rDAO.addRequest(users.get(0), users.get(1));
				}else if(m.getType().equals("banUser")){
					Usuari user = (Usuari) m.getObject();
					usuariDAO uDAO = new usuariDAO();
					uDAO.banUser(user);
				}else if(m.getType().equals("newPlayer")){
					Partida p = (Partida)m.getObject();
					partidaDAO pDAO = new partidaDAO();
					if(pDAO.hasPlayerOne(p) && p.getIdPartida() != pDAO.getPlayerOne(p)){
						pDAO.addPlayerTwo(p, p.getJugadors().get(1));
					}else{
						pDAO.addPlayerOne(p, p.getJugadors().get(0));
					}
					server.broadcastClients();
				}else if(m.getType().equals("newSpectator")){
					spectatorDAO sDAO = new spectatorDAO();
					Partida p = (Partida)m.getObject();
					for(Usuari u : p.getEspectadors()){
						if(!sDAO.isSpectating(p, u)){
							sDAO.addSpectator(p, u);
						}
					}
					server.broadcastClients();
				}else if(m.getType().equals("removeFromWaitingRoom")){
					//Treure de la waiting room
					//Si es jugador, treure de partida
					//Si no és jugador, treure de espectadors
					//Fer broadcast.

				}else if(m.getType().equals("updateWaitingRooms")){

				} else if (m.getType().equals("userLeft")) {
					Usuari u = (Usuari) m.getObject();
					partidaDAO pDAO = new partidaDAO();
					spectatorDAO sDAO = new spectatorDAO();
					Partida part = pDAO.userIsPlayer(u);
					if(part != null){
						pDAO.removePlayer(part, u);
					}else{
						sDAO.removeSpectator(u);
					}
				}

			}
		} catch (IOException | ClassNotFoundException e1){
				// en cas derror aturem el servidor dedicat
				stopDedicatedServer();
				// eliminem el servidor dedicat del conjunt de servidors dedicats
				clients.remove(this);
				// invoquem el metode del servidor que mostra els servidors dedicats actuals
				server.showClients();
			} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public Date convertToDateViaInstant(LocalDate dateToConvert) {
		return java.util.Date.from(dateToConvert.atStartOfDay()
				.atZone(ZoneId.systemDefault())
				.toInstant());
	}

	public void privateMessage(String message, Object o) throws IOException {
		if (message.equals("Friends")){
			Usuari usuari = (Usuari) o;
			amicDAO aDAO = new amicDAO();
			ArrayList<Usuari> a = aDAO.getAmics(usuari);
			Message messageResposta = new Message(a, "FriendsResposta");
			objectOut.writeObject(messageResposta);
		} else if (message.equals("getAllGames")) {
			partidaDAO pDao = new partidaDAO();
			ArrayList<Partida> p = pDao.getAllPartides();
			objectOut.writeObject(new Message(p, "allGamesReply"));
		} else if (message.equals("getAllRunningGames")) {
			objectOut.reset();
			partidaDAO pDao = new partidaDAO();
			ArrayList<Partida> p = pDao.getRunningPartides();
			objectOut.writeObject(new Message(p, "allGamesReply"));
		}else if(message.equals("updateSpectators")){

		}
	}


	public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
		return dateToConvert.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}

	private ObjectOutputStream getOutChannel() {
		return objectOut;
	}


}
