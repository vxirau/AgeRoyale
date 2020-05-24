package src.View;

import src.Controller.GameController;
import src.Controller.MenuController;
import src.Controller.TroopController;
import src.Controller.TroopUpdate;
import src.Controller.WaitingController;
import src.Edifici;
import src.Message;
import src.Model.Network.UserService;
import src.Tropa;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
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
    public static BufferedImage[] bufferedImages1,bufferedImages2 ;
    public long elapsed = 0;


    private CopyOnWriteArrayList<TroopUpdate> updates;

    private  TroopController troopController;
    private static int xMousePosition;
    private static int yMousePosition;
    private  boolean rebut = false;
    private  boolean trobat = false;
    public static boolean deleted = false;
    private static BufferedImage image;
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
        mouseIsClicked = false;
        whichTroop = 10;
        this.deck = new Deck(width, height);
        //Creem el mapa i li donem la mesura en tiles ( en aquest cas, sera de 10 x 20)
        gameMap = new ImageMap(IMAGE_MAP_PATH);
        this.bufferedImages1 = new BufferedImage[10];
        this.bufferedImages2 = new BufferedImage[10];
        ompleBandera(bufferedImages1, bufferedImages2);
        this.setVisible(true);
        this.setResizable(false);
        //this.getContentPane().setLayout(new GridLayout(ROWS,COLUMNS));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
            for (Iterator<Tropa> iterator = tropes.iterator(); iterator.hasNext();) {
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
            }
            cont = 0;

        }

        //Copiem els grafics al joc
        System.arraycopy(pixels, 0, pixelsImage, 0, pixelsImage.length);
        //L'objecte g s'encarregara de dibuixar els grafics a la pantalla
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        for (Iterator<Tropa> iterator = tropes.iterator(); iterator.hasNext();) {
            Tropa tropa = iterator.next();
            /*try {
                imageh = ImageIO.read(GameView.class.getResource("/resources/verde.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            switch (tropa.getNumTorre()) {
                case 0:
                    moveBandera(elapsed,0,g);
                    //g.drawImage(imageh, 32, 128, null);
                    break;
                case 1:
                    moveBandera(elapsed,1,g);
                    //g.drawImage(imageh, 224, 128, null);
                    break;
                case 2:
                    moveBandera(elapsed,2,g);
                    //g.drawImage(imageh, 32, 448, null);
                    break;
                case 3:
                    moveBandera(elapsed,3,g);
                    //g.drawImage(imageh, 224, 448, null);
                    break;
            }
        }

        deck.showDeck(g, xMousePosition, yMousePosition, mouseIsClicked, whichTroop);

        g.dispose();
        //Mostrem el que tenim
        bufferStrategy.show();

    }

    public void moveBandera(long time, int num, Graphics g){

        if(time < 250){
            drawFlag(num,0,g);
        } else if(time >= 250 && time < 500){
            drawFlag(num,1,g);
        } else if(time >= 500 && time < 750){
            drawFlag(num,2,g);
        } else if(time >= 750 && time < 1000){
            drawFlag(num,3,g);
        }else if(time >= 1000 && time < 1250){
            drawFlag(num,4,g);
        }else if(time >= 1250 && time < 1500){
            drawFlag(num,5,g);
        }else if(time >= 1500 && time < 1750){
            drawFlag(num,6,g);
        }else if(time >= 1750 && time < 2000){
            drawFlag(num,7,g);
        }else if(time >= 2000 && time < 2250){
            drawFlag(num,8,g);
        }else if(time >= 2250 && time < 2500){
            drawFlag(num,9,g);
        }

    }

    public void drawFlag(int num, int index, Graphics g){
        switch (num){
            case 0:
                g.drawImage(bufferedImages1[index], 40, 96, null);
                break;
            case 1:
                g.drawImage(bufferedImages1[index], 248, 96, null);
                break;
            case 2:
                g.drawImage(bufferedImages2[index], 40, 512, null);
                break;
            case 3:
                g.drawImage(bufferedImages2[index], 248, 512, null);
                break;
        }
    }

    public void ompleBandera(BufferedImage[] b, BufferedImage[] c) {
        for (int i = 0; i < 10; i++) {

                try {
                    b[i] = ImageIO.read(GameView.class.getResourceAsStream("/resources/bandera_" + (i+1) + ".png"));
                    c[i] = ImageIO.read(GameView.class.getResourceAsStream("/resources/bandera_" + (i+11) + ".png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

        }

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
        final long[] startTime = {System.currentTimeMillis()};
        while(gameIsRunning){
            elapsed = System.currentTimeMillis() - startTime[0];

            if(elapsed > 2500){
                startTime[0] = System.currentTimeMillis();
            }

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

    public void registerController(GameController gameController){
        this.addMouseMotionListener(gameController);
        this.addMouseListener(gameController);
        this.addWindowListener(gameController.windowListener);
        //this.addWindowListener(w);
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
