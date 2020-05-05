package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Partida;
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
	private UserService uService;
	private ArrayList<Partida> allGames;
	private ActionListener actionListenerCreaPartida = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			boolean ok =true;
			boolean privacitat=false;

			if(((JButton)e.getSource()).getText().equals("Crear nova partida")){
				String m = JOptionPane.showInputDialog("Enter game name:");
				if(m!=null){
					int a=JOptionPane.showConfirmDialog(vista, "Do you want your game to be private?");
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
	}

	public synchronized void initMessage() {
		Message m = new Message(null, "getAllGames");
		uService.sendGetPartides(m, this);
	}

	public static void startGame(int num, int privacitat, Partida p){
		GameView gView = null;
		try {
			gView = new GameView();
		} catch (IOException rer) {
			rer.printStackTrace();
		}
		gView.startGame();
		GameController controller = new GameController(gView);
		gView.registerController(controller);
		gView.setVisible(true);
	}

	public void setAllGames(ArrayList<Partida> allGames) {
		if (allGames != null) {
			this.allGames = allGames;
		} else {
			this.allGames = new ArrayList<>();
		}
		vista.setAllGames(this.allGames);
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
}
