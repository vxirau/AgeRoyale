package src.Controller;

import src.Usuari;
import src.View.TropesView;

public class TropesController {

    private TropesView tropesView;
    private Usuari usuari;

    public TropesController(Usuari usr) {
        this.usuari = usr;
    }

    public void setTropesView(TropesView tropesView) {
        this.tropesView = tropesView;

        if (usuari.getTropes() != null) {
            if (usuari.getTropes().size() > 0) {
                //tropesView.getJlTropes1_foto();
                tropesView.getJlTropes1_vida().setText(Integer.toString(usuari.getTropes().get(0).getVida()));
                tropesView.getJlTropes1_dany().setText(Integer.toString(usuari.getTropes().get(0).getAtac()));
            }
            if (usuari.getTropes().size() > 1) {
                //tropesView.getJlTropes1_foto();
                tropesView.getJlTropes2_vida().setText(Integer.toString(usuari.getTropes().get(1).getVida()));
                tropesView.getJlTropes2_dany().setText(Integer.toString(usuari.getTropes().get(1).getAtac()));
            }
            if (usuari.getTropes().size() > 2) {
                //tropesView.getJlTropes3_foto();
                tropesView.getJlTropes3_vida().setText(Integer.toString(usuari.getTropes().get(2).getVida()));
                tropesView.getJlTropes3_dany().setText(Integer.toString(usuari.getTropes().get(2).getAtac()));
            }
            if (usuari.getTropes().size() > 3) {
                //tropesView.getJlTropes4_foto();
                tropesView.getJlTropes4_vida().setText(Integer.toString(usuari.getTropes().get(3).getVida()));
                tropesView.getJlTropes4_dany().setText(Integer.toString(usuari.getTropes().get(3).getAtac()));
            }
        }
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }
}
