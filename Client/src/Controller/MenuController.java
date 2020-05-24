package src.Controller;

import src.Invite;
import src.Message;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.MenuView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;


/**
* Classe destinada a controlar el menú del client
 * Les diferentes vistes es gestionen desde aqui pel que aquesta classe te instancies de tots els controllers
* */
public class MenuController {

    private MenuView view;
    private UserService uService;
    public Usuari user;

    //Controllers
    private ConfigController configController;
    private TropesController tropesController;
    private MainController mainController;
    private FriendsController friendsController;
    private RoomsController roomsController;
    private WaitingController waitingController;
    private ArrayList<Usuari> requests;
    private ArrayList<Usuari> requested;

    /**
    * WindowListener encarregat de escoltar quan la finestra es tanca, i fer lo pertinent per la part de servidor (logout)
    * */
    private WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            Object[] aux = {"Si", "No"};
            ImageIcon imagen = new ImageIcon(this.getClass().getResource("/resources/escut.png"));

            int confirmed = JOptionPane.showOptionDialog(view,"Estas segur que vols tancar la sessió?", "Logout",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, imagen, aux, aux[0]);

            if (confirmed == JOptionPane.YES_OPTION) {
                Message message = new Message(user, "Logout");
                uService.sendLogout(message);
                view.dispose();
                System.exit(0);
            }
        }
    };

    /**
    * Constructor de la casse
    * @param view vista grafica del menu per que el contrlador pugui fer-ne canvis
     * @param userService variable que permet la connexió del client amb el servidor
     * @parma usr usuari que ha iniciat sessió a la aplicació.
     * @param requests array de usuaris que han enviat una solicitud al nostre client
     * @param requested array de usuaris als que el nostre client ha enviat solicitud
     * @throws InterruptedException per detectar si hi ha algun problema al inciar el servidor ja que aquesta classe és la que inicia la comuncació
    * */
    public MenuController(MenuView view, UserService userService, Usuari usr, ArrayList<Usuari> requests, ArrayList<Usuari> requested) throws InterruptedException {
        this.view = view;
        this.uService = userService;
        this.user = usr;
        this.requests = requests;
        this.requested = requested;

        if(!userService.serviceStarted()){
           userService.startServerComunication();
        }
        userService.setMenuController(this);

        view.addWindowListener(windowListener);

        initControllers();

        view.setMenuController(this);

        initControllersViews();

    }


    /**
    * Encarregada de inicialitzar tots els controllers de totes les vistes que es mostren
    * */
    private void initControllers() {
        configController = new ConfigController(user, uService);
        tropesController = new TropesController(user);
        mainController = new MainController(user);
        friendsController = new FriendsController(user, uService, this, requests, requested);
        roomsController = new RoomsController(user, uService, this);
        waitingController = new WaitingController(roomsController, null, uService, user);
    }


    /**
    * Encarregada de passar totes les viestes als controllers inicialitzats prèviament
    * */
    private void initControllersViews() {
        configController.setConfigView(view.getConfigView());
        tropesController.setTropesView(view.getTropesView());
        mainController.setMainView(view.getMainView());
        friendsController.setFriendView(view.getFriendView(), view.getFriendRequestView());
        roomsController.setVista(view.getRoomListView());
        roomsController.setAllGames(roomsController.getAllGames());
        waitingController.setView(view.getWaitingRoomView());
    }


    /**
    * Retorna el controller de la vista de partides
     * @return roomsController variable de tipus RoomsController
    * */
    public RoomsController getRoomsController() {
        return roomsController;
    }

    /**
     * Retorna el controller de la vista de configuració
     * @return configController variable de tipus ConfigController
     * */
    public ConfigController getConfigController() {
        return configController;
    }

    /**
     * Retorna el controller de la vista d'amics
     * @return friendsController variable de tipus FriendsController
     * */
    public FriendsController getFriendsController() {
        return friendsController;
    }

    /**
     * Retorna el controller de la vista de waiting room
     * @return waitingController variable de tipus WaitingController
     * */
    public WaitingController getWaitingController() {
        return waitingController;
    }

    /**
    * Encarregada de actualitzar totes les vistes, es sol cridar quan hi ha nova informació per part del servidor
    * */
    public void updateViews() {
        view.updateViews();
    }


    /**
    * Retorna el tamany de la llista de solicituds
     * @return requestsSize un enter amb el tamany de la llista
    * */
    public int getRequestSize() {
        return requests.size();
    }


    /**
    * Encarregada de detectar quan es reb una solicitud a una partida en marxa. La petició s'inicia en una sala d'espera.
     * @param invite objecte que conté la informacío de la partida a la que s'ha convidat el client
    * */
    public void inviteRecived(Invite invite) {
        view.setVisible(true);
        Object[] options = {"Acceptar", "Declinar"};
        String message = invite.getOrigen().getNickName() + " t'ha convidat a la partida " + invite.getPartida().getName() + ". Acceptes?";
        int n = JOptionPane.showOptionDialog(view,
                message,
                "Invitacio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options,  options[0]);
        if (n==JOptionPane.YES_OPTION){
            roomsController.gameSelected(invite.getPartida());
        }
    }

    /**
    * Retorna la vista del menú
     * @return view variable de tipus MenuView
    * */
    public MenuView getView() {
        return view;
    }
}
