package src.View;

import src.Controller.GameController;
import src.Controller.MenuController;
import src.Controller.TroopController;
import src.Controller.TroopUpdate;
import src.Controller.WaitingController;
import src.Message;
import src.Model.Network.UserService;
import src.Tropa;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameView extends JFrame implements Runnable, Serializable {

    private static Thread thread;
    private final int[] pixels;
    private final int width;
    private final int height;
    public static final int ROWS = 20;
    public static final int COLUMNS = 10;
    private static final int DECK_SPACE = 106;
    private static volatile boolean gameIsRunning = false;
    private GameController gameController;
    private static boolean sendcheck = true;
    private int flag = 0;
    public static int ident = 0;


    private CopyOnWriteArrayList<TroopUpdate> updates;
    private  TroopController troopController;
    private static int xMousePosition;
    private static int yMousePosition;
    private  boolean rebut = false;
    private  boolean trobat = false;
    public static boolean deleted = false;
    private static BufferedImage image;
    private static Tropa tropa;
    private static int cont = 0;

    //Variable per accedir a la imatge a partir dels seus pixels
    private static int[] pixelsImage;

    private CopyOnWriteArrayList<Tropa> tropes;
    //private ArrayList<Tropa> troops;

    private static GameMap gameMap;
    private boolean mouseIsClicked;

    private Deck deck;
    private int whichTroop;



    private static final String IMAGE_MAP_PATH  = "/resources/pixels_map.png";


    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public GameView() throws IOException {



        this.width = 32 * COLUMNS;
        this.height = 32 * ROWS + DECK_SPACE;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.pixelsImage = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        this.pixels = new int[width * height];
        this.tropes = new CopyOnWriteArrayList<>();
        //this.troops = new ArrayList<>();
        this.updates = new CopyOnWriteArrayList<>();
        this.tropa = new Tropa();
        mouseIsClicked = false;
        whichTroop = 10;
        this.deck = new Deck(width, height);


        //Creem el mapa i li donem la mesura en tiles ( en aquest cas, sera de 10 x 20)

        gameMap = new ImageMap(IMAGE_MAP_PATH);
        this.setVisible(true);
        this.setResizable(false);
        //this.getContentPane().setLayout(new GridLayout(ROWS,COLUMNS));
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(width, height);
        this.setLocationRelativeTo(null);

        //gameMap.showMap(0, 0, this);


    }





    /*public void update(){
        if(tropes.size() > 0){
            for(int i = 0; i < tropes.size(); i++){
                tropes.get(i).update();
            }
        }

    }*/

    //Metode que ens dibuixa els tiles del nostre mapa
    public void drawTile(int compensX, int compensY, Tile tile){

        for(int i = 0; i < tile.getSprite().getSide(); i++){
            int yPosition = i + compensY;

            for(int j = 0; j < tile.getSprite().getSide(); j++){
                int xPosition = j + compensX;
                if(xPosition < -tile.getSprite().getSide() || xPosition >= width || yPosition < -tile.getSprite().getSide() || yPosition >= height){
                    break;
                }
                pixels[xPosition + yPosition * width] = tile.getSprite().pixels[i + j * tile.getSprite().getSide()];
            }
        }
    }


    //Metode que ens dibuixa les tropes al nostre mapa
    public void drawTroop(float compensX, float compensY, Tropa troop){
        for(int i = 0; i < troop.getSprite().getSide(); i++){
            int yPosition = (int) (i + compensY);
            int count = 0;
            for(int j = 0; j < troop.getSprite().getSide(); j++){
                int xPosition = (int) (j + compensX);
                if(xPosition < -troop.getSprite().getSide() || xPosition >= width || yPosition < -troop.getSprite().getSide() || yPosition >= height){
                    break;
                }
                int troopPixelColor = troop.getSprite().pixels[i + j * troop.getSprite().getSide()];
                //Si el color del pixel del jugador es diferent que el rosa utilitzat de fons al spritesheet, el dibuixem
                if(troopPixelColor != 0xffff00ff){
                    pixels[xPosition + yPosition * width] = troopPixelColor;
                }
            }
        }
    }

    public synchronized void startGame(){
        gameIsRunning = true;
        thread = new Thread(this, "GameGraphics");
        thread.start();
    }

    public synchronized void stopGame() throws InterruptedException {
        gameIsRunning = false;
        thread.join();
    }

    public void updateGame(){

    }


    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }




        public synchronized void showGraphics(){
        //Creem un buffer per tal de dibuixar els grafics en segon pla
        BufferStrategy bufferStrategy = getBufferStrategy();
        if(bufferStrategy == null){
            createBufferStrategy(2);
            return;
        }

        gameMap.showMap(0, 0, this);

        if(tropes.size() > 0) {
            for (Iterator<Tropa> iterator = tropes.iterator(); iterator.hasNext(); ) {
                Tropa tropa = iterator.next();

                TroopUpdate update = new TroopUpdate(troopController, this);

                if (tropa.isOn()) {

                    update.startThread(tropa, cont,true);
                    tropa.setOn(false);
                    update.getT().start();
                    updates.add(update);

                }

                update.catchTroop(tropa,cont);

                if (!updates.isEmpty() && updates.size() == tropes.size()) {
                    updates.get(cont).setTropa(tropa);
                    troopController.show(tropa);
                }

                cont++;


                //VERSIÓ 1
                 /*try {
                    Thread.sleep(160/tropes.size());
                } catch (Exception e) {
                    System.out.println(e);
                }

                synchronized (String.class) {

                    troopController.update(tropes.get(i), i);

            }*/

                //VERSIÓ 2
               /*if (troops.size() > 0) {
                    for(Tropa t: troops){
                        if(t.equals(tropes.get(i))){
                           trobat = true;
                        } /*else {

                            troopController.update(tropes.get(i), i);
                            trobat = false;
                        }
                    }

                    if(!trobat) {
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        troopController.update(tropes.get(i), i);
                        //trobat = false;
                        troops.add(tropes.get(i));
                    } else {
                        trobat = false;
                    }

                } else {
                    troopController.update(tropes.get(i), i);
                    troops.add(tropes.get(i));
                }*/

                //VERSIO 3
                /*if(tropa == null){
                    troopController.update(tropes.get(i), i);
                    tropa = tropes.get(i);
                } else {
                    if(rebut){
                        troopController.update(tropes.get(i), i);
                        rebut = false;
                        tropa = tropes.get(i);
                    }
                }*/


            }
            cont = 0;
        }

        //Copiem els grafics al joc
        System.arraycopy(pixels, 0, pixelsImage, 0, pixelsImage.length);

        //L'objecte g s'encarregara de dibuixar els grafics a la pantalla
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        deck.showDeck(g, xMousePosition, yMousePosition, mouseIsClicked, whichTroop);
        g.dispose();
        //Mostrem el que tenim
        bufferStrategy.show();

    }


    @Override
    public void run() {

        final int NS_PER_SECOND = 1000000000;
        final byte FPS = 30;
        final double NS_PER_FRAME = NS_PER_SECOND/FPS;
        long updateReference = System.nanoTime();
        double elapsedTime;
        double delta = 0;


        requestFocus();


        while(gameIsRunning){

            //Message message = new Message(this, "Game Refresh");
            final long loopStart = System.nanoTime();

            elapsedTime = loopStart - updateReference;
            updateReference = loopStart;

            delta += elapsedTime/NS_PER_FRAME;

            while(delta >= 1){
                //updateGame();


                delta--;
            }

            synchronized (Integer.class) {
                showGraphics();
            }

            synchronized (Tropa.class) {
                if (sendcheck) {
                    gameController.sendCheck();
                    sendcheck = false;
                }
            }



            //showGraphics();


        }
    }

    public void registerController(GameController gameController, WaitingController w){
        this.addMouseMotionListener(gameController);
        this.addMouseListener(gameController);
        // TODO: this.addWindowListener(w);
    }

    public  int getxMousePosition() {
        return xMousePosition;
    }

    public  void setxMousePosition(int xMousePosition) {
        GameView.xMousePosition = xMousePosition;
    }

    public  int getyMousePosition() {
        return yMousePosition;
    }

    public  void setyMousePosition(int yMousePosition) {
        GameView.yMousePosition = yMousePosition;
    }

    public void updateMouse(int xMousePosition, int yMousePosition, boolean mouseIsClicked){
        this.xMousePosition = xMousePosition;
        this.yMousePosition = yMousePosition;
        this.mouseIsClicked = mouseIsClicked;
    }



    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        GameView.gameMap = gameMap;
    }

    public int getWhichTroop() {
        return whichTroop;
    }

    public void setWhichTroop(int whichTroop) {
        this.whichTroop = whichTroop;
    }


    public void setGameController(GameController gameController) {
        this.gameController = gameController;

    }

    public void setTroopController(TroopController troopController) {
        this.troopController = troopController;

    }



    public CopyOnWriteArrayList<Tropa> getTropes() {
        return tropes;
    }

    public void setTropes(CopyOnWriteArrayList<Tropa> tropes) {
        this.tropes = tropes;
    }

    public  boolean isRebut() {
        return rebut;
    }

    public void setRebut(boolean rebut) {
        this.rebut = rebut;
    }

    public static boolean isGameIsRunning() {
        return gameIsRunning;
    }

    public static void setGameIsRunning(boolean gameIsRunning) {
        GameView.gameIsRunning = gameIsRunning;
    }

    private void updateServer(){


    }

    public static boolean isSendcheck() {
        return sendcheck;
    }

    public static void setSendcheck(boolean sendcheck) {
        GameView.sendcheck = sendcheck;
    }


    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public CopyOnWriteArrayList<TroopUpdate> getUpdates() {
        return updates;
    }

    public void setUpdates(CopyOnWriteArrayList<TroopUpdate> updates) {
        this.updates = updates;
    }

    public static boolean isDeleted() {
        return deleted;
    }

    public static void setDeleted(boolean deleted) {
        GameView.deleted = deleted;
    }
}
