package src.View;

import src.Controller.FriendsController;
import src.Usuari;
import src.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
* Classe destinada a mostrar els amics del usuari i hereda de JFrame.
* */
public class FriendView extends JFrame {
    private JPanel jpFriends;
    private JPanel jpAmics;
    private JPanel[] jpsAmics;
    private JScrollPane scrollPaneAmics;
    private JButton jbSearchAmic;
    private JTextField jtfSearchAmic;
    private JButton request;
    private FriendsController friendsController;
    private Usuari usuari;


    /**
    * Constructor de la classe
     * @param usr usuari del client
     * @param friendsCtrl controlador de la fienstra
    * */
    public FriendView(Usuari usr, FriendsController friendsCtrl) {
        this.usuari = usr;
        setAmics(usr.getAmics());
        this.friendsController = friendsCtrl;
    }

    /**
    * Inicia la finestra grafica
    * */
    public void initAll(){
        this.removeAll();

			//------------------------------------------------------OK------------------------------------------------------
        jpFriends = new JPanel(null);
        jpFriends.setOpaque(false);

        jpsAmics = null;
        jpsAmics = new JPanel[usuari.getAmics().size()];

        jpAmics = new JPanel();
        jpAmics.setLayout(new BoxLayout(jpAmics, BoxLayout.Y_AXIS));
        jpAmics.setOpaque(false);


        scrollPaneAmics = new JScrollPane();
        scrollPaneAmics.setBounds(0, 200, 450, 500);
        scrollPaneAmics.setEnabled(true);
        scrollPaneAmics.setOpaque(false);
        scrollPaneAmics.getViewport().setOpaque(false);

        JPanel titol = new JPanel();
        titol.setBounds(20, 30, 400, 300);
        titol.setOpaque(false);
        titol.setLayout(null);
        JButton jpFriendsTitle = new JButton("Amics");
        jpFriendsTitle.setBounds(45, 20, 300, 30);
        jpFriendsTitle.setOpaque(false);
        jpFriendsTitle.setContentAreaFilled(false);
        jpFriendsTitle.setBorderPainted(false);
        jpFriendsTitle.setForeground(Color.WHITE);
        jpFriendsTitle.setFont(new Font("Helvetica", Font.BOLD, 30));
        titol.add(jpFriendsTitle);

        request = new JButton();
        request.setText("Friend Request");
        request.setBackground(Color.decode("#4F1900"));
        request.setOpaque(true);
        request.setEnabled(true);
        request.setBounds(130, 55, 120, 30);
        request.setForeground(Color.WHITE);

        titol.add(request);

        ImageIcon fonsProgressBar = new ImageIcon(this.getClass().getResource("/resources/friends_title_bg.png"));
        Icon iconoProgressBar = new ImageIcon(fonsProgressBar.getImage().getScaledInstance(300, 100, Image.SCALE_FAST));
        JLabel fondo_petit= new JLabel();
        fondo_petit.setIcon(iconoProgressBar);
        getLayeredPane().add(fondo_petit, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo_petit.setBounds(50, 5, 300, 100);
        titol.add(fondo_petit);
        jpFriends.add(titol);

        JPanel jpSearchAmic = new JPanel(new GridLayout(1, 1)) {
            protected void paintComponent(Graphics g) {
                ImageIcon elementButton = new ImageIcon(this.getClass().getResource("/resources/busqueda_amic.png"));
                g.drawImage(elementButton.getImage(), 0, 0, null);
                super.paintComponent(g);
            }
        };

        jpSearchAmic.setBounds(30, 140, 263, 37);
        jpSearchAmic.setOpaque(false);

        jtfSearchAmic = new JTextField();
        jtfSearchAmic.setOpaque(false);
        jtfSearchAmic.setBounds(20, 10, 100, 15);
        jtfSearchAmic.setMargin(new Insets(10, 10, 10, 10));
        jtfSearchAmic.setBorder(BorderFactory.createEmptyBorder());
        jpSearchAmic.add(jtfSearchAmic);
        jpFriends.add(jpSearchAmic);

        jbSearchAmic = new JButton("");
        jbSearchAmic.setBounds(330, 140, 60, 40);
        jbSearchAmic.setOpaque(false);
        jbSearchAmic.setHorizontalTextPosition(JButton.CENTER);
        jbSearchAmic.setVerticalTextPosition(JButton.CENTER);
        jbSearchAmic.setContentAreaFilled(false);
        jbSearchAmic.setBorderPainted(false);

        ImageIcon fonsButtonUser = new ImageIcon(this.getClass().getResource("/resources/boto_cerca_amic.png"));
        Icon iconoButtonUser = new ImageIcon(fonsButtonUser.getImage().getScaledInstance(50, 50, Image.SCALE_FAST));
        jbSearchAmic.setIcon(iconoButtonUser);
        jpFriends.add(jbSearchAmic);
        //--------------------------------------------------------------------------------------------------------------


        for (int i = 0; i < usuari.getAmics().size() ; i++) {
            jpsAmics[i] = new JPanel(new GridLayout(3, 1)){
                protected void paintComponent(Graphics g) {
                    ImageIcon elementButton = new ImageIcon(this.getClass().getResource("/resources/friend_bg.png"));
                    g.drawImage(elementButton.getImage(), 0, 0, null);
                    super.paintComponent(g);
                }
            };
            int finalI = i;
            jpsAmics[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    friendsController.removeFriend(usuari.getAmics().get(finalI));
                }
            });

            jpsAmics[i].setOpaque(false);
            jpsAmics[i].setVisible(true);

            JLabel nomAmic = new JLabel();
						nomAmic.setText("<html><font color='white'>" + Utils.ferEspais(14) + " "+ usuari.getAmics().get(i).getNickName() + "</font></html>");
            nomAmic.setForeground(Color.WHITE);
            nomAmic.setBorder(new EmptyBorder(10,0,0,0));//top,left,bottom,right

            nomAmic.setFont(new Font("Helvetica", 0, 20));
            jpsAmics[i].add(nomAmic);


            JLabel jlLvl = new JLabel("Lvl 0.");
            if (usuari.getStats() != null) {
                int experiencia = 0;
                int lvl = 0;
                experiencia = (usuari.getStats().getTotalVictories() * 3) + (usuari.getStats().getTotalPartides() - usuari.getStats().getTotalVictories());
                while (experiencia > 100) {
                    lvl++;
                    experiencia -= 100;
                }
                jlLvl.setText("Lvl " + lvl + ".");
            }

            JLabel online = new JLabel();
            if(usuari.getAmics().get(i).isOnline()){
                online.setText("<html><font color='white'>" + Utils.ferEspais(22) + " Online" +  "</font></html>");
                online.setForeground(Color.WHITE);

            }else{
                online.setText("<html><font color='white'>" + Utils.ferEspais(22) + " Offline" + "</font></html>");
                online.setForeground(Color.GRAY);
            }

            jpsAmics[i].add(online);
            jpsAmics[i].setPreferredSize(new Dimension(410,90));
            jpsAmics[i].setMaximumSize(new Dimension(430,90));
            jpsAmics[i].setSize(430, 90);
            jpAmics.add(jpsAmics[i]);
        }



