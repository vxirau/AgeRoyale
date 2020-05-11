package src.Controller;

import src.View.GameView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GameController implements MouseListener, MouseMotionListener, Runnable {

    private GameView gameView;
    private boolean mouseIsClicked;
    private int whichTroop;

    public GameController(GameView gameView){
        this.gameView = gameView;
        this.mouseIsClicked = false;

    }


    @Override
    public void run() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int troopX = 0;
        int y = 0;

        if(e.getY() > 630) {
            if(!mouseIsClicked){
                System.out.println("AHORA ESTA CLICADO");
                mouseIsClicked = true;
                troopX = e.getX();
                if(troopX >= 0 && troopX < 80){
                    this.whichTroop = 0;
                }
                if(troopX >= 80 && troopX < 1600){
                   this.whichTroop = 1;
                }
                if(troopX >= 160 && troopX < 240){
                    this.whichTroop = 2;
                }
                if(troopX >= 240 && troopX < 320){
                    this.whichTroop = 3;
                }

                gameView.updateMouse(e.getX(), e.getY(), mouseIsClicked);
                gameView.selectTroopFromDeck(this.whichTroop);
            }
        }
        if(mouseIsClicked){

            if(e.getY() <= 630){
                System.out.println("INVOCO TROPA BRO");
                if(e.getY() <= 355){
                    y = 355;
                } else {
                    y = e.getY();
                }
                gameView.updateMouse(e.getX(), y, mouseIsClicked);
                gameView.invokeTroop(this.whichTroop);
                mouseIsClicked = false;

            }


        }

    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

        System.out.println(e.getX() + ", " + e.getY());
        gameView.updateMouse(e.getX(), e.getY(), mouseIsClicked);
    }
}
