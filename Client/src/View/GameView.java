package src.View;

import src.Controller.GameController;
import src.Controller.UpdateTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.TimerTask;

public class GameView extends JFrame implements ActionListener {


    private JPanel[][] panels;
    public static final int ROWS = 20;
    public static final int COLUMNS = 12;
    private static int aux1 = 0;
    private static int aux2 = 0;


    public GameView() {


        panels = new JPanel[ROWS][COLUMNS];
        super.getContentPane().setLayout(new GridLayout(ROWS, COLUMNS));
        JPanel aux;
        for (int i=0; i<ROWS; i++) {
            for (int j=0; j<COLUMNS; j++) {
                aux = new JPanel();
                if(i==0 || i==19 || i == 1 || i==18){
                    if(j==4 || j==5 || j==6 || j==7){
                        aux.setBackground(Color.gray);
                    } else {
                      aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                      aux.setBackground(new Color(0,128,0,100));
                    }

                } else if(i==9 || i == 10){
                    if(j==1 || j==10 || j==2 || j==9){
                        aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                        aux.setBackground(Color.ORANGE);
                    } else {
                        aux.setBackground(Color.BLUE);
                    }
                } else if(j==1 || j==2 || j==9 || j==10){
                    if(i == 4 || i == 5 || i == 14 || i == 15){
                    aux.setBackground(Color.gray);
                  } else if(i == 6 || i==7 || i==8 || i ==13 || i==12 || i == 11){
                        aux.setBackground(new Color(34,139,34,255));
                        aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                    } else {
                   aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                   aux.setBackground(new Color(0,128,0,100));
                  }

                } else {
                    aux.setBackground(new Color(0,128,0,100));
                    aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.RED, Color.RED));
                }

                panels[i][j] = aux;
                super.getContentPane().add(panels[i][j]);
              }
        }
        super.setSize(800, 1000);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}


  public void registerController(GameController controller) {

		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLUMNS; j++) {
				panels[i][j].setName(String.valueOf(i)+"-"+String.valueOf(j));
				panels[i][j].addMouseListener(controller);
			}
		}

		this.addWindowListener(controller);
	}

	public void updateGrid(int i, int j) {

        aux1 = i;
        aux2 = j;

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                /*JLabel jl = new JLabel();
                jl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/knight-removebg-preview.png")));
                panels[aux1][aux2].add(jl);*/

                TestPane tp = new TestPane();
                panels[aux1][aux2].add(tp);
                //GameView.super.getContentPane().add(tp);
                GameView.super.setVisible(true);

                Thread thread = new Thread(new Repainter(tp));
                thread.setDaemon(true);
                thread.start();
            }
        });
    }

    public class Repainter implements Runnable {

        private JPanel panel;

        public Repainter(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                }
                panel.repaint();
            }
        }
    }

    public class TestPane extends JPanel {



        public TestPane() {
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(60, 100);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics g2d = (Graphics2D) g.create();
            aux2++;
            ((Graphics2D) g2d).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.fillOval(aux1, aux2, 30, 30);
            g2d.dispose();
        }


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
