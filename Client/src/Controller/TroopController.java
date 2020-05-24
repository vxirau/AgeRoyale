package src.Controller;

import src.Edifici;
import src.Message;
import src.Model.Network.DedicatedServer;
import src.Model.Network.UserService;
import src.Tropa;
import src.View.Deck;
import src.View.GameView;
import src.View.Sprite;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;


/**
* Classe encarregada de controlar els moviments de la tropa a la vista.
* */
public class TroopController {

    private GameView gameView;
    private UserService uService;
    private Tropa troop;
    public int indice;
    private int cont = 0;
    private boolean accept = false;
    private float minDistance = Float.MAX_VALUE;
    public CopyOnWriteArrayList<Tropa> troops = new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Tropa> founds =  new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Tropa> detected =  new CopyOnWriteArrayList<>();


    /**
    * Constructor de la classe
     * @param gameView vista del joc
     * @param userService variable que permet la comunicació amb el servidor desde el client
    * */
    public TroopController(GameView gameView, UserService userService){
        this.gameView = gameView;
        this.uService = userService;


        gameView.setTroopController(this);

    }


    /**
    * Encarregada de detectar la informació de la ubicació de la tropa i de la seva sitaució al joc
     * @param tropa variable de tipus tropa amb tota la informació que necessita per funcionar
    * */
    public void checkTroopsStatus(Tropa tropa) {

        troops = detectedTroops(gameView.getTropes(), tropa);

        if (!troops.isEmpty()) {
            for (Tropa t : troops) {
                float c1 = tropa.getxPosition() - t.getxPosition();
                float c2 = tropa.getyPosition() - t.getyPosition();
                float distance = (float) Math.sqrt(c1 * c1 + c2 * c2);
                if (distance < minDistance) {
                    minDistance = distance;
                    troop = t;
                /*System.out.println("----------------------------------------------");
                System.out.println("Tropa mes propera: " + troop.getWhichSprite());
                System.out.println("Tropa agafada: " + t.getWhichSprite());
                System.out.println("distance: " + distance);
                System.out.println("----------------------------------------------");*/
                }
            }

            tropa.setVida(tropa.getVida() - troop.getAtac());
            tropa.setFighting(true);

            if(tropa.getDefaultY() < 320 && tropa.getVida() <= 0){

                if(gameView.getDeck().getGoldRectangle().getX() + 10 <= 280){
                    Double d = gameView.getDeck().getGoldRectangle().getWidth();
                    Integer i = d.intValue();
                    Double d2 =  gameView.getDeck().getGoldRectangle().getHeight();
                    Integer j = d2.intValue();
                    gameView.getDeck().getGoldRectangle().setBounds( gameView.getDeck().getGoldRectangle().x, gameView.getDeck().getGoldRectangle().y,   i+10 , j);
                }
            }

            if(tropa.getTroopType() == 2){
                while (cont < 3) {
                    bombExplosion(tropa, cont);
                    cont++;
                }
            }


        }
        founds.removeAll(founds);
        minDistance = Float.MAX_VALUE;

    }


    /**
    * Detecta tropes aprop de una tropa en concret.
     * @param tropes array de tropes que estan ara mateix a la partida
     * @param t troba al voltant de que volem trobar altres tropes
     * @return llista de tropes que estan aprop de la tropa passada
    * */
    public CopyOnWriteArrayList<Tropa> detectedTroops(CopyOnWriteArrayList<Tropa> tropes, Tropa t){
        ArrayList<Point2D> points = new ArrayList<>();
        points.add(new Point2D.Float(t.getxPosition() + 20,t.getyPosition() + 20));
        points.add(new Point2D.Float(t.getxPosition() - 20,t.getyPosition() - 20));

        for(Tropa tropa: tropes){
            if(!tropa.equals(t)) {
                if((tropa.getDefaultY() < 320 && t.getDefaultY() > 320) || (tropa.getDefaultY() > 320 && t.getDefaultY() < 320)) {
                /*System.out.println("------------------DIFERENT-------------------");
                System.out.println("Tropa diferent: " + tropa.getWhichSprite());
                System.out.println("Tropa agafada: " + t.getWhichSprite());
                System.out.println("----------------------------------------------");*/
                    if (tropa.getxPosition() > points.get(1).getX() && tropa.getxPosition() < points.get(0).getX()) {
                        if (tropa.getyPosition() > points.get(1).getY() && tropa.getyPosition() < points.get(0).getY()) {
                            founds.add(tropa);
                        }
                    }
                }
            } else {
                /*System.out.println("--------------------EQUALS-------------------");
                System.out.println("Tropa diferent: " + tropa.getWhichSprite());
                System.out.println("Tropa agafada: " + t.getWhichSprite());
                System.out.println("----------------------------------------------");*/
            }
        }

        if(founds.isEmpty()){
            t.setFighting(false);
        }
        return founds;
    }


