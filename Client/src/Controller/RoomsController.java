package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Partida;
import src.Tropa;
import src.Usuari;
import src.View.GameView;
import src.View.MenuView;
import src.View.RoomListView;
import src.View.WaitingRoomView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RoomsController {
	private RoomListView vista;
	private Usuari usuari;
	private static UserService uService;
	private ArrayList<Partida> allGames;
	private ArrayList<Tropa> tropes;
	private static MenuView menuView;
	private WaitingController roomControl;

	private ActionListener actionListenerCreaPartida = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean ok =true;
			boolean privacitat=false;

			if(((JButton)e.getSource()).getText().equals("Crear nova partida")){
				String m = JOptionPane.showInputDialog("Introduir nom de la partida: ");
				if(m!=null){
					int a=JOptionPane.showConfirmDialog(vista, "Vols que la teva partida sigui privada?");
					if(a==JOptionPane.YES_OPTION){
						privacitat=false;
					}else if(a==JOptionPane.NO_OPTION){
						privacitat=true;
					}else{
						ok=false;
					}
				}else{
					ok=false;
				}
				if(ok){
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
					LocalDateTime now = LocalDateTime.now();
					Partida p = new Partida(m, dtf.format(now), privacitat, usuari.getNickName());
					Message mes = new Message(p, "roomCreate");
					uService.sendPartida(mes);
				}
			}
		}
	};

	public RoomsController(Usuari u, UserService uService) {
		this.usuari = u;
		this.uService = uService;
		if(!uService.serviceStarted()){
			uService.startServerComunication();
		}

	}

	public synchronized void initMessage() {
		Message m = new Message(null, "getAllRunningGames");
		uService.sendGetPartides(m, this);
	}

	public static UserService getService() {
		return uService;
	}


	public static GameView startGame(int num, int privacitat, Partida p, WaitingController w) throws IOException {
		GameView gView = null;
		try {
			gView = new GameView();
		} catch (IOException rer) {
			rer.printStackTrace();
		}
		gView.startGame();
		GameController controller = new GameController(gView,uService);
		gView.registerController(controller, w);
		TroopController tcontrol = new TroopController(gView,uService);

		GameView finalGView = gView;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				finalGView.registerController(controller, w);
				finalGView.setTroopController(tcontrol);
				finalGView.setVisible(true);
			}
		});

		//gView.setVisible(true);
		return finalGView;
	}

	public void setMenuView(MenuView v){
		this.menuView = v;
	}

	public MenuView getMenuView(){
		return this.menuView;
	}

	public void setAllGames(ArrayList<Partida> allGames) {
		if (allGames != null) {
			this.allGames = allGames;
		} else {
			this.allGames = new ArrayList<>();
		}
		if (vista != null) {
			vista.setAllGames(this.allGames);
		}
	}

	public static void setClientVisible(boolean visible){
		menuView.setVisible(visible);
	}

	public void setVista(RoomListView vista) {
		this.vista = vista;
	}

	public ActionListener getActionListenerCreaPartida() {
		return actionListenerCreaPartida;
	}

	public void setUsuari(Usuari usuari) {
		this.usuari = usuari;
	}


	public void gameSelected(Partida p, int total){
				ImageIcon imagen = new ImageIcon(this.getClass().getResource("/resources/escut.png"));
				vista.setVisible(false);
				Object[] options = {"Jugador", "Espectador"};
				String missatge = "";
				if(p.getJugadors().size()<2){
					int n = JOptionPane.showOptionDialog(vista,
							"Vols entrar com a espectador o com a jugador?",
							"Partida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
							imagen, options,  options[0]);
					if(n==JOptionPane.YES_OPTION){
						p.getJugadors().add(this.usuari);
						missatge = "newPlayer";

					}else if(n==JOptionPane.NO_OPTION){
						if(p.getEspectadors() == null){
							p.setEspectadors(new ArrayList<>());
						}
						missatge = "newSpectator";
						p.getEspectadors().add(this.usuari);
					}

					WaitingRoomView waitingRoom = new WaitingRoomView(p, this.usuari);
					roomControl = new WaitingController(total, this,p, waitingRoom, uService, this.usuari);
					waitingRoom.setController(roomControl);
					waitingRoom.initAll();
					waitingRoom.setVisible(true);
					updateGameTable(p,missatge);
					RoomsController.setClientVisible(false);
				}else{
					p.getEspectadors().add(this.usuari);

					WaitingRoomView waitingRoom = new WaitingRoomView(p, this.usuari);
					roomControl = new WaitingController(total, this,p, waitingRoom, uService, this.usuari);
					waitingRoom.setController(roomControl);
					waitingRoom.setVisible(true);
					waitingRoom.initAll();
					updateGameTable(p,"newSpectator");
					RoomsController.setClientVisible(false);
				}

	}


	public ArrayList<Partida> getAllGames() {
		return allGames;
	}

	public void updateGameTable(Partida p, String newPlayer) {
		Message m = new Message(p, newPlayer);
		uService.sendWaitingRoom(m, roomControl);
	}
}
