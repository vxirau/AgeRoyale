package src.Controller;

import src.Model.Network.DedicatedServer;
import src.Tropa;
import src.View.Sprite;

public class TroopSController {

    public TroopSController(){

    }


    public Tropa moveOffensiveTroop(Tropa tropa, float xVariation, float yVariation, int cont) {

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

                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                    break;
                case 1:
                    //this.sprite = Sprite.SKELETON_RIGHT_LEFT_FOOT;
                    tropa.setSprite(tropa.getMov().get(1));
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                    break;
                case 2:
                    //this.sprite = Sprite.SKELETON_RIGHT_RIGHT_FOOT;
                    tropa.setSprite(tropa.getMov().get(2));
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                    DedicatedServer.cont = -1;
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
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                    break;
                case 1:
                    //this.sprite = Sprite.SKELETON_FRONT_LEFT_FOOT;
                    tropa.setSprite(tropa.getMov().get(4));
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                    break;
                case 2:
                    //this.sprite = Sprite.SKELETON_FRONT_RIGHT_FOOT;
                    tropa.setSprite(tropa.getMov().get(5));
                    DedicatedServer.cont = -1;
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/

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
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                    break;
                case 1:
                    //this.sprite = Sprite.SKELETON_FRONT_LEFT_FOOT;
                    tropa.setSprite(tropa.getMov().get(4));
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                    break;
                case 2:
                    //this.sprite = Sprite.SKELETON_FRONT_RIGHT_FOOT;
                    tropa.setSprite(tropa.getMov().get(5));
                    DedicatedServer.cont = -1;
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
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

    return tropa;


    }
    private void bombExplosion(Tropa tropa, int cont) {
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
                DedicatedServer.cont = -1;
                break;
            default:
                break;
        }
    }

    //Metode per detectar si col·lisionem amb alguna cosa al mapa
    private boolean onCollision(Tropa tropa, int xVariation, int yVariation){
        boolean collision = false;


        //Obtenim la posicio del jugador
        int xPosition = (int) (tropa.getxPosition());
        int yPosition = (int) (tropa.getyPosition());

        //Controlem fins a quin punt podra col·lisionar la tropa
        int leftMargin = -6;
        int rightMargin = 18;
        int supMargin = -4;
        int infMargin = 31;

        //Detectem els quatre bordes de l'sprite
        int leftBorder = (xPosition + rightMargin) / tropa.getSprite().getSide();
        int rightBorder = (xPosition + rightMargin + leftMargin) / tropa.getSprite().getSide();
        int supBorder = (yPosition + infMargin) / tropa.getSprite().getSide();
        int infBorder = (yPosition + infMargin + supMargin) / tropa.getSprite().getSide();

        //Controlem si l'sprite al qual avancem es solid i parem el moviment
        if(tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).isSolid()){

        }
        //Si xoquem amb un objecte de front, canviem la seva direccio
        if(tropa.getGameMap().getSpecificTile(leftBorder + infBorder * tropa.getGameMap().getMapWidth()).isSolid()){
            //TODO: CANVIAR LES COLISIONS I FER QUE ES PARI EL PERSONAJE SI EL CASTELL O LA TORRE NO ESTAN DESTRUITS
            //Si ens trobem a la part esquerra del mapa  i xoquem amb l'aigua, busquem el el pont esquerra
            if(xPosition < 160  && yPosition > 200){
                tropa.setxVariation(-2);
            }
            //Si ens trobem a la part esquerra del mapa  i xoquem amb la torre , l'ataquem o anem a pel castell principal
            if(xPosition < 160  && yPosition < 200){
                tropa.setxVariation(2);
            }
            //Si ens trobem a la part dreta del mapa  i xoquem amb l'aigua, busquem el el pont dret
            if(xPosition >= 160 && yPosition < 200){
                tropa.setxVariation(-2);
            }
            //Si ens trobem a la part dreta del mapa  i xoquem amb la torre, l'ataquem o anem a pel castell principal
            if(xPosition >= 160 && yPosition > 200){
                tropa.setxVariation(2);
            }
            tropa.setyVariation(0);
            collision = true;
        }else{
            //Si ens estavem movent cap a l'esquerra per evitar una col·lisio i l'evitem, continuem el cami de la tropa
            if(tropa.getxVariation() < 0) {
                tropa.setyVariation(-2);
                tropa.setxVariation(0);
            }
            if(tropa.getxVariation() > 0){
                tropa.setyVariation(-2);
                tropa.setxVariation(0);
            }
        }

        if(tropa.getGameMap().getSpecificTile(rightBorder + supBorder * tropa.getGameMap().getMapWidth()).isSolid()){

            if(tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).getSprite() == Sprite.BRIDGE){
                tropa.setyVariation(2);
                collision = false;
                tropa.setxVariation(0);
                System.out.println("KLK SURMANO");

            } else {

                collision = true;
                tropa.setxVariation(2);
                tropa.setyVariation(0);
            }
        }

        if(tropa.getGameMap().getSpecificTile(rightBorder + infBorder * tropa.getGameMap().getMapWidth()).isSolid()){
            //this.yVariation = 0;
            System.out.println("CHOQUE derecho");

            collision = true;
        }

        return collision;
    }

}
