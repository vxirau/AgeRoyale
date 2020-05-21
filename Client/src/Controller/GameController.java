package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Tropa;
import src.View.Deck;
import src.View.GameView;
import src.View.MenuView;
import src.View.Sprite;

import java.awt.event.*;
import java.io.IOException;

public class GameController implements MouseListener, MouseMotionListener, Runnable, ActionListener {

    private GameView gameView;
    private boolean mouseIsClicked;
    private int whichTroop;
    private Deck deck;
    private UserService uService;
    private int id;
    private MenuController menuController;
    public WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
            super.windowClosed(e);
            menuController.getView().setVisible(true);
            menuController.getView().invokeAdjustViews(MenuView.MAIN);
            // ban usuaris
        }
    };

    public GameController(GameView gameView,UserService userService, MenuController menuController) throws IOException {
        this.gameView = gameView;
        this.mouseIsClicked = false;
        this.deck = new Deck(this.gameView.getWidth(), this.gameView.getHeight());
        this.uService = userService;
        this.menuController = menuController;

        gameView.setGameController(this);
    }

    @Override
    public void run() {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int troopX = 0;
        int y = 0;

        if(e.getY() > 620) {
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
            if(e.getY() <= 620){
                System.out.println("INVOCO TROPA BRO");
                if(e.getY() <= 355){
                    y = 355;
                } else {
                    y = e.getY();
                }
                gameView.updateMouse(e.getX(), y, mouseIsClicked);
                //Invocarem a la tropa si tenim l'or suficient
                if(gameView.getDeck().isEnoughGold(this.whichTroop)){
                    invokeTroop(this.whichTroop);
                    gameView.getDeck().updateUserGold(whichTroop);
                    mouseIsClicked = false;
                }

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
                skeleton.setSprites(Sprite.SKELETON_BACK);
                skeleton.setOn(true);
                skeleton.setIdPartida(10);
                skeleton.setWhichSprite("SKELETON_BACK");
                skeleton.setVida(10);
                skeleton.setAtac(7);
                gameView.getTropes().add(skeleton);
                Message m = new Message(skeleton, "add tropa");


                synchronized (Tropa.class){
                    uService.addTropa(m);
               }

                break;
            case 1:
                Tropa goblin = new Tropa(gameView.getGameMap(), gameView.getxMousePosition(), gameView.getyMousePosition(), Sprite.GOBLIN_BACK);
                goblin.setSprites(Sprite.GOBLIN_BACK);
                goblin.setOn(true);
                goblin.setIdPartida(10);
                goblin.setWhichSprite("GOBLIN_BACK");
                goblin.setVida(10);
                goblin.setAtac(2);
                gameView.getTropes().add(goblin);
                Message message = new Message(goblin, "add tropa");

                synchronized (Tropa.class){
                    uService.addTropa(message);
                }

                break;
            case 2:
                Tropa wizard = new Tropa(gameView.getGameMap(), gameView.getxMousePosition(), gameView.getyMousePosition(), Sprite.MAGIC_TOWER);
                wizard.setSprites(Sprite.MAGIC_TOWER);
                wizard.setyVariation(0);
                wizard.setOn(true);
                wizard.setIdPartida(10);
                wizard.setWhichSprite("MAGIC_TOWER");
                wizard.setAtac(3);
                wizard.setVida(20);
                gameView.getTropes().add(wizard);
                Message mwizard = new Message(wizard, "add tropa");

                synchronized (Tropa.class){
                    uService.addTropa(mwizard);
                }

                break;
            case 3:
                Tropa bomb = new Tropa(gameView.getGameMap(), gameView.getxMousePosition(), gameView.getyMousePosition(), Sprite.BOMB);
                bomb.setSprites(Sprite.BOMB);
                bomb.setyVariation(0);
                bomb.setOn(true);
                bomb.setIdPartida(10);
                bomb.setWhichSprite("BOMB");
                bomb.setAtac(11);
                bomb.setVida(1000);
                gameView.getTropes().add(bomb);
                Message mbomb = new Message(bomb, "add tropa");

                synchronized (Tropa.class){
                    uService.addTropa(mbomb);
                }
                break;
            default:
                break;
        }
    }

    public void sendCheck(){

        Message m = new Message(gameView.getTropes(),"checkID");

        synchronized (Tropa.class) {
            uService.sendCheck(m, this);
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }
}
