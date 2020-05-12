package src.View;

import src.Controller.FriendsController;
import src.Usuari;
import src.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class FriendView extends JFrame {
    private JPanel jpFriends;
    private JPanel jpAmics;
    private JPanel[] jpsAmics;
    private ArrayList<Usuari> amics;
    private JScrollPane scrollPaneAmics;
    private JButton jbSearchAmic;
    private JTextField jtfSearchAmic;
    private Usuari usuari;
    private FriendsController friendsController;

    public FriendView(Usuari usr, FriendsController friendsCtrl) {
        this.usuari = usr;
        setAmics(usr.getAmics());
        this.friendsController = friendsCtrl;
    }
    public void initAll(){
        this.removeAll();

			//------------------------------------------------------OK------------------------------------------------------
        jpFriends = new JPanel(null);
        jpFriends.setOpaque(false);

        jpsAmics = new JPanel[amics.size()];

        jpAmics = new JPanel();
        jpAmics.setLayout(new GridLayout(amics.size(), 1, 0, 1));
        jpAmics.setOpaque(false);
/*
        scrollPaneAmics = new JScrollPane();
        scrollPaneAmics.setBounds(0, 200, 430, 500);
        scrollPaneAmics.setEnabled(true);
        scrollPaneAmics.setOpaque(false);
*/
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
			//--------------------------------------------------------------------------------------------------------------

        for (int i = 0; i < amics.size() ; i++) {
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
								friendsController.removeFriend(amics.get(finalI));
							}
						});

            jpsAmics[i].setOpaque(false);
            jpsAmics[i].setVisible(true);

            JLabel nomAmic = new JLabel();
						nomAmic.setText("<html><font color='white'>" + Utils.ferEspais(14) + " "+ amics.get(i).getNickName() + "</font></html>");
            nomAmic.setForeground(Color.WHITE);
            nomAmic.setBorder(new EmptyBorder(10,0,0,0));//top,left,bottom,right

            nomAmic.setFont(new Font("Helvetica", 0, 20));
            nomAmic.setBounds(300, 290, 100, 15);
            jpsAmics[i].add(nomAmic);

						/*JLabel aux = null;
            if (teComAmic(amics.get(i))){
              aux = new JLabel("No tiene amigosh");
            } else {
              aux = new JLabel("No tiene amigosh");
            }
						jpsAmics[i].add(aux);
            */

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
						if(amics.get(i).isOnline()){
							online.setText("<html><font color='white'>" + Utils.ferEspais(22) + " Online" +  "</font></html>");
							online.setForeground(Color.WHITE);

                        }else{
							online.setText("<html><font color='white'>" + Utils.ferEspais(22) + " Offline" + "</font></html>");
							online.setForeground(Color.GRAY);
						}

            online.setBounds(300, 320, 100, 15);
						jpsAmics[i].add(online);


						jpsAmics[i].setBounds(15, 60 + (110 * i), 410, 90);

            jpAmics.add(jpsAmics[i]);

						//jpAmics.add(separator);
        }



		//------------------------------------------------------OK------------------------------------------------------
		//jpAmics.setBounds(0, 0, 300, 500);
		jpAmics.setVisible(true);
		//scrollPaneAmics.setViewportView(jpAmics);
		Component table;
		JScrollPane amics = new JScrollPane(jpAmics);
        amics.setBounds(0, 200, 430, 500);
        amics.setEnabled(true);
        amics.setOpaque(false);
        //scrollPaneAmics.getViewport().setOpaque(false);
        jpFriends.add(amics);
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

    private boolean teComAmic(Usuari amic){
      for (Usuari amics :usuari.getAmics() ) {
        if (amics.getIdUsuari() == amic.getIdUsuari()) {
          return true;
        }
      }
      return false;
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
        initAll();
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
