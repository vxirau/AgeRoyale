package src.View;

import src.Controller.WaitingController;
import src.Partida;
import src.Usuari;
import src.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class WaitingRoomView extends JFrame {
    private JButton start;
    private ArrayList<Usuari> friends;

    //Publica, esperar a que es connecti algú i quan es connecti començar partida

    //Privada, convidar amics en linia (amics entren si accepten)

    public WaitingRoomView(Partida p, Usuari usr){
        this.friends = usr.getAmics();
        JPanel main  = new JPanel();
        main.setLayout(null);
        main.setOpaque(false);

        JLabel jlTitol = new JLabel();
        jlTitol.setText("Waiting for players...");
        jlTitol.setBounds(85, 30, 300, 60);
        jlTitol.setHorizontalAlignment(SwingConstants.CENTER);
        jlTitol.setOpaque(false);
        jlTitol.setForeground(Color.WHITE);
        jlTitol.setFont(new Font("Herculanum", Font.BOLD, 25));
        main.add(jlTitol);

        JScrollPane players = new JScrollPane();
        players.setEnabled(true);
        players.getViewport().setOpaque(false);
        players.setBounds(30,100,400,100);
        players.setOpaque(false);
        JPanel jugador = new JPanel();
        jugador.setLayout(null);
        jugador.setOpaque(false);


        JScrollPane spectators = new JScrollPane();
        spectators.setOpaque(false);
        spectators.setEnabled(true);
        spectators.getViewport().setOpaque(false);
        spectators.setBounds(30,220,400,200);
        JPanel espectador = new JPanel();
        espectador.setLayout(null);
        espectador.setOpaque(false);

        spectators.setViewportView(espectador);
        players.setViewportView(jugador);
        main.add(players);
        main.add(spectators);
        start = new JButton();
        start.setText("Start Game");
        start.setHorizontalAlignment(SwingConstants.CENTER);
        start.setBounds(30,100,100,50);
        //main.add(start);

        //TODO: detectar quan es connecti algú, començar partida
        //TODO: crear taula rooms, amb les diferentes partides pendents
        //TODO: posar fondo a la view

        if(!p.isPublic() && p.getHost().equals(usr.getNickName())){
            JLabel separator = new JLabel();
            separator.setText(Utils.ferDottedLine(41));
            separator.setBounds(22, 450, 440, 10);
            separator.setForeground(Color.decode("#FFDC60"));
            separator.setFont(new Font("Arial", Font.BOLD, 30));
            main.add(separator);

            JScrollPane friendInvites = new JScrollPane();
            friendInvites.setEnabled(true);
            friendInvites.getViewport().setOpaque(false);
            friendInvites.setOpaque(false);
            friendInvites.setBounds(30,480,400,200);
            JPanel inviteFriends = new JPanel();
            inviteFriends.setLayout(new BoxLayout(inviteFriends, BoxLayout.Y_AXIS));
            inviteFriends.setOpaque(false);
            
            for(Usuari f : friends){
                friendInvites.add(createFriend(f));
            }

            friendInvites.setViewportView(inviteFriends);
            main.add(friendInvites);
        }


        ImageIcon imagen2 = new ImageIcon(this.getClass().getResource("/resources/fondo-rojo-oscuro-marron_28629-798.png"));
        Icon icono = new ImageIcon(imagen2.getImage().getScaledInstance(450, 800, Image.SCALE_DEFAULT));
        JLabel fondo = new JLabel();
        fondo.setIcon(icono);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, 450, 800);
        main.add(fondo);
        setSize(450, 800);
        this.setContentPane(main);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(450, 800);
    }

    public void setController(WaitingController controller){
        this.addWindowListener(controller);
        start.addActionListener(controller);
    }
    
    public JPanel createFriend(Usuari u){
        JPanel amic = new JPanel(new GridLayout(3, 1)){
            protected void paintComponent(Graphics g) {
                ImageIcon elementButton = new ImageIcon(this.getClass().getResource("/resources/friend_bg.png"));
                g.drawImage(elementButton.getImage(), 0, 0, null);
                super.paintComponent(g);
            }
        };
        amic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        amic.setOpaque(false);
        amic.setVisible(true);

        JLabel nomAmic = new JLabel();
        nomAmic.setText("<html><font color='white'>" + Utils.ferEspais(14) + " "+ u.getNickName() + "</font></html>");
        nomAmic.setForeground(Color.WHITE);
        nomAmic.setBorder(new EmptyBorder(10,0,0,0));//top,left,bottom,right

        nomAmic.setFont(new Font("Helvetica", 0, 20));
        amic.add(nomAmic);

        JLabel online = new JLabel();
        if(u.isOnline()){
            online.setText("<html><font color='white'>" + Utils.ferEspais(22) + " Online" +  "</font></html>");
            online.setForeground(Color.WHITE);

        }else{
            online.setText("<html><font color='white'>" + Utils.ferEspais(22) + " Offline" + "</font></html>");
            online.setForeground(Color.GRAY);
        }

        amic.add(online);
        amic.setPreferredSize(new Dimension(410,90));
        amic.setMaximumSize(new Dimension(430,90));
        amic.setSize(430, 90);
        return amic;
    }
} 
