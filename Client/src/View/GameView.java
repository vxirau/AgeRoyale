package src.View;

import src.Controller.GameController;
import src.Controller.UpdateTask;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.TimerTask;

public class GameView extends JFrame implements ActionListener {


    private JPanel[][] panels;
    private JPanel player;
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
                if(i==0 || i==19 || i == 1 || i == 2 || i==18 || i == 17){
                    if(j==4 || j==5 || j==6 || j==7){
                        aux.setBackground(new Color(255,128,0,255));
                    } else {
                      aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.MAGENTA, Color.MAGENTA));
                      aux.setBackground(new Color(76,40,130,255));
                    }

                } else if(i==9 || i == 10){
                    if(j==1 || j==10 || j==2 || j==9){
                        //aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.MAGENTA, Color.MAGENTA));
                        aux.setBackground(Color.ORANGE);
                    } else {
                        aux.setBackground(Color.BLUE);
                    }
                } else if(j==1 || j==2 || j==9 || j==10){
                    if(i == 4 || i == 5 || i == 14 || i == 15){
                    aux.setBackground(new Color(255,128,0,255));
                  } else if(i == 6 || i==7 || i==8 || i ==13 || i==12 || i == 11){
                        aux.setBackground(new Color(76,40,130,170));
                        aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.MAGENTA, Color.MAGENTA));
                    } else {
                   aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.MAGENTA, Color.MAGENTA));
                   aux.setBackground(new Color(76,40,130,255));
                  }
                } else {
                    aux.setBackground(new Color(76,40,130,255));
                    aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.MAGENTA, Color.MAGENTA));
                }

                panels[i][j] = aux;
                super.getContentPane().add(panels[i][j]);
              }
        }
        super.setSize(800, 1000);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        /*this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.getContentPane().setLayout(new GridLayout(ROWS,COLUMNS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,800);*/

	}




  public void registerController(GameController controller) {

		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLUMNS; j++) {
				panels[i][j].setName(String.valueOf(i)+"-"+String.valueOf(j));
				panels[i][j].addMouseListener(controller);
			}
		}

		this.addWindowListener(controller);


		//this.addMouseListener(controller);

	}

	public void updateGrid(int i, int j) {


      aux1 = i;
      aux2 = j;
      JLabel jl = new JLabel();
      jl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/knight-removebg-preview.png")));
      panels[aux1][aux2].add(jl);
      revalidate();
      repaint();


        /*JPanel panel = (JPanel)this.getContentPane();

        JLabel label = new JLabel();
        label.setSize(400,800);
        label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/knight-removebg-preview.png")));
        label.setLocation(i,j);

        panel.add(label);
        panel.setLocation(i,j);

        //this.pack();
        this.setVisible(true);

        player = new JPanel();
        JLabel jl = new JLabel();
        jl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/knight-removebg-preview.png")));


        player.add(jl);
        player.setLocation(i, j);
        player.setVisible(true);*/

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
