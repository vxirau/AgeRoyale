package src.Controller;

import src.Invite;
import src.Message;
import src.Model.Network.UserService;
import src.Partida;
import src.Usuari;
import src.View.MenuView;
import src.View.WaitingRoomView;

import javax.swing.*;
import java.awt.event.*;

public class WaitingController implements ActionListener {

    private RoomsController roomsController;
    private Partida p;
    private WaitingRoomView view;
    private UserService userService;
    private Usuari usuari;

    public WaitingController(RoomsController roomsController, Partida p, UserService uService, Usuari usr){
        this.roomsController = roomsController;
        this.p = p;
        this.userService = uService;
        this.usuari = usr;
    }

    public void setView(WaitingRoomView view) {
        this.view = view;
    }

    public void updateGame(Partida p){
        this.p = p;
        view.setPartida(p);
        view.initAll();
        view.setController(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();
        if (btn.getText().equals("Start Game")) {
            Message m = new Message(p, "startGame");
            userService.sendStartGame(m, roomsController);
            roomsController.setStartGame(p);
        }
        if (btn.getText().equals("Go back")) {
            roomsController.getMenuController().getView().invokeAdjustViews(MenuView.CREAPARTIDA);
            Message m = new Message(usuari, "userLeft");
            userService.sendObject(m);

        }

        /*if(e == null){
            try {
                p.setIdPartida(10);
                gv = roomsController.startGame(total,0, p, this,false);
                view.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else  //if(((JButton)e.getSource()).getText().equals("Start Game")){
            try {
                //p.setIdPartida(10);
                gv = roomsController.startGame(p, this);
                view.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        //}*/

    }

    public void inviteFriend(Usuari f) {
        Invite invite = new Invite(usuari, f, p);
        Message m = new Message(invite,"Invite");
        userService.sendObject(m);
    }
}
