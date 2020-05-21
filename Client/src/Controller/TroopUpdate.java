package src.Controller;

import src.Tropa;
import src.View.GameView;

public class TroopUpdate implements Runnable{

    private TroopController troopController;
    private Tropa tropa;
    private int index;
    private GameView gameView;
    private Thread t;
    private boolean trobat;


    public TroopUpdate(TroopController tController, GameView gameView){
        this.troopController = tController;
        this.gameView = gameView;

    }
    public void catchTroop(Tropa tropa, int index){
        this.tropa = tropa;
        this.index = index;
        //this.trobat = trobat;
        //this.t = new Thread(this,"tropa");
    }

    public void startThread(Tropa tropa, int index,boolean trobat){
        this.tropa = tropa;
        this.index = index;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void run() {

        while(gameView.isGameIsRunning()){

            if(troopController.isAccept() || trobat){
                troopController.setAccept(false);
                trobat = false;

                if(!t.getState().equals(Thread.State.TERMINATED) && !t.getState().equals(Thread.State.TIMED_WAITING)) {

                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (Tropa.class) {
                        troopController.checkTroopsStatus(tropa);

                        if(tropa.getTroopType() == 2){
                            if(!troopController.troops.isEmpty()){
                                int cont = 0;
                                while(cont < 3){
                                    troopController.bombExplosion(tropa,cont);
                                    cont++;
                                }
                            }
                        }

                        troopController.update(tropa, index);

                    }

                }
            }
        }
    }
}
