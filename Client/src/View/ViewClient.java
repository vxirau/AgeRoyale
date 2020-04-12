package src.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewClient extends javax.swing.JFrame implements ActionListener {

    public ViewClient(){
        //Tot el jpanel por defecto
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Foto Ingapirca");
        setLocationRelativeTo(null);
        setSize(450,800);
        pack();
        BackView pnlFondo = new BackView();
        pnlFondo.setjFramePadre(this);
        this.add(pnlFondo, BorderLayout.CENTER);
        this.pack();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //listeners dels jpanels
    }
}
