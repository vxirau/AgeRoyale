package src.View;

import src.Usuari;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class FriendView extends JFrame {

    private JPanel jpFriends;
    private JPanel jpAmics;
    private JPanel[] jpsAmics;
    private ArrayList<Usuari> amics;
    private JScrollPane scrollPaneAmics;
    private JButton jbSearchAmic;
    private JTextField jtfSearchAmic;

    public FriendView(ArrayList<Usuari> amicsRecived) {
        jpFriends = new JPanel(null);
        jpFriends.setOpaque(false);

        amics = amicsRecived;
        jpsAmics = new JPanel[amics.size()];

        jpAmics = new JPanel(null);
        jpAmics.setOpaque(false);

        scrollPaneAmics = new JScrollPane();
        scrollPaneAmics.setBounds(0, 200, 450, 520);
        scrollPaneAmics.setOpaque(false);

        JButton jpFriendsTitle = new JButton("Amics");
        jpFriendsTitle.setBounds(70, 40, 300, 100);
        jpFriendsTitle.setOpaque(false);
        jpFriendsTitle.setContentAreaFilled(false);
        jpFriendsTitle.setBorderPainted(false);
        jpFriendsTitle.setForeground(Color.WHITE);
        jpFriendsTitle.setFont(new Font("Helvetica", Font.BOLD, 30));
        jpFriendsTitle.setAlignmentX(SwingConstants.CENTER);
        jpFriendsTitle.setHorizontalAlignment(SwingConstants.CENTER);
        jpFriendsTitle.setHorizontalTextPosition(SwingConstants.CENTER);
        jpFriendsTitle.setAlignmentY(SwingConstants.CENTER);
        jpFriendsTitle.setVerticalAlignment(SwingConstants.CENTER);
        jpFriendsTitle.setVerticalTextPosition(SwingConstants.CENTER);
        ImageIcon fonsProgressBar = new ImageIcon(this.getClass().getResource("/resources/friends_title_bg.png"));
        Icon iconoProgressBar = new ImageIcon(fonsProgressBar.getImage().getScaledInstance(300, 100, Image.SCALE_FAST));
        jpFriendsTitle.setIcon(iconoProgressBar);
        jpFriends.add(jpFriendsTitle);

        //TODO: Panell de cerca

        for (int i = 0; i < amics.size() ; i++) {
            jpsAmics[i] = new JPanel(new GridLayout(1, 3)){
                protected void paintComponent(Graphics g) {
                    ImageIcon elementButton = new ImageIcon(this.getClass().getResource("/resources/friend_bg.png"));
                    g.drawImage(elementButton.getImage(), 0, 0, null);
                    super.paintComponent(g);
                }
            };
            jpsAmics[i].setOpaque(false);
            jpsAmics[i].setVisible(true);
            jpsAmics[i].setAlignmentY(SwingConstants.CENTER);
            jpsAmics[i].setBounds(15, 60 * amics.size() * i, 410, 90);
            jpsAmics[i].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
            JLabel nomAmic = new JLabel(amics.get(i).getNickName());
            nomAmic.setForeground(Color.WHITE);
            nomAmic.setFont(new Font("Helvetica", 0, 15));
            nomAmic.setBounds(0, 0, 100, 15);
            jpsAmics[i].add(nomAmic);

            //TODO: crearfuncio que comprovi aixo per id de usuari
            //if (usuari.getAmics().contains(amics.get(i))){
            JLabel aux = new JLabel("BU");
            //} else {
            //TODO: boto per afegir aquest a amics
            //}

            jpsAmics[i].add(aux);
            jpAmics.add(jpsAmics[i]);
        }
        jpAmics.setVisible(true);
        jpAmics.setBounds(0, 0, 300, 500);
        jpAmics.setBackground(Color.green);
        scrollPaneAmics.setViewportView(jpAmics);
        scrollPaneAmics.getViewport().setOpaque(false);
        jpFriends.add(scrollPaneAmics);
        //jpFriends.add(jpAmics);

        ImageIcon img = new ImageIcon(this.getClass().getResource("/resources/fondoMadera.png"));
        Icon icono = new ImageIcon(img.getImage().getScaledInstance(450, 700, Image.SCALE_DEFAULT));
        JLabel fondo = new JLabel();
        fondo.setIcon(icono);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, 450, 700);
        jpFriends.add(fondo);
    }

    public JPanel getJpFriends() {
        return jpFriends;
    }

    public void setJpFriends(JPanel jpFriends) {
        this.jpFriends = jpFriends;
    }

    public JPanel getJpAmics() {
        return jpAmics;
    }

    public void setJpAmics(JPanel jpAmics) {
        this.jpAmics = jpAmics;
    }

    public JPanel[] getJpsAmics() {
        return jpsAmics;
    }

    public void setJpsAmics(JPanel[] jpsAmics) {
        this.jpsAmics = jpsAmics;
    }

    public ArrayList<Usuari> getAmics() {
        return amics;
    }

    public void setAmics(ArrayList<Usuari> amics) {
        this.amics = amics;
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
}