		//------------------------------------------------------OK------------------------------------------------------
		jpAmics.setVisible(true);
        scrollPaneAmics.setViewportView(jpAmics);
        jpFriends.add(scrollPaneAmics);
        ImageIcon img = new ImageIcon(this.getClass().getResource("/resources/fondoMadera.png"));
        Icon icono = new ImageIcon(img.getImage().getScaledInstance(450, 700, Image.SCALE_DEFAULT));
        JLabel fondo = new JLabel();
        fondo.setIcon(icono);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, 450, 700);
        jpFriends.add(fondo);
				//--------------------------------------------------------------------------------------------------------------
        revalidate();
        repaint();
    }


    /**
    * Getter de la variable jpFriends
     * @return variable de tipus JPanel
    * */
    public JPanel getJpFriends() {
        return jpFriends;
    }

    /**
    * Setter de la variable jpFriends
     * @param jpFriends variable de tipus JPanel que reb per valor
    * */
    public void setJpFriends(JPanel jpFriends) {
        this.jpFriends = jpFriends;
    }

    /**
     * Getter de la variable jpAmics
     * @return variable de tipus JPanel
     * */
    public JPanel getJpAmics() {
        return jpAmics;
    }

    /**
     * Setter de la variable jpAmics
     * @param jpAmics variable de tipus JPanel que reb per valor
     * */
    public void setJpAmics(JPanel jpAmics) {
        this.jpAmics = jpAmics;
    }

    /**
     * Getter de la variable jpsAmics
     * @return variable de tipus JPanel[]
     * */
    public JPanel[] getJpsAmics() {
        return jpsAmics;
    }

    /**
     * Setter de la variable jpsAmics
     * @param jpsAmics variable de tipus JPanel[] que reb per valor
     * */
    public void setJpsAmics(JPanel[] jpsAmics) {
        this.jpsAmics = jpsAmics;
    }

    /**
     * Setter dels amics del usuari.
     * @param amics llista de usuaris que corresponen als amics del usuari
     * */
    public void setAmics(ArrayList<Usuari> amics) {
        this.usuari.setAmics(amics);
        initAll();
    }

    public JTextField getTextField(){
        return this.jtfSearchAmic;
    }

    public JScrollPane getScrollPaneAmics() {
        return scrollPaneAmics;
    }

    public void setScrollPaneAmics(JScrollPane scrollPaneAmics) {
        this.scrollPaneAmics = scrollPaneAmics;
    }

    public JButton getJbSearchAmic() {
        return jbSearchAmic;
    }

    public void setJbSearchAmic(JButton jbSearchAmic) {
        this.jbSearchAmic = jbSearchAmic;
    }

    public JTextField getJtfSearchAmic() {
        return jtfSearchAmic;
    }

    public void setJtfSearchAmic(JTextField jtfSearchAmic) {
        this.jtfSearchAmic = jtfSearchAmic;
    }


    public void setControllers(KeyListener listenerDelTextField, MouseListener listenerCercaAmic, FriendsController controller) {
        jtfSearchAmic.addKeyListener(listenerDelTextField);
        jbSearchAmic.addMouseListener(listenerCercaAmic);
        request.addActionListener(controller);
    }

    public void setAmicsUsuari(ArrayList<Usuari> update) {
        this.usuari.setAmics(update);
    }
}
