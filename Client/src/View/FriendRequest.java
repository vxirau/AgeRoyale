package src.View;

import src.Controller.FriendsController;
import src.Controller.RoomsController;
import src.Partida;
import src.Usuari;
import src.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

public class FriendRequest extends JFrame {
    private JPanel jpPare;
    private JPanel[] jpsRequest;
    private JScrollPane jScrollPane;
    private JPanel jpRequest;
    private FriendsController controller;
    private ArrayList<Usuari> requests;

    public FriendRequest(FriendsController controller, ArrayList<Usuari> requests) {
        this.controller = controller;
        this.requests = requests;
        setTitle("RequestFriend");
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        initComponents();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);
    }

    private void initComponents() {
        colocarPanel();
        colocarElements();
    }

    private void colocarPanel() {
        jpPare = new JPanel();
        jpPare.setLayout(null);
        jpPare.setOpaque(true);
        this.getContentPane().add(jpPare);
    }

    private void colocarElements() {


        jpRequest = new JPanel(null);
        jpRequest.setOpaque(false);

        jpsRequest = new JPanel[requests.size()];

        jpRequest = new JPanel(new GridLayout(10, 1));
        jpRequest.setOpaque(false);

        jScrollPane = new JScrollPane();
        jScrollPane.setBounds(0, 200, 450, 500);
        jScrollPane.setEnabled(true);
        jScrollPane.setOpaque(false);
        jScrollPane.getViewport().setOpaque(false);


        //Titol
        JPanel titol = new JPanel();
        titol.setBounds(10, 40, 600, 300);
        titol.setOpaque(false);
        titol.setLayout(null);
        JLabel jlTitol = new JLabel();
        jlTitol.setText("Friend Request");
        jlTitol.setBounds(10, 40, 400, 80);
        jlTitol.setHorizontalAlignment(SwingConstants.CENTER);
        jlTitol.setOpaque(false);
        jlTitol.setForeground(Color.decode("#4F1900"));
        jlTitol.setFont(new Font("Herculanum", Font.BOLD, 30));
        titol.add(jlTitol);
        ImageIcon fonsProgressBar = new ImageIcon(this.getClass().getResource("/resources/friends_title_bg.png"));
        Icon iconoProgressBar = new ImageIcon(fonsProgressBar.getImage().getScaledInstance(350, 100, Image.SCALE_FAST));
        JLabel fondo_petit= new JLabel();
        fondo_petit.setIcon(iconoProgressBar);
        getLayeredPane().add(fondo_petit, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo_petit.setBounds(40, 30, 500, 100);
        titol.add(fondo_petit);
        jpPare.add(titol);




        for (int i = 0; i < requests.size(); i++) {
            jpsRequest[i] = new JPanel(new GridLayout(2, 1)) {
                protected void paintComponent(Graphics g) {
                    ImageIcon elementButton = new ImageIcon(this.getClass().getResource("/resources/friend_bg.png"));
                    g.drawImage(elementButton.getImage(), 20, 0, null);
                    super.paintComponent(g);
                }
            };

            jpsRequest[i].setOpaque(false);
            jpsRequest[i].setVisible(true);

            JLabel nomAmic = new JLabel();
            nomAmic.setText(requests.get(i).getNickName());
            nomAmic.setLayout(null);
            nomAmic.setForeground(Color.WHITE);
            nomAmic.setHorizontalAlignment(SwingConstants.CENTER);
            nomAmic.setBorder(new EmptyBorder(20, 0, 0, 0));//top,left,bottom,right
            nomAmic.setFont(new Font("Helvetica", Font.BOLD, 25));
            jpsRequest[i].add(nomAmic);

            int finalI = i;
            jpsRequest[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    controller.requestFriend(requests.get(finalI));
                }
            });

            jpRequest.add(jpsRequest[i]);

        }

        setSize(450, 800);
        jpRequest.setVisible(true);
        jScrollPane.setViewportView(jpRequest);
        jpPare.add(jScrollPane);

        ImageIcon imagen2 = new ImageIcon(this.getClass().getResource("/resources/fondoMadera.png"));
        Icon icono = new ImageIcon(imagen2.getImage().getScaledInstance(450, 800, Image.SCALE_DEFAULT));
        JLabel fondo = new JLabel();
        fondo.setIcon(icono);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, 450, 800);
        jpPare.add(fondo);

        this.setContentPane(jpPare);

        revalidate();
        repaint();


    }
}