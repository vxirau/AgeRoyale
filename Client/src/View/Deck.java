package src.View;

import org.w3c.dom.css.Rect;
import src.Tropa;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class Deck implements Serializable {

    private Rectangle deckRectangle;
    private Rectangle clockRectangle;
    private Rectangle deckRectangleBorder;
    private Rectangle clockDelimiter;
    private Rectangle goldRectangle;
    private boolean reset;
    private int deckHeight;
    private int clockHeight;
    private int goldDecrease;
    private String clock;
    private int clockTime;
    private int width;
    private GameView gameView;
    private static final String SKELETON_DECK_PATH  = "/resources/skeleton_deck.png";
    private static final String GOBLIN_DECK_PATH  = "/resources/goblin_deck.png";
    private static final String WIZARD_DECK_PATH  = "/resources/wizard_deck.png";
    private static final String BOMB_DECK_PATH  = "/resources/bomb_deck.png";
    private Date createdDate ;
    private boolean characterIsSelected;
    private int whichTroop;
    BufferedImage[] bufferedImage;

    private final Color  DECK_RECTANGLE_COLOR;


    public Deck(int screenWidth, int screenHeight) throws IOException {

        this.gameView = gameView;
        clock = "00 : 00";
        deckHeight = 96;
        clockHeight = 32;
        goldDecrease = 40;
        deckRectangle = new Rectangle(0, screenHeight - deckHeight + 5, screenWidth, deckHeight);
        deckRectangleBorder = new Rectangle(deckRectangle.x, deckRectangle.y , deckRectangle.width, 3 );
        clockRectangle = new Rectangle(deckRectangle.x, deckRectangle.y + 30, screenWidth, clockHeight);
        goldRectangle = new Rectangle(deckRectangle.x + 20, deckRectangle.y + 55 , screenWidth - goldDecrease, 8);
        clockDelimiter = new Rectangle(deckRectangle.x, clockRectangle.y + 65 , deckRectangle.width, 1 );
        whichTroop = 10;
        DECK_RECTANGLE_COLOR = new Color(23,23,23);
        bufferedImage = new BufferedImage[4];
        bufferedImage[0] = ImageIO.read(Deck.class.getResource(SKELETON_DECK_PATH));
        bufferedImage[1] = ImageIO.read(Deck.class.getResource(GOBLIN_DECK_PATH));
        bufferedImage[2] = ImageIO.read(Deck.class.getResource(WIZARD_DECK_PATH));
        bufferedImage[3] = ImageIO.read(Deck.class.getResource(BOMB_DECK_PATH));
        characterIsSelected = false;
        createdDate = new java.util.Date();
        clockTime = 0;

    }

    public void showDeck(final Graphics g, int xMousePosition, int yMousePosition, boolean mouseIsClicked, int whichTroop){
        drawDeck(g, xMousePosition, yMousePosition, mouseIsClicked, whichTroop);
    }

    public void selectTroop(int whichTroop){
        this.whichTroop = whichTroop;
    }

    private void drawDeck(final Graphics g, int xMousePosition, int yMousePosition, boolean mouseIsClicked, int whichTroop){
        drawFilledRect(g, deckRectangle, DECK_RECTANGLE_COLOR, xMousePosition, yMousePosition, mouseIsClicked, whichTroop);
        drawFilledRect(g, deckRectangleBorder, Color.WHITE, xMousePosition, yMousePosition, mouseIsClicked, whichTroop);
        drawClockDelimiter(g, clockDelimiter, Color.GRAY);
        drawAvailableGold(g, goldRectangle, Color.ORANGE);
    }
    private void drawFilledRect(final Graphics g, final Rectangle r, final Color c, int xMousePosition, int yMousePosition, boolean mouseIsClicked, int whichTroop){


        g.setColor(c);
        g.fillRect(r.x, r.y, r.width, r.height);
        if(mouseIsClicked){
            //Fem que el personatge seleccionat segueixi la trajectoria del ratoli (drag)
            switch (whichTroop){
                case 0:
                    g.drawImage(bufferedImage[0], (xMousePosition - 10), (yMousePosition - 10), null);

                    break;
                case 1:
                    g.drawImage(bufferedImage[1], (xMousePosition - 10), (yMousePosition - 10), null);

                    break;
                case 2:
                    g.drawImage(bufferedImage[2], (xMousePosition - 10), (yMousePosition - 10), null);

                    break;
                case 3:
                    g.drawImage(bufferedImage[3], (xMousePosition - 10), (yMousePosition - 10), null);

                    break;
                default:
                    break;
            }

        }
        //Dibuixem els personatges del deck
        g.drawImage(bufferedImage[0],r.x + 30, r.y + 5, null);
        g.drawString("SKULL", r.x + 25, r.y + 50);
        g.drawImage(bufferedImage[1], r.x + 110, r.y + 5, null);
        g.drawString("GOBLIN", r.x + 100, r.y + 50);
        g.drawImage(bufferedImage[2], r.x + 190, r.y + 5, null);
        g.drawString("WIZARD", r.x + 180, r.y + 50);
        g.drawImage(bufferedImage[3], r.x + 260, r.y + 5, null);
        g.drawString("BOMB", r.x + 260, r.y + 50);

        java.util.Date now = new java.util.Date();
        if(clockTime < (int)((now.getTime() - createdDate.getTime()) / 1000)) {
            //System.out.println("PASSA UN SEGON");

            if ((goldRectangle.width + 5) < deckRectangle.width - 40) {
                goldDecrease = 5;
                goldRectangle.width += goldDecrease;
                goldRectangle.setBounds(goldRectangle.x, goldRectangle.y, goldRectangle.width, goldRectangle.height);
            }else{
                goldRectangle.setBounds(goldRectangle.x, goldRectangle.y, deckRectangle.width - 40, goldRectangle.height);
            }


        }
        clockTime = (int)((now.getTime() - createdDate.getTime()) / 1000);
        if(clockTime <= 9){
            g.drawString("00 : 0" + clockTime, clockRectangle.x + 140, clockRectangle.y + 50);
        }else if(clockTime > 9 && clockTime < 60){
            g.drawString("00 : " + clockTime, clockRectangle.x + 140, clockRectangle.y + 50);
        }else if(clockTime >= 60 && clockTime <= 69){

            g.drawString("01 : 0" + (clockTime- 60), clockRectangle.x + 140, clockRectangle.y + 50);
        }else if(clockTime > 69 ){
            g.drawString("01 : " + (clockTime - 60), clockRectangle.x + 140, clockRectangle.y + 50);
        }




    }
    private void drawClockDelimiter(final Graphics g, final Rectangle r, final Color c){
        g.setColor(c);
        g.fillRect(r.x, r.y, r.width, r.height);
    }

    private void drawAvailableGold(final Graphics g, final Rectangle r, final Color c){
        g.setColor(c);
        g.fillRect(r.x, r.y, r.width, r.height);
    }

    public void updateUserGold(int whichTroop){
        switch (whichTroop){
            case 0:
                goldDecrease = 150;

            case 1:
                goldDecrease = 160;

            case 2:
                goldDecrease = 75;

            case 3:
                goldDecrease = 100;

        }
        goldRectangle.width -= goldDecrease;
        goldRectangle.setBounds(goldRectangle.x, goldRectangle.y, goldRectangle.width, goldRectangle.height);

    }

    public boolean isEnoughGold(int whichTroop){
        switch (whichTroop){
            case 0:
                if(goldRectangle.width - 150 > 0){
                    return true;
                }else{
                    return false;
                }


            case 1:
                if(goldRectangle.width- 160 > 0){
                    return true;
                }else{
                    return false;
                }


            case 2:
                if(goldRectangle.width- 75 > 0){
                    return true;
                }else{
                    return false;
                }


            case 3:
                if(goldRectangle.width- 100 > 0){
                    return true;
                }else{
                    return false;
                }

            default:
                break;

        }
        return true;
    }




}
