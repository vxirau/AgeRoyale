package src.Controller;

import src.Invite;
import src.Message;
import src.Model.Network.UserService;
import src.Partida;
import src.Usuari;
import src.View.GameView;
import src.View.WaitingRoomView;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class WaitingController implements ActionListener, WindowListener {

    private RoomsController roomsController;
    private int total;
    private Partida p;
    private WaitingRoomView view;
    private GameView gv;
    private UserService userService;
    private Usuari usuari;

    public WaitingController(int total, RoomsController roomsController, Partida p, WaitingRoomView w, UserService uService, Usuari usr){
        this.roomsController = roomsController;
        this.total = total;
        this.view = w;
        this.p = p;
        this.userService = uService;
        this.usuari = usr;
    }

    public void updateGame(Partida p){
        view.setPartida(p);
        view.initAll(true);
        view.setController(this);
        System.out.println("hola");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ESTOY HASTA LOS COJONES");
        if(e == null){
            try {
                p.setIdPartida(10);
                gv = roomsController.startGame(total,0, p, this,false);
                view.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if(((JButton)e.getSource()).getText().equals("Start Game")){
            try {
                p.setIdPartida(10);
                gv = roomsController.startGame(total,0, p, this,false);
                view.setVisible(false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        JFrame j = (JFrame)e.getSource();
        if(j instanceof  WaitingRoomView){
            view.setVisible(false);
            Message m = new Message(usuari, "userLeft");
            userService.sendObject(m);
            roomsController.getMenuView().setVisible(true);
        }else if(j instanceof GameView){
            view.setVisible(false);
            gv.setVisible(false);
            //TODO: BANNEJAR SI S'HA PIRAT ABANS DORA

            roomsController.getMenuView().setVisible(true);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public void inviteFriend(Usuari f) {
        Invite invite = new Invite(usuari, f, p);
        Message m = new Message(invite,"Invite");
        userService.sendObject(m);
    }
}
