package src.Controller;

import src.Tropa;
import src.View.Deck;
import src.View.GameView;
import src.View.Sprite;

import java.io.IOException;

public class TroopController {

    private GameView gameView;
    private static int cont = 0;

    public TroopController(GameView gameView) throws IOException {
        this.gameView = gameView;

    }

    public void update(){
        if(gameView.getTropes().size() > 0){
            for(int i = 0; i < gameView.getTropes().size(); i++){
                //gameView.getTropes().get(i).update();
                updateTropa(gameView.getTropes().get(i));
            }
        }
    }

    public void updateTropa(Tropa tropa){
        if(tropa.isPlaying()) {
            if(tropa.getTroopType() == 0 || tropa.getTroopType() == 1){
                moveOffensiveTroop(tropa, tropa.getxVariation(), tropa.getyVariation(), cont);
            }else if(tropa.getTroopType() == 2){
                bombExplosion(tropa, cont);
            }else{

            }
            cont++;
        }
    }

    private void moveOffensiveTroop(Tropa tropa, float xVariation, float yVariation, int cont) {
        //Es mou cap a la dreta (east)
        float xTroop;
        float yTroop;
        if (xVariation > 0) {
             tropa.setTroopDirection('e');
             xTroop = tropa.getxPosition() + xVariation;
            tropa.setxPosition(xTroop);
            switch (cont) {
                case 0:
                    //this.sprite = Sprite.SKELETON_RIGHT;
                    tropa.setSprite(tropa.getMov().get(0));

                    try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 1:
                    //this.sprite = Sprite.SKELETON_RIGHT_LEFT_FOOT;
                    tropa.setSprite(tropa.getMov().get(1));
                    try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 2:
                    //this.sprite = Sprite.SKELETON_RIGHT_RIGHT_FOOT;
                    tropa.setSprite(tropa.getMov().get(2));
                    try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    this.cont = -1;
                    break;
                default:
                    break;
            }

        }
        //Es mou cap a l'esquerra (west)
        if (xVariation < 0) {
            tropa.setTroopDirection('w');
            xTroop = tropa.getxPosition() + xVariation;
            tropa.setxPosition(xTroop);
        }
        //Es mou cap abaix (south)
        if (yVariation > 0) {
            tropa.setTroopDirection('s');
            yTroop = tropa.getyPosition() + yVariation;
            tropa.setyPosition(yTroop);
            switch (cont) {
                case 0:

                    //this.sprite = Sprite.SKELETON_FRONT;
                    tropa.setSprite(tropa.getMov().get(3));
                    try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 1:
                    //this.sprite = Sprite.SKELETON_FRONT_LEFT_FOOT;
                    tropa.setSprite(tropa.getMov().get(4));
                    try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 2:
                    //this.sprite = Sprite.SKELETON_FRONT_RIGHT_FOOT;
                    tropa.setSprite(tropa.getMov().get(5));
                    this.cont = -1;
                    try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    this.cont = -1;
                    break;
                default:
                    break;
            }

        }
        //Es mou cap adalt (north)
        if (yVariation < 0) {
            tropa.setTroopDirection('n');
            yTroop = tropa.getyPosition() + yVariation;
            tropa.setyPosition(yTroop);
            switch (cont) {
                case 0:
                    //this.sprite = Sprite.SKELETON_FRONT;
                    tropa.setSprite(tropa.getMov().get(3));
                    try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 1:
                    //this.sprite = Sprite.SKELETON_FRONT_LEFT_FOOT;
                    tropa.setSprite(tropa.getMov().get(4));
                    try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    break;
                case 2:
                    //this.sprite = Sprite.SKELETON_FRONT_RIGHT_FOOT;
                    tropa.setSprite(tropa.getMov().get(5));
                    this.cont = -1;
                    try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    this.cont = -1;
                    break;
                default:
                    break;
            }
        }

        //Si la tropa no ha estat destruida, la movem
        if (!tropa.entityIsDestroyed()) {
            if (onCollision(tropa, (int) xVariation, 0)) {
                //updatexPosition(xVariation);
                xTroop = tropa.getxPosition() + xVariation;
                tropa.setxPosition(xTroop);
            } else {
                xVariation = 0;
            }
            if (onCollision(tropa, 0, (int) yVariation)) {
                //updateyPosition(yVariation);
                yTroop = tropa.getyPosition() + yVariation;
                tropa.setyPosition(yTroop);
            } else {
                yVariation = 0;
            }
        }

    }
    private void bombExplosion(Tropa tropa, int cont){
        switch (cont) {
            case 0:
                //this.sprite = Sprite.SKELETON_RIGHT;

                tropa.setSprite(tropa.getMov().get(0));

                try {
                    Thread.sleep(160);
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case 1:
                //this.sprite = Sprite.SKELETON_RIGHT_LEFT_FOOT;
                tropa.setSprite(tropa.getMov().get(1));
                try {
                    Thread.sleep(160);
                } catch (Exception e) {
                    System.out.println(e);
                }
                break;
            case 2:
                //this.sprite = Sprite.SKELETON_RIGHT_RIGHT_FOOT;
                tropa.setSprite(tropa.getMov().get(2));
                try {
                    Thread.sleep(160);
                } catch (Exception e) {
                    System.out.println(e);
                }

                break;
            case 3:
                //this.sprite = Sprite.SKELETON_RIGHT_RIGHT_FOOT;
                tropa.setSprite(tropa.getMov().get(3));
                try {
                    Thread.sleep(160);
                } catch (Exception e) {
                    System.out.println(e);
                }
                this.cont = -1;
                break;
            default:
                break;
        }
    }
    //Metode per detectar si col·lisionem amb alguna cosa al mapa
    private boolean onCollision(Tropa tropa, int xVariation, int yVariation){
        boolean collision = false;

        int xPosition = (int) (tropa.getxPosition() + xVariation);
        int yPosition = (int) (tropa.getyPosition() + yVariation);

        int leftMargin = -6;
        int rightMargin = 18;
        int supMargin = -4;
        int infMargin = 31;

        int leftBorder = (xPosition + rightMargin) / tropa.getSprite().getSide();
        int rightBorder = (xPosition + rightMargin + leftMargin) / tropa.getSprite().getSide();
        int supBorder = (yPosition + infMargin) / tropa.getSprite().getSide();
        int infBorder = (yPosition + infMargin + supMargin) / tropa.getSprite().getSide();

        if(tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).isSolid()){

            if(tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).getSprite() == Sprite.BRIDGE){
                tropa.setyVariation(2);
                collision = false;
                tropa.setxVariation(0);
            } else {
                collision = true;
                tropa.setxVariation(2);
                tropa.setyVariation(0);
            }

        }
        if(tropa.getGameMap().getSpecificTile(leftBorder + infBorder * tropa.getGameMap().getMapWidth()).isSolid()){
            tropa.setyVariation(0);
            collision = true;
        }
        if(tropa.getGameMap().getSpecificTile(rightBorder + supBorder * tropa.getGameMap().getMapWidth()).isSolid()){

            if(tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).getSprite() == Sprite.BRIDGE){
                tropa.setyVariation(2);
                collision = false;
                tropa.setxVariation(0);
            } else {
                collision = true;
                tropa.setxVariation(2);
                tropa.setyVariation(0);
            }

        }
        if(tropa.getGameMap().getSpecificTile(rightBorder + infBorder * tropa.getGameMap().getMapWidth()).isSolid()){
            //this.yVariation = 0;
            collision = true;
        }

        return collision;
    }
}