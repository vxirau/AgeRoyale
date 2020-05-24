package src.Controller;

import src.Edifici;
import src.GameMetadata;
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

public class TroopController {

    private GameView gameView;
    private UserService uService;
    public static CopyOnWriteArrayList<Tropa> troops = new CopyOnWriteArrayList<>();
    private static Tropa troop;
    //public static int indice;
    private static int cont = 0;
    private boolean accept = false;
    private static float minDistance = Float.MAX_VALUE;
    private CopyOnWriteArrayList<Tropa> founds =  new CopyOnWriteArrayList<>();
    private CopyOnWriteArrayList<Tropa> detected =  new CopyOnWriteArrayList<>();



    public TroopController(GameView gameView, UserService userService) throws IOException {
        this.gameView = gameView;
        this.uService = userService;

        gameView.setTroopController(this);

    }



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
        //checkTroopsStatus(t);
        if(gameView.getTropes().size() > 0){
            //for(int i = 0; i < gameView.getTropes().size(); i++){
                //gameView.getTropes().get(i).update();
                if(t != null) {
                    updateTropa(t);
                    //indice = i;
                    // es pot mirar de fer a un altre lloc
                //}
            }
        }
    }

    public synchronized void updateTropa(Tropa tropa){

        if(tropa.isPlaying()) {

            Message m = new Message(tropa, "Tropa update");
            uService.sendTropa(m, this);


        }

    }

    public void getTropa(Tropa t){
        t.setOn(false);



            for (int i = 0; i <gameView.getTropes().size(); i++) {
                if(gameView.getTropes().get(i).getInitialX() == t.getInitialX() && gameView.getTropes().get(i).getInitialY() == t.getInitialY()) {
                    //gameView.getTropes().set(indice,t);
                    gameView.getTropes().set(i, t);
                    gameView.setRebut(true);
                }
            }



    }

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

    public void show(Tropa t){

        gameView.drawTroop(t.getxPosition(), t.getyPosition(), t);


    }

   /* public void destroyTroop(Tropa tropa) {
        System.out.println("COMO NO FUNCIONES TE REVIENTO HIJOUPUTA");
        ArrayList<Tropa> troopsAux = gameView.getTropes();

        troopsAux.remove(tropa);
        gameView.setTroops(troopsAux);


    }*/


    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }

    public GameView getGameView() {
        return gameView;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public CopyOnWriteArrayList<Tropa> getDetected() {
        return detected;
    }

    public void setDetected(CopyOnWriteArrayList<Tropa> detected) {
        this.detected = detected;
    }

    //on collision
}
