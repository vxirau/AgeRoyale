package src.Controller;

import src.Tropa;
import src.View.GameView;


/**
* Classe destinada a actualitzar la tropa a la partida, implementa runnable per poder-se executar en paralel a les demés tasques de la partida
* */
public class TroopUpdate implements Runnable{

    private TroopController troopController;
    private Tropa tropa;
    //private int index;
    private GameView gameView;
    private Thread t;
    private boolean trobat;

    /**
    * Constructor de la classe
     * @param tController controlador de la tropa
     * @param gameView vista de la partida
    * */
    public TroopUpdate(TroopController tController, GameView gameView){
        this.troopController = tController;
        this.gameView = gameView;

    }

    /**
     * Encarregada de iniciiar el thread de execució de la tropa. Cada tropa és un thread
     * @param tropa tropa que ha de estar al thread
     * @param trobat, si s'ha trobat una topa en el seu desplaçament o encar ano
     * */
    public void startThread(Tropa tropa, boolean trobat){
        this.tropa = tropa;
        this.trobat = trobat;
        this.t = new Thread(this,"tropa");
    }

    /**
     * Retorna el thread actual
     * @return t thread actual
    */
    public Thread getT() {
        return t;
    }

    /**
    * Assigna el thread que li passen al thread actual
     * @param t thread que reb per valor
    * */
    public void setT(Thread t) {
        this.t = t;
    }

    /**
    * Retorna la tropa de la classe
     * @return tropa variable de tipus Tropa
    * */
    public Tropa getTropa() {
        return tropa;
    }

    /**
    * Assigna la tropa a la tropa de la classe
     * @param tropa tropa que li passen per valor a la funció
    * */
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
