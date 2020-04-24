package src.Controller;

import src.View.GameView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.reflect.InvocationTargetException;

public class GameController extends MouseAdapter implements WindowListener {

    private GameView view;

    public GameController(GameView view){
        this.view = view;
    }


    public void mouseClicked(MouseEvent event) {
        String[] aux;
        String quin = ((JPanel)event.getSource()).getName();
        aux = quin.split("-");
        int aux1 = Integer.parseInt(aux[0]);
        int aux2 = Integer.parseInt(aux[1]);
        view.updateGrid(aux1,aux2);

    }


    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

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
