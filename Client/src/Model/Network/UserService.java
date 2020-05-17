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
import src.View.ViewRegistre;

import javax.swing.*;
import java.io.*;
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
    private RegisterViewController registerViewController;
    private RoomsController roomsController;
    private LoginViewController loginViewController;
    private ConfigController configController;
    private FriendsController friendsController;
    private TroopController troopController;
	private WaitingController waitingController;
	private MenuController menuController;

	public void setMenuController(MenuController menuController) {
		this.menuController = menuController;
	}

	private boolean flag;
    private GameController gameController;


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
					JOptionPane.showOptionDialog(new JFrame(), "DE SUPER PUTA MARE SOCI", "Congratulacions", JOptionPane.PLAIN_MESSAGE, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
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
					troopController.getTropa(t);
					troopController.show(t);
					troopController.setAccept(true);
				} else if(jelow.getType().equals("Bomba resposta")){
					Tropa t = (Tropa) jelow.getObject();
					troopController.getTropa(t);

					troopController.show(t);


				}else if(jelow.getType().equals("Destruir bomba")) {
					Tropa t = (Tropa) jelow.getObject();
					troopController.destroyTroop(t);

				} else if(jelow.getType().equals("FindFriendResposta")){
                	friendsController.setFriends((ArrayList<Usuari>) jelow.getObject());
				}else if(jelow.getType().equals("requestsReply")){
					loginViewController.onRequestsRecieved((ArrayList<Usuari>) jelow.getObject());
				} else if(jelow.getType().equals("tropesCheck")){

					Tropa t = (Tropa) jelow.getObject();
					if(t.getWhichSprite() != null) {
						if (t.getWhichSprite().equals("SKELETON_BACK")) {
							t.setSprite(Sprite.SKELETON_BACK);
							t.setGameMap(gameController.getGameView().getGameMap());
							t.setIdPartida(10);
							t.setOn(true);
							t.setSprites(Sprite.SKELETON_BACK);
							//t.setTroopDirection('s');
							t.setyPosition(596 - t.getyPosition());
							t.setxPosition(300 - t.getxPosition());
							t.setyVariation(2);
						}
						gameController.getGameView().getTropes().add(t);

						//gameController.getGameView().setFlag(2);

					}
					gameController.getGameView().setSendcheck(true);
				}else if(jelow.getType().equals("updateWaiting")){
					Partida p= (Partida)jelow.getObject();
					waitingController.updateGame(p);
				}else if(jelow.getType().equals("InviteRecived")){
					Invite invite = (Invite) jelow.getObject();
					menuController.inviteRecived(invite);
				}


			} catch (IOException | ClassNotFoundException | InterruptedException e ) {
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

	public void sendUserPKUpdate(Message message, ConfigController configCntroller) {
		try{
			this.configController = configCntroller;
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

	public void sendFriendSearch(Message message, FriendsController friendsCtrl) {
		try{
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
			//this.doStream.reset();
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
			this.doStream.reset();
			this.doStream.writeObject(m);
		} catch (IOException e) {
			e.printStackTrace();
			stopServerComunication();
			showMessage("ERROR DE CONNEXIÓ AMB EL SERVIDOR (missatge no enviat)");
		}
	}

}
