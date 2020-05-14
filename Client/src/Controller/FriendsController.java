package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.FriendRequest;
import src.View.FriendView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FriendsController implements ActionListener{

    private FriendView friendView;

    private MenuController menuController;

    private Usuari usuari;
    private UserService uService;
    private ArrayList<Usuari> friends;
    private ArrayList<Usuari> requests;

    private String cerca;

    @Override
    public void actionPerformed(ActionEvent e) {
        String boto = ((JButton) e.getSource()).getText();
        if(boto.equals("Amics")){
            FriendRequest r = new FriendRequest(this, requests);
            r.setVisible(true);
        }
    }

    public MouseListener listenerCercaAmic = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            Message mes = new Message(friendView.getJtfSearchAmic().getText(), "FindFriend");
            uService.sendFriendSearch(mes, FriendsController.this);
        }
    };

    public KeyListener listenerDelTextField = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            cerca = FriendsController.this.friendView.getJtfSearchAmic().getText();
            if (cerca.equals("")){
                resetMessage();
                System.out.println("Vas liat");
            }
        }
    };

    public FriendsController(Usuari usr, UserService userService, MenuController menuCtrl, ArrayList<Usuari> requests) {
        this.usuari = usr;
        this.uService = userService;
        this.menuController = menuCtrl;
        this.requests = requests;
    }


    public void removeFriend(Usuari user){
        boolean eliminar=false, ok=true;
        int a= JOptionPane.showConfirmDialog(friendView, "Vols eliminar aquest amic?");
        if(a==JOptionPane.YES_OPTION){
            eliminar=true;
        }else if(a==JOptionPane.NO_OPTION){
            eliminar=false;
        }else{
            ok=false;
        }

        if(ok && eliminar){
            ArrayList<Usuari> parella = new ArrayList<>();
            parella.add(usuari);
            parella.add(user);
            Message mes = new Message(parella, "removeFriend");
            uService.sendGetFriends(mes, this, false);
        }
    }

    public void setFriendView(FriendView friendView) {
        this.friendView = friendView;
        friendView.setControllers(listenerDelTextField, listenerCercaAmic, this);
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public synchronized void resetMessage() {
        Message m = new Message(usuari, "Friends");
        uService.sendGetFriends(m, this, true);
    }

     public void setFriends(ArrayList<Usuari> amics) throws InterruptedException {
        this.friends = amics;
        friendView.setAmics(amics);
        menuController.updateViews();
        friendView.setControllers(listenerDelTextField, listenerCercaAmic, this);
        friendView.getJtfSearchAmic().setText(cerca);
     }

    public void setAmicsUsuari(ArrayList<Usuari> amicsUsuari) {
        usuari.setAmics(amicsUsuari);
        friendView.setAmicsUsuari(amicsUsuari);
    }

    public void requestFriend(Usuari u){
        ArrayList<Usuari> users = new ArrayList<>();
        users.add(this.usuari);
        users.add(u);
        int a= JOptionPane.showConfirmDialog(friendView, "Vols acceptar aquesta solicitud?");
        if(a==JOptionPane.YES_OPTION){
            Message m = new Message(users, "acceptRequest");
            uService.sendFriendSearch(m, this);
        }else if(a==JOptionPane.NO_OPTION){
            Message m = new Message(users, "removeRequest");
            uService.sendFriendSearch(m, this);
        }


    }


}
