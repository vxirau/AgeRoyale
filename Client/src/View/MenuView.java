package src.View;

import src.Controller.RoomsController;
import src.Message;
import src.Model.Network.UserService;
import src.Partida;
import src.Usuari;
import src.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MenuView extends JFrame implements ActionListener {
    //Panell actual
    private JPanel jpActive;

    //JPanel de Main
    private JPanel jpMain;
    private JButton jpMainTopUser;
    private JProgressBar jProgressBar;
    private JLabel jlProgressBarPercent;
    private JLabel jlLvl;
    private JLabel jlVictories;
    private JLabel jlTempsXVictoria;
    private JLabel jlTropaMesUtilitzada;

    //JPanel de tropes
    private JPanel jpTropes;
    private JLabel jlContingutTropes = new JLabel("provisional tropes");

    //JPanel de Config
    private JPanel jpConfig;
    private JLabel jlContingutConfig = new JLabel("provisional Config");

    //JPanel de Friends
    private JPanel jpFriends;
    private JLabel jlContingutFriends = new JLabel("provisional firends");

    //Jpanel de partida
    private RoomListView roomListView;

    //Menu inferior
    private JPanel jpMenu;
    private JPanel jpMenuConfig;
    private JPanel jpMenuTropes;
    private JPanel jpMenuMain;
    private JPanel jpMenuCrearPartida;
    private JPanel jpMenuFriends;

    private UserService uService;
    private RoomListView roomListView;
    private static ArrayList<Partida> allGames;

    private final MouseListener mouseActionMenu = new MouseAdapter() {
        /*
        @Override
        public void mouseEntered(MouseEvent e) {

            JPanel item = (JPanel) e.getSource();
            if (item.getName().substring(item.getName().length() - 1).equals("_")) {
                item.setBackground(Color.decode("#eee"));
            }
        };

        @Override
        public void mouseExited(MouseEvent e) {
            JPanel item = (JPanel) e.getSource();
            adjustViews();
        };

             */
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            JPanel item = (JPanel) e.getSource();
            try {
                adjustViews(item.getName());
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }
    };

    public MenuView(UserService us) throws InterruptedException {
			this.uService = us;
        init();
        basic();
    }

    private void init() throws InterruptedException {
        this.setLayout(new BorderLayout());
        //jpPare.setOpaque(true);

        //iniciem el menu
        initMenu();

        //Iniciem els 5 panels pricipals
        initConfig();
        initTropes();
        initMain();
        initFriends();
        initCrearPartida();
        //Marquem la primera vista que mostrarem al iniciar
        adjustViews("Main_");


        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);

    }

    private void basic() {
        this.setSize(450, 800);
        this.setTitle("Menu");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void initMenu() {
        int width = 81;
        int height = 90;
        String bgColor = "#282828";
        String borderColor = "#D8D8D8";

        jpMenuConfig = new JPanel(new BorderLayout());
        jpMenuConfig.setName("Config_");
        jpMenuConfig.addMouseListener(mouseActionMenu);
        jpMenuConfig.setBackground(Color.decode(bgColor));
        jpMenuConfig.setPreferredSize(new Dimension(width, height));
        jpMenuConfig.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode(borderColor)));
        jpMenuConfig.add(new JLabel(new ImageIcon("Client/resources/menu_engranatge_item.png")));

        jpMenuTropes = new JPanel(new BorderLayout());
        jpMenuTropes.setName("Tropes_");
        jpMenuTropes.addMouseListener(mouseActionMenu);
        jpMenuTropes.setBackground(Color.decode(bgColor));
        jpMenuTropes.setPreferredSize(new Dimension(width, height));
        jpMenuTropes.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode(borderColor)));
        jpMenuTropes.add(new JLabel(new ImageIcon("Client/resources/menu_tropes_item.png")));

        jpMenuMain = new JPanel(new BorderLayout());
        jpMenuMain.setName("Main_");
        jpMenuMain.addMouseListener(mouseActionMenu);
        jpMenuMain.setBackground(Color.decode(bgColor));
        jpMenuMain.setPreferredSize(new Dimension(width, height));
        jpMenuMain.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode(borderColor)));
        jpMenuMain.add(new JLabel(new ImageIcon("Client/resources/menu_main_item.png")));

        jpMenuFriends = new JPanel(new BorderLayout());
        jpMenuFriends.setName("Friends_");
        jpMenuFriends.addMouseListener(mouseActionMenu);
        jpMenuFriends.setBackground(Color.decode(bgColor));
        jpMenuFriends.setPreferredSize(new Dimension(width, height));
        jpMenuFriends.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode(borderColor)));
        jpMenuFriends.add(new JLabel(new ImageIcon("Client/resources/menu_friends_item.png")));

        jpMenuCrearPartida = new JPanel(new BorderLayout());
        jpMenuCrearPartida.setName("CrearPartida_");
        jpMenuCrearPartida.addMouseListener(mouseActionMenu);
        jpMenuCrearPartida.setBackground(Color.decode(bgColor));
        jpMenuCrearPartida.setPreferredSize(new Dimension(width, height));
        jpMenuCrearPartida.add(new JLabel(new ImageIcon("Client/resources/menu_crearPartida_item.png")));

        jpMenu = new JPanel(new GridLayout(1, 5));
        jpMenu.setPreferredSize(new Dimension(450, height));
        jpMenu.setBackground(Color.decode(bgColor));

        jpMenu.add(jpMenuConfig);
        jpMenu.add(jpMenuTropes);
        jpMenu.add(jpMenuMain);
        jpMenu.add(jpMenuFriends);
        jpMenu.add(jpMenuCrearPartida);

        this.add(jpMenu, BorderLayout.SOUTH);
    }


    private void adjustViews(String name) {
        String bgColor = "#282828";
        String bgColorSelected = "#361111";

        if (jpActive != null) {
            this.remove(jpActive);
        }

        if (name.equals("Config_")){
            jpActive = jpConfig;

            jpMenuConfig.setBackground(Color.decode(bgColorSelected));
            jpMenuTropes.setBackground(Color.decode(bgColor));
            jpMenuMain.setBackground(Color.decode(bgColor));
            jpMenuCrearPartida.setBackground(Color.decode(bgColor));
            jpMenuFriends.setBackground(Color.decode(bgColor));
        }
        if (name.equals("Tropes_")){
            jpActive = jpTropes;

            jpMenuConfig.setBackground(Color.decode(bgColor));
            jpMenuTropes.setBackground(Color.decode(bgColorSelected));
            jpMenuMain.setBackground(Color.decode(bgColor));
            jpMenuCrearPartida.setBackground(Color.decode(bgColor));
            jpMenuFriends.setBackground(Color.decode(bgColor));
        }
        if (name.equals("Main_")){
            jpActive = jpMain;

            jpMenuConfig.setBackground(Color.decode(bgColor));
            jpMenuTropes.setBackground(Color.decode(bgColor));
            jpMenuMain.setBackground(Color.decode(bgColorSelected));
            jpMenuCrearPartida.setBackground(Color.decode(bgColor));
            jpMenuFriends.setBackground(Color.decode(bgColor));
        }
        if (name.equals("Friends_")){
            jpActive = jpFriends;

            jpMenuConfig.setBackground(Color.decode(bgColor));
            jpMenuTropes.setBackground(Color.decode(bgColor));
            jpMenuMain.setBackground(Color.decode(bgColor));
            jpMenuCrearPartida.setBackground(Color.decode(bgColor));
            jpMenuFriends.setBackground(Color.decode(bgColorSelected));
        }
        if (name.equals("CrearPartida_")){
            if(roomListView==null){
                initCrearPartida();
                adjustViews("CrearPartida_");
            }
            jpActive = roomListView.getJpPare();
            jpActive.setVisible(true);
            jpMenuConfig.setBackground(Color.decode(bgColor));
            jpMenuTropes.setBackground(Color.decode(bgColor));
            jpMenuMain.setBackground(Color.decode(bgColor));
            jpMenuCrearPartida.setBackground(Color.decode(bgColorSelected));
            jpMenuFriends.setBackground(Color.decode(bgColor));
        }

        this.add(jpActive, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    private void initConfig() {
        jpConfig = new JPanel();
        jpConfig.add(jlContingutConfig);
    }

    private void initTropes() {
        jpTropes = new JPanel();
        jpTropes.add(jlContingutTropes);
    }

    private void initMain() {
        String bgColorTop = "#85201F";
        String bgColorMid = "#232745";

        jpMain = new JPanel(null);

        jpMainTopUser = new JButton("");
        jpMainTopUser.setBounds(20, 20, 50, 50);
        jpMainTopUser.setOpaque(false);
        jpMainTopUser.setHorizontalTextPosition(JButton.CENTER);
        jpMainTopUser.setVerticalTextPosition(JButton.CENTER);
        //jpMainTopUser.setBackground(Color.decode(bgColorTop));
        //jpMainTopUser.add(new JLabel(new ImageIcon("Client/resources/main_logo_user.png"), SwingConstants.CENTER));
        jpMainTopUser.setContentAreaFilled(false);
        jpMainTopUser.setBorderPainted(false);
        ImageIcon fonsButton= new ImageIcon(this.getClass().getResource("/resources/main_logo_user.png"));
        Icon iconoButton = new ImageIcon(fonsButton.getImage().getScaledInstance(50, 50, Image.SCALE_FAST));
        jpMainTopUser.setIcon(iconoButton);
        jpMain.add(jpMainTopUser);

        JPanel jpMainTopProgress = new JPanel(new GridLayout(2, 1));
        jpMainTopProgress.setBounds(80, 20, 290, 50);
        jpMainTopProgress.setBackground(Color.decode(bgColorTop));
        jpMainTopProgress.setBorder(BorderFactory.createLineBorder(Color.decode(bgColorTop), 11,true));

        jProgressBar = new JProgressBar(SwingConstants.HORIZONTAL);
        jProgressBar.setBounds(100,30,250, 20);
        jProgressBar.setMinimum(0);
        jProgressBar.setMaximum(100);
        jProgressBar.setValue(40);
        jpMainTopProgress.add(jProgressBar);

        jlProgressBarPercent = new JLabel("40%", SwingConstants.CENTER);
        jlProgressBarPercent.setForeground(Color.white);
        jlProgressBarPercent.setBounds(130, 60, 20, 20);
        jpMainTopProgress.add(jlProgressBarPercent);
        jpMain.add(jpMainTopProgress);


        JPanel jpMainTopLvl = new JPanel(new BorderLayout());
        jpMainTopLvl.setBounds(380, 20, 50, 50);
        jpMainTopLvl.setBackground(Color.decode(bgColorTop));
        jlLvl = new JLabel("Lvl. 0");
        jlLvl.setForeground(Color.white);
        jpMainTopLvl.add(jlLvl);

        jpMain.add(jpMainTopLvl);


    }

    private void initFriends() {
        jpFriends = new JPanel();
        jpFriends.add(jlContingutFriends);
    }

    public static void setAllGames(ArrayList<Partida> partides){
        if(partides!=null){
            allGames = partides;
            System.out.println("setAllGames");
        }else{
            JOptionPane.showOptionDialog(new JFrame(), "LOKO HI HA QUELCOM MALAMENT" , "Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
        }
    }

    private synchronized void initCrearPartida() throws InterruptedException {
        Message m = new Message(null, "getAllGames");
        uService.sendGetPartides(m);
        //FALTA DETECTAR USUARI LOGUEJAT A LA MENU VIEW
        if(allGames!=null){
        //allGames = new ArrayList<>();
            RoomsController controller = new RoomsController(this, new Usuari(), uService);
            roomListView = new RoomListView(allGames);
            roomListView.RegisterController(controller);
        }



    }

    public void registerController(ActionListener controlador) {
        //Button.addActionListener(controlador);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
