package src.Controller;

import src.Edifici;
import src.Model.Network.DedicatedServer;
import src.Tropa;

import java.util.ArrayList;

/**
 * Aquesta classe és el controller destinat a la vista de les tropes
 */
public class TroopSController {
    private static Float minDistance = Float.MAX_VALUE;
    private static Tropa troop;
    public ArrayList<Edifici> edificiDef;

    public static int indice = 0;
    private static final int VARIATION = 2;
    private static final int CENTER_PADDING = 160;
    private static final int CASTLE_PADDING = 110;
    private static final int OWN_CASTLE_PADDING = 530;
    private static final int TOWER_PADDING_OPPONENT = 200;
    private static final int OWN_TOWER_DELIM = 350;
    private static final int LEFT_WATER_PADDING = 25;
    private static final int RIGHT_WATER_PADDING = 260;
    private static final int OWN_TOWER_PADDING_LEFT = 105;
    private static final int OWN_TOWER_PADDING_RIGHT = 205;
    private static final int HALF_MAP = 320;

    /**
     * Constructor de la classe
     */
    public TroopSController(){
        edificiDef = new ArrayList<>();
        ompleEdifici();
    }

    /**
     * Funció que mou la tropa ofensiva
     * @param tropa tropa a moure
     * @param xVariation variació de la x
     * @param yVariation variació de la y
     * @param cont variable auxiliar que indica quin moviment ha de fer el personatge
     * @return tropa tropa moguda
     */
    public synchronized Tropa moveOffensiveTroop(Tropa tropa, float xVariation, float yVariation, int cont) {
        //Es mou cap a la dreta (east)
        float xTroop;
        float yTroop;

        //La tropa nomes es moura si no esta lluitant
        if(!tropa.isFighting()){

            if(tropa.getDefaultY() < 320){
                if(xVariation == 0 && yVariation == 0){
                   tropa.setyVariation(2);
                }
            } else if(tropa.getDefaultY() > 320){
                if(xVariation == 0 && yVariation == 0){
                    tropa.setyVariation(-2);
                }
            }

            if (xVariation > 0) {
                tropa.setTroopDirection('e');

                xTroop = tropa.getxPosition() + xVariation;
                tropa.setxPosition(xTroop);
                switch (cont) {
                    case 0:
                        //this.sprite = Sprite.SKELETON_RIGHT;
                        if(tropa.getTroopType() == 0){
                            tropa.setSprite(tropa.getMov().get(0));
                        }


                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                        break;
                    case 1:
                        //this.sprite = Sprite.SKELETON_RIGHT_LEFT_FOOT;
                        if(tropa.getTroopType() == 0){
                            tropa.setSprite(tropa.getMov().get(1));
                        }
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                        break;
                    case 2:
                        //this.sprite = Sprite.SKELETON_RIGHT_RIGHT_FOOT;
                        if(tropa.getTroopType() == 0){
                            tropa.setSprite(tropa.getMov().get(2));
                        }
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

                switch (cont) {
                    case 0:
                        //this.sprite = Sprite.SKELETON_RIGHT;
                        if(tropa.getTroopType() == 0){
                            tropa.setSprite(tropa.getMov().get(6));
                        }


                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                        break;
                    case 1:
                        //this.sprite = Sprite.SKELETON_RIGHT_LEFT_FOOT;
                        if(tropa.getTroopType() == 0){
                            tropa.setSprite(tropa.getMov().get(7));
                        }
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                        break;
                    case 2:
                        //this.sprite = Sprite.SKELETON_RIGHT_RIGHT_FOOT;
                        if(tropa.getTroopType() == 0){
                            tropa.setSprite(tropa.getMov().get(8));
                        }
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
            //Es mou cap abaix (south)
            if (yVariation > 0) {

                tropa.setTroopDirection('s');
                yTroop = tropa.getyPosition() + yVariation;
                tropa.setyPosition(yTroop);
                switch (cont) {
                    case 0:
                        //this.sprite = Sprite.SKELETON_FRONT;
                        tropa.setSprite(tropa.getMov().get(9));
                        /*if(tropa.getTroopType() == 0){
                            tropa.setSprite(tropa.getMov().get(3));
                        }else if (tropa.getTroopType() == 1){
                            tropa.setSprite(tropa.getMov().get(9));
                        }*/
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                        break;
                    case 1:
                        //this.sprite = Sprite.SKELETON_FRONT_LEFT_FOOT;
                        tropa.setSprite(tropa.getMov().get(10));
                        /*if(tropa.getTroopType() == 0){
                            tropa.setSprite(tropa.getMov().get(4));
                        }else if (tropa.getTroopType() == 1){
                            tropa.setSprite(tropa.getMov().get(10));
                        }*/
                    /*try {
                        Thread.sleep(160);
                    } catch (Exception e) {
                        System.out.println(e);
                    }*/
                        break;
                    case 2:
                        //this.sprite = Sprite.SKELETON_FRONT_RIGHT_FOOT;
                        tropa.setSprite(tropa.getMov().get(11));
                        /*if(tropa.getTroopType() == 0){
                            tropa.setSprite(tropa.getMov().get(5));
                        }else if (tropa.getTroopType() == 1){
                            tropa.setSprite(tropa.getMov().get(11));
                        }*/
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
                if (onCollision(tropa)) {
                    //updatexPosition(xVariation);
                    xTroop = tropa.getxPosition() + xVariation;
                    tropa.setxPosition(xTroop);
                } else {

                    xVariation = 0;
                }
                if (onCollision(tropa)) {
                    //updateyPosition(yVariation);
                    yTroop = tropa.getyPosition() + yVariation;
                    tropa.setyPosition(yTroop);
                } else {

                    yVariation = 0;
                }
            }
        }else{
            tropa.setxVariation(0);
            tropa.setyVariation(0);

        }


    return tropa;


    }



    //Comprova l'estat de totes les tropes del joc
    public void checkTroopsStatus(){

    }

    //Metode per detectar si col·lisionem amb alguna cosa al mapa
    /*private boolean onCollision(Tropa tropa, int xVariation, int yVariation){
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
            if(tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).getSprite() == Sprite.TOWER_DOWN_LEFT_ENEMY || tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).getSprite() == Sprite.TOWER_DOWN_RIGHT_ENEMY
                || tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).getSprite() == Sprite.CASTLE_DOWN_LEFT_ENEMY || tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).getSprite() == Sprite.CASTLE_DOWN_RIGHT_ENEMY){
                //TODO: CANVIAR LES COLISIONS I FER QUE ES PARI EL PERSONAJE SI EL CASTELL O LA TORRE NO ESTAN DESTRUITS
            }

            //Si ens trobem a la part esquerra del mapa  i xoquem amb l'aigua, busquem el el pont esquerra
            if(xPosition < 160  && yPosition > 200){
                if(xPosition < 60){
                    tropa.setxVariation(2);
                }else{
                    tropa.setxVariation(-2);
                }

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
                if(xPosition > 250){
                    tropa.setxVariation(-2);
                }else{
                    tropa.setxVariation(2);
                }

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
    }*/

    /**
     * Funció que indica si ha hagut una colisió entre tropes o edificis
     * @param tropa tropa que ha pogut colisionar
     * @return collision indica si hi ha hagut una colisió o no
     */
    private boolean onCollision(Tropa tropa){
        boolean collision = false;



        //Obtenim la posicio del jugador
        int xPosition = (int) (tropa.getxPosition());
        int yPosition = (int) (tropa.getyPosition());

        //Controlem fins a quin punt podra col·lisionar la tropa
        int leftMargin = -6; //-6
        int rightMargin = 18; //18
        int supMargin = -4; //-4
        int infMargin = 31; //31

        //Detectem els quatre bordes de l'sprite
        int leftBorder = (xPosition + rightMargin) / tropa.getSprite().getSide();
        int rightBorder = (xPosition + rightMargin + leftMargin) / tropa.getSprite().getSide();
        int supBorder = (yPosition + infMargin) / tropa.getSprite().getSide();
        int infBorder = (yPosition + infMargin + supMargin) / tropa.getSprite().getSide();

        //Controlem si l'sprite al qual avancem es solid i parem el moviment
        if(tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).isSolid()){

        }
        //El sistema de col·lisions actuara en funcio de la direccio que hagi pres la tropa.
        //Detectarem l'usuari de la tropa amb el defaultY ficat a l'invocar la tropa,  i la direccio per tal de redirigir el moviment
        if(tropa.getTroopDirection() != 's' && tropa.getDefaultY() > HALF_MAP) {

            //TROPES ALIADES
            //Si xoquem amb un objecte de front, canviem la seva direccio
            if (tropa.getGameMap().getSpecificTile(leftBorder + infBorder * tropa.getGameMap().getMapWidth()).isSolid()) {
                //PART ESQUERRA DEL MAPA
                //Si ens trobem a la part esquerra del mapa, i al nostre camp
                if(xPosition < CENTER_PADDING){
                    if(yPosition > OWN_TOWER_DELIM){
                        //T'accidentes de manera pronunciada cap a un objectiu aliè a les teves idees politico-socials
                        tropa.setxVariation(VARIATION);
                    }else if(yPosition < OWN_TOWER_DELIM && xPosition < LEFT_WATER_PADDING && yPosition > TOWER_PADDING_OPPONENT ){
                        //T'accidentes de manera pronunciada cap a l'H2O
                        tropa.setxVariation(VARIATION);
                    }else if (yPosition < OWN_TOWER_DELIM && xPosition > LEFT_WATER_PADDING && yPosition > TOWER_PADDING_OPPONENT ){
                        tropa.setxVariation(-VARIATION);
                    }else if (yPosition < TOWER_PADDING_OPPONENT &&  yPosition > CASTLE_PADDING){
                        //ataquem torre
                        //si vida <= 0 ----> canviem sprite i solid del tile
                        if( edificiDef.get(0).getEntityLife() > 0){
                            tropa.setxVariation(0);
                            edificiDef.get(0).setEntityLife(edificiDef.get(0).getEntityLife() - tropa.getAtac());
                            System.out.println("ME HACES DAÑO BBSITAA: " + edificiDef.get(0).getEntityLife());

                        } else {

                            tropa.setNumTorre(0);
                            tropa.setxVariation(VARIATION);

                            //tornar a moure la tropa


                        }
                    } else if(yPosition < CASTLE_PADDING){

                        if( edificiDef.get(4).getEntityLife() > 0){
                            tropa.setxVariation(0);
                            edificiDef.get(4).setEntityLife(edificiDef.get(4).getEntityLife() - tropa.getAtac());
                            System.out.println("ME HACES DAÑO BBSITAA: " + edificiDef.get(4).getEntityLife());

                        } else {

                            tropa.setNumTorre(4);
                            //tropa.setxVariation(VARIATION);


                        }
                    }
                }

                //PART DRETA DEL MAPA
                //Si ens trobem a la part esquerra del mapa, i al nostre camp
                if(xPosition >= CENTER_PADDING){
                    if(yPosition > OWN_TOWER_DELIM){
                        tropa.setxVariation(-VARIATION);
                    }else if(yPosition < OWN_TOWER_DELIM && xPosition > RIGHT_WATER_PADDING && yPosition > TOWER_PADDING_OPPONENT ){
                        tropa.setxVariation(-VARIATION);
                    }else if (yPosition < OWN_TOWER_DELIM && xPosition < RIGHT_WATER_PADDING && yPosition > TOWER_PADDING_OPPONENT ){
                        tropa.setxVariation(VARIATION);
                    }else if (yPosition < TOWER_PADDING_OPPONENT &&  yPosition > CASTLE_PADDING){
                        //tropa.setxVariation(-VARIATION);
                        if( edificiDef.get(1).getEntityLife() > 0){
                            tropa.setxVariation(0);
                            edificiDef.get(1).setEntityLife(edificiDef.get(1).getEntityLife() - tropa.getAtac());
                            System.out.println("ME HACES DAÑO BBSITAA: " + edificiDef.get(1).getEntityLife());

                        } else {

                            tropa.setNumTorre(1);
                            tropa.setxVariation(-VARIATION);

                            //tornar a moure la tropa

                        }


                    }else if(yPosition < CASTLE_PADDING){
                        //atac al castell
                        if( edificiDef.get(4).getEntityLife() > 0){
                            tropa.setxVariation(0);
                            edificiDef.get(4).setEntityLife(edificiDef.get(4).getEntityLife() - tropa.getAtac());
                            System.out.println("ME HACES DAÑO BBSITAA: " + edificiDef.get(4).getEntityLife());

                        } else {

                            tropa.setNumTorre(5);
                            //tropa.setxVariation(VARIATION);



                        }
                    }
                }


                tropa.setyVariation(0);
                collision = true;
            } else {


                //Si ens estavem movent cap a l'esquerra per evitar una col·lisio i l'evitem, continuem el cami de la tropa
                if ((tropa.getxVariation() < 0 || tropa.getxVariation() > 0)) {

                        tropa.setyVariation(-VARIATION);
                        tropa.setxVariation(0);

                }

            }

            /*if (tropa.getGameMap().getSpecificTile(rightBorder + supBorder * tropa.getGameMap().getMapWidth()).isSolid()) {

                if (tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).getSprite() == Sprite.BRIDGE) {
                    tropa.setyVariation(VARIATION);
                    collision = false;
                    tropa.setxVariation(0);
                    System.out.println("KLK SURMANO");

                } else {

                    collision = true;
                    tropa.setxVariation(VARIATION);
                    tropa.setyVariation(0);
                }
            }

            if (tropa.getGameMap().getSpecificTile(rightBorder + infBorder * tropa.getGameMap().getMapWidth()).isSolid()) {
                //this.yVariation = 0;
                System.out.println("CHOQUE derecho");

                collision = true;
            }*/
        }
        if(tropa.getTroopDirection() != 'n' && tropa.getDefaultY() < HALF_MAP) {
            //TROPES ENEMIGUES

            //Si xoquem amb un objecte de front, canviem la seva direccio
            if (tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).isSolid()) {

                //PART ESQUERRA DEL MAPA
                //Si ens trobem a la part esquerra del mapa
                if(xPosition < CENTER_PADDING){
                    if(yPosition < TOWER_PADDING_OPPONENT){
                        tropa.setxVariation(VARIATION);
                    }else if(yPosition < OWN_TOWER_DELIM && xPosition < LEFT_WATER_PADDING && yPosition > TOWER_PADDING_OPPONENT ){
                        tropa.setxVariation(VARIATION);
                    }else if (yPosition < OWN_TOWER_DELIM && xPosition > LEFT_WATER_PADDING && yPosition > TOWER_PADDING_OPPONENT ){
                        tropa.setxVariation(-VARIATION);
                    }else if (yPosition > OWN_TOWER_DELIM &&  yPosition < OWN_CASTLE_PADDING){
                        //tropa.setxVariation(VARIATION);
                        //tropa.setxVariation(-VARIATION);
                        if( edificiDef.get(2).getEntityLife() > 0){
                            tropa.setxVariation(0);

                            edificiDef.get(2).setEntityLife(edificiDef.get(2).getEntityLife() - tropa.getAtac());
                            System.out.println("ME HACES DAÑO BBSITAA: " + edificiDef.get(2).getEntityLife());

                        } else {

                            tropa.setNumTorre(2);
                            tropa.setxVariation(VARIATION);
                            //tornar a moure la tropa

                        }

                    } else if(yPosition > OWN_CASTLE_PADDING){
                        //atac al castell

                        if( edificiDef.get(5).getEntityLife() > 0){
                            tropa.setxVariation(0);
                            edificiDef.get(5).setEntityLife(edificiDef.get(5).getEntityLife() - tropa.getAtac());
                            System.out.println("ME HACES DAÑO BBSITAA: " + edificiDef.get(5).getEntityLife());

                        } else {

                            tropa.setNumTorre(5);
                            //tropa.setxVariation(VARIATION);



                        }
                    }
                }

                //PART DRETA DEL MAPA
                //Si ens trobem a la part dreta del mapa
                if(xPosition >= CENTER_PADDING){
                    if(yPosition < TOWER_PADDING_OPPONENT){
                        tropa.setxVariation(-VARIATION);
                    }else if(yPosition > TOWER_PADDING_OPPONENT && xPosition > RIGHT_WATER_PADDING && yPosition < OWN_TOWER_DELIM ){
                        tropa.setxVariation(-VARIATION);
                    }else if (yPosition < OWN_TOWER_DELIM && xPosition < RIGHT_WATER_PADDING && yPosition > TOWER_PADDING_OPPONENT ){
                        tropa.setxVariation(VARIATION);
                    }else if (yPosition > OWN_TOWER_DELIM &&  yPosition < OWN_CASTLE_PADDING){
                       // tropa.setxVariation(-VARIATION);
                        tropa.setxVariation(0);

                        if( edificiDef.get(3).getEntityLife() > 0){
                            tropa.setyVariation(0);
                            edificiDef.get(3).setEntityLife(edificiDef.get(3).getEntityLife() - tropa.getAtac());
                            System.out.println("ME HACES DAÑO BBSITAA: " + edificiDef.get(3).getEntityLife());

                        } else {

                            tropa.setNumTorre(3);
                            tropa.setxVariation(-VARIATION);

                        }

                    }  else if(yPosition > OWN_CASTLE_PADDING){
                        //atac al castell
                        if( edificiDef.get(5).getEntityLife() > 0){
                            tropa.setxVariation(0);
                            edificiDef.get(5).setEntityLife(edificiDef.get(5).getEntityLife() - tropa.getAtac());
                            System.out.println("ME HACES DAÑO BBSITAA: " + edificiDef.get(5).getEntityLife());

                        } else {

                            tropa.setNumTorre(5);
                            //tropa.setxVariation(VARIATION);
                            
                        }
                    }
                }

                tropa.setyVariation(0);
                collision = true;
            } else {


                //Si ens estavem movent cap a l'esquerra per evitar una col·lisio i l'evitem, continuem el cami de la tropa
                if (tropa.getxVariation() < 0 || tropa.getxVariation() > 0) {

                        tropa.setyVariation(VARIATION);
                        tropa.setxVariation(0);

                }

            }

            /*if (tropa.getGameMap().getSpecificTile(rightBorder + supBorder * tropa.getGameMap().getMapWidth()).isSolid()) {

                if (tropa.getGameMap().getSpecificTile(leftBorder + supBorder * tropa.getGameMap().getMapWidth()).getSprite() == Sprite.BRIDGE) {
                    tropa.setyVariation(VARIATION);
                    collision = false;
                    tropa.setxVariation(0);
                    System.out.println("KLK SURMANO");

                } else {

                    collision = true;
                    tropa.setxVariation(VARIATION);
                    tropa.setyVariation(0);
                }
            }

            if (tropa.getGameMap().getSpecificTile(rightBorder + infBorder * tropa.getGameMap().getMapWidth()).isSolid()) {
                //this.yVariation = 0;
                System.out.println("CHOQUE derecho");

                collision = true;
            }*/
        }
        return collision;
    }

    /**
     * Funció que omple el mapa amb els edificis corresponents
     */
    public void ompleEdifici() {

        for (int i = 0; i < 6; i++) {
            Edifici e = new Edifici();
            switch (i) {
                case 0:
                    e.getTiles()[0][0] = 1;
                    e.getTiles()[0][1] = 5;
                    e.getTiles()[1][0] = 2;
                    e.getTiles()[1][1] = 5;
                    e.getTiles()[2][0] = 1;
                    e.getTiles()[2][1] = 6;
                    e.getTiles()[3][0] = 2;
                    e.getTiles()[3][1] = 6;
                    e.setEntityLife(20);
                    break;
                case 1:
                    e.getTiles()[0][0] = 7;
                    e.getTiles()[0][1] = 5;
                    e.getTiles()[1][0] = 8;
                    e.getTiles()[1][1] = 5;
                    e.getTiles()[2][0] = 7;
                    e.getTiles()[2][1] = 6;
                    e.getTiles()[3][0] = 8;
                    e.getTiles()[3][1] = 6;
                    e.setEntityLife(20);
                    break;
                case 2:
                    e.getTiles()[0][0] = 1;
                    e.getTiles()[0][1] = 14;
                    e.getTiles()[1][0] = 2;
                    e.getTiles()[1][1] = 14;
                    e.getTiles()[2][0] = 1;
                    e.getTiles()[2][1] = 15;
                    e.getTiles()[3][0] = 2;
                    e.getTiles()[3][1] = 15;
                    e.setEntityLife(20);
                    break;
                case 3:
                    e.getTiles()[0][0] = 7;
                    e.getTiles()[0][1] = 14;
                    e.getTiles()[1][0] = 8;
                    e.getTiles()[1][1] = 14;
                    e.getTiles()[2][0] = 7;
                    e.getTiles()[2][1] = 15;
                    e.getTiles()[3][0] = 8;
                    e.getTiles()[3][1] = 15;
                    e.setEntityLife(20);
                    break;
                case 4:
                    e.setEntityLife(100);
                    break;
                case 5:
                    e.setEntityLife(100);
                    break;
                default:
                    break;
            }
            edificiDef.add(e);
        }
    }

    /**
     * Retorna els edificis del mapa
     * @return edificiDef llista d'edificis del mapa
     */
    public  ArrayList<Edifici> getEdificiDef() {
        return edificiDef;
    }

    /**
     * Assigna els edificis del mapa
     * @return edificiDef llista d'edificis del mapa
     */
    public void setEdificiDef(ArrayList<Edifici> edificiDef) {
        this.edificiDef = edificiDef;
    }
}
