package src.Controller;

import src.Tropa;
import src.View.GameView;


/**
* Classe destinada a actualitzar la tropa a la partida, implementa runnable per poder-se executar en paralel a les demés tasques de la partida
* */
public class TroopUpdate implements Runnable{

    private TroopController troopController;
    private Tropa tropa;
    private int index;
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
    * Assigna a les variables de la classe l'index i quina tropa s'ha detectat
     * @param tropa tropa que li passa el servidor
     * @param index index de la tropa que reb
    * */
    public void catchTroop(Tropa tropa, int index){
        this.tropa = tropa;
        this.index = index;
        //this.trobat = trobat;
        //this.t = new Thread(this,"tropa");
    }

    /**
    * Encarregada de iniciiar el thread de execució de la tropa. Cada tropa és un thread
     * @param tropa tropa que ha de estar al thread
     * @param index id de la tropa
     * @param trobat, si s'ha trobat una topa en el seu desplaçament o encar ano
    * */
    public void startThread(Tropa tropa, int index,boolean trobat){
        this.tropa = tropa;
        this.index = index;
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

    /**
    * Retorna el id de la tropa seleccionada
     * @return index enter que representa el id de la tropa
    * */
    public int getIndex() {
        return index;
    }

    /**
    * Assigna l'index de la tropa
     * @param index index de la tropa actual
    * */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
    * Funció necessaria ja que la classe implementa un runnable. És la funció que executa el thread
    * */
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
                        troopController.checkTroopsStatus(tropa);

                        /*if(tropa.getTroopType() == 2){
                            if(!troopController.troops.isEmpty()){
                                int cont = 0;
                                while(cont < 3){
                                    troopController.bombExplosion(tropa,cont);
                                    cont++;
                                }
                                tropa.setVida(0);
                            }
                        }*/

                        troopController.update(tropa, index);

                    }

                }
            }
        }
    }
}
