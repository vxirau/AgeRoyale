package src.Controller;

import src.Invite;
import src.Message;
import src.Model.Network.UserService;
import src.Partida;
import src.Usuari;
import src.View.MenuView;
import src.View.WaitingRoomView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

public class MenuController {

    private MenuView view;
    private UserService uService;
    private Usuari user;

    //Controllers
    private ConfigController configController;
    private TropesController tropesController;
    private MainController mainController;
    private FriendsController friendsController;
    private RoomsController roomsController;
    private ArrayList<Usuari> requests;
    private ArrayList<Usuari> requested;

    //Listener
    private WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            Message message = new Message(user, "Logout");
            uService.sendLogout(message);
        }
    };


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

        view.setUsuari(user);
        view.setuService(userService);
        view.addWindowListener(windowListener);

        initControllers();

        view.setMenuController(this);

        initControllersViews();

    }

    private void initControllers() {
        configController = new ConfigController(user, uService, this);
        tropesController = new TropesController(user);
        mainController = new MainController(user);
        friendsController = new FriendsController(user, uService, this, requests, requested);
        roomsController = new RoomsController(user, uService, this);
    }

    private void initControllersViews() {
        configController.setConfigView(view.getConfigView());
        tropesController.setTropesView(view.getTropesView());
        mainController.setMainView(view.getMainView());
        friendsController.setFriendView(view.getFriendView());
        roomsController.setVista(view.getRoomListView());
        roomsController.setAllGames(roomsController.getAllGames());
    }

    public RoomsController getRoomsController() {
        return roomsController;
    }

    public ConfigController getConfigController() {
        return configController;
    }

    public FriendsController getFriendsController() {
        return friendsController;
    }

    public void updateViews() {
        try {
            view.updateViews();
        } catch (InterruptedException e){
        }
    }

    public int getRequestSize() {
        return requests.size();
    }

    public void inviteRecived(Invite invite) {
        view.setVisible(true);
        Object[] options = {"Acceptar", "Declinar"};
        String message = invite.getOrigen().getNickName() + " t'ha convidat a la partida " + invite.getPartida().getName() + ". Acceptes?";
        int n = JOptionPane.showOptionDialog(view,
                message,
                "Invitacio", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options,  options[0]);
        if (n==JOptionPane.YES_OPTION){
            roomsController.gameSelected(invite.getPartida(), roomsController.getRoomListView().getTotal(invite.getPartida()));
        }
    }
}
