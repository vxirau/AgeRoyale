package src.Controller;

import src.*;
import src.Model.Network.UserService;
import src.View.Deck;
import src.View.GameView;
import src.View.MenuView;
import src.View.Sprite;

import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;

/**
* Classe que controla la partida. Implementa un seguit de interficies per poder escoltar les diferentes accions del client
* */
public class GameController implements MouseListener, MouseMotionListener, Runnable, ActionListener {


    //Declaració de variables
    private GameView gameView;
    private boolean mouseIsClicked;
    private int whichTroop;
    private GameMetadata metadata;

    public GameMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(GameMetadata metadata) {
        this.metadata = metadata;
    }

    private Deck deck;
    private UserService uService;
    private int id;
    private MenuController menuController;
    private ArrayList<Tropa> tropaStatic;
    private Partida partida;
    private Usuari usuari;



    /**
    * WindowListener destinada a saber si s'ha tancat la finestr abans dora i bannejar en cas que calgui.
    * */
    public WindowListener windowListener = new WindowAdapter() {
        @Override
        public void windowClosed(WindowEvent e) {
            super.windowClosed(e);
            menuController.getView().setVisible(true);
            menuController.getView().invokeAdjustViews(MenuView.MAIN);
            // ban usuaris


        }
    };
    /**
     * Constructor de la classe.
     * @param gameView vista grafica del joc
     * @param userService servei que permet al client connectar-se amb el servidor
     * @param menuController controlador del menú. Emprat per actualitzar les vistes o informació que calguin
     * @param p partida que esta inicialitzada
     * @throws IOException en cas que hi hagués algun error retorna aquesta excepció.
     * */
    public GameController(GameView gameView,UserService userService, MenuController menuController, Partida p, Usuari usuari) throws IOException {



        this.gameView = gameView;
        this.gameView.setUser(usuari);
        this.mouseIsClicked = false;
        this.deck = new Deck(this.gameView.getWidth(), this.gameView.getHeight());
        this.uService = userService;
        this.menuController = menuController;
        this.partida = p;
        this.usuari = usuari;
        this.metadata = new GameMetadata();
        gameView.setGameController(this);
    }

    /**
     * Donat que aqesta classe implementa de runnable, ha de tenir la funció de run pertinent.
    * */
    @Override
    public void run() {

    }


