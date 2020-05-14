package src.Controller;

import src.Message;
import src.Model.Network.UserService;
import src.Tropa;
import src.View.Deck;
import src.View.GameView;
import src.View.Sprite;

import java.io.IOException;
import java.util.ArrayList;

public class TroopController {

    private GameView gameView;
    private UserService uService;
    private ArrayList<Tropa> troops = new ArrayList<>();
    private static Tropa tropa;
    public static int index;
    public boolean accept = true;
    public static int total;


    public TroopController(GameView gameView, UserService userService) throws IOException {
        this.gameView = gameView;
        this.uService = userService;

        //userService.startServerComunication(); //TODO: comentar

        gameView.setTroopController(this);

    }

    public void update(Tropa t, int i){
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
                //bombExplosion(tropa, cont);
                //crida uservice


            }else{

            }

        }
    }

    public void getTropa(Tropa t){



        gameView.getTropes().set(index, t);

        gameView.setRebut(true);


    }

    public void show(Tropa t){
        gameView.drawTroop(t.getxPosition(), t.getyPosition(), t);
    }


    //on collision
}
