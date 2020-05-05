package src.Controller;

import src.View.GameView;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GameController extends MouseAdapter implements KeyListener, MouseListener {

    private GameView view;
    private int aux1;
    private int aux2;
    private static final int nTeclas = 120;
    private final  boolean[] teclas = new boolean[nTeclas];

    public boolean up;
    public boolean down;
    public boolean left;
    public boolean right;


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
        System.out.println("PUTA");

        /*System.out.println(event.getX());
        System.out.println(event.getY());
        try {
            view.updateGrid(event.getX(),event.getY());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    public void update(){
        up = teclas[KeyEvent.VK_W];
        down = teclas[KeyEvent.VK_S];
        left = teclas[KeyEvent.VK_A];
        right = teclas[KeyEvent.VK_D];

    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        teclas[e.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        teclas[e.getKeyCode()] = false;
    }


}
