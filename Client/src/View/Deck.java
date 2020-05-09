package src.View;

import src.Tropa;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Deck {

    private Rectangle deckRectangle;
    private Rectangle deckRectangleBorder;
    private int deckHeight;
    private int width;
    private GameView gameView;
    private static final String SKELETON_DECK_PATH  = "/resources/skeleton_deck.png";
    private static final String GOBLIN_DECK_PATH  = "/resources/goblin_deck.png";
    private static final String WIZARD_DECK_PATH  = "/resources/wizard_deck.png";
    private static final String BOMB_DECK_PATH  = "/resources/bomb_deck.png";
    private boolean characterIsSelected;
    private int whichTroop;
    BufferedImage[] bufferedImage;

    private final Color  DECK_RECTANGLE_COLOR;


    public Deck(GameView gameView, int screenWidth, int screenHeight) throws IOException {

        this.gameView = gameView;
        deckHeight = 64;
        deckRectangle = new Rectangle(0, screenHeight - deckHeight + 5, screenWidth, deckHeight);
        deckRectangleBorder = new Rectangle(deckRectangle.x, deckRectangle.y , deckRectangle.width, 3 );
        whichTroop = 10;
        DECK_RECTANGLE_COLOR = new Color(23,23,23);
        bufferedImage = new BufferedImage[4];
        bufferedImage[0] = ImageIO.read(Deck.class.getResource(SKELETON_DECK_PATH));
        bufferedImage[1] = ImageIO.read(Deck.class.getResource(GOBLIN_DECK_PATH));
        bufferedImage[2] = ImageIO.read(Deck.class.getResource(WIZARD_DECK_PATH));
        bufferedImage[3] = ImageIO.read(Deck.class.getResource(BOMB_DECK_PATH));
        characterIsSelected = false;

    }

    public void showDeck(final Graphics g, int xMousePosition, int yMousePosition, boolean mouseIsClicked, int whichTroop){
        drawDeck(g, xMousePosition, yMousePosition, mouseIsClicked, whichTroop);
    }

    public void selectTroop(int whichTroop){
        this.whichTroop = whichTroop;
    }

    private void drawDeck(final Graphics g, int xMousePosition, int yMousePosition, boolean mouseIsClicked, int whichTroop){
        drawFilledRect(g, deckRectangle, DECK_RECTANGLE_COLOR, xMousePosition, yMousePosition, mouseIsClicked,whichTroop);
        drawFilledRect(g, deckRectangleBorder, Color.WHITE, xMousePosition, yMousePosition, mouseIsClicked, whichTroop);
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



    }





}
