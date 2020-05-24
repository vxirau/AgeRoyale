package src.Model.Network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import src.*;
import src.Controller.*;
import src.Message;
import src.Partida;
import src.Tropa;
import src.Usuari;
import src.View.*;
import src.View.LoginView;

import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class UserService extends Thread{
  	private Socket socket;
	private ObjectOutputStream doStream;
	private ObjectInputStream doInput;
	private DataInputStream dInput;
	private boolean isOn = false;
	private boolean found = false;
	private Object[] options = {"Entèsos"};
  	private Path current = Paths.get("./Server/resources/config.json");
  	private String arxiu = current.toAbsolutePath().toString();
	private boolean flag;

	private LoginViewController loginViewController;
	private RegisterViewController registerViewController;
	private ConfigController configController;
	private MenuController menuController;
	private FriendsController friendsController;
	private RoomsController roomsController;
	private TroopController troopController;
	private WaitingController waitingController;
	private GameController gameController;


	public void setMenuController(MenuController menuController) {
		this.menuController = menuController;
	}


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

	public void startServerComunication() {
		// iniciem la comunicacio amb el servidor
		this.start();
		isOn = true;
	}

	public void stopServerComunication()  {
		// aturem la comunicacio amb el servidor
		this.isOn = false;
		this.interrupt();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void showMessage(String alerta){
		JOptionPane.showOptionDialog(new JFrame(), alerta,"Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
	}

	public boolean serviceStarted(){
		return isOn;
	}




	public void run() {
		while (isOn) {
			try {


				Message jelow = (Message) doInput.readObject();

				//System.out.println("Arriba a client: " + jelow.getType());
				if (jelow.getType().equals("REGISTER_OK")) {
					LoginView lview = new LoginView();
					LoginViewController controller = new LoginViewController(lview, UserService.this);
					lview.loginViewsetListener(controller);
					registerViewController.setView(false);
					lview.setVisible(true);
					Usuari user = (Usuari) jelow.getObject();
					lview.setUsuari(user.getNickName());
					lview.setPassword(user.getPassword());
				} else if (jelow.getType().equals("allGamesReply") && roomsController!=null) {
					ArrayList<Partida> p = (ArrayList<Partida>)jelow.getObject();
					roomsController.setAllGames(p);
				} else if (jelow.getType().equals("Login resposta")) {
					if (jelow.getObject() != null) {
						Usuari loginU = (Usuari) jelow.getObject();
						if(loginU.getIdUsuari() == -30 ){
							loginViewController.userIsBanned(loginU);
						}else{
							loginViewController.loginSuccessful((Usuari) jelow.getObject());
						}
					} else {
						loginViewController.loginNotSuccessful();
					}
				} else if(jelow.getType().equals("UserPKUpdatesResposta")){
					String resposta = (String) jelow.getObject();
					if (resposta.equals("UserPKUpdates_KO")){
						configController.canviNotSuccessful();
					} else if (resposta.equals("UserPKUpdates_OK")){
						configController.canviSuccessful();
					}
				} else if(jelow.getType().equals("FriendsResposta") && friendsController!=null) {
					ArrayList<Usuari> amics = (ArrayList<Usuari>) jelow.getObject();
					friendsController.setFriends(amics);
                    if (flag) {
                        friendsController.setAmicsUsuari(amics);
                    }
                    this.flag = false;
        		} else if(jelow.getType().equals("Tropa resposta")){
					Tropa t = (Tropa) jelow.getObject();
					if(t != null) {
                        if (t.getVida() > 0) {
                        	if(t.getTroopType() == 2){
                        		if(t.getVida() < 1000){
									troopController.deleteTropa(t);
								} else {
									troopController.getTropa(t);
									//troopController.show(t);
									troopController.setAccept(true);
								}
							} else {
								troopController.getTropa(t);
								//troopController.show(t);
								troopController.setAccept(true);
							}

                        } else {

							/*or(int i = 0; i < troopController.getGameView().getTropes().size(); i++){
								if(found){
									troopController.getGameView().getUpdates().get(i).setIndex(i-1);
									System.out.println("La he trobat al mediooo");
								}
								if(troopController.getGameView().getTropes().get(i).equals(t)){
									found = true;
								}
							}
							found = false;*/
							troopController.deleteTropa(t);
                        }
                    }

					//troopController.getGameView().setSendcheck(true);
				} else if(jelow.getType().equals("FindFriendResposta")){
                	friendsController.setFriends((ArrayList<Usuari>) jelow.getObject());
				}else if(jelow.getType().equals("requestsReply")){
					loginViewController.onRequestsRecieved((ArrayList<Usuari>) jelow.getObject());
				} else if(jelow.getType().equals("requestsReplyUpdate")) {
					ArrayList<Usuari>  requests = (ArrayList<Usuari>) jelow.getObject();
					friendsController.setRequests(requests);
				}/*else if(jelow.getType().equals("tropesCheck")){

					Tropa t = (Tropa) jelow.getObject();
					if(t.getWhichSprite() != null) {
						t.setIdPartida(10);
						t.setOn(true);
						if(t.getWhichSprite().equals("BOMB") ) {
                            t.setyPosition(610 - t.getyPosition());
                            t.setxPosition(285 - t.getxPosition());
                        } else if(t.getWhichSprite().equals("SKELETON_BACK") || t.getWhichSprite().equals("GOBLIN_BACK")){
                            t.setyPosition(590 - t.getyPosition());
                            t.setxPosition(294 - t.getxPosition());
                        } else if(t.getWhichSprite().equals("MAGIC_TOWER")){
                            t.setyPosition(590 - t.getyPosition());
                            t.setxPosition(284 - t.getxPosition());
                        }
						t.setyVariation(2);
						t.setDefaultY(10);
						t.setGameMap(gameController.getGameView().getGameMap());
						if (t.getWhichSprite().equals("SKELETON_BACK")) {
							t.setSprite(Sprite.SKELETON_BACK);
							t.setSprites(Sprite.SKELETON_BACK);
							t.setNumTorre(-1);
							gameController.getGameView().getTropes().add(t);
						} else if(t.getWhichSprite().equals("GOBLIN_BACK")){
							t.setSprite(Sprite.GOBLIN_BACK);
							t.setSprites(Sprite.GOBLIN_BACK);
							t.setNumTorre(-1);
							gameController.getGameView().getTropes().add(t);
						} else if(t.getWhichSprite().equals("MAGIC_TOWER")){
							t.setyVariation(0);
							t.setSprite(Sprite.MAGIC_TOWER);
							t.setSprites(Sprite.MAGIC_TOWER);
							t.setNumTorre(-1);
							gameController.getGameView().getTropes().add(t);
						} else if(t.getWhichSprite().equals("BOMB")){
							t.setyVariation(0);
							t.setSprite(Sprite.BOMB);
							t.setSprites(Sprite.BOMB);
							t.setNumTorre(-1);
							gameController.getGameView().getTropes().add(t);
						}
					}
					gameController.getGameView().setSendcheck(true);
				}*/else if(jelow.getType().equals("updateWaiting")){
					Partida p= (Partida)jelow.getObject();
					waitingController.updateGame(p);
				}else if(jelow.getType().equals("InviteRecived")){
					Invite invite = (Invite) jelow.getObject();
					menuController.inviteRecived(invite);
				} else if(jelow.getType().equals("StartGameAsPlayerRecived")){
					roomsController.startGame(roomsController.startGamePartida, true);
				} else if(jelow.getType().equals("StartGameAsSpectatorRecived")){
					roomsController.startGame(roomsController.startGamePartida, false);
				} else if(jelow.getType().equals("SetTropesStats")){
					gameController.setTropaStatic((ArrayList<Tropa>) jelow.getObject());
				}else if(jelow.getType().equals("Tropa Answer")){

					Tropa t = (Tropa) jelow.getObject();

					gameController.getGameView().getTroopsToAdd().add(t);



				}



			} catch (IOException | ClassNotFoundException e ) {
				e.printStackTrace();
				stopServerComunication();
				System.out.println("*** ESTA EL SERVIDOR EN EXECUCIO? ***");
			}

		}
	}

	public void sendRegister(Object user, RegisterViewController registerViewCtrl) {
		this.registerViewController = registerViewCtrl;
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

	public void sendObject(Object object) {
		try{
			this.doStream.reset();
			this.doStream.writeObject(object);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void sendGetPartides(Object message, RoomsController roomsController) {
		this.roomsController = roomsController;
		try{
			this.doStream.reset();
			this.doStream.writeObject(message);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}
	public void sendWaitingRoom(Object message, WaitingController waitingController) {
		this.waitingController = waitingController;
		try{
			this.doStream.reset();
			this.doStream.writeObject(message);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void sendLogin(Object message, LoginViewController loginViewController) {
		try{
			this.loginViewController = loginViewController;
			this.doStream.reset();
			this.doStream.writeObject(message);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void sendLogout(Object message){
		try{
			this.doStream.reset();
			this.doStream.writeObject(message);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}


	public void sendPassUpdate(Object message) {
		try{
			this.doStream.reset();
			this.doStream.writeObject(message);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void sendUserPKUpdate(Message message, ConfigController configController) {
		try{
			this.configController = configController;
			this.doStream.reset();
			this.doStream.writeObject(message);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

  public void sendGetFriends(Message message, FriendsController friendsCtrl, boolean flag) {
		try{
			this.flag = flag;
			this.friendsController = friendsCtrl;
			this.doStream.reset();
			this.doStream.writeObject(message);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void sendFriendSearch(Message message, FriendsController friendsCtrl, boolean flag) {
		try{
			this.flag = flag;
			this.friendsController = friendsCtrl;
			this.doStream.reset();
			this.doStream.writeObject(message);
		} catch (IOException e) {
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void sendTropa(Message message, TroopController troopController){
		try{

			this.troopController = troopController;
			this.doStream.reset();
			this.doStream.writeObject(message);
		} catch (IOException e) {
			e.printStackTrace();
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void addTropa(Message m){

		try {
			this.doStream.reset();
			this.doStream.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void sendCheck(Message m, GameController gcontroller){
		try{
			this.gameController = gcontroller;
			this.doStream.writeObject(m);
			this.doStream.reset();
		} catch (IOException e) {
			e.printStackTrace();
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void sendStartGame(Message m, RoomsController roomsController) {
		try{
			this.roomsController = roomsController;
			this.doStream.reset();
			this.doStream.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

	public void sendEdificis(Message m){
		try{
			this.doStream.reset();
			this.doStream.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

    public void sendsGetTropes(Message m, GameController controller) {
        try{
            this.gameController = controller;
            this.doStream.reset();
            this.doStream.writeObject(m);
        } catch (IOException e) {
            e.printStackTrace();
            stopServerComunication();
            showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
        }
    }
}
