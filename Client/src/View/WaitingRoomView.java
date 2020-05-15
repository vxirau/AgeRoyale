package src.View;

import src.Controller.WaitingController;

import javax.swing.*;

public class WaitingRoomView extends JFrame {
    private JButton start;

    //Publica, esperar a que es connecti algú i quan es connecti començar partida

    //Privada, convidar amics en linia (amics entren si accepten)

    public WaitingRoomView(){
        JPanel main  = new JPanel();
        start = new JButton();
        start.setText("Start Game");
        main.add(start);
        this.setContentPane(main);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(450, 800);
    }

    public void setController(WaitingController controller){
        this.addWindowListener(controller);
        start.addActionListener(controller);
    }
} 
