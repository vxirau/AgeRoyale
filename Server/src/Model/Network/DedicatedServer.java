package src.Model.Network;

import src.*;
import src.Controller.ControllerServer;
import src.Controller.TroopSController;
import src.Message;
import src.Model.Database.DAO.*;
import src.Partida;
import src.Tropa;
import src.Usuari;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Classe que representa un servidor dedicat per cada client. Hereda de thread ja que s'executa paralelament a l'execució del programa per poder-se fer servir en qualsevol moment
 */
public class DedicatedServer extends Thread {

	private boolean isOn;
	private Socket sClient;
	private ObjectInputStream dataInput;
	private DataOutputStream dataOut;
	private ObjectOutputStream objectOut;
	private CopyOnWriteArrayList<DedicatedServer> clients;
	private Integer inRoom = null;
	private TroopSController troopSController;
	private Usuari clientUser;
	private Server server;
	public static int cont = 0;
	public static int cont2 = 0;

	private static CopyOnWriteArrayList<Tropa> deleted;

	/**
	 * Contructor de la classe
	 * @param sClient client amb el que s'estableix la connexió
	 * @param clients clients connectats al servidor
	 * @param server servidor
	 * @throws IOException en cas que hi hagués algun error retorna aquesta excepció.
	 */
	public DedicatedServer(Socket sClient, CopyOnWriteArrayList<DedicatedServer> clients, Server server) throws IOException {
		this.isOn = false;
		this.sClient = sClient;
		this.clients = clients;
		this.server = server;
		dataInput = new ObjectInputStream(sClient.getInputStream());
		objectOut = new ObjectOutputStream(sClient.getOutputStream());
		this.troopSController = new TroopSController();
		this.deleted = new CopyOnWriteArrayList<>();
	}

	/**
	 * S'inicialitza la comunicació entre servidor i client
	 */
	public void startDedicatedServer() {
		// iniciem el servidor dedicat
		isOn = true;
		this.start();
	}

	/**
	 * S'atura la connexió entre servidor i client
	 */
	public void stopDedicatedServer() {
		// aturem el servidor dedicat
		this.isOn = false;
		this.interrupt();
	}

