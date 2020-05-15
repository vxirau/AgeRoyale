package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Partida;
import src.Tropa;
import src.Usuari;
import src.View.GameView;
import src.View.MenuView;
import src.View.RoomListView;

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
					try {
						p.startPartida();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
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

	public ArrayList<Partida> getAllGames() {
		return allGames;
	}
}
