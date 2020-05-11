package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.FriendView;

import javax.swing.*;
import java.util.ArrayList;

public class FriendsController {

    private FriendView friendView;

    private Usuari usuari;
    private UserService uService;
    private ArrayList<Usuari> friends;

    public FriendsController(Usuari usr, UserService userService) {
        this.usuari = usr;
        this.uService = userService;
    }


		public void removeFriend(int i){
			boolean eliminar=false, ok=false;
			int a= JOptionPane.showConfirmDialog(friendView, "Vols eliminar aquest amic?");
			if(a==JOptionPane.YES_OPTION){
				eliminar=true;
			}else if(a==JOptionPane.NO_OPTION){
				eliminar=false;
			}else{
				ok=false;
			}

			if(ok){
				Message mes = new Message(i, "removeFriend");
				uService.sendPartida(mes);
			}
		}

    public void setFriendView(FriendView friendView) {
        this.friendView = friendView;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public synchronized void initMessage() {
            Message m = new Message(usuari, "Friends");
		    uService.sendGetFriends(m, this);
	   }

     public void setFriends(ArrayList<Usuari> amics) {
        this.friends = amics;
        friendView.setAmics(amics);
     }


}
