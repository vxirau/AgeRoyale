package src.View;

import src.Controller.MenuController;
import src.Controller.RoomsController;
import src.Message;
import src.Model.Network.UserService;
import src.Partida;
import src.Usuari;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class MenuView extends JFrame implements ActionListener, Runnable {

    public static String actualView = "";
    public static final String MAIN = "Main_";
    public static final String CONFIGURACIO = "Config_";
    public static final String TROPES = "Tropes_";
    public static final String AMICS = "Friends_";
    public static final String CREAPARTIDA = "CrearPartida_";


    private UserService uService;
    private Usuari usuari;
    private MenuController menuController;

    private static Thread thread;
    private static volatile boolean onTroopsView = false;
    private boolean firstTimeOnThread = true;
    //Panell actual
    private JPanel jpActive;

    //JPanel de Main
    private MainView mainView;
    private JPanel jpMain;

    //JPanel de tropes
    private TropesView tropesView;
    private JPanel jpTropes;

    //JPanel de Config
    private ConfigView configView;
    private JPanel jpConfig;

    //JPanel de Friends
    private FriendView friendView;
    private JPanel jpFriends;

    //Jpanel de partida
    private RoomListView roomListView;
    private JPanel jpCrearPartida;

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
            try {
                adjustViews(item.getName());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    };

    public MenuView() {

    }

    public void setMenuController(MenuController menuController) throws InterruptedException {
        this.menuController = menuController;
        init();
        basic();
    }

    private void init() throws InterruptedException {
        this.setLayout(new BorderLayout());

        //iniciem el menu
        initMenu();

        //Iniciem els 5 panels pricipals
        initConfig();
        initTropes();
        initMain();
        initFriends();
        initCrearPartida();
        thread = new Thread(this, "Troop Selection Timer");
        //Marquem la primera vista que mostrarem al iniciar
        adjustViews(MenuView.MAIN);
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

    private void adjustViews(String name) throws InterruptedException {
        actualView = name;
        String bgColor = "#282828";
        String bgColorSelected = "#361111";

        if (jpActive != null) {
            this.remove(jpActive);
        }

        if (name.equals(MenuView.CONFIGURACIO)){
            if(thread.isAlive()) stopTimer();

            jpActive = jpConfig;

            jpMenuConfig.setBackground(Color.decode(bgColorSelected));
            jpMenuTropes.setBackground(Color.decode(bgColor));
            jpMenuMain.setBackground(Color.decode(bgColor));
            jpMenuCrearPartida.setBackground(Color.decode(bgColor));
            jpMenuFriends.setBackground(Color.decode(bgColor));
        }
        if (name.equals(MenuView.TROPES)){
            startTimer();
            jpActive = jpTropes;

            jpMenuConfig.setBackground(Color.decode(bgColor));
            jpMenuTropes.setBackground(Color.decode(bgColorSelected));
            jpMenuMain.setBackground(Color.decode(bgColor));
            jpMenuCrearPartida.setBackground(Color.decode(bgColor));
            jpMenuFriends.setBackground(Color.decode(bgColor));
        }
        if (name.equals(MenuView.MAIN)){
            if(thread.isAlive()) stopTimer();
            jpActive = jpMain;

            jpMenuConfig.setBackground(Color.decode(bgColor));
            jpMenuTropes.setBackground(Color.decode(bgColor));
            jpMenuMain.setBackground(Color.decode(bgColorSelected));
            jpMenuCrearPartida.setBackground(Color.decode(bgColor));
            jpMenuFriends.setBackground(Color.decode(bgColor));
        }
        if (name.equals(MenuView.AMICS)){
            if(thread.isAlive()) stopTimer();
            jpActive = friendView.getJpFriends();

            jpMenuConfig.setBackground(Color.decode(bgColor));
            jpMenuTropes.setBackground(Color.decode(bgColor));
            jpMenuMain.setBackground(Color.decode(bgColor));
            jpMenuCrearPartida.setBackground(Color.decode(bgColor));
            jpMenuFriends.setBackground(Color.decode(bgColorSelected));
        }
        if (name.equals(MenuView.CREAPARTIDA)){
            if(thread.isAlive()) stopTimer();
            jpActive = roomListView.getJpPare();

            jpMenuConfig.setBackground(Color.decode(bgColor));
            jpMenuTropes.setBackground(Color.decode(bgColor));
            jpMenuMain.setBackground(Color.decode(bgColor));
            jpMenuCrearPartida.setBackground(Color.decode(bgColorSelected));
            jpMenuFriends.setBackground(Color.decode(bgColor));
        }

        if (jpActive != null) {
            this.add(jpActive, BorderLayout.CENTER);
        }
        this.revalidate();
        this.repaint();
    }

    private synchronized void initMenu() {
        int width = 81;
        int height = 90;
        String bgColor = "#282828";
        String borderColor = "#D8D8D8";

        jpMenuConfig = new JPanel(new BorderLayout());
        jpMenuConfig.setName(MenuView.CONFIGURACIO);
        jpMenuConfig.addMouseListener(mouseActionMenu);
        jpMenuConfig.setBackground(Color.decode(bgColor));
        jpMenuConfig.setPreferredSize(new Dimension(width, height));
        jpMenuConfig.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode(borderColor)));
        jpMenuConfig.add(new JLabel(new ImageIcon("Client/resources/menu_engranatge_item.png")));

        jpMenuTropes = new JPanel(new BorderLayout());
        jpMenuTropes.setName(MenuView.TROPES);
        jpMenuTropes.addMouseListener(mouseActionMenu);
        jpMenuTropes.setBackground(Color.decode(bgColor));
        jpMenuTropes.setPreferredSize(new Dimension(width, height));
        jpMenuTropes.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode(borderColor)));
        jpMenuTropes.add(new JLabel(new ImageIcon("Client/resources/menu_tropes_item.png")));

        jpMenuMain = new JPanel(new BorderLayout());
        jpMenuMain.setName(MenuView.MAIN);
        jpMenuMain.addMouseListener(mouseActionMenu);
        jpMenuMain.setBackground(Color.decode(bgColor));
        jpMenuMain.setPreferredSize(new Dimension(width, height));
        jpMenuMain.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode(borderColor)));
        jpMenuMain.add(new JLabel(new ImageIcon("Client/resources/menu_main_item.png")));

        jpMenuFriends = new JPanel(new BorderLayout());
        jpMenuFriends.setName(MenuView.AMICS);
        jpMenuFriends.addMouseListener(mouseActionMenu);
        jpMenuFriends.setBackground(Color.decode(bgColor));
        jpMenuFriends.setPreferredSize(new Dimension(width, height));
        jpMenuFriends.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.decode(borderColor)));
        jpMenuFriends.add(new JLabel(new ImageIcon("Client/resources/menu_friends_item.png")));

        jpMenuCrearPartida = new JPanel(new BorderLayout());
        jpMenuCrearPartida.setName(MenuView.CREAPARTIDA);
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
        configView = new ConfigView(menuController.getConfigController());
        jpConfig = configView.getJpConfig();
    }

    private void initTropes() {
        tropesView = new TropesView();
        jpTropes = tropesView.getJpTropes();
    }




    private void initMain() {
        mainView = new MainView(this);
        jpMain = mainView.getJpMain();
    }

    public void initFriends() {
        friendView = new FriendView(usuari, menuController.getFriendsController());

        Usuari main = new Usuari();
        main.setNickName("hola");
        main.setAmics(new ArrayList<Usuari>());
        setUsuari(main);
        Usuari u = new Usuari();
        u.setNickName("Uno");
        Usuari d = new Usuari();
        d.setNickName("Dos");
        ArrayList<Usuari> amics = new ArrayList<>();
        amics.add(d);
        amics.add(u);

        //friendView.setAmics(amics);
        jpFriends = friendView.getJpFriends();
    }

    private void initCrearPartida() {
        roomListView = new RoomListView(menuController.getRoomsController());
        jpCrearPartida = roomListView.getJpPare();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void setUsuari(Usuari usuari){
        this.usuari = usuari;
    }

    public void invokeAdjustViews(String view) throws InterruptedException {
        adjustViews(view);
    }

    public void setuService(UserService uService) {
        this.uService = uService;
    }

    public MainView getMainView() {
        return mainView;
    }

    public TropesView getTropesView() {
        return tropesView;
    }

    public ConfigView getConfigView() {
        return configView;
    }

    public FriendView getFriendView() {
        return friendView;
    }

    public RoomListView getRoomListView() {
        return roomListView;
    }

    public void updateViews() throws InterruptedException {
        adjustViews(actualView);
    }




    @Override
    public void run() {
        final long[] startTime = {System.currentTimeMillis()};
        while(onTroopsView){
            long elapsedTime = System.currentTimeMillis() - startTime[0];

            if(elapsedTime > 900){
                startTime[0] = System.currentTimeMillis();
                //elapsedSeconds = 0;
            }

            tropesView.updateTropes(elapsedTime);

            System.out.println(elapsedTime);
        }


    }

    public synchronized void startTimer() throws InterruptedException {
        onTroopsView = true;
        thread = new Thread(this, "Troop Selection Timer");
        thread.start();

    }
    public synchronized void stopTimer() throws InterruptedException {
        onTroopsView = false;
        thread.interrupt();
        thread.stop();
        //thread.join();

    }
}




