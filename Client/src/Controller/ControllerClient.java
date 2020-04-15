package src.Controller;

import src.View.ViewRegistre;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ControllerClient extends MouseAdapter implements WindowListener {
    private ViewRegistre viewRegistre;


    public ControllerClient(ViewRegistre vClient){
        this.viewRegistre = vClient;
        //Comunicacio amb el server
    }

    public void mouseClicked(MouseEvent event) {

        String botoClicked = ((JPanel)event.getSource()).getName();
        //Enviem quin boto s'ha selecionat(BACK/REGISTER)

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
