package src.Controller;

import src.Usuari;
import src.View.MainView;


/**
* Contraldor destinat a controlar la finestra del menú
* */
public class MainController {

    private MainView mainView;
    private Usuari usuari;


    /**
    * Constructor de la classe
    * @param usr usuari que ha inciat sessió a la aplicació
    * */
    public MainController(Usuari usr) {
        this.usuari = usr;
    }


    /**
    * Assigna la vista al controlador. La vista és la principal del client.
     * @param mainView Vista principal
    * */
    public void setMainView(MainView mainView) {
        this.mainView = mainView;
        setInfoView();
    }


    /**
    * Funcío encarregada de carregar tota la informació de la vista als labels i botons pertinents.
    * */
    private void setInfoView() {
        int experiencia = 0;
        int lvl = 0;

        if (usuari.getStats() != null) {
            experiencia = (usuari.getStats().getTotalVictories() * 3) + (usuari.getStats().getTotalPartides() - usuari.getStats().getTotalVictories());
            while (experiencia > 100) {
                lvl++;
                experiencia -= 100;
            }

        }
        mainView.getjProgressBar().setValue(experiencia);
        mainView.getJbMainTopLvl().setText("Lvl. " + lvl);

        if (usuari.getStats() != null) {
            mainView.getJlVictories().setText(usuari.getStats().getTotalVictories() + " victories");
            mainView.getJlTempsXVictoria().setText("Avg " + usuari.getStats().getAvgDurationVictories() + " min per victoria");
        }
        if (usuari.getStats().getTropaMesUtilitzada() != null && !usuari.getStats().getTropaMesUtilitzada().equals("")){
            mainView.getJlTropaMesUtilitzada().setText("Tropa: " + usuari.getStats().getTropaMesUtilitzada());
        } else {
            mainView.getJlTropaMesUtilitzada().setText("Tropa: -");
        }
    }


    /**
    * Encarregada de assignar el usuari que rep per paràmetre al usuari de la classe. Serveix per actualitzar la informació d'aquest.
     * @param usuari usuari de resposta que envia el servidor amb informació actualitzada
    * */
    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }
}
