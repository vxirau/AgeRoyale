package src.View;

import src.Controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class GameView extends JFrame implements ActionListener, Runnable {


    private JPanel[][] panels;
    private JPanel player;
    private static int x = 0;
    private static int y = 0;
    private static Thread thread;
    private final int[] pixels;
    private final int width;
    private final int height;
    public static final int ROWS = 20;
    public static final int COLUMNS = 12;
    private static int aux1 = 0;
    private static int aux2 = 0;
    private static volatile boolean gameIsRunning = false;

    private static BufferedImage image;
    //Variable per accedir a la imatge a partir dels seus pixels
    private static int[] pixelsImage;
    //Temporal
    private Sprite herba;
    private final static int SPRITE_SIDE = 32;
    private final static int SPRITE_MASK = SPRITE_SIDE - 1;

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    public GameView() {
        this.width = 400;
        this.height = 800;
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.pixelsImage = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        pixels = new int[width * height];
        /*panels = new JPanel[ROWS][COLUMNS];
        super.getContentPane().setLayout(new GridLayout(ROWS, COLUMNS));
        JPanel aux;
        for (int i=0; i<ROWS; i++) {
            for (int j=0; j<COLUMNS; j++) {
                aux = new JPanel();
                //Creacio dels castells dels reis
                if(i==0 || i==19 || i == 1 || i == 2 || i==18 || i == 17){

                    if(j==4 || j==5 || j==6 || j==7){
                        aux.setBackground(new Color(255,128,0,255));
                    } else {
                      aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.MAGENTA, Color.MAGENTA));
                      aux.setBackground(new Color(76,40,130,255));
                    }
                //Creacio del riu i ponts
                } else if(i==9 || i == 10){
                    //Ponts
                    if(j==1 || j==10 || j==2 || j==9){
                        //aux.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED, Color.MAGENTA, Color.MAGENTA));
                        aux.setBackground(Color.ORANGE);
                        //Riu
                    } else {
                        aux.setBackground(Color.BLUE);
                    }

                } else if(j==1 || j==2 || j==9 || j==10){
                    //Creacio de les torres
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
        }*/
        //System.out.println("hola");


        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        //this.getContentPane().setLayout(new GridLayout(ROWS,COLUMNS));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(400,800);

	}

    public void clearScreen(){
        //Donem el valor de negre a tots els pixels
        for(int i = 0; i < pixels.length; i++){
            pixels[i] = 0;
        }

    }

    //temporal. dibuixa els fgrafuic
    public void drawGraphics(final int compensX, final int compensY){
        for(int i = 0; i < height; i++){
            int positionY = i;// + compensY;
            //Vigilem que no ens sortim del mapa verticalment
            if(positionY < 0 || positionY >= height){
                continue;
            }
            for (int j = 0; j < width; j++){
                int positionX = j;// + compensX;
                //Vigilem que no ens sortim del mapa horitzontalment
                if(positionX < 0 || positionX >= width){
                    continue;
                }

                //Temporal
                pixels[positionX + positionY * width] = Sprite.GRASS.pixels[(j & SPRITE_MASK) + (i & SPRITE_MASK) * SPRITE_SIDE];

            }
        }
    }

    public void showTile(int compensX, int compensY, Tile tile){


        for(int i = 0; i < tile.getSprite().getSide(); i++){
            int yPosition = i + compensY;

            for(int j = 0; j < tile.getSprite().getSide(); j++){
                int xPosition = j + compensX;
                pixels[xPosition + yPosition * width] = tile.getSprite().pixels[i + j * tile.getSprite().getSide()];
            }
        }






    }

    public void registerController(GameController controller) {

		/*for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLUMNS; j++) {
				panels[i][j].setName(String.valueOf(i)+"-"+String.valueOf(j));
				panels[i][j].addMouseListener(controller);
			}
		}

		this.addWindowListener(controller);*/


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

    public synchronized void startGame(){
        gameIsRunning = true;
        thread = new Thread(this, "GameGraphics");
        thread.start();

    }

    public synchronized void stopGame() throws InterruptedException {
        gameIsRunning = false;
        thread.join();
    }

    public void updateGame(){

    }

    public void showGraphics(){
        //Creem un buffer per tal de dibuixar els grafics en segon pla
        BufferStrategy bufferStrategy = getBufferStrategy();
        if(bufferStrategy == null){
            createBufferStrategy(3);
            return;
        }
        clearScreen();
        drawGraphics(x, y);

        //Copiem els grafics al joc
        System.arraycopy(pixels, 0, pixelsImage, 0, pixelsImage.length);

        //L'objecte g s'encarregara de dibuixar els grafics a la pantalla
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();

        //Mostrem el que tenim
        bufferStrategy.show();
        /*for(int i = 0; i < pixels.length; i++){
            pixelsImage[i] = pixels[i];
        }*/

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void run() {

        final int NS_PER_SECOND = 1000000000;
        final byte FPS = 60;
        final double NS_PER_FRAME = NS_PER_SECOND/FPS;

        long updateReference = System.nanoTime();
        double elapsedTime;
        double delta = 0;


        requestFocus();

        while(gameIsRunning){
            final long loopStart = System.nanoTime();

            elapsedTime = loopStart - updateReference;
            updateReference = loopStart;

            delta += elapsedTime/NS_PER_FRAME;

            while(delta >= 1){
                updateGame();
                delta--;
            }


            showGraphics();




        }
    }
}
