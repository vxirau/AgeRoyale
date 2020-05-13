package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Tropa;
import src.Usuari;
import src.View.Deck;
import src.View.GameView;
import src.View.Sprite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class GameController implements MouseListener, MouseMotionListener, Runnable, ActionListener {

    private GameView gameView;
    private boolean mouseIsClicked;
    private int whichTroop;
    private Deck deck;
    private UserService uService;

    public GameController(GameView gameView,UserService userService) throws IOException {
        this.gameView = gameView;
        this.mouseIsClicked = false;
        this.deck = new Deck(this.gameView, this.gameView.getWidth(), this.gameView.getHeight());
        this.uService = userService;

        //userService.startServerComunication();//TODO: comentar

        gameView.setGameController(this);

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
                selectTroopFromDeck(whichTroop);
                //gameView.selectTroopFromDeck(this.whichTroop);
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
                invokeTroop(this.whichTroop);
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
        gameView.updateMouse(e.getX(), e.getY(), mouseIsClicked);
    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }


    public void selectTroopFromDeck(int whichTroop){
        gameView.setWhichTroop(whichTroop);
        deck.selectTroop(whichTroop);
    }



    public void invokeTroop(int whichTroop){
        switch (whichTroop){
            case 0:
                Tropa skeleton = new Tropa(gameView.getGameMap(), gameView.getxMousePosition(), gameView.getyMousePosition(), Sprite.SKELETON_BACK);
                gameView.getTropes().add(skeleton);
                break;
            case 1:
                Tropa goblin = new Tropa(gameView.getGameMap(), gameView.getxMousePosition(), gameView.getyMousePosition(), Sprite.GOBLIN_BACK);
                gameView.getTropes().add(goblin);
                break;
            case 2:
                Tropa wizard = new Tropa(gameView.getGameMap(), gameView.getxMousePosition(), gameView.getyMousePosition(), Sprite.MAGIC_TOWER);
                gameView.getTropes().add(wizard);
                break;
            case 3:
                Tropa bomb = new Tropa(gameView.getGameMap(), gameView.getxMousePosition(), gameView.getyMousePosition(), Sprite.BOMB);
                gameView.getTropes().add(bomb);
                break;
            default:
                break;
        }
    }

}
