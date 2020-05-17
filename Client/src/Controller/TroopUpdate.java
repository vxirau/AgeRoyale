package src.Controller;

import src.Tropa;
import src.View.GameView;

public class TroopUpdate implements Runnable{

    private TroopController troopController;
    private Tropa tropa;
    private int indice;
    private GameView gameView;
    private Thread t;
    private boolean trobat;


    public TroopUpdate(TroopController tController, GameView gameView){
        this.troopController = tController;
        this.gameView = gameView;

    }
    public void catchTroop(Tropa tropa, int index, boolean trobat){
        this.tropa = tropa;
        this.indice = index;
        this.trobat = trobat;
        this.t = new Thread(this,"tropa");
    }

    public Thread getT() {
        return t;
    }

    public void setT(Thread t) {
        this.t = t;
    }

    public Tropa getTropa() {
        return tropa;
    }

    public void setTropa(Tropa tropa) {
        this.tropa = tropa;
    }


    @Override
    public void run() {

        while(gameView.isGameIsRunning()){

            if(troopController.isAccept() || trobat){
                troopController.setAccept(false);
                trobat = false;

                synchronized (Tropa.class) {
                    troopController.update(tropa, indice);
                }

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
