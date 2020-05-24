package src.Controller;

import src.Tropa;
import src.View.GameView;

public class TroopUpdate implements Runnable{

    private TroopController troopController;
    private Tropa tropa;
    //private int index;
    private GameView gameView;
    private Thread t;
    private boolean trobat;


    public TroopUpdate(TroopController tController, GameView gameView){
        this.troopController = tController;
        this.gameView = gameView;

    }


    public void startThread(Tropa tropa, boolean trobat){
        this.tropa = tropa;
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

                if(!t.getState().equals(Thread.State.TERMINATED) && !t.getState().equals(Thread.State.TIMED_WAITING)) {

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (Tropa.class) {
                        if(!tropa.isChecked()) {
                            troopController.checkTroopsStatus(tropa);
                            troopController.update(tropa);

                        }
                    }

                }
            }
        }
    }
}
