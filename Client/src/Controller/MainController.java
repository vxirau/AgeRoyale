package src.Controller;

import src.Usuari;
import src.View.MainView;

public class MainController {

    private MainView mainView;
    private Usuari usuari;

    public MainController(Usuari usr) {
        this.usuari = usr;
    }

    public void setMainView(MainView mainView) {
        this.mainView = mainView;
        setInfoView();
    }

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

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }
}
