package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.FriendRequestView;
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
    private ArrayList<Usuari> requested;

    private String cerca;

    @Override
    public void actionPerformed(ActionEvent e) {
        String boto = ((JButton) e.getSource()).getText();
        if(boto.equals("Friend Request")){
            FriendRequestView r = new FriendRequestView(this, requests);
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
            }
        }
    };

    public FriendsController(Usuari usr, UserService userService, MenuController menuCtrl, ArrayList<Usuari> requests, ArrayList<Usuari> requested) {
        this.usuari = usr;
        this.uService = userService;
        this.menuController = menuCtrl;
        this.requests = requests;
        this.requested = requested;
    }


    public void removeFriend(Usuari user){
        boolean eliminar=false, ok=true;
        ArrayList<Usuari> parella = new ArrayList<>();
        parella.add(usuari);
        parella.add(user);
        if(this.usuari.getAmics().contains(user)){
            int a= JOptionPane.showConfirmDialog(friendView, "Vols eliminar aquest amic?");
            if(a==JOptionPane.YES_OPTION){
                eliminar=true;
            }else if(a==JOptionPane.NO_OPTION){
                eliminar=false;
            }else{
                ok=false;
            }
            if(ok && eliminar){
                Message mes = new Message(parella, "removeFriend");
                uService.sendGetFriends(mes, this, false);
            }
        }else{
            if(requestHasUser(user)){
                int a= JOptionPane.showConfirmDialog(friendView, "Vols enviar una solicitud a: " + user.getNickName());
                if(a==JOptionPane.YES_OPTION){
                    eliminar=true;
                }else if(a==JOptionPane.NO_OPTION){
                    eliminar=false;
                }else{
                    ok=false;
                }
                if(ok && eliminar){
                    Message mes = new Message(parella, "sendRequest");
                    requested.add(user);
                    uService.sendGetFriends(mes, this, false);
                }
            }else{
                JOptionPane.showMessageDialog(friendView, "Ja has enviat una solicitud a aquest usuari!");
            }

        }



    }

    private boolean requestHasUser(Usuari u){
        boolean has = true;
        for(Usuari user: requested) {
            if(user.getIdUsuari() == u.getIdUsuari()){
                has = false;
            }
        }
        return has;
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

     public void setFriends(ArrayList<Usuari> amics) {
        if(friendView.getTextField().getText().toString().length()>0){
            clearFriendList(amics);
        }
        this.friends = amics;
        friendView.setAmics(amics);
        menuController.updateViews();
        friendView.setControllers(listenerDelTextField, listenerCercaAmic, this);
        friendView.getJtfSearchAmic().setText(cerca);
     }

     public void clearFriendList(ArrayList<Usuari> amics){
        for(int i=0; i<amics.size() ;i++){
            if(amics.get(i).getIdUsuari() == this.usuari.getIdUsuari()){
                amics.remove(i);
            }
            if(this.usuari.getAmics().contains(amics.get(i))){
                amics.remove(i);
            }
        }
     }

    public void setAmicsUsuari(ArrayList<Usuari> amicsUsuari) {
        usuari.setAmics(amicsUsuari);
        friendView.setAmicsUsuari(amicsUsuari);
        friendView.getJtfSearchAmic().setText(cerca);
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
            Message m = new Message(users, "denyRequest");
            uService.sendFriendSearch(m, this);
        }


    }


}
