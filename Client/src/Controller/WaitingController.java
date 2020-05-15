package src.Controller;

import src.Model.Network.UserService;
import src.Partida;
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

    public WaitingController(int total, RoomsController roomsController, Partida p, WaitingRoomView w, UserService uService){
        this.roomsController = roomsController;
        this.total = total;
        this.view = w;
        this.p = p;
        this.userService = uService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JButton)e.getSource()).getText().equals("Start Game")){
            try {
                gv = roomsController.startGame(total,0, p, this);
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
            roomsController.getMenuView().setVisible(true);
        }else if(j instanceof GameView){
            view.setVisible(false);
            gv.setVisible(false);
            //TODO: BANNEJAR USUARI SI ES ABANS DORA
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
}
