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

        initControllers();

        view.setMenuController(this);

        initControllersViews();

        view.setUsuari(usr);
        view.setuService(userService);
        view.addWindowListener(windowListener);
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


/*
    public void userUpdate(Usuari user){
        configController.setUsuari(user);
        tropesController.setUsuari(user);
        mainController.setUsuari(user);
        friendsController.setUsuari(user);
        roomsController.setUsuari(user);
    }

 */
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
