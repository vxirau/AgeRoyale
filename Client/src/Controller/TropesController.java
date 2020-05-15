package src.Controller;

import src.Usuari;
import src.View.TropesView;

import javax.swing.*;
import java.awt.*;

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
                tropesView.getJlTropes1_vida().setText(Integer.toString(usuari.getTropes().get(0).getVida()));
                tropesView.getJlTropes1_dany().setText(Integer.toString(usuari.getTropes().get(0).getAtac()));
            } else {
                ImageIcon tropa1_foto = new ImageIcon(this.getClass().getResource("/resources/tropes_foto_default.png"));
                Icon iconotropa1foto = new ImageIcon(tropa1_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                tropesView.getJlTropes1_foto().setIcon(iconotropa1foto);
            }
            if (usuari.getTropes().size() > 1) {
                tropesView.getJlTropes2_vida().setText(Integer.toString(usuari.getTropes().get(1).getVida()));
                tropesView.getJlTropes2_dany().setText(Integer.toString(usuari.getTropes().get(1).getAtac()));
            } else {
                ImageIcon tropa2_foto = new ImageIcon(this.getClass().getResource("/resources/tropes_foto_default.png"));
                Icon iconotropa2foto = new ImageIcon(tropa2_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                tropesView.getJlTropes2_foto().setIcon(iconotropa2foto);
            }
            if (usuari.getTropes().size() > 2) {
                tropesView.getJlTropes3_vida().setText(Integer.toString(usuari.getTropes().get(2).getVida()));
                tropesView.getJlTropes3_dany().setText(Integer.toString(usuari.getTropes().get(2).getAtac()));
            } else {
                ImageIcon tropa3_foto = new ImageIcon(this.getClass().getResource("/resources/tropes_foto_default.png"));
                Icon iconotropa3foto = new ImageIcon(tropa3_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                tropesView.getJlTropes3_foto().setIcon(iconotropa3foto);
            }
            if (usuari.getTropes().size() > 3) {
                tropesView.getJlTropes4_vida().setText(Integer.toString(usuari.getTropes().get(3).getVida()));
                tropesView.getJlTropes4_dany().setText(Integer.toString(usuari.getTropes().get(3).getAtac()));
            } else {
                ImageIcon tropa4_foto = new ImageIcon(this.getClass().getResource("/resources/tropes_foto_default.png"));
                Icon iconotropa4foto = new ImageIcon(tropa4_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                tropesView.getJlTropes4_foto().setIcon(iconotropa4foto);
            }
        }
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }
}
