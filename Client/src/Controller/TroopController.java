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
    public static CopyOnWriteArrayList<Tropa> troops = new CopyOnWriteArrayList<>();
    private static Tropa troop;
    private static int cont = 0;

    public int indice;

    private boolean accept = false;
    private float minDistance = Float.MAX_VALUE;

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
        //Detectem si tenim enemics propers, i fem que ens ataquin
        troops = detectedTroops(gameView.getTropes(), tropa);

        if (!troops.isEmpty()) {
            for (Tropa t : troops) {
                //Atacarem a l'enemic que tinguem mes a prop
                float c1 = tropa.getxPosition() - t.getxPosition();
                float c2 = tropa.getyPosition() - t.getyPosition();
                float distance = (float) Math.sqrt(c1 * c1 + c2 * c2);
                if (distance < minDistance) {
                    minDistance = distance;
                    troop = t;

                }
            }

            tropa.setVida(tropa.getVida() - troop.getAtac());
            tropa.setFighting(true);

            //Si s'elimina la tropa, rebem una compensació d'or
            if(tropa.getDefaultY() < 320 && tropa.getVida() <= 0){
                if(gameView.getDeck().getGoldRectangle().getX() + 10 <= 280){
                    Double d = gameView.getDeck().getGoldRectangle().getWidth();
                    Integer i = d.intValue();
                    Double d2 =  gameView.getDeck().getGoldRectangle().getHeight();
                    Integer j = d2.intValue();
                    gameView.getDeck().getGoldRectangle().setBounds( gameView.getDeck().getGoldRectangle().x, gameView.getDeck().getGoldRectangle().y,   i+10 , j);
                }
            }


        }
        founds.clear();
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

                    if (tropa.getxPosition() > points.get(1).getX() && tropa.getxPosition() < points.get(0).getX()) {
                        if (tropa.getyPosition() > points.get(1).getY() && tropa.getyPosition() < points.get(0).getY()) {
                            founds.add(tropa);
                        }
                    }
                }
            }
        }

        if(founds.isEmpty()){
            t.setFighting(false);
        }
        return founds;
    }



    public synchronized void update(Tropa t){

        if(gameView.getTropes().size() > 0){

                if(t != null) {
                    updateTropa(t);

            }
        }
    }

    /**
    * Actualitza la tropa en posició i sprite
     * @param tropa tropa a actualitzar
    * */
    public void updateTropa(Tropa tropa){

        if(tropa.isPlaying()) {

            Message m = new Message(tropa, "Tropa update");
            uService.sendTropa(m, this);


        }

    }

    /**
     *Passa la tropa que es reb per parametre a la vista
     * @param t tropa que reb desde el servidor
    * */
    public void getTropa(Tropa t){
        t.setOn(false);

            for (int i = 0; i <gameView.getTropes().size(); i++) {
                if(gameView.getTropes().get(i).getInitialX() == t.getInitialX() && gameView.getTropes().get(i).getInitialY() == t.getInitialY()) {
                    gameView.getTropes().set(i, t);
                    gameView.setRebut(true);
                }
            }



    }


    /**
    * Elimina la tropa de la vista si ha de ser eliminada.
     * @param t tropa a eliminar
    * */
    public void deleteTropa(Tropa t){

       /*if(indice < gameView.getTropes().size()) {


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
       }*/

        for (int i = 0; i <gameView.getTropes().size(); i++) {
            if(gameView.getTropes().get(i).getInitialX() == t.getInitialX() && gameView.getTropes().get(i).getInitialY() == t.getInitialY()) {
                //gameView.getTropes().set(indice,t);
                Tropa troopToDelete = gameView.getTropes().get(i);
                gameView.getTropes().remove(troopToDelete);
                //gameView.setRebut(true);
            }

            if(gameView.getUpdates().get(i).getTropa().getInitialX() == t.getInitialX() && gameView.getUpdates().get(i).getTropa().getInitialY() == t.getInitialY()){
                gameView.getUpdates().get(i).getT().interrupt();
                gameView.getUpdates().get(i).getT().stop();
                gameView.getUpdates().remove(i);

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
