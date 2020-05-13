package src.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainView extends JFrame{
    private MenuView menuView;
    private JPanel jpMain;

    private JButton jbMainTopUser;
    private JProgressBar jProgressBar;
    private JButton jbMainTopLvl;
    private JLabel jlVictories;
    private JLabel jlTempsXVictoria;
    private JLabel jlTropaMesUtilitzada;
    private MouseListener mouseClickBatlla;

    public MainView(MenuView menuView) {
        this.menuView = menuView;

        jbMainTopUser = new JButton();
        jProgressBar = new JProgressBar();
        jbMainTopLvl = new JButton();
        jlVictories = new JLabel();
        jlTempsXVictoria = new JLabel();
        jlTropaMesUtilitzada = new JLabel();

        String bgColorMid = "#232745";

        jpMain = new JPanel(null);

        jbMainTopUser.setText("");
        jbMainTopUser.setBounds(20, 20, 50, 50);
        jbMainTopUser.setOpaque(false);
        jbMainTopUser.setHorizontalTextPosition(JButton.CENTER);
        jbMainTopUser.setVerticalTextPosition(JButton.CENTER);
        jbMainTopUser.setContentAreaFilled(false);
        jbMainTopUser.setBorderPainted(false);
        jbMainTopUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    menuView.invokeAdjustViews(MenuView.CONFIGURACIO);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
        ImageIcon fonsButtonUser = new ImageIcon(this.getClass().getResource("/resources/main_logo_user.png"));
        Icon iconoButtonUser = new ImageIcon(fonsButtonUser.getImage().getScaledInstance(50, 50, Image.SCALE_FAST));
        jbMainTopUser.setIcon(iconoButtonUser);
        jpMain.add(jbMainTopUser);

        JButton jpMainTopProgress = new JButton();
        jpMainTopProgress.setBounds(80, 20, 280, 50);
        jpMainTopProgress.setOpaque(false);
        jpMainTopProgress.setContentAreaFilled(false);
        jpMainTopProgress.setBorderPainted(false);
        ImageIcon fonsProgressBar = new ImageIcon(this.getClass().getResource("/resources/main_progress_bg.png"));
        Icon iconoProgressBar = new ImageIcon(fonsProgressBar.getImage().getScaledInstance(290, 50, Image.SCALE_FAST));
        jpMainTopProgress.setIcon(iconoProgressBar);
        jProgressBar.setOrientation(SwingConstants.HORIZONTAL);
        jProgressBar.setMinimum(0);
        jProgressBar.setMaximum(100);
        jProgressBar.setValue(0);
        jProgressBar.setStringPainted(true);
        jpMainTopProgress.add(jProgressBar);
        jpMain.add(jpMainTopProgress);

        jbMainTopLvl.setText("Lvl. X");
        jbMainTopLvl.setBounds(360, 20, 70, 50);
        jbMainTopLvl.setOpaque(false);
        jbMainTopLvl.setForeground(Color.white);
        jbMainTopLvl.setHorizontalTextPosition(JButton.CENTER);
        jbMainTopLvl.setVerticalTextPosition(JButton.CENTER);
        jbMainTopLvl.setContentAreaFilled(false);
        jbMainTopLvl.setBorderPainted(false);
        ImageIcon fonsLvl = new ImageIcon(this.getClass().getResource("/resources/main_lvl_bg.png"));
        Icon iconoLvl = new ImageIcon(fonsLvl.getImage().getScaledInstance(50, 50, Image.SCALE_FAST));
        jbMainTopLvl.setIcon(iconoLvl);
        jpMain.add(jbMainTopLvl);

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
        jpMainBtnBatalla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    menuView.invokeAdjustViews(MenuView.CREAPARTIDA);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        });
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

    public MenuView getMenuView() {
        return menuView;
    }

    public void setMenuView(MenuView menuView) {
        this.menuView = menuView;
    }

    public JPanel getJpMain() {
        return jpMain;
    }

    public void setJpMain(JPanel jpMain) {
        this.jpMain = jpMain;
    }

    public JButton getJpMainTopUser() {
        return jbMainTopUser;
    }

    public void setJpMainTopUser(JButton jbMainTopUser) {
        this.jbMainTopUser = jbMainTopUser;
    }

    public JProgressBar getjProgressBar() {
        return jProgressBar;
    }

    public void setjProgressBar(JProgressBar jProgressBar) {
        this.jProgressBar = jProgressBar;
    }

    public JButton getJbMainTopLvl() {
        return jbMainTopLvl;
    }

    public void setJbMainTopLvl(JButton jbMainTopLvl) {
        this.jbMainTopLvl = jbMainTopLvl;
    }

    public JLabel getJlVictories() {
        return jlVictories;
    }

    public void setJlVictories(JLabel jlVictories) {
        this.jlVictories = jlVictories;
    }

    public JLabel getJlTempsXVictoria() {
        return jlTempsXVictoria;
    }

    public void setJlTempsXVictoria(JLabel jlTempsXVictoria) {
        this.jlTempsXVictoria = jlTempsXVictoria;
    }

    public JLabel getJlTropaMesUtilitzada() {
        return jlTropaMesUtilitzada;
    }

    public void setJlTropaMesUtilitzada(JLabel jlTropaMesUtilitzada) {
        this.jlTropaMesUtilitzada = jlTropaMesUtilitzada;
    }

    public MouseListener getMouseClickBatlla() {
        return mouseClickBatlla;
    }

    public void setMouseClickBatlla(MouseListener mouseClickBatlla) {
        this.mouseClickBatlla = mouseClickBatlla;
    }
}
