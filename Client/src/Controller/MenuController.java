package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.MenuView;

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

    //Listener
    private WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            System.out.println("closiing");
            Message message = new Message(user, "Logout");
            uService.sendLogout(message);
        }
    };


    public MenuController(MenuView view, UserService userService, Usuari usr, ArrayList<Usuari> requests) throws InterruptedException {
        this.view = view;
        this.uService = userService;
        this.user = usr;
        this.requests = requests;

        if(!userService.serviceStarted()){
           userService.startServerComunication(); //TODO: descomentar
        }

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
        friendsController = new FriendsController(user, uService, this, requests);
        roomsController = new RoomsController(user, uService); //todo: this
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

    public void updateViews() throws InterruptedException {
        view.updateViews();
    }

    public int getRequestSize() {
        return requests.size();
    }
}