    /**
    * Donat que la classe implement MouseListener, ha de implementar la funció pertinent a la interficie. Aquesta esta destinada a detectar quan el ratolí s'ha premut.
     * @param e MouseEvent que detecta la informació de on i quan s'ha premut el ratolí.
    * */
    @Override
    public void mouseClicked(MouseEvent e) {

        int troopX = 0;
        int y = 0;
        int x = 0;

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
                if(e.getX() >= 135 && e.getX() <= 180) {
                    x = 166;
                } else {
                    x = e.getX();
                }
                gameView.updateMouse(x, y, mouseIsClicked);
                //Invocarem a la tropa si tenim l'or suficient
                if(gameView.getDeck().isEnoughGold(this.whichTroop)){
                    invokeTroop(this.whichTroop);
                    gameView.getDeck().updateUserGold(whichTroop);
                    mouseIsClicked = false;
                }

            }
        }

    }


    /**
    * --
    * */
    @Override
    public void mousePressed(MouseEvent e) {


    }

    /**
     * --
     * */
    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * --
     * */
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /**
     * --
     * */
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * --
     * */
    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
    * Detecció de moviment del ratolí. Es fa servir per colocar la tropa a la vista.
     * @param e informació de la posició del ratolí
    * */
    @Override
    public void mouseMoved(MouseEvent e) {
        gameView.updateMouse(e.getX(), e.getY(), mouseIsClicked);
    }


    /**
     * --
     * */
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    /**
    * Funció encarregada de detectar quina tropa s'ha seleccionat de les tropes disponibles a la part inferior de la interficie gràfica.
     * @param whichTroop integer que conté l'id de la tropa seleccionada.
    * */
    public void selectTroopFromDeck(int whichTroop){
        gameView.setWhichTroop(whichTroop);
        deck.selectTroop(whichTroop);
    }
    /**
     * Encarregada de mostrar la tropa a la vista. Amb la tropa seleccionada, detecta quin sprite correspon i el coloca a la vista a la posició que s'hagués deixat.
     * @param whichTroop enter que representa el "id" de la tropa, es fa servir per detectar quina tropa és
     * */
    public synchronized void invokeTroop(int whichTroop){


        switch (whichTroop){
            case 0:

                Tropa skeleton = new Tropa(gameView.getGameMap(), gameView.getxMousePosition(), gameView.getyMousePosition(), Sprite.SKELETON_BACK, this.usuari);

                skeleton.setSprites(Sprite.SKELETON_BACK);
                skeleton.setOn(true);
                skeleton.setNumTorre(-1);
                skeleton.setIdPartida(partida.getIdPartida());
                skeleton.setWhichSprite("SKELETON_BACK");

                tropaStatic.forEach(tropa -> {if(tropa.getIdTropa() == 1) {skeleton.setVida(tropa.getVida()); skeleton.setAtac(tropa.getAtac());}} );
                skeleton.setInitialX(skeleton.getxPosition());
                skeleton.setInitialY(skeleton.getyPosition());
                gameView.getTropes().add(skeleton);


                Message m = new Message(skeleton, "add tropa");



                synchronized (Tropa.class){
                    uService.addTropa(m);
               }

                break;
            case 1:
                Tropa goblin = new Tropa(gameView.getGameMap(), gameView.getxMousePosition(), gameView.getyMousePosition(), Sprite.GOBLIN_BACK, this.usuari);
                goblin.setSprites(Sprite.GOBLIN_BACK);
                goblin.setOn(true);
                goblin.setNumTorre(-1);
                goblin.setIdPartida(partida.getIdPartida());
                goblin.setWhichSprite("GOBLIN_BACK");
                goblin.setInitialX(goblin.getxPosition());
                goblin.setInitialY(goblin.getyPosition());
                tropaStatic.forEach(tropa -> {if(tropa.getIdTropa() == 2) {goblin.setVida(tropa.getVida()); goblin.setAtac(tropa.getAtac());}} );
                goblin.setInitialX(goblin.getxPosition());
                goblin.setInitialY(goblin.getyPosition());
                gameView.getTropes().add(goblin);
                Message message = new Message(goblin, "add tropa");

                synchronized (Tropa.class){
                    uService.addTropa(message);
                }

                break;
            case 2:
                Tropa wizard = new Tropa(gameView.getGameMap(), gameView.getxMousePosition(), gameView.getyMousePosition(), Sprite.MAGIC_TOWER, this.usuari);
                wizard.setSprites(Sprite.MAGIC_TOWER);
                wizard.setyVariation(0);
                wizard.setOn(true);
                wizard.setNumTorre(-1);
                wizard.setIdPartida(partida.getIdPartida());
                wizard.setWhichSprite("MAGIC_TOWER");
                wizard.setInitialX(wizard.getxPosition());
                wizard.setInitialY(wizard.getyPosition());

                tropaStatic.forEach(tropa -> {if(tropa.getIdTropa() == 3) {wizard.setVida(tropa.getVida()); wizard.setAtac(tropa.getAtac());}} );


                gameView.getTropes().add(wizard);
                Message mwizard = new Message(wizard, "add tropa");

                synchronized (Tropa.class){
                    uService.addTropa(mwizard);
                }

                break;
            case 3:
                Tropa bomb = new Tropa(gameView.getGameMap(), gameView.getxMousePosition(), gameView.getyMousePosition(), Sprite.BOMB, this.usuari);
                bomb.setSprites(Sprite.BOMB);
                bomb.setyVariation(0);
                bomb.setOn(true);
                bomb.setNumTorre(-1);
                bomb.setIdPartida(partida.getIdPartida());
                bomb.setWhichSprite("BOMB");
                bomb.setInitialX(bomb.getxPosition());
                bomb.setInitialY(bomb.getyPosition());

                tropaStatic.forEach(tropa -> {if(tropa.getIdTropa() == 4) {bomb.setVida(tropa.getVida());bomb.setAtac(tropa.getAtac());}} );

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


    /**
    * Envia la comprovació del id al servidor
    * */
    public void sendCheck(){

        Message m = new Message(gameView.getTropes(),"checkID");

        synchronized (Tropa.class) {
            uService.sendCheck(m, this);
        }

    }

    /**
    * Encarregada de retornar el id del controller
     * @return id enter que té l'id desat.
    * */
    public int getId() {
        return id;
    }

    /**
    * Assigna el id de la classe al id que reb
     * @param id id enviat pel servidor o la vista
    * */
    public void setId(int id) {
        this.id = id;
    }


    /**
    * Encarregada de retornar la vista grafica
     * @return gameView retorna l'objecte GameView, la vista a la que esta assignat el controller
    * */
    public GameView getGameView() {
        return gameView;
    }


    /**
     * Assigna la game view a la game view que reb per paramtre
     * @param gameView vista grafica del joc
    * */
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }


    /**
    * Retorna la llista de tropes emprades a la partida.
     * @return tropaStatic llista de tropes.
    * */
    public ArrayList<Tropa> getTropaStatic() {
        return tropaStatic;
    }


    /**
    * Assigna la llista de tropes emprades a la partda a les que reb per paràmetre
     * @param tropaStatic llista de tropes enviades pel servidor
    * */
    public void setTropaStatic(ArrayList<Tropa> tropaStatic) {
        this.tropaStatic = tropaStatic;
        gameView.startGame();
    }
}