	/**
	 * El servidor rep els missatges del client corresponent i retorna una resposta
	 */
	public void run() {
		String in;
		String[] aux;

		try {
			while(isOn) {
				Message m = (Message) dataInput.readObject();
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
					server.broadcastClients();
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
						server.broadcastClients();
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
					server.removeDedicated(this);
					clients.remove(this);
					server.showClients();
					server.broadcastClients();
				} else if (m.getType().equals("PasswordUpdate")) {
					Usuari usuari = (Usuari) m.getObject();
					usuariDAO uDAO = new usuariDAO();
					uDAO.updatePass(usuari);
				} else if (m.getType().equals("getRequests")) {
					Usuari usuari = (Usuari) m.getObject();
					requestsDAO aDAO = new requestsDAO();
					ArrayList<Usuari> friendRequests = aDAO.getFriendRequests(usuari);
					Message messageResposta = new Message(friendRequests, "requestsReply");
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
				} else if (m.getType().equals("Friends")) {
					Usuari usuari = (Usuari) m.getObject();
					amicDAO aDAO = new amicDAO();
					ArrayList<Usuari> a = aDAO.getAmics(usuari);
					Message messageResposta = new Message(a, "FriendsResposta");
					objectOut.writeObject(messageResposta);
				} else if (m.getType().equals("removeFriend")) {
					ArrayList<Usuari> users = (ArrayList<Usuari>) m.getObject();
					amicDAO aDAO = new amicDAO();
					aDAO.removeAmic(users.get(0), users.get(1));
					ArrayList<Usuari> a = aDAO.getAmics(users.get(0));
					Message messageResposta = new Message(a, "FriendsResposta");
					objectOut.writeObject(messageResposta);
				} else if (m.getType().equals("Tropa update")) {
					Tropa t = (Tropa) m.getObject();
					if(t.getVida() <= 0){
						deleted.add(t);
					} else {
						if (t.getTroopType() == 0 || t.getTroopType() == 1) {
							t = troopSController.moveOffensiveTroop(t, t.getxVariation(), t.getyVariation(), cont);
							cont++;
							//objectOut.reset();
						}
					}
					/*else if(t.getTroopType() == 3){
						troopSController.bombExplosion(t,cont2);
						cont2++;
					}*/
					Message mresposta = new Message(t, "Tropa resposta");
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
					//Ja el fas amb el broadcast
					//Message messageResposta = new Message(a, "FriendsResposta");
					//objectOut.writeObject(messageResposta);
					Message messageResposta2 = new Message(rDAO.getFriendRequests(users.get(0)), "requestsReplyUpdate");
					objectOut.writeObject(messageResposta2);
					server.broadcastClients();
				}else if(m.getType().equals("removeRequest")){
					ArrayList<Usuari> users = (ArrayList<Usuari>) m.getObject();
					requestsDAO rDAO = new requestsDAO();
					rDAO.removeRequest(users.get(0), users.get(1));
				}else if(m.getType().equals("denyRequest")){
					ArrayList<Usuari> users = (ArrayList<Usuari>) m.getObject();
					requestsDAO rDAO = new requestsDAO();
					rDAO.denyRequest(users.get(0), users.get(1));
					server.broadcastClients();
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
					if(!pDAO.hasPlayerOne(p) &&  p.getJugadors().size() > 0) { //TODO: descobrir que fa aixo aqui -> if(pDAO.hasPlayerOne(p) && p.getIdPartida() != pDAO.getPlayerOne(p)) : versio original
						pDAO.addPlayerOne(p, p.getJugadors().get(0));
					}else if (!pDAO.hasPlayerTwo(p) && p.getJugadors().size() > 1){
						pDAO.addPlayerTwo(p, p.getJugadors().get(1));
					}
					inRoom = p.getIdPartida();
					server.broadcastClients();
				}else if(m.getType().equals("newSpectator")){
					spectatorDAO sDAO = new spectatorDAO();
					Partida p = (Partida)m.getObject();
					for(Usuari u : p.getEspectadors()){
						if(!sDAO.isSpectating(p, u)){
							sDAO.addSpectator(p, u);
						}
					}
					inRoom = p.getIdPartida();
					server.broadcastClients();
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
					inRoom = null;
					server.broadcastClients();
				}else if(m.getType().equals("Invite")) {
					Invite invite = (Invite) m.getObject();
					server.broadcastInvite(invite);
				} else if(m.getType().equals("GetTropesStats")) {
					Message message = new Message(new tropesDAO(){}.getAllTropes(),"SetTropesStats");
					objectOut.writeObject(message);
				} else if(m.getType().equals("add tropa")){
                    Tropa t = (Tropa) m.getObject();
                    tropaPartidaDAO pDAO = new tropaPartidaDAO();
                    pDAO.addTropa(t);
                    statsDAO sDAO = new statsDAO();

                    if(t.getTroopType() != -1) {
                    	String type = "";
						if (t.getTroopType() == 0) type = "Skeleton";
						if (t.getTroopType() == 1) type = "Goblin";
						if (t.getTroopType() == 2) type = "Bomb";
						if (t.getTroopType() == 3) type = "Wizard";
						sDAO.updateUsedTroop(clientUser.getIdUsuari(), type);
					}
                } else if(m.getType().equals("checkID")){
                    Tropa troop = new Tropa();
                    boolean trobat = false;
                    CopyOnWriteArrayList<Tropa> vistes = (CopyOnWriteArrayList<Tropa>) m.getObject();
                    tropaPartidaDAO pDAO = new tropaPartidaDAO();
                    ArrayList<Tropa> tropes = pDAO.getTropesPartida(inRoom);

                    if (deleted.size() % 2 == 0) {
                    	if (tropes.size() > (vistes.size() + (deleted.size() / 2))) {
                    		troop = tropes.get(tropes.size() - 1);
                    		cont2++;
                    	}
					}

                    Message message = new Message(troop,"tropesCheck");
                    objectOut.writeObject(message);


					/*tropaPartidaDAO pDAO = new tropaPartidaDAO();
					ArrayList<Tropa> tropes = pDAO.getTropesPartida(10);

					if(!tropes.isEmpty()) {
						Message message = new Message(tropes.get(0), "tropesCheck");
						objectOut.writeObject(message);
					}*/

                }else if(m.getType().equals("startGame")){
					Partida p = (Partida) m.getObject();
					server.broadcastStartGame(p);
				} else if(m.getType().equals("Edificis")){
					ArrayList<Edifici> edificiDef = (ArrayList<Edifici>) m.getObject();
					troopSController.setEdificiDef(edificiDef);
					System.out.println("HE LLEGADOO");
				}
            }
		} catch (IOException | ClassNotFoundException | ParseException e1 ){
			e1.printStackTrace();
			// en cas derror aturem el servidor dedicat
			stopDedicatedServer();
			// eliminem el servidor dedicat del conjunt de servidors dedicats
			clients.remove(this);
			// invoquem el metode del servidor que mostra els servidors dedicats actuals
			server.showClients();

		}

	}

	/**
	 * Converteix una data de tipus LocalDate a Date
	 * @param dateToConvert data a convertir de tipus LocalDate
	 * @return dateToConvert retorna la data de tipus Date
	 */
	public Date convertToDateViaInstant(LocalDate dateToConvert) {
		return java.util.Date.from(dateToConvert.atStartOfDay()
				.atZone(ZoneId.systemDefault())
				.toInstant());
	}

	/**
	 * Missatges que envia el client al servidor, i el servidor respon. Són missatges privats perquè són els encarregats de dur a terme les broadcast.
	 * @param message missatge rebut per part del client
	 * @throws IOException en cas que hi hagués algun error retorna aquesta excepció.
	 */
	public void privateMessage(String message) throws IOException {

		if (message.equals("Friends") && this.clientUser != null){
			Usuari usuari = this.clientUser;
			amicDAO aDAO = new amicDAO();
			ArrayList<Usuari> a = aDAO.getAmics(usuari);
			Message messageResposta = new Message(a, "FriendsResposta");
			objectOut.writeObject(messageResposta);
		} else if (message.equals("getAllRunningGames")) {
			objectOut.reset();
			partidaDAO pDao = new partidaDAO();
			ArrayList<Partida> p = pDao.getRunningPartides();
			objectOut.writeObject(new Message(p, "allGamesReply"));
		}else if(message.equals("updateWaitingRoom")){
			if(inRoom != null){
				partidaDAO pDAO = new partidaDAO();
				Partida p = pDAO.getPartida(inRoom);
				objectOut.writeObject(new Message(p, "updateWaiting"));
			}
		}
	}

	/**
	 * El servidor rep un missatge que correspon a les invitacions per part d'un client a un altre
	 * @param invite invitació corresponent
	 */
	public void inviteMessage(Invite invite) {
		if (clientUser != null && clientUser.getIdUsuari() == invite.getDesti().getIdUsuari()){
			try {
				partidaDAO pDAO = new partidaDAO();
				Invite inviteToSend = new Invite(invite.getOrigen(), invite.getDesti(), pDAO.getPartida(invite.getPartida().getIdPartida()));
				objectOut.writeObject(new Message(inviteToSend, "InviteRecived"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * El servidor rep un missatge per part del clinet de inicialitzar la partida, i el servidor respon
	 * @param partida Partida a iniciar
	 */
	public void startGameMessage(Partida partida) {
		if (clientUser != null && inRoom != null && partida.getIdPartida() == inRoom && (partida.getJugadors().get(0).getIdUsuari() == clientUser.getIdUsuari() || partida.getJugadors().get(1).getIdUsuari() == clientUser.getIdUsuari())){
			try {
				objectOut.writeObject(new Message(null, "StartGameAsPlayerRecived"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (clientUser != null && inRoom != null && partida.getIdPartida() == inRoom){
			try {
				objectOut.writeObject(new Message(null, "StartGameAsSpectatorRecived"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
