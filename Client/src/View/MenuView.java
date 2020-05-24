package src.View;

import src.Controller.MenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
* Classe del menú principal. Com les demés, exten de JFrame perque volem que sigui una finestra e implementa Runnable per poder-la tractar com a un thread
* */
public class MenuView extends JFrame implements Runnable {

    private MenuController menuController;

    //CONSTANTS PER CONTROLAR EL MENU
    public static String actualView = "";
    public static final String MAIN = "Main_";
    public static final String CONFIGURACIO = "Config_";
    public static final String TROPES = "Tropes_";
    public static final String AMICS = "Friends_";
    public static final String AMICSREQUEST = "FriendsRequest_";
    public static final String CREAPARTIDA = "CrearPartida_";
    public static final String WAITINROOM = "WaitingRoom_";

    //Animacions
    private Thread thread;
    private volatile boolean onTroopsView = false;

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

    //JPanel de FriendsRequest
    private FriendRequestView friendRequestView;

    //Jpanel de partida
    private RoomListView roomListView;

    //JPanel de WaitingRoom
    private WaitingRoomView waitingRoomView;

    //Menu inferior
    private JPanel jpMenu;
    private JPanel jpMenuConfig;
    private JPanel jpMenuTropes;
    private JPanel jpMenuMain;
    private JPanel jpMenuCrearPartida;
    private JPanel jpMenuFriends;
    private boolean shown = false;

