package src.View;

import javax.swing.*;
import java.awt.*;

/**
 * Classe que representa la pantalla gràfica de les Tropes. Hereda de JFrame ja que volem que sigui una finestra.
 */
public class TropesView extends JFrame{

    private JPanel jpTropes;
    private JPanel jpTropes1;
    private JButton jlTropes1_foto;
    private JLabel jlTropes1_vida;
    private JLabel jlTropes1_dany;
    private JPanel jpTropes2;
    private JButton jlTropes2_foto;
    private JLabel jlTropes2_vida;
    private JLabel jlTropes2_dany;
    private JPanel jpTropes3;
    private JButton jlTropes3_foto;
    private JLabel jlTropes3_vida;
    private JLabel jlTropes3_dany;
    private JPanel jpTropes4;
    private JButton jlTropes4_foto;
    private JLabel jlTropes4_vida;
    private JLabel jlTropes4_dany;

    private ImageIcon tropa1_foto;
    private ImageIcon tropa2_foto;
    private ImageIcon tropa3_foto;
    private ImageIcon tropa4_foto;

    private Icon iconotropa1foto;
    private Icon iconotropa2foto;
    private Icon iconotropa3foto;
    private Icon iconotropa4foto;

    public static final String SKELETON_FRONT = "/resources/skeleton_deck.png";
    public static final String SKELETON_FRONT_RIGHT = "/resources/skeleton_right_deck.png";
    public static final String SKELETON_FRONT_LEFT = "/resources/skeleton_left_deck.png";
    public static final String GOBLIN_FRONT = "/resources/goblin_deck.png";
    public static final String GOBLIN_FRONT_RIGHT = "/resources/goblin_right_deck.png";
    public static final String GOBLIN_FRONT_LEFT = "/resources/goblin_left_deck.png";
    public static final String WIZARD = "/resources/wizard_deck.png";
    public static final String BOMB = "/resources/bomb_deck.png";
    public static final String BOMB_PHASE1 = "/resources/bomb_phase1_deck.png";
    public static final String BOMB_PHASE2 = "/resources/bomb_phase2_deck.png";


