package src.Controller;

import src.Model.Network.UserService;
import src.Usuari;
import src.View.FriendView;

public class FriendsController {

    private FriendView friendView;

    private Usuari usuari;
    private UserService uService;

    public FriendsController(Usuari usr, UserService userService) {
        this.usuari = usr;
        this.uService = userService;
    }

    public void setFriendView(FriendView friendView) {
        this.friendView = friendView;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }
}
