package src.View;

import src.Controller.RoomsController;
import src.Message;
import src.Model.Network.UserService;
import src.Partida;
import src.Usuari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class MenuView extends JFrame implements ActionListener {
    private UserService uService;

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

    //JPanel de Config
    private JPanel jpConfig;
    private JLabel jlContingutConfig = new JLabel("provisional Config");

    //JPanel de Friends
    private JPanel jpFriends;
    private JLabel jlContingutFriends = new JLabel("provisional firends");

    //Jpanel de partida
    private RoomListView roomListView;
    private static ArrayList<Partida> allGames;

    //Menu inferior
    private JPanel jpMenu;
    private JPanel jpMenuConfig;
    private JPanel jpMenuTropes;
    private JPanel jpMenuMain;
    private JPanel jpMenuCrearPartida;
    private JPanel jpMenuFriends;

    private final MouseListener mouseActionMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            JPanel item = (JPanel) e.getSource();
            adjustViews(item.getName());
        }
    };

    private final MouseListener mouseClickBatlla = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            adjustViews("CrearPartida_");
        }
    };

    public MenuView(UserService userService) throws InterruptedException {
		this.uService = userService;
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
    }

    private void basic() {
        this.setSize(450, 800);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        this.setTitle("Menu");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

    private void initConfig() {
        jpConfig = new JPanel();
        jpConfig.add(jlContingutConfig);
    }

    private void initTropes() {
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
        ImageIcon tropa1_foto = new ImageIcon(this.getClass().getResource("/resources/tropes_foto_default.png"));
        Icon iconotropa1foto = new ImageIcon(tropa1_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
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
        ImageIcon tropa2_foto = new ImageIcon(this.getClass().getResource("/resources/tropes_foto_default.png"));
        Icon iconotropa2foto = new ImageIcon(tropa2_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
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
        ImageIcon tropa3_foto = new ImageIcon(this.getClass().getResource("/resources/tropes_foto_default.png"));
        Icon iconotropa3foto = new ImageIcon(tropa3_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
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
        ImageIcon tropa4_foto = new ImageIcon(this.getClass().getResource("/resources/tropes_foto_default.png"));
        Icon iconotropa4foto = new ImageIcon(tropa4_foto.getImage().getScaledInstance(30, 30, Image.SCALE_FAST));
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

    private void initMain() {
        String bgColorMid = "#232745";

        jpMain = new JPanel(null);

        jpMainTopUser = new JButton("");
        jpMainTopUser.setBounds(20, 20, 50, 50);
        jpMainTopUser.setOpaque(false);
        jpMainTopUser.setHorizontalTextPosition(JButton.CENTER);
        jpMainTopUser.setVerticalTextPosition(JButton.CENTER);
        jpMainTopUser.setContentAreaFilled(false);
        jpMainTopUser.setBorderPainted(false);
        ImageIcon fonsButtonUser = new ImageIcon(this.getClass().getResource("/resources/main_logo_user.png"));
        Icon iconoButtonUser = new ImageIcon(fonsButtonUser.getImage().getScaledInstance(50, 50, Image.SCALE_FAST));
        jpMainTopUser.setIcon(iconoButtonUser);
        jpMain.add(jpMainTopUser);

        JButton jpMainTopProgress = new JButton();
        jpMainTopProgress.setBounds(80, 20, 280, 50);
        jpMainTopProgress.setOpaque(false);
        jpMainTopProgress.setContentAreaFilled(false);
        jpMainTopProgress.setBorderPainted(false);
        ImageIcon fonsProgressBar = new ImageIcon(this.getClass().getResource("/resources/main_progress_bg.png"));
        Icon iconoProgressBar = new ImageIcon(fonsProgressBar.getImage().getScaledInstance(290, 50, Image.SCALE_FAST));
        jpMainTopProgress.setIcon(iconoProgressBar);
        jProgressBar = new JProgressBar(SwingConstants.HORIZONTAL);
        jProgressBar.setMinimum(0);
        jProgressBar.setMaximum(100);
        jProgressBar.setValue(40);
        jProgressBar.setStringPainted(true);
        jpMainTopProgress.add(jProgressBar);
        jpMain.add(jpMainTopProgress);

        JButton jpMainTopLvl = new JButton("Lvl. 0");
        jpMainTopLvl.setBounds(360, 20, 70, 50);
        jpMainTopLvl.setOpaque(false);
        jpMainTopLvl.setForeground(Color.white);
        jpMainTopLvl.setHorizontalTextPosition(JButton.CENTER);
        jpMainTopLvl.setVerticalTextPosition(JButton.CENTER);
        jpMainTopLvl.setContentAreaFilled(false);
        jpMainTopLvl.setBorderPainted(false);
        ImageIcon fonsLvl = new ImageIcon(this.getClass().getResource("/resources/main_lvl_bg.png"));
        Icon iconoLvl = new ImageIcon(fonsLvl.getImage().getScaledInstance(50, 50, Image.SCALE_FAST));
        jpMainTopLvl.setIcon(iconoLvl);
        jpMain.add(jpMainTopLvl);

        JPanel jpMainMiddle = new JPanel(new GridBagLayout());
        jpMainMiddle.setBounds(80, 130, 280, 320);
        jpMainMiddle.setBackground(Color.decode(bgColorMid));

        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL;
        //c.insets = new Insets(20,20,0,10);  //top padding
        c.weightx = 0.5;
        c.gridx = 0;
        //c.gridwidth = 2;
        c.gridy = 0;
        jpMainMiddle.add(new JLabel(new ImageIcon("Client/resources/main_victories_icon.png"), SwingConstants.CENTER), c);

        //c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(20,20,0,10);  //top padding
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridy = 0;
        jlVictories = new JLabel();
        jlVictories.setText("X VICTORIES");
        jlVictories.setForeground(Color.WHITE);
        jlVictories.setVerticalAlignment(SwingConstants.CENTER);
        jpMainMiddle.add(jlVictories, c);

        //c.fill = GridBagConstraints.HORIZONTAL;
        //c.insets = new Insets(20,20,0,10);  //top padding
        c.weightx = 0.5;
        c.gridx = 0;
        //c.gridwidth = 2;
        c.gridy = 1;
        jpMainMiddle.add(new JLabel(new ImageIcon("Client/resources/main_clock_icon.png"), SwingConstants.CENTER), c);

        //c.fill = GridBagConstraints.HORIZONTAL;
        //c.insets = new Insets(20,20,0,10);  //top padding
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridy = 1;
        jlTempsXVictoria = new JLabel();
        jlTempsXVictoria.setText("AVG de X min per vicoria");
        jlTempsXVictoria.setForeground(Color.white);
        jlTempsXVictoria.setVerticalAlignment(SwingConstants.CENTER);
        jpMainMiddle.add(jlTempsXVictoria, c);

        //c.fill = GridBagConstraints.HORIZONTAL;
        //c.insets = new Insets(20,20,0,10);  //top padding
        c.weightx = 0.5;
        c.gridx = 0;
        //c.gridwidth = 2;
        c.gridy = 2;
        jpMainMiddle.add(new JLabel(new ImageIcon("Client/resources/main_defaultTropa_icon.png"), SwingConstants.CENTER), c);

        c.fill = GridBagConstraints.HORIZONTAL;
        //c.insets = new Insets(20,20,0,10);  //top padding
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridwidth = 2;
        c.gridy = 2;
        jlTropaMesUtilitzada = new JLabel();
        jlTropaMesUtilitzada.setText("AVG de X min per vicoria");
        jlTropaMesUtilitzada.setForeground(Color.white);
        jlTropaMesUtilitzada.setVerticalAlignment(SwingConstants.CENTER);
        jpMainMiddle.add(jlTropaMesUtilitzada, c);

        jpMain.add(jpMainMiddle);

        JButton jpMainBtnBatalla = new JButton();
        jpMainBtnBatalla.setText("Batalla");
        jpMainBtnBatalla.setBounds(80, 550, 280, 50);
        jpMainBtnBatalla.setOpaque(false);
        jpMainBtnBatalla.setHorizontalTextPosition(JButton.CENTER);
        jpMainBtnBatalla.setFont(new Font("Helvetica", Font.BOLD, 30));
        jpMainBtnBatalla.setForeground(Color.WHITE);
        jpMainBtnBatalla.setContentAreaFilled(false);
        jpMainBtnBatalla.setBorderPainted(false);
        jpMainBtnBatalla.addMouseListener(mouseClickBatlla);
        ImageIcon fonsButton= new ImageIcon(this.getClass().getResource("/resources/main_batallaButton.png"));
        Icon iconoButton = new ImageIcon(fonsButton.getImage().getScaledInstance(280, 50, Image.SCALE_FAST));
        jpMainBtnBatalla.setIcon(iconoButton);
        jpMain.add(jpMainBtnBatalla);

        ImageIcon img = new ImageIcon(this.getClass().getResource("/resources/bg_main.png"));
        Icon icono = new ImageIcon(img.getImage().getScaledInstance(450, 700, Image.SCALE_DEFAULT));
        JLabel fondo = new JLabel();
        fondo.setIcon(icono);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, 450, 700);
        jpMain.add(fondo);

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
            //JOptionPane.showOptionDialog(new JFrame(), "LOKO HI HA QUELCOM MALAMENT" , "Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
        }
    }

    private synchronized void initCrearPartida() {
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
