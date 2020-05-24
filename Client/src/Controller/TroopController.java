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



    public TroopController(GameView gameView, UserService userService) throws IOException {
        this.gameView = gameView;
        this.uService = userService;

        //userService.startServerComunication(); //TODO: comentar

        gameView.setTroopController(this);

    }



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

    public void getTropa(Tropa t){
        t.setOn(false);

        //if(indice < gameView.getTropes().size()){
            gameView.getTropes().set(indice,t);
            gameView.setRebut(true);
        //}

        //gameView.setSendcheck(true);

    }

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
