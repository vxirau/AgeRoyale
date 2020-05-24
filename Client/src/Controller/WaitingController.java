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


/**
* Controlador de la sala de espera a la que entra el jugador quan vol iniciar una partida. Implementa actionListener
* */
public class WaitingController implements ActionListener {

    private RoomsController roomsController;
    private Partida p;
    private WaitingRoomView view;
    private UserService userService;
    private Usuari usuari;


    /**
    * Constructor de la classe
     * @param roomsController controlador de la llista de partides en marxa
     * @param p partida a la que ha entrat l'usuari
     * @param uService servei que permet la connexió del client amb el servidor
     * @parma usr usuari que ha inciat sessió a la aplicació com a client.
    * */
    public WaitingController(RoomsController roomsController, Partida p, UserService uService, Usuari usr){
        this.roomsController = roomsController;
        this.p = p;
        this.userService = uService;
        this.usuari = usr;
    }


    /**
    * Assigna la vista de la sala de espera a la variable global
     * @param view variable de tipus WaitingRoomView
    * */
    public void setView(WaitingRoomView view) {
        this.view = view;
    }


    /**
    * Encarregada de actualitzar la informació de la partida a la base de dades quan entra un usuari a la sala d'espera
     * @param p partida a la que ha accedit
    * */
    public void updateGame(Partida p){
        this.p = p;
        view.setPartida(p);
        view.initAll();
        view.setController(this);
    }


    /**
    * Funció obligatoria ja que la classe implementa actionListener. Aquesta rebrà la informació de quin objecte ha interaccionat amb l'usuari
     * @param e variable que conté la informació del objecte premut
    * */
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

    }


    /**
    * Enviar el missatge al servidor de que es vol convidar un amic a la partida
     * @param f usuari que vol convidar a la partida
    * */
    public void inviteFriend(Usuari f) {
        Invite invite = new Invite(usuari, f, p);
        Message m = new Message(invite,"Invite");
        userService.sendObject(m);
    }
}