    /**
    * Fa la animcació de fer explotar una bomba
     * @param tropa objecte troba que representa la bomba
     * @param cont variable que ens indica en quin estat de la explosió està
     * @return tropa, la tropa modificada. Es modifica el seu sprite
    * */
    public Tropa bombExplosion(Tropa tropa, int cont) {
        switch (cont) {
            case 0:
                //this.sprite = Sprite.SKELETON_RIGHT;
                tropa.setSprite(tropa.getMov().get(1));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            case 1:
                //this.sprite = Sprite.SKELETON_RIGHT_LEFT_FOOT;
                tropa.setSprite(tropa.getMov().get(2));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            case 2:
                //this.sprite = Sprite.SKELETON_RIGHT_RIGHT_FOOT;
                tropa.setSprite(tropa.getMov().get(3));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            case 3:
                //this.sprite = Sprite.SKELETON_RIGHT_RIGHT_FOOT;
                tropa.setSprite(tropa.getMov().get(4));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.cont = 0;
                //tropa.setPlaying(false);
                //tropa.setEntityIsDestroyed(true);

                break;
            default:
                break;
        }
        return tropa;
    }

    /*private void checkTroopsStatus(Tropa tropa){
        //Recorrem tot el camp del joc, i si trobem una tropa a un tile al nostre davant, l'ataquem i ella ens ataca.
        //L'atac es redueix a la meitat ja que quan es torni a cridar el metode amb l'altra tropa, aquesta tornara a atacar a l'actual
        if (gameView.getTropes().size() > 1) {
            for (int k = 0; k < gameView.getTropes().size(); k++) {
                if (gameView.getTropes().get(k) != tropa) {
                    if (tropa.getTroopTile().isInsideTile(gameView.getTropes().get(k).getxPosition(), gameView.getTropes().get(k).getyPosition() + 32)) {
                        System.out.println("TE ATACO HIJOPUTA");
                        //Si tenim una tropa a la dreta parem i l'ataquem
                        tropa.setVida(tropa.getVida() - (gameView.getTropes().get(k).getAtac() / 2));
                        tropa.setFighting(true);
                        gameView.getTropes().get(k).setVida(gameView.getTropes().get(k).getVida() - (tropa.getAtac() / 2));
                        gameView.getTropes().get(k).setFighting(true);
                        //Si alguna de les dues tropes que s'ataquen mor, la destruim i treiem de la partida
                        if(tropa.getVida() < 0){
                            tropa.setEntityIsDestroyed(true);
                            gameView.getTropes().remove(tropa);
                        }
                        if(gameView.getTropes().get(k).getVida() < 0){
                            gameView.getTropes().get(k).setEntityIsDestroyed(true);
                            gameView.getTropes().remove(gameView.getTropes().get(k));
                        }
                    }
                }
            }
        }
    }*/

    /*public void sendBuild(ArrayList<Edifici> e){
        Message m = new Message(e, "Edificis");
        uService.sendEdificis(m);
    }*/

    /**
    * Actualitza la tropa en questió. La i simbolitza les vegades que s'ha actualitzat
     * @param t tropa a modificar
     * @param i vegeades actualitzat
    * */
    public void update(Tropa t, int i){
        //checkTroopsStatus(t);
        if(gameView.getTropes().size() > 0){
            //for(int i = 0; i < gameView.getTropes().size(); i++){
                //gameView.getTropes().get(i).update();
                if(t != null) {
                    updateTropa(t);
                    indice = i;
                    // es pot mirar de fer a un altre lloc
                //}
            }
        }
    }

