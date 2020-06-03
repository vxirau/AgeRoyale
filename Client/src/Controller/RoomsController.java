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
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


/**
* Controlador destinat al llistat de partides en curs. Desde aqui s'iniciarà també una partida quan sigui premuda a la vista.
* */
public class RoomsController {
	private RoomListView vista;
	private Usuari usuari;
	private UserService uService;
	private ArrayList<Partida> allGames;
	private MenuView menuView;
	private MenuController menuController;
	public Partida startGamePartida;


	/**
	* Objecte de tipus actionListener que assignarem a la vista per que quan es premi una partida surti els dialegs pertinents.
	* */
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


	/**
	* Constructor de la classe
	 * @param u usuari que ha inciat sessió al client
	 * @param uService variable que permet la comunicació amb el servidor per part del client
	 * @param menuCtrl controlador del menú. Es farà servir per actualitzar la informació del client quan hi hagi canvis
	* */
	public RoomsController(Usuari u, UserService uService, MenuController menuCtrl) {
		this.menuController = menuCtrl;
		this.usuari = u;
		this.uService = uService;
		if(!uService.serviceStarted()){
			uService.startServerComunication();
		}

	}

	/**
	* Envia el missatge al servidor per demanar que retorni la informació del tots els jocs que estan em marxa
	* */
	public synchronized void initMessage() {
		Message m = new Message(null, "getAllRunningGames");
		uService.sendGetPartides(m, this);
	}


	/**
	* Retorna la variable de tipus UserService
	 * @return uService variable de la classe anomenada uService
	* */
	public UserService getService() {
		return uService;
	}

	/**
	* Assigna a la partida que es passa desde el servidor al objecte partida de la classe
	 * @param partida variable de tipus partida que reb desde el servidor
	* */
	public void setStartGame(Partida partida){
		this.startGamePartida = partida;
	}


	/**
	* Encarregada de iniciar el joc
	 * @param p objecte partida que conté el id de la partida que ha de començar
	 * @param isPlayer boolea que indica si el usuari que s'uneix es jugador o espectador
	 * @return gView variable de tipus GameView que conté la informació de la finestra de la partida
	 * @throws IOException en cas que no s'hagi pogut crear bé el constructor de la GameView llençarà aquesta excepció
	* */
	public GameView startGame(Partida p, boolean isPlayer) throws IOException {

		GameView gView = null;
		try {
			gView = new GameView();
		} catch (IOException rer) {
			rer.printStackTrace();
		}
		GameController controller = new GameController(gView,uService,menuController, p, usuari, isPlayer);
		uService.sendsGetTropes(new Message(null, "GetTropesStats"), controller);
		controller.setId(p.getIdPartida());
		gView.registerController(controller);
		TroopController tcontrol = new TroopController(gView,uService);
		gView.setTroopController(tcontrol);
		gView.setVisible(true);
		menuController.getView().setVisible(false);
		return gView;
	}

	/**
	* Assigna la vista que es passa per parametre a la variable global
	 * @param v variable de tipus MenuView que s'assigna a la variable global de la classe
	* */
	public void setMenuView(MenuView v){
		this.menuView = v;
	}

	/**
	* Retorna la variable menuView de la classe
	 * @return menuView variable de tipus MenuView
	* */
	public MenuView getMenuView(){
		return this.menuView;
	}


	/**
	* Assigna la llista de partidas a les variables de la classe
	 * @param allGames llista de parties que ha retornat el servidor.
	* */
	public void setAllGames(ArrayList<Partida> allGames) {
		if (allGames != null) {
			this.allGames = allGames;
		} else {
			this.allGames = new ArrayList<>();
		}
		if (vista != null) {
			vista.setAllGames(this.allGames);
		}
		menuController.updateViews();
	}


	/**
	* Assigna la visibilitat del client a la vista a partir del booleano que li entra
	 * @param visible booleano que indica la visibilitat de la vista
	* */
	public void setClientVisible(boolean visible){
		menuView.setVisible(visible);
	}


	/**
	* Assigna la vista que li passen a la vista de la classe
	 * @param vista vista que reb desde el servei o algun altre controlador
	* */
	public void setVista(RoomListView vista) {
		this.vista = vista;
	}

