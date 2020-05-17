package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Tropa;
import src.View.Deck;
import src.View.GameView;
import src.View.Sprite;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class TroopController {

    private GameView gameView;
    private UserService uService;
    private ArrayList<Tropa> troops = new ArrayList<>();
    private static Tropa tropa;
    public static int index;
    private boolean accept = false;


    public TroopController(GameView gameView, UserService userService) throws IOException {
        this.gameView = gameView;
        this.uService = userService;

        //userService.startServerComunication(); //TODO: comentar

        gameView.setTroopController(this);

    }
    private void checkTroopsStatus(Tropa tropa){
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
    }



    public synchronized void update(Tropa t, int i){
            checkTroopsStatus(t);

        if(gameView.getTropes().size() > 0){
            //for(int i = 0; i < gameView.getTropes().size(); i++){
                //gameView.getTropes().get(i).update();
                if(t != null) {
                    updateTropa(t);
                    index = i;
                    // es pot mirar de fer a un altre lloc
                //}
            }
        }
    }

    public void updateTropa(Tropa tropa){
        if(tropa.isPlaying()) {
            if(tropa.getTroopType() == 0 || tropa.getTroopType() == 1){
                //moveOffensiveTroop(tropa, tropa.getxVariation(), tropa.getyVariation(), cont);
                //crida uservice
                Message m = new Message(tropa, "Tropa update");
                uService.sendTropa(m, this);

            }else if(tropa.getTroopType() == 2){

                Message m = new Message(tropa, "Bomba update");
                uService.sendTropa(m, this);

            }
        }
    }

    public void getTropa(Tropa t){

        t.setOn(false);
        gameView.getTropes().set(index, t);
        gameView.setRebut(true);

    }

    public void show(Tropa t){

        gameView.drawTroop(t.getxPosition(), t.getyPosition(), t);


    }

    public void destroyTroop(Tropa tropa) {
        System.out.println("COMO NO FUNCIONES TE REVIENTO HIJOUPUTA");
        ArrayList<Tropa> troopsAux = gameView.getTropes();

        troopsAux.remove(tropa);
        gameView.setTroops(troopsAux);


    }


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

    //on collision
}