    /**
    * Actualitza la tropa en posició i sprite
     * @param tropa tropa a actualitzar
    * */
    public void updateTropa(Tropa tropa){

        if(tropa.isPlaying()) {
            //if(tropa.getTroopType() == 0 || tropa.getTroopType() == 1 || tropa.getTroopType() == 3){
                //moveOffensiveTroop(tropa, tropa.getxVariation(), tropa.getyVariation(), cont);
                //crida uservice

                //gameView.setSendcheck(false);
                Message m = new Message(tropa, "Tropa update");

                uService.sendTropa(m, this);


            /*}else if(tropa.getTroopType() == 2){
                detected = detectedTroops(gameView.getTropes(),tropa);
                if(!detected.isEmpty()){

                    Message m = new Message(tropa, "Bomba update");
                    uService.sendTropa(m, this);

                }
            }*/
        }
    }

    /**
     *Passa la tropa que es reb per parametre a la vista
     * @param t tropa que reb desde el servidor
    * */
    public void getTropa(Tropa t){
        t.setOn(false);

        //if(indice < gameView.getTropes().size()){
            gameView.getTropes().set(indice,t);
            gameView.setRebut(true);
        //}

        //gameView.setSendcheck(true);

    }


    /**
    * Elimina la tropa de la vista si ha de ser eliminada.
     * @param t tropa a eliminar
    * */
    public void deleteTropa(Tropa t){

       if(indice < gameView.getTropes().size()) {
            if(t.getTroopType() == 2){
                System.out.println("BOMBA OUT");
            }

            gameView.getTropes().remove(indice);
            System.out.println(gameView.getUpdates().get(indice).getT().getState());
            gameView.getUpdates().get(indice).getT().interrupt();
            gameView.getUpdates().get(indice).getT().stop();
            gameView.getUpdates().remove(indice);

            for(int i = 0; i < gameView.getUpdates().size(); i++){
                if(gameView.getUpdates().get(i).getIndex() > i){
                    System.out.println("The modificat l'index de " + gameView.getUpdates().get(i).getIndex() + " a : " + i);
                    gameView.getUpdates().get(i).setIndex(i);
                } else {
                    System.out.println("Index: " + gameView.getUpdates().get(i).getIndex());
                }
            }
       }

    }

    /**
    * Mostra la tropa a la pantalla grafica
     * @param t tropa a mostrar
    * */
    public void show(Tropa t){

        gameView.drawTroop(t.getxPosition(), t.getyPosition(), t);


    }

   /* public void destroyTroop(Tropa tropa) {
        System.out.println("COMO NO FUNCIONES TE REVIENTO HIJOUPUTA");
        ArrayList<Tropa> troopsAux = gameView.getTropes();

        troopsAux.remove(tropa);
        gameView.setTroops(troopsAux);


    }*/

    /**
    * Retorna la variable accept de la classe
    *  @return accept variable boolean indicant si s'ha acceptat o no
    * */
    public boolean isAccept() {
        return accept;
    }

    /**
     *Assigna la variable accept de la classe
     * @param accept boolea que volem que s'assigni a la variable accept de la classe
     * */
    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    /**
     * Retorna la vista del joc
     * @return gameView retorna la vista de tipus GameView
     * */
    public GameView getGameView() {
        return gameView;
    }

    /**
     * Assigna la vista del joc a la vista del joc de la classe 
     * @param gameView variable de tipus GameView que s'assignarà a la classe
     * */
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    /**
     *Retorna el array de tropes detectades de la classe
     * @return detected array de Tropes
     * */
    public CopyOnWriteArrayList<Tropa> getDetected() {
        return detected;
    }

    /**
     * Assigna les tropes detectades a les tropes que li passen per valor
     * @param detected llista de tropes a assignar
     * */
    public void setDetected(CopyOnWriteArrayList<Tropa> detected) {
        this.detected = detected;
    }


}
