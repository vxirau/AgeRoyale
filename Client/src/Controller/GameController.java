package src.Controller;

import src.View.GameView;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GameController extends MouseAdapter implements WindowListener {

    private GameView view;
    private int aux1;
    private int aux2;

    public GameController(GameView view){
        this.view = view;
    }


    public void mouseClicked(MouseEvent event) {

        String[] aux;
        String quin = ((JPanel)event.getSource()).getName();
        aux = quin.split("-");
        aux1 = Integer.parseInt(aux[0]);
        aux2 = Integer.parseInt(aux[1]);
        Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (aux1 >= 19) {
                    ((Timer)e.getSource()).stop();
                }
                view.updateGrid(aux1,aux2);
                aux1++;

            }
        });
        timer.start();

        /*System.out.println(event.getX());
        System.out.println(event.getY());
        try {
            view.updateGrid(event.getX(),event.getY());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

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
