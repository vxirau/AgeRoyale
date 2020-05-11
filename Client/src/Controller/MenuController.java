package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.MenuView;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

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


    public MenuController(MenuView view, UserService userService, Usuari usr) {
        this.view = view;
        this.uService = userService;
        this.user = usr;

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
        friendsController = new FriendsController(user, uService);
        roomsController = new RoomsController(user, uService);
    }

    private void initControllersViews() {
        configController.setConfigView(view.getConfigView());
        tropesController.setTropesView(view.getTropesView());
        mainController.setMainView(view.getMainView());
        friendsController.setFriendView(view.getFriendView());
        roomsController.setVista(view.getRoomListView());
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

}
