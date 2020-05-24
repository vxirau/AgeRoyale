package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Usuari;
import src.View.FriendRequestView;
import src.View.FriendView;
import src.View.MenuView;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FriendsController implements ActionListener{

    private FriendView friendView;
    private FriendRequestView friendRequestView;

    private MenuController menuController;

    private Usuari usuari;
    private UserService uService;
    private ArrayList<Usuari> requests;
    private ArrayList<Usuari> requested;

    private String cerca;

    public MouseListener listenerCercaAmic = new MouseAdapter(){
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            Message mes = new Message(friendView.getJtfSearchAmic().getText(), "FindFriend");
            uService.sendFriendSearch(mes, FriendsController.this, false);
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

    /*
    @Override
    public void actionPerformed(ActionEvent e) {
        String boto = ((JButton) e.getSource()).getText();
        if(boto.equals("Friend Request")){
            FriendRequestView r = new FriendRequestView(this, requests);
            r.setVisible(true);
        }
    }

     */

    public void setRequests (ArrayList<Usuari> request){
        request.removeIf(usr -> usr.getIdUsuari() == -20);
        this.requests = request;
        friendRequestView.setRequests(requests);
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

    public void setFriendView(FriendView friendView, FriendRequestView friendRequestView) {
        this.friendView = friendView;
        this.friendRequestView = friendRequestView;
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
            uService.sendFriendSearch(m, this, true);
            requests.remove(u);
        }else if(a==JOptionPane.NO_OPTION){
            Message m = new Message(users, "denyRequest");
            uService.sendFriendSearch(m, this, true);
            requests.remove(u);
        }
        friendRequestView.setRequests(requests);
    }

    public ArrayList<Usuari> getRequests() {
        return requests;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if (button.getText().equals("Friend Request")){
            menuController.getView().invokeAdjustViews(MenuView.AMICSREQUEST);
        } else {
            menuController.getView().invokeAdjustViews(MenuView.AMICS);
        }
    }
}
