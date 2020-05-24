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

/**
 * Vista de la sala d'espera de la partida. Hereda de JFrame ja que volem que sigui una finestra.
 */
public class WaitingRoomView extends JFrame {
    private JButton start;
    private Partida p;
    private Usuari usr;
    private JPanel main;
    private WaitingController waitingController;

    /**
     * Constructor de la classe
     * @param p partida a la que correspon la sala d'espera
     * @param usr usuari que ha iniciat sessió
     * @param waitingController controlador assignat a aquesta vista
     */
    public WaitingRoomView(Partida p, Usuari usr, WaitingController waitingController) {
        this.p = p;
        this.usr = usr;
        this.waitingController = waitingController;
        main = new JPanel();
        if (p != null) initAll();
    }

    /**
     * S'assigna la partida
     * @param partida partida a la que es farà la sala d'espera
     */
    public void setPartida(Partida partida){
        this.p = partida;
        initAll();
    }

    /**
     * Inicialitza la pantalla gràfica de la sala d'espera amb els components gràfics corresponents
     */
    public void initAll(){
        this.removeAll();

        main  = new JPanel();
        main.setLayout(null);
        main.setOpaque(false);

        JButton back = new JButton();
        back.setText("Go back");
        back.setBackground(Color.decode("#4F1900"));
        back.setOpaque(true);
        back.setEnabled(true);
        back.setBounds(10, 10, 90, 30);
        back.setForeground(Color.WHITE);
        back.addActionListener(waitingController);
        main.add(back);

        JLabel jlTitol = new JLabel();
        jlTitol.setText("Waiting for players...");
        jlTitol.setBounds(85, 30, 300, 60);
        jlTitol.setHorizontalAlignment(SwingConstants.CENTER);
        jlTitol.setOpaque(false);
        jlTitol.setForeground(Color.WHITE);
        jlTitol.setFont(new Font("Herculanum", Font.BOLD, 25));
        main.add(jlTitol);

        JLabel jName = new JLabel();
        jName.setText("Nom Sala: " + p.getName());
        jName.setBounds(85, 700, 300, 60);
        jName.setHorizontalAlignment(SwingConstants.CENTER);
        jName.setOpaque(false);
        jName.setForeground(Color.WHITE);
        jName.setFont(new Font("Herculanum", Font.BOLD, 25));
        main.add(jName);

        JScrollPane players = new JScrollPane();
        players.setEnabled(true);
        players.getViewport().setOpaque(false);
        players.setBounds(30,100,400,100);
        players.setOpaque(false);
        JPanel jugador = new JPanel();
        players.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jugador.setLayout(new BoxLayout(jugador, BoxLayout.Y_AXIS));
        jugador.setOpaque(false);


        JLabel playersText = new JLabel();
        playersText.setText("Jugadors Connectats");
        playersText.setBounds(85, 80, 300, 30);
        playersText.setHorizontalAlignment(SwingConstants.CENTER);
        playersText.setOpaque(false);
        playersText.setForeground(Color.WHITE);
        playersText.setFont(new Font("Herculanum", Font.BOLD, 15));
        main.add(playersText);

        JLabel espectadorsText = new JLabel();
        espectadorsText.setText("Espectadors Connectats");
        espectadorsText.setBounds(85, 200, 300, 30);
        espectadorsText.setHorizontalAlignment(SwingConstants.CENTER);
        espectadorsText.setOpaque(false);
        espectadorsText.setForeground(Color.WHITE);
        espectadorsText.setFont(new Font("Herculanum", Font.BOLD, 15));
        main.add(espectadorsText);

        JScrollPane spectators = new JScrollPane();
        spectators.setOpaque(false);
        spectators.setEnabled(true);
        spectators.getViewport().setOpaque(false);
        spectators.setBounds(30,220,400,200);
        JPanel espectador = new JPanel();
        spectators.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        espectador.setLayout(new BoxLayout(espectador, BoxLayout.Y_AXIS));
        espectador.setOpaque(false);

        if(p.getJugadors()!=null){
            for(Usuari f : p.getJugadors()){
                jugador.add(createFriend(f));
                jugador.add(makeSeparator());
            }
        }

        if(p.getEspectadors()!=null){
            for(Usuari f : p.getEspectadors()){
                espectador.add(createFriend(f));
                espectador.add(makeSeparator());
            }
        }

        spectators.setViewportView(espectador);
        players.setViewportView(jugador);
        main.add(players);
        main.add(spectators);

        start = new JButton();
        start.setText("Start Game");
        start.setHorizontalAlignment(SwingConstants.CENTER);
        start.setBounds(85, 660, 300, 30);
        start.setVisible(false);
        main.add(start);

        if(p.getHost().equals(usr.getNickName()) && p.getJugadors().size()<2){  //if(!p.isPublic() && p.getHost().equals(usr.getNickName()) && p.getJugadors().size()<2){
            JLabel separator = new JLabel();
            separator.setText(Utils.ferDottedLine(41));
            separator.setBounds(22, 440, 440, 10);
            separator.setForeground(Color.decode("#FFDC60"));
            separator.setFont(new Font("Arial", Font.BOLD, 30));
            main.add(separator);


            JLabel inviteText = new JLabel();
            inviteText.setText("Tria amics per convidar");
            inviteText.setBounds(85, 455, 300, 30);
            inviteText.setHorizontalAlignment(SwingConstants.CENTER);
            inviteText.setOpaque(false);
            inviteText.setForeground(Color.WHITE);
            inviteText.setFont(new Font("Herculanum", Font.BOLD, 15));
            main.add(inviteText);

            JLabel info = new JLabel();
            info.setText("Només es mostren els amics en linia");
            info.setBounds(85, 680, 300, 30);
            info.setHorizontalAlignment(SwingConstants.CENTER);
            info.setOpaque(false);
            info.setForeground(Color.WHITE);
            info.setFont(new Font("Herculanum", Font.BOLD, 15));
            main.add(info);

            JScrollPane friendInvites = new JScrollPane();
            friendInvites.setEnabled(true);
            friendInvites.getViewport().setOpaque(false);
            friendInvites.setOpaque(false);
            friendInvites.setBounds(30,480,400,200);
            JPanel inviteFriends = new JPanel();
            friendInvites.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            inviteFriends.setLayout(new BoxLayout(inviteFriends, BoxLayout.Y_AXIS));
            inviteFriends.setOpaque(false);

            for(Usuari f : usr.getAmics()){
                if(f.isOnline()){
                    JPanel panel = createFriend(f);
                    panel.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (WaitingRoomView.this.waitingController != null){
                                WaitingRoomView.this.waitingController.inviteFriend(f);
                            }
                        }
                    });
                    inviteFriends.add(panel);
                    inviteFriends.add(makeSeparator());
                }
            }

            friendInvites.setViewportView(inviteFriends);
            main.add(friendInvites);
        }
        if(p.getJugadors().size()==2 && p.getHost().equals(usr.getNickName())){
            start.setVisible(true);
        }

        ImageIcon imagen2 = new ImageIcon(this.getClass().getResource("/resources/fondo-rojo-oscuro-marron_28629-798.png"));
        Icon icono = new ImageIcon(imagen2.getImage().getScaledInstance(450, 800, Image.SCALE_DEFAULT));
        JLabel fondo = new JLabel();
        fondo.setIcon(icono);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, 450, 800);
        main.add(fondo);

        /*
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(450, 800);
        this.setContentPane(main);
         */

        revalidate();
        repaint();

    }

    /**
     * S'assigna el controlador a la vista
     * @param controller controlador que se li assigna
     */
    public void setController(WaitingController controller){
        start.addActionListener(controller);
    }

    /**
     * Genera el Jpanel de un amic
     * @param u usuari corresponent a amic
     * @return amic retorna el JPanel generat
     */
    public JPanel createFriend(Usuari u){
        JPanel amic = new JPanel(new GridLayout(1, 1));
        amic.setBackground(Color.decode("#FFDC60"));

        amic.setVisible(true);
        JLabel nomAmic = new JLabel();
        nomAmic.setText(u.getNickName());
        nomAmic.setForeground(Color.BLACK);
        nomAmic.setBorder(new EmptyBorder(10,0,0,0));
        nomAmic.setFont(new Font("Helvetica", Font.BOLD, 20));
        nomAmic.setHorizontalAlignment(SwingConstants.CENTER);

        amic.add(nomAmic);

        amic.setPreferredSize(new Dimension(400,50));
        amic.setMaximumSize(new Dimension(400,50));
        amic.setSize(400, 50);
        return amic;
    }

    /**
     * Genera un JPanel que representa una separació entre els JPanels d'amics
     * @return
     */
    public JPanel makeSeparator(){
        JPanel amic = new JPanel();
        amic.setPreferredSize(new Dimension(360,5));
        amic.setMaximumSize(new Dimension(360,5));
        amic.setSize(360, 5);
        amic.setVisible(true);
        amic.setOpaque(false);
        return amic;
    }

    /**
     * Retorna el Jpanel pare
     * @return main retorna el pare JPanel
     */
    public JPanel getJPanelPare() {
        return main;
    }
}
