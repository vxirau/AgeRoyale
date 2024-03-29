package src.View;

import src.Controller.ConfigController;
import src.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
* Vista de la configuració del usuari. Hereda de JFrame ja que volem que sigui una finestra.
* */
public class ConfigView extends JFrame {

    private ConfigController configController;
    private JPanel jpConfig;
    private JTextField jtfConfigNickname;
    private JTextField jtfConfigCorreu;
    private JTextField jtfConfigContrasenya;
    private JButton jbConfigSave;


    /**
    * Constructor de la classe
     * @param confiController controlador assignat a aquesta vista
    * */
    public ConfigView(ConfigController confiController) {
        this.configController = confiController;
        initAll();
    }


    /**
    * Encarregada de inicialitzar tots els elements de UI de la classe
    * */
    public void initAll(){
        jpConfig = new JPanel(null);

        JButton jpConfigTitle = new JButton("Configuració");
        jpConfigTitle.setBounds(70, 35, 300, 130);
        jpConfigTitle.setOpaque(false);
        jpConfigTitle.setContentAreaFilled(false);
        jpConfigTitle.setBorderPainted(false);
        jpConfigTitle.setForeground(Color.WHITE);
        jpConfigTitle.setFont(new Font("Helvetica", Font.BOLD, 30));
        jpConfigTitle.setAlignmentX(SwingConstants.CENTER);
        jpConfigTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jpConfigTitle.setHorizontalTextPosition(SwingConstants.CENTER);
        jpConfigTitle.setAlignmentY(SwingConstants.CENTER);
        jpConfigTitle.setVerticalAlignment(SwingConstants.CENTER);
        jpConfigTitle.setVerticalTextPosition(SwingConstants.CENTER);
        ImageIcon fonsProgressBar = new ImageIcon(this.getClass().getResource("/resources/config_title_bg.png"));
        Icon iconoProgressBar = new ImageIcon(fonsProgressBar.getImage().getScaledInstance(300, 130, Image.SCALE_FAST));
        jpConfigTitle.setIcon(iconoProgressBar);
        jpConfig.add(jpConfigTitle);

        //NICKNAME
        JPanel jpConfigNickname = new JPanel(new GridLayout(3, 1)) {
            protected void paintComponent(Graphics g) {
                ImageIcon elementButton = new ImageIcon(this.getClass().getResource("/resources/config_element_bg.png"));
                g.drawImage(elementButton.getImage(), 0, 0, null);
                super.paintComponent(g);
            }
        };
        jpConfigNickname.setBounds(70, 210, 300, 130);
        jpConfigNickname.setOpaque(false);

        JLabel jlConfigNickname = new JLabel();
        jlConfigNickname.setText("<html><font color='black'><br/>" + Utils.ferEspais(10) + " Usuari: " + "</font></html>");
        jlConfigNickname.setForeground(Color.WHITE);
        jlConfigNickname.setFont(new Font("Helvetica", 0, 15));
        jlConfigNickname.setBounds(120, 180, 250, 15);
        jpConfigNickname.add(jlConfigNickname);

        jtfConfigNickname = new JTextField();
        jtfConfigNickname.setOpaque(false);
        jtfConfigNickname.setBounds(120, 200, 250, 15);
        jtfConfigNickname.setBorder(BorderFactory.createEmptyBorder(0, 45, 0, 30));
        jpConfigNickname.add(jtfConfigNickname);
        jpConfig.add(jpConfigNickname);

        //CORREU
        JPanel jpConfigCorreu = new JPanel(new GridLayout(3, 1)) {
            protected void paintComponent(Graphics g) {
                ImageIcon elementButton = new ImageIcon(this.getClass().getResource("/resources/config_element_bg.png"));
                g.drawImage(elementButton.getImage(), 0, 0, null);
                super.paintComponent(g);
            }
        };
        jpConfigCorreu.setBounds(70, 350, 300, 130);
        jpConfigCorreu.setOpaque(false);

        JLabel jlConfigCorreu = new JLabel();
        jlConfigCorreu.setText("<html><font color='black'><br/>" + Utils.ferEspais(10) + " Correu: " + "</font></html>");
        jlConfigCorreu.setForeground(Color.WHITE);
        jlConfigCorreu.setFont(new Font("Helvetica", 0, 15));
        jlConfigCorreu.setBounds(120, 180, 250, 15);
        jpConfigCorreu.add(jlConfigCorreu);

        jtfConfigCorreu = new JTextField();
        jtfConfigCorreu.setOpaque(false);
        jtfConfigCorreu.setBounds(120, 200, 250, 15);
        jtfConfigCorreu.setBorder(BorderFactory.createEmptyBorder(0, 45, 0, 30));
        jpConfigCorreu.add(jtfConfigCorreu);
        jpConfig.add(jpConfigCorreu);

        //CONTRASENYA
        JPanel jpConfigContrasenya = new JPanel(new GridLayout(3, 1)) {
            protected void paintComponent(Graphics g) {
                ImageIcon elementButton = new ImageIcon(this.getClass().getResource("/resources/config_element_bg.png"));
                g.drawImage(elementButton.getImage(), 0, 0, null);
                super.paintComponent(g);
            }
        };
        jpConfigContrasenya.setBounds(70, 490, 300, 130);
        jpConfigContrasenya.setOpaque(false);

        JLabel jlConfigContrasenya = new JLabel();
        jlConfigContrasenya.setText("<html><font color='black'><br/>" + Utils.ferEspais(10) + " Contrasenya: " + "</font></html>");
        jlConfigContrasenya.setForeground(Color.WHITE);
        jlConfigContrasenya.setFont(new Font("Helvetica", 0, 15));
        jlConfigContrasenya.setBounds(120, 180, 250, 15);
        jpConfigContrasenya.add(jlConfigContrasenya);

        jtfConfigContrasenya = new JTextField();
        jtfConfigContrasenya.setOpaque(false);
        jtfConfigContrasenya.setBounds(120, 200, 250, 15);
        jtfConfigContrasenya.setBorder(BorderFactory.createEmptyBorder(0, 45, 0, 30));
        jpConfigContrasenya.add(jtfConfigContrasenya);
        jpConfig.add(jpConfigContrasenya);

        jbConfigSave = new JButton();
        jbConfigSave.setName("SAVE");
        jbConfigSave.setText("Guardar");
        jbConfigSave.setBounds(110, 615, 200,30);
        jbConfigSave.setOpaque(false);
        jbConfigSave.setHorizontalTextPosition(JButton.CENTER);
        jbConfigSave.setFont(new Font("Helvetica", Font.BOLD, 15));
        jbConfigSave.setForeground(Color.BLACK);
        jbConfigSave.setContentAreaFilled(false);
        jbConfigSave.setBorderPainted(false);
        jbConfigSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                configController.saveBtnClicked();
            }
        });
        ImageIcon fonsButton= new ImageIcon(this.getClass().getResource("/resources/config_saveButton_bg.png"));
        Icon iconoButton = new ImageIcon(fonsButton.getImage().getScaledInstance(200, 30, Image.SCALE_FAST));
        jbConfigSave.setIcon(iconoButton);
        jpConfig.add(jbConfigSave);

        ImageIcon img = new ImageIcon(this.getClass().getResource("/resources/bg_config.png"));
        Icon icono = new ImageIcon(img.getImage().getScaledInstance(450, 700, Image.SCALE_DEFAULT));
        JLabel fondo = new JLabel();
        fondo.setIcon(icono);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, 450, 700);
        jpConfig.add(fondo);
    }

    /**
    * Retorna el panell de configuració principal
     * @return jpConfig variable de tipus JPanel que conté tota la informació de la vista
    * */
    public JPanel getJpConfig() {
        return jpConfig;
    }

    /**
    * Assigna el panell princpal de la vista al panell que li passen per valor
     * @param jpConfig variable de tipus JPanel que conté tota la vista
    * */
    public void setJpConfig(JPanel jpConfig) {
        this.jpConfig = jpConfig;
    }

    /**
    * Retorna la variable de tipus JTextField on es posa en nom de usuari introduit a la part de configuracio
     * @return jtfConfigNickname variable de tipus JTextField on esta la informació del usuari
    * */
    public JTextField getJtfConfigNickname() {
        return jtfConfigNickname;
    }

    /**
     * Assigna el cuadre de text del nom al que reb per parametre
     * @param jtfConfigNickname variable de tipus JTextField que conté el nom del usuari
     * */
    public void setJtfConfigNickname(JTextField jtfConfigNickname) {
        this.jtfConfigNickname = jtfConfigNickname;
    }

    /**
     * Retorna la variable de tipus JTextField on es posa el correu de usuari introduit a la part de configuracio
     * @return jtfConfigCorreu variable de tipus JTextField on esta la informació del usuari
     * */
    public JTextField getJtfConfigCorreu() {
        return jtfConfigCorreu;
    }

    /**
     * Assigna el cuadre de text del nom al que reb per parametre
     * @param jtfConfigCorreu variable de tipus JTextField que conté el correu del usuari
     * */
    public void setJtfConfigCorreu(JTextField jtfConfigCorreu) {
        this.jtfConfigCorreu = jtfConfigCorreu;
    }

    /**
     * Retorna la variable de tipus JTextField on es posa la conrasenya de usuari introduit a la part de configuracio
     * @return jtfConfigContrasenya variable de tipus JTextField on esta la informació del usuari
     * */
    public JTextField getJtfConfigContrasenya() {
        return jtfConfigContrasenya;
    }

    /**
     * Assigna el cuadre de text del nom al que reb per parametre
     * @param jtfConfigContrasenya variable de tipus JTextField que conté ela contrasenya del usuari
     * */
    public void setJtfConfigContrasenya(JTextField jtfConfigContrasenya) {
        this.jtfConfigContrasenya = jtfConfigContrasenya;
    }

    /**
    * Retorna el jbutton de desar informació
     * @return jbConfigSave variabel de tipus JButton
    * */
    public JButton getJbConfigSave() {
        return jbConfigSave;
    }


    /**
    * Assigna el JButton el JButton que reb per valor
     * @param jbConfigSave JButton a assignar
    * */
    public void setJbConfigSave(JButton jbConfigSave) {
        this.jbConfigSave = jbConfigSave;
    }

}