    /**
     * Constructor de la classe. Inicialitza la pantalla gràfica
     */
    public TropesView() {

        String bgColor = "#85201F";
        String borderColor = "#979797";

        jpTropes = new JPanel(null);

        //TROPA 1
        jpTropes1 = new JPanel(new GridLayout(3, 2));
        jpTropes1.setBackground(Color.decode(bgColor));
        jpTropes1.setBounds(200, 150, 80, 100);
        jpTropes1.setBorder(BorderFactory.createLineBorder(Color.decode(borderColor), 3));

        jlTropes1_foto = new JButton();
        jlTropes1_foto.setOpaque(false);
        jlTropes1_foto.setContentAreaFilled(false);
        jlTropes1_foto.setBorderPainted(false);
        tropa1_foto = new ImageIcon(this.getClass().getResource(SKELETON_FRONT));
        iconotropa1foto = new ImageIcon(tropa1_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
        jlTropes1_foto.setIcon(iconotropa1foto);
        jpTropes1.add(jlTropes1_foto);
        jpTropes1.add(new JLabel(""));

        JButton jbTropes1Vida = new JButton();
        jbTropes1Vida.setOpaque(false);
        jbTropes1Vida.setContentAreaFilled(false);
        jbTropes1Vida.setBorderPainted(false);
        ImageIcon vida1 = new ImageIcon(this.getClass().getResource("/resources/tropes_vida.png"));
        Icon vida1icon = new ImageIcon(vida1.getImage().getScaledInstance(20, 20, Image.SCALE_FAST));
        jbTropes1Vida.setIcon(vida1icon);
        jpTropes1.add(jbTropes1Vida);
        jlTropes1_vida = new JLabel("0",SwingConstants.CENTER);
        jlTropes1_vida.setForeground(Color.WHITE);
        jpTropes1.add(jlTropes1_vida);


        JButton jbTropes1Dany = new JButton();
        jbTropes1Dany.setOpaque(false);
        jbTropes1Dany.setContentAreaFilled(false);
        jbTropes1Dany.setBorderPainted(false);
        ImageIcon dany1 = new ImageIcon(this.getClass().getResource("/resources/tropes_dany.png"));
        Icon dany1icon = new ImageIcon(dany1.getImage().getScaledInstance(20, 20, Image.SCALE_FAST));
        jbTropes1Dany.setIcon(dany1icon);
        jpTropes1.add(jbTropes1Dany);
        jlTropes1_dany = new JLabel("0", SwingConstants.CENTER);
        jlTropes1_dany.setForeground(Color.WHITE);
        jpTropes1.add(jlTropes1_dany);

        jpTropes.add(jpTropes1);

        //TROPA 2
        jpTropes2 = new JPanel(new GridLayout(3, 2));
        jpTropes2.setBackground(Color.decode(bgColor));
        jpTropes2.setBounds(300, 150, 80, 100);
        jpTropes2.setBorder(BorderFactory.createLineBorder(Color.decode(borderColor), 3));

        jlTropes2_foto = new JButton();
        jlTropes2_foto.setOpaque(false);
        jlTropes2_foto.setContentAreaFilled(false);
        jlTropes2_foto.setBorderPainted(false);
        tropa2_foto = new ImageIcon(this.getClass().getResource(GOBLIN_FRONT));
        iconotropa2foto = new ImageIcon(tropa2_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
        jlTropes2_foto.setIcon(iconotropa2foto);
        jpTropes2.add(jlTropes2_foto);
        jpTropes2.add(new JLabel(""));

        JButton jbTropes2Vida = new JButton();
        jbTropes2Vida.setOpaque(false);
        jbTropes2Vida.setContentAreaFilled(false);
        jbTropes2Vida.setBorderPainted(false);
        ImageIcon vida2 = new ImageIcon(this.getClass().getResource("/resources/tropes_vida.png"));
        Icon vida2icon = new ImageIcon(vida2.getImage().getScaledInstance(20, 20, Image.SCALE_FAST));
        jbTropes2Vida.setIcon(vida2icon);
        jpTropes2.add(jbTropes2Vida);
        jlTropes2_vida = new JLabel("0",SwingConstants.CENTER);
        jlTropes2_vida.setForeground(Color.WHITE);
        jpTropes2.add(jlTropes2_vida);


        JButton jbTropes2Dany = new JButton();
        jbTropes2Dany.setOpaque(false);
        jbTropes2Dany.setContentAreaFilled(false);
        jbTropes2Dany.setBorderPainted(false);
        ImageIcon dany2 = new ImageIcon(this.getClass().getResource("/resources/tropes_dany.png"));
        Icon dany2icon = new ImageIcon(dany2.getImage().getScaledInstance(20, 20, Image.SCALE_FAST));
        jbTropes2Dany.setIcon(dany2icon);
        jpTropes2.add(jbTropes2Dany);
        jlTropes2_dany = new JLabel("0", SwingConstants.CENTER);
        jlTropes2_dany.setForeground(Color.WHITE);
        jpTropes2.add(jlTropes2_dany);

        jpTropes.add(jpTropes2);

        //TROPA 3
        jpTropes3 = new JPanel(new GridLayout(3, 2));
        jpTropes3.setBackground(Color.decode(bgColor));
        jpTropes3.setBounds(75, 440, 80, 100);
        jpTropes3.setBorder(BorderFactory.createLineBorder(Color.decode(borderColor), 3));

        jlTropes3_foto = new JButton();
        jlTropes3_foto.setOpaque(false);
        jlTropes3_foto.setContentAreaFilled(false);
        jlTropes3_foto.setBorderPainted(false);
        tropa3_foto = new ImageIcon(this.getClass().getResource(WIZARD));
        iconotropa3foto = new ImageIcon(tropa3_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
        jlTropes3_foto.setIcon(iconotropa3foto);
        jpTropes3.add(jlTropes3_foto);
        jpTropes3.add(new JLabel(""));

        JButton jbTropes3Vida = new JButton();
        jbTropes3Vida.setOpaque(false);
        jbTropes3Vida.setContentAreaFilled(false);
        jbTropes3Vida.setBorderPainted(false);
        ImageIcon vida3 = new ImageIcon(this.getClass().getResource("/resources/tropes_vida.png"));
        Icon vida3icon = new ImageIcon(vida3.getImage().getScaledInstance(20, 20, Image.SCALE_FAST));
        jbTropes3Vida.setIcon(vida3icon);
        jpTropes3.add(jbTropes3Vida);
        jlTropes3_vida = new JLabel("0",SwingConstants.CENTER);
        jlTropes3_vida.setForeground(Color.WHITE);
        jpTropes3.add(jlTropes3_vida);


        JButton jbTropes3Dany = new JButton();
        jbTropes3Dany.setOpaque(false);
        jbTropes3Dany.setContentAreaFilled(false);
        jbTropes3Dany.setBorderPainted(false);
        ImageIcon dany3 = new ImageIcon(this.getClass().getResource("/resources/tropes_dany.png"));
        Icon dany3icon = new ImageIcon(dany3.getImage().getScaledInstance(20, 20, Image.SCALE_FAST));
        jbTropes3Dany.setIcon(dany3icon);
        jpTropes3.add(jbTropes3Dany);
        jlTropes3_dany = new JLabel("0", SwingConstants.CENTER);
        jlTropes3_dany.setForeground(Color.WHITE);
        jpTropes3.add(jlTropes3_dany);

        jpTropes.add(jpTropes3);

        //TROPA 4
        jpTropes4 = new JPanel(new GridLayout(3, 2));
        jpTropes4.setBackground(Color.decode(bgColor));
        jpTropes4.setBounds(175, 440, 80, 100);
        jpTropes4.setBorder(BorderFactory.createLineBorder(Color.decode(borderColor), 3));

        jlTropes4_foto = new JButton();
        jlTropes4_foto.setOpaque(false);
        jlTropes4_foto.setContentAreaFilled(false);
        jlTropes4_foto.setBorderPainted(false);
        tropa4_foto = new ImageIcon(this.getClass().getResource(BOMB));
        iconotropa4foto = new ImageIcon(tropa4_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
        jlTropes4_foto.setIcon(iconotropa4foto);
        jpTropes4.add(jlTropes4_foto);
        jpTropes4.add(new JLabel(""));

        JButton jbTropes4Vida = new JButton();
        jbTropes4Vida.setOpaque(false);
        jbTropes4Vida.setContentAreaFilled(false);
        jbTropes4Vida.setBorderPainted(false);
        ImageIcon vida4 = new ImageIcon(this.getClass().getResource("/resources/tropes_vida.png"));
        Icon vida4icon = new ImageIcon(vida4.getImage().getScaledInstance(20, 20, Image.SCALE_FAST));
        jbTropes4Vida.setIcon(vida4icon);
        jpTropes4.add(jbTropes4Vida);
        jlTropes4_vida = new JLabel("0",SwingConstants.CENTER);
        jlTropes4_vida.setForeground(Color.WHITE);
        jpTropes4.add(jlTropes4_vida);


        JButton jbTropes4Dany = new JButton();
        jbTropes4Dany.setOpaque(false);
        jbTropes4Dany.setContentAreaFilled(false);
        jbTropes4Dany.setBorderPainted(false);
        ImageIcon dany4 = new ImageIcon(this.getClass().getResource("/resources/tropes_dany.png"));
        Icon dany4icon = new ImageIcon(dany4.getImage().getScaledInstance(20, 20, Image.SCALE_FAST));
        jbTropes4Dany.setIcon(dany4icon);
        jpTropes4.add(jbTropes4Dany);
        jlTropes4_dany = new JLabel("0", SwingConstants.CENTER);
        jlTropes4_dany.setForeground(Color.WHITE);
        jpTropes4.add(jlTropes4_dany);

        jpTropes.add(jpTropes4);

        //FONS
        ImageIcon img = new ImageIcon(this.getClass().getResource("/resources/bg_tropes.png"));
        Icon icono = new ImageIcon(img.getImage().getScaledInstance(450, 700, Image.SCALE_DEFAULT));
        JLabel fondo = new JLabel();
        fondo.setIcon(icono);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, 450, 700);
        jpTropes.add(fondo);
    }

    /**
     * Actualitza les tropes
     * @param elapsedTime temps cada quan s'ha d'actualitzar la tropa
     */
    public void updateTropes(long elapsedTime){
        if(elapsedTime < 300){
            //TROPA 1 UPDATE
                tropa1_foto = new ImageIcon(this.getClass().getResource(SKELETON_FRONT));
                iconotropa1foto = new ImageIcon(tropa1_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes1_foto.setIcon(iconotropa1foto);


            //TROPA 2 UPDATE
                tropa2_foto = new ImageIcon(this.getClass().getResource(GOBLIN_FRONT));
                iconotropa2foto = new ImageIcon(tropa2_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes2_foto.setIcon(iconotropa2foto);


            //TROPA 3 UPDATE
                tropa3_foto = new ImageIcon(this.getClass().getResource(WIZARD));
                iconotropa3foto = new ImageIcon(tropa3_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes3_foto.setIcon(iconotropa3foto);


            //TROPA 4 UPDATE
                tropa4_foto = new ImageIcon(this.getClass().getResource(BOMB));
                iconotropa4foto = new ImageIcon(tropa4_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes4_foto.setIcon(iconotropa4foto);


        } else if(elapsedTime < 600 && elapsedTime > 300){
            //TROPA 1 UPDATE
                tropa1_foto = new ImageIcon(this.getClass().getResource(SKELETON_FRONT_LEFT));
                iconotropa1foto = new ImageIcon(tropa1_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes1_foto.setIcon(iconotropa1foto);


            //TROPA 2 UPDATE
                tropa2_foto = new ImageIcon(this.getClass().getResource(GOBLIN_FRONT_LEFT));
                iconotropa2foto = new ImageIcon(tropa2_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes2_foto.setIcon(iconotropa2foto);


            //TROPA 3 UPDATE
                tropa3_foto = new ImageIcon(this.getClass().getResource(WIZARD));
                iconotropa3foto = new ImageIcon(tropa3_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes3_foto.setIcon(iconotropa3foto);


            //TROPA 4 UPDATE
                tropa4_foto = new ImageIcon(this.getClass().getResource(BOMB_PHASE1));
                iconotropa4foto = new ImageIcon(tropa4_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes4_foto.setIcon(iconotropa4foto);


        }else if(elapsedTime < 900 && elapsedTime > 600){
            //TROPA 1 UPDATE
                tropa1_foto = new ImageIcon(this.getClass().getResource(SKELETON_FRONT_RIGHT));
                iconotropa1foto = new ImageIcon(tropa1_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes1_foto.setIcon(iconotropa1foto);


            //TROPA 2 UPDATE
                tropa2_foto = new ImageIcon(this.getClass().getResource(GOBLIN_FRONT_RIGHT));
                iconotropa2foto = new ImageIcon(tropa2_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes2_foto.setIcon(iconotropa2foto);

            //TROPA 3 UPDATE
                tropa3_foto = new ImageIcon(this.getClass().getResource(WIZARD));
                iconotropa3foto = new ImageIcon(tropa3_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes3_foto.setIcon(iconotropa3foto);


            //TROPA 4 UPDATE
                tropa4_foto = new ImageIcon(this.getClass().getResource(BOMB_PHASE2));
                iconotropa4foto = new ImageIcon(tropa4_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
                jlTropes4_foto.setIcon(iconotropa4foto);

        }
    }

    /**
     * Retorna el JPanel de les tropes
     * @return jpTropes és el Jpanel corresponent
     */
    public JPanel getJpTropes() {
        return jpTropes;
    }

    /**
     * Assigna el JPanel de les tropes
     * @param jpTropes és el Jpanel corresponent
     */
    public void setJpTropes(JPanel jpTropes) {
        this.jpTropes = jpTropes;
    }

    /**
     * Retorna la foto de la tropa 1
     * @return jlTropes1_foto representa la foto de la tropa 1
     */
    public JButton getJlTropes1_foto() {
        return jlTropes1_foto;
    }

    /**
     * Retorna la vida de la tropa 1
     * @return jlTropes1_vida representa la vida de la tropa 1
     */
    public JLabel getJlTropes1_vida() {
        return jlTropes1_vida;
    }

    /**
     * Retorna el dany de la tropa 1
     * @return jlTropes1_dany representa el dany de la tropa 1
     */
    public JLabel getJlTropes1_dany() {
        return jlTropes1_dany;
    }

    /**
     * Retorna la foto de la tropa 2
     * @return jlTropes2_foto representa la foto de la tropa 2
     */
    public JButton getJlTropes2_foto() {
        return jlTropes2_foto;
    }

    /**
     * Retorna la vida de la tropa 2
     * @return jlTropes2_vida representa la vida de la tropa 2
     */
    public JLabel getJlTropes2_vida() {
        return jlTropes2_vida;
    }

    /**
     * Retorna el dany de la tropa 2
     * @return jlTropes2_dany representa el dany de la tropa 2
     */
    public JLabel getJlTropes2_dany() {
        return jlTropes2_dany;
    }

    /**
     * Retorna la foto de la tropa 3
     * @return jlTropes3_foto representa la foto de la tropa 3
     */
    public JButton getJlTropes3_foto() {
        return jlTropes3_foto;
    }

    /**
     * Retorna la vida de la tropa 3
     * @return jlTropes3_vida representa la vida de la tropa 3
     */
    public JLabel getJlTropes3_vida() {
        return jlTropes3_vida;
    }

    /**
     * Retorna el dany de la tropa 3
     * @return jlTropes3_dany representa el dany de la tropa 3
     */
    public JLabel getJlTropes3_dany() {
        return jlTropes3_dany;
    }

    /**
     * Retorna la foto de la tropa 4
     * @return jlTropes4_foto representa la foto de la tropa 4
     */
    public JButton getJlTropes4_foto() {
        return jlTropes4_foto;
    }

    /**
     * Retorna la vida de la tropa 4
     * @return jlTropes4_vida representa la vida de la tropa 4
     */
    public JLabel getJlTropes4_vida() {
        return jlTropes4_vida;
    }

    /**
     * Retorna el dany de la tropa 4
     * @return jlTropes4_dany representa el dany de la tropa 4
     */
    public JLabel getJlTropes4_dany() {
        return jlTropes4_dany;
    }


}