    /**
    * Listener encarregat de detectar els clicks del ratolí a la finestra fràfica
    * */
    private MouseListener mouseActionMenu = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            super.mouseClicked(e);
            JPanel item = (JPanel) e.getSource();
            adjustViews(item.getName());
        }
    };

    /**
    * Constructor de la classe
    * */
    public MenuView() {

    }

    /**
    * Encarregat de assignar els controllers del menu
     * @param menuController variable de tipus MenuController responsable del control de la vista
    * */
    public void setMenuController(MenuController menuController) {
        this.menuController = menuController;
        init();
        basic();
    }


    /**
    * Inicialitza la finestra grafica
    * */
    private void init(){
        this.setLayout(new BorderLayout());

        initMenu();
        initConfig();
        initTropes();
        initMain();
        initFriends();
        initFriendsRequest();
        initCrearPartida();
        initWaitingRoom();

        thread = new Thread(this, "Troop Selection Timer");

        //Marquem la primera vista que mostrarem al iniciar
        adjustViews(MenuView.MAIN);
    }

    /**
    * Dedicada a la assignació de atributs de la finestra
    * */
    private void basic() {
        this.setSize(450, 800);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        this.setTitle("Menu");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    /**
    * Ajusta les vistes de la pantalla. En funció de quina es vol mostrar canviarà el jp Actiu
     * @param name string amb el nom de quina de les finestres s'ha de mostrar.
    * */
    private void adjustViews(String name) {
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
            this.setTitle("Menu");
            jpMenu.setVisible(true);

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
            if(menuController.getRequestSize()>0 && !shown){
                shown = true;
                JOptionPane.showMessageDialog(this, "Tens solicituds d'amistat pendents!", "Recorda!", JOptionPane.INFORMATION_MESSAGE);
            }

            jpMenuConfig.setBackground(Color.decode(bgColor));
            jpMenuTropes.setBackground(Color.decode(bgColor));
            jpMenuMain.setBackground(Color.decode(bgColor));
            jpMenuCrearPartida.setBackground(Color.decode(bgColor));
            jpMenuFriends.setBackground(Color.decode(bgColorSelected));
        }
        if (name.equals(MenuView.AMICSREQUEST)){
            if(thread.isAlive()) stopTimer();
            jpActive = friendRequestView.getJpPare();

            jpMenuConfig.setBackground(Color.decode(bgColor));
            jpMenuTropes.setBackground(Color.decode(bgColor));
            jpMenuMain.setBackground(Color.decode(bgColor));
            jpMenuCrearPartida.setBackground(Color.decode(bgColor));
            jpMenuFriends.setBackground(Color.decode(bgColorSelected));
        }
        if (name.equals(MenuView.CREAPARTIDA)){
            this.setTitle("Menu");
            jpMenu.setVisible(true);

            if(thread.isAlive()) stopTimer();
            jpActive = roomListView.getJpPare();

            jpMenuConfig.setBackground(Color.decode(bgColor));
            jpMenuTropes.setBackground(Color.decode(bgColor));
            jpMenuMain.setBackground(Color.decode(bgColor));
            jpMenuCrearPartida.setBackground(Color.decode(bgColorSelected));
            jpMenuFriends.setBackground(Color.decode(bgColor));
        }
        if (name.equals(MenuView.WAITINROOM)){
            if(thread.isAlive()) stopTimer();
            jpActive = waitingRoomView.getJPanelPare();
            this.setTitle("Waiting Room");
            jpMenu.setVisible(false);
        }

        if (jpActive != null) {
            this.add(jpActive, BorderLayout.CENTER);
        }
        this.revalidate();
        this.repaint();
    }

    /**
    * Inicialitza la part inferior del client, el menú
    * */
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

    /**
    * Inicialitza la configuració, tant finestra com controller
    * */
    private void initConfig() {
        configView = new ConfigView(menuController.getConfigController());
        jpConfig = configView.getJpConfig();
    }

    /**
     * Inicialitza la vista de les tropes, tant finestra com controller
     * */
    private void initTropes() {
        tropesView = new TropesView();
        jpTropes = tropesView.getJpTropes();
    }

    /**
     * Inicialitza la vista principal, tant finestra com controller
     * */
    private void initMain() {
        mainView = new MainView(this);
        jpMain = mainView.getJpMain();
    }

    /**
     * Inicialitza amics, tant finestra com controller
     * */
    public void initFriends() {
        friendView = new FriendView(menuController.user, menuController.getFriendsController());
    }

    /**
     * Inicialitza la finestra de solicituds
     * */
    private void initFriendsRequest() {
        friendRequestView = new FriendRequestView(menuController.getFriendsController(), menuController.getFriendsController().getRequests() == null ? new ArrayList<>() : menuController.getFriendsController().getRequests() );
    }

    /**
     * Inicialitza la finestra de creació de partida
     * */
    private void initCrearPartida() {
        roomListView = new RoomListView(menuController.getRoomsController(), menuController.user);
    }

    /**
     * Inicialitza la finestra de la sala d'espera
     * */
    private void initWaitingRoom() {
        waitingRoomView = new WaitingRoomView(null, menuController.user, menuController.getWaitingController());
    }

    /**
    * Funció que crida a un altre funció de la classe.
     * @param view string amb el nom de la vista a actualitzar
    * */
    public void invokeAdjustViews(String view) {
        adjustViews(view);
    }

    /**
    * Retorna la vista principal
     * @return mainView variable de tipus MainView
    * */
    public MainView getMainView() {
        return mainView;
    }

    /**
     * Retorna la vista de les tropes
     * @return tropesView variable de tipus TropesView
     * */
    public TropesView getTropesView() {
        return tropesView;
    }

    /**
     * Retorna la vista de configuració
     * @return configView variable de tipus ConfigView
     * */
    public ConfigView getConfigView() {
        return configView;
    }

    /**
     * Retorna la vista dels amics
     * @return friendView variable de tipus FriendView
     * */
    public FriendView getFriendView() {
        return friendView;
    }

    /**
     * Retorna la vista de partides
     * @return roomListView variable de tipus RoomListView
     * */
    public RoomListView getRoomListView() {
        return roomListView;
    }

    /**
     * Retorna la vista de solicituds d'amistat
     * @return friendRequestView variable de tipus FriendRequestView
     * */
    public FriendRequestView getFriendRequestView() {
        return friendRequestView;
    }

    /**
    * Setter per la vista de solicituds d'amistat
     * @param friendRequestView vista de solicituds.
    * */
    public void setFriendRequestView(FriendRequestView friendRequestView) {
        this.friendRequestView = friendRequestView;
    }

    /**
     * Retorna la vista de la sala d'espera
     * @return waitingRoomView variable de tipus WaitingRoomView
     * */
    public WaitingRoomView getWaitingRoomView() {
        return waitingRoomView;
    }

    /**
    * Actualitza les vistes en funció de la actual
    * */
    public void updateViews() {
        adjustViews(actualView);
    }

    /**
    * Thread de la classe destinat a fer que les tropes a la vista de tropes estigui animades
    * */
    @Override
    public void run() {
        final long[] startTime = {System.currentTimeMillis()};
        while(onTroopsView){
            long elapsedTime = System.currentTimeMillis() - startTime[0];

            if(elapsedTime > 900){
                startTime[0] = System.currentTimeMillis();
            }
            tropesView.updateTropes(elapsedTime);
        }
    }

    /**
    * Inicia el contador de tropes
    * */
    public synchronized void startTimer() {
        onTroopsView = true;
        thread = new Thread(this, "Troop Selection Timer");
        thread.start();
    }

    /**
     * Atura el contador de tropes
     * */
    public synchronized void stopTimer() {
        onTroopsView = false;
        thread.interrupt();
        thread.stop();
    }
}




