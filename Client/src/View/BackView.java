package src.View;

import javax.swing.*;
import java.awt.*;

public class BackView extends javax.swing.JPanel{
    private JFrame jFramePadre;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JPasswordField txpPassword;
    private javax.swing.JTextField txtLogin;


    public BackView(){
        this.setSize(450, 800);
    }

    @Override
    public void paintComponent(Graphics g){
        System.out.println("paintComponent");
        Dimension tamanio = getSize();
        ImageIcon imagenFondo = new ImageIcon(getClass().
                getResource("/resources/SE_MAMO_WEY.png"));
        g.drawImage(imagenFondo.getImage(), 0, 0,
                tamanio.width, tamanio.height, null);
        setOpaque(false);
        super.paintComponent(g);
    }

    public JFrame getjFramePadre() {
        return jFramePadre;
    }

    public void setjFramePadre(JFrame jFramePadre) {
        this.jFramePadre = jFramePadre;
    }
}
