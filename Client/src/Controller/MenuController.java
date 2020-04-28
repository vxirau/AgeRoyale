package src.Controller;

import src.Model.Network.UserService;
import src.Usuari;
import src.View.MenuView;

public class MenuController{

    private MenuView view;
    private UserService uService;
    private Usuari user;

    //Controllers
    private ConfigController configController;
    private TropesController tropesController;
    private MainController mainController;
    private FriendsController friendsController;
    private RoomsController roomsController;


    public MenuController(MenuView view, UserService userService, Usuari usr) {
        this.view = view;
        this.uService = userService;
        this.user = usr;

        if(!userService.serviceStarted()){
           //userService.startServerComunication(); //TODO: descomentar
        }

        initControllers();

        view.setMenuController(this);

        initControllersViews();

        view.setUsuari(usr);
        view.setuService(userService);
    }

    private void initControllers() {
        configController = new ConfigController(user, uService);
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
}