	/**
	* Retorna el listener de la partida
	 * @return actionListenerCreaPartida variable de tipus ActionListener que s'assignarà a elements de UI
	* */
	public ActionListener getActionListenerCreaPartida() {
		return actionListenerCreaPartida;
	}


	/**
	* Assigna l'usuari al usuari de la classe
	 * @param usuari usuari que reb desde el servidor
	*/
	public void setUsuari(Usuari usuari) {
		this.usuari = usuari;
	}


	/**
	* Quan es selecciona una partida es crida a aquesta funció. Encarregada de detectar com vol iniciar la partida i entrar a la waiting room
	 * @param p partida que ha premut l'usuari
	* */
	public void gameSelected(Partida p){
		ImageIcon imagen = new ImageIcon(this.getClass().getResource("/resources/escut.png"));
		vista.setVisible(false);
		Object[] options = {"Jugador", "Espectador"};
		String missatge = "";
		boolean flag = true;
		setStartGame(p);
		if(p.getJugadors().size()<2){
			int n = JOptionPane.showOptionDialog(vista,
					"Vols entrar com a espectador o com a jugador?",
					"Partida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
					imagen, options,  options[0]);
			if(n==JOptionPane.YES_OPTION){
				boolean alredyPlayer = false;
				for (Usuari user:p.getJugadors() ) {
					if (user.getIdUsuari() == usuari.getIdUsuari()) {
						alredyPlayer = true;
						break;
					}
				}
				p.getEspectadors().removeIf(user -> user.getIdUsuari() == usuari.getIdUsuari());
				if (!alredyPlayer) p.getJugadors().add(this.usuari);

				missatge = "newPlayer";
			}else if(n==JOptionPane.NO_OPTION){
				if(p.getEspectadors() == null){
					p.setEspectadors(new ArrayList<>());
				}
				missatge = "newSpectator";

				boolean alredySpectator = false;
				for (Usuari user:p.getEspectadors() ) {
					if (user.getIdUsuari() == usuari.getIdUsuari()) {
						alredySpectator = true;
						break;
					}
				}
				p.getJugadors().removeIf(user -> user.getIdUsuari() == usuari.getIdUsuari());
				if (!alredySpectator) p.getEspectadors().add(this.usuari);

			} else {
				flag = false;
			}
			if(flag) {
				menuController.getWaitingController().updateGame(p);
				menuController.getView().invokeAdjustViews(MenuView.WAITINROOM);
				updateGameTable(p, missatge);

			}
		}else{
			if(p.getEspectadors() == null){
				p.setEspectadors(new ArrayList<>());
			}

			boolean alredySpectator = false;
			for (Usuari user:p.getEspectadors() ) {
				if (user.getIdUsuari() == usuari.getIdUsuari()) {
					alredySpectator = true;
					break;
				}
			}
			p.getJugadors().removeIf(user -> user.getIdUsuari() == usuari.getIdUsuari());
			if (!alredySpectator) p.getEspectadors().add(this.usuari);

			menuController.getWaitingController().updateGame(p);
			menuController.getView().invokeAdjustViews(MenuView.WAITINROOM);
			updateGameTable(p,"newSpectator");
		}

	}


	/**
	* Funció encarregada de retorna la vista del llistat de partides
	 * @return vista variable de tipus RoomListView
	* */
	public RoomListView getRoomListView() {
		return vista;
	}


	/**
	* Encarregada de retornar la llsita de partides que s'estan mostrant a la vista
	 * @return allGames llista de partides
	* */
	public ArrayList<Partida> getAllGames() {
		return allGames;
	}

	/**
	* encarregada de actualitzar la taula de partida en el servidor, envia el missatge de si el now jugador es espectador o no
	 * @param p partida a la que vol unir-se
	 * @param newPlayer com vol unir-se a aquesta partida si com a espectador o com a jugador
	* */
	public void updateGameTable(Partida p, String newPlayer) {
		Message m = new Message(p, newPlayer);
		uService.sendWaitingRoom(m, menuController.getWaitingController());
	}


	/**
	* Retorna el controlador del menu
	 * @return menuController variable te tipus MenuController
	* */
	public MenuController getMenuController() {
		return menuController;
	}
}
