package src;

import src.View.GameMap;
import src.View.GameView;
import src.View.Sprite;
import src.View.Tile;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

public class Tropa extends Entity implements Serializable {

    private Sprite sprite;
    private char troopDirection = 'n';
    private boolean isMoving = false;
    private GameMap gameMap;
    private float xVariation;
    private float yVariation;
    private static final int SIDE = 32;
    private boolean isFighting;
    private boolean isOn;
    private int idPartida;
    private int idUsuariInvoke;




    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    private boolean isChecked = false;


    public int getIdUsuariInvoke() {
        return idUsuariInvoke;
    }

    public void setIdUsuariInvoke(int idUsuariInvoke) {
        this.idUsuariInvoke = idUsuariInvoke;
    }


    private String whichSprite;
    private Usuari usr;
    private int defaultY;
    private float initialX;
    private float initialY;
    private int numTorre;
    private int hasDestroyedCastle;


    public int getDestroyedCastle() {
        return hasDestroyedCastle;
    }

    public void setdestroyedCastle(int hasDestroyedCastle) {
        this.hasDestroyedCastle = hasDestroyedCastle;
    }

    private int troopType;
    private int idTropa;
    private int vida;
    private int cost;
    private int atac;
    private int alcance;
    private boolean ofensiva;
    private Tile tile;

    public float getInitialX() {
        return initialX;
    }

    public void setInitialX(float initialX) {
        this.initialX = initialX;
    }

    public float getInitialY() {
        return initialY;
    }

    public void setInitialY(float initialY) {
        this.initialY = initialY;
    }

    private boolean isPlaying = true;
    private static int cont = 0;
    private ArrayList<Sprite> mov = new ArrayList<>();

    /**
     * Comprova que hi ha per sobre d'aquell quadrat
     * @return Tile representa un quadrat del mapa
     * */
    public Tile getTroopTile() {
        for(int i = 0; i < 20; i ++){
            for(int j = 0; j < 10; j++){
                if(this.xPosition >= this.getGameMap().getMapTiles()[i][j].getX1() && this.xPosition < this.getGameMap().getMapTiles()[i][j].getX2()
                    && this.yPosition >= this.getGameMap().getMapTiles()[i][j].getY1() && this.yPosition < this.getGameMap().getMapTiles()[i][j].getY2()){
                    //Trobem el tile al que es troba la nostra tropa i el retornem.
                    return this.getGameMap().getTilesArray()[i];

                }
            }
        }
        return null;
    }

    /**
     * Assigna un quadrat del mapa
     * @param tile representa un quadrat del mapa
     * */
    public void setTile(Tile tile) {
        this.tile = tile;
    }

    /**
     * Retorna el tipus de tropa
     * @return troopType representa el tipus de tropa
     * */
    public int getTroopType() {
        return troopType;
    }

    /**
     * Assigna el tipus de tropa
     * @param troopType representa el tipus de tropa
     * */
    public void setTroopType(int troopType) {
        this.troopType = troopType;
    }

    /**
     * Indica si està jugant una partida o no
     * @return isPlaying boolean que indica si està jugant o no
     * */
    public boolean isPlaying() {
        return isPlaying;
    }

    /**
     * Assigna si està jugant o no una partida
     * @param playing boolean que indica si està jugant o no
     * */
    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    /**
     * Retorna la imatge que se li assigna a un quadrat del mapa
     * @return sprite representa la imatge assignada a un quadrat
     * */
    public Sprite getSprite() {
        return sprite;
    }

    /**
     * Constructor de la classe Tropa
     * */
    public Tropa() {

    }

    /**
     * Indica si està lluitant o no
     * @return isFighting boolean que indica si està lluitant
     * */
    public boolean isFighting() {
        return isFighting;
    }

    /**
     * Assigna si està lluitant o no
     * @param fighting boolean que indica si està lluitant
     * */
    public void setFighting(boolean fighting) {
        isFighting = fighting;
    }

    public Tropa(GameMap gameMap, float xPosition, float yPosition, Sprite sprite, Usuari user) {
        this.gameMap = gameMap;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.sprite = sprite;
        this.troopType = -1;
        xVariation = 0;
        yVariation = (float) -2;
        this.isFighting = false;
        this.defaultY = 500;
        this.idUsuariInvoke = user.getIdUsuari();
    }

    /**
     * Constructor secundari de la classe Tropa
     * @param gameMap representa el mapa del joc
     * @param idTropa indica el ide de la tropa
     * @param vida indica la quantitat de vida que té el jugador
     * @param cost indica el que costa (or) cada personatge
     * @param atac indica el tipus d'atac
     * @param alcance indica lo lluny que pot arribar a atacar
     * @param ofensiva indica si és un personatge d'atac o de defensiva
     * */
    public Tropa(GameMap gameMap, int idTropa, int vida, int cost, int atac, int alcance, boolean ofensiva) {
        this.gameMap = gameMap;
        this.idTropa = idTropa;
        this.vida = vida;
        this.cost = cost;
        this.atac = atac;
        this.alcance = alcance;
        this.ofensiva = ofensiva;
    }



    /**
     * Assigna les imatges a cada quadrat
     * @param sprite representa una imatge
     * */
    public void setSprites(Sprite sprite){

        if(sprite.equals(Sprite.SKELETON_BACK)){

            this.troopType = 0;
            mov.add(Sprite.SKELETON_RIGHT);
            mov.add(Sprite.SKELETON_RIGHT_LEFT_FOOT);
            mov.add(Sprite.SKELETON_RIGHT_RIGHT_FOOT);
            mov.add(Sprite.SKELETON_BACK);
            mov.add(Sprite.SKELETON_BACK_LEFT_FOOT);
            mov.add(Sprite.SKELETON_BACK_RIGHT_FOOT);
            mov.add(Sprite.SKELETON_LEFT);
            mov.add(Sprite.SKELETON_LEFT_LEFT_FOOT);
            mov.add(Sprite.SKELETON_LEFT_RIGHT_FOOT);
            mov.add(Sprite.SKELETON_FRONT);
            mov.add(Sprite.SKELETON_FRONT_LEFT_FOOT);
            mov.add(Sprite.SKELETON_FRONT_RIGHT_FOOT);


        } else if(sprite.equals(Sprite.GOBLIN_BACK)){
            this.troopType = 1;

            mov.add(Sprite.GOBLIN_RIGHT);
            mov.add(Sprite.GOBLIN_RIGHT_LEFT_FOOT);
            mov.add(Sprite.GOBLIN_RIGHT_RIGHT_FOOT);
            mov.add(Sprite.GOBLIN_BACK);
            mov.add(Sprite.GOBLIN_BACK_LEFT_FOOT);
            mov.add(Sprite.GOBLIN_BACK_RIGHT_FOOT);
            mov.add(Sprite.GOBLIN_LEFT);
            mov.add(Sprite.GOBLIN_LEFT_LEFT_FOOT);
            mov.add(Sprite.GOBLIN_LEFT_RIGHT_FOOT);
            mov.add(Sprite.GOBLIN_FRONT);
            mov.add(Sprite.GOBLIN_FRONT_LEFT_FOOT);
            mov.add(Sprite.GOBLIN_FRONT_RIGHT_FOOT);

        }else if(sprite.equals(Sprite.BOMB)){
            this.troopType = 2;

            mov.add(Sprite.BOMB);
            mov.add(Sprite.BOMB_PHASE_1);
            mov.add(Sprite.BOMB_PHASE_2);
            mov.add(Sprite.BOMB_PHASE_3);
            mov.add(Sprite.BOMB_PHASE_4);


        }else {
            this.troopType = 3;
            mov.add(Sprite.MAGIC_TOWER);
        }
    }


    /**
     * Mostra el joc
     * @param gameView representa la pantalla gràfica del joc
     * */
    public void show(GameView gameView){
        gameView.drawTroop(xPosition, yPosition, this);
    }

    /**
     * Assigna una imatge a un quadrat
     * @param sprite representa una imatge
     * */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    /**
     * Retorna la direcció de la tropa
     * @return troopDirection representa la direcció de la tropa
     * */
    public char getTroopDirection() {
        return troopDirection;
    }

    /**
     * Assigna la direcció de la tropa
     * @param troopDirection representa la direcció de la tropa
     * */
    public void setTroopDirection(char troopDirection) {
        this.troopDirection = troopDirection;
    }

    /**
     * Retorna si la tropa s'està movent
     * @return isMoving representa si s'està movent o no
     * */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Assigna si la tropa s'està movent
     * @param moving representa si s'està movent o no
     * */
    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    /**
     * Retorna el mapa del joc
     * @return gameMap representa el mapa del joc
     * */
    public GameMap getGameMap() {
        return gameMap;
    }

    /**
     * Assigna el mapa del joc
     * @param gameMap representa el mapa del joc
     * */
    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * Retorna la variació de la x
     * @return xVariation representa la variació de la x
     * */
    public float getxVariation() {
        return xVariation;
    }

    /**
     * Assigna la variació de la x
     * @param xVariation representa la variació de la x
     * */
    public void setxVariation(float xVariation) {
        this.xVariation = xVariation;
    }

    /**
     * Retorna la variació de la y
     * @return yVariation representa la variació de la y
     * */
    public float getyVariation() {
        return yVariation;
    }

    /**
     * Assigna la variació de la y
     * @param yVariation representa la variació de la y
     * */
    public void setyVariation(float yVariation) {
        this.yVariation = yVariation;
    }

    /**
     * Retorna una variable auxiliar per a detectar la posició i estat de la tropa
     * @return cont variable auxiliar per a detectar la posició i estat de la tropa
     * */
    public static int getCont() {
        return cont;
    }

    /**
     * Assigna cont, una variable auxiliar per a detectar la posició i estat de la tropa
     * @param cont variable auxiliar per a detectar la posició i estat de la tropa
     * */
    public static void setCont(int cont) {
        Tropa.cont = cont;
    }

    /**
     * Retorna el moviment que segueix cada personatge (imatges per les que passa)
     * @return mov determina el moviment que segueix
     * */
    public ArrayList<Sprite> getMov() {
        return mov;
    }

    /**
     * Assigna el moviment que segueix cada personatge (imatges per les que passa)
     * @param mov determina el moviment que segueix
     * */
    public void setMov(ArrayList<Sprite> mov) {
        this.mov = mov;
    }

    /**
     * Retorna l'id de la tropa
     * @return idTropa determina el id de la tropa
     * */
    public int getIdTropa() {
        return idTropa;
    }

    /**
     * Assigna l'id de la tropa
     * @param idTropa determina el id de la tropa
     * */
    public void setIdTropa(int idTropa) {
        this.idTropa = idTropa;
    }

    /**
     * Retorna la vida de cada tropa
     * @return vida determina la vida que té cada tropa
     * */
    public int getVida() {
        return vida;
    }

    /**
     * Assigna la vida de cada tropa
     * @param vida determina la vida que té cada tropa
     * */
    public void setVida(int vida) {
        this.vida = vida;
    }

    /**
     * Retorna el cost de cada personatge
     * @return cost determina el cost de cada personatge
     * */
    public int getCost() {
        return cost;
    }

    /**
     * Assigna el cost de cada personatge
     * @param cost determina el cost de cada personatge
     * */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Retorna l'atac que realitza cada personatge
     * @return atac determina el cost de cada personatge
     * */
    public int getAtac() {
        return atac;
    }

    /**
     * Asisgna l'atac que realitza cada personatge
     * @param atac determina el cost de cada personatge
     * */
    public void setAtac(int atac) {
        this.atac = atac;
    }

    /**
     * Retorna la distancia a la que pot atacar un personatge
     * @return alcance distancia a la que pot atacar un personatge
     * */
    public int getAlcance() {
        return alcance;
    }

    /**
     * Assigna la distancia a la que pot atacar un personatge
     * @param alcance distancia a la que pot atacar un personatge
     * */
    public void setAlcance(int alcance) {
        this.alcance = alcance;
    }

    /**
     * Determina si es ofensiu o no
     * @return ofensiva Determina si es ofensiu o no
     * */
    public boolean isOfensiva() {
        return ofensiva;
    }

    /**
     * Assigna si es ofensiu o no
     * @param ofensiva Determina si es ofensiu o no
     * */
    public void setOfensiva(boolean ofensiva) {
        this.ofensiva = ofensiva;
    }

    /**
     * Determina si està actiu o no
     * @return isOn Determina si està actiu o no
     * */
    public boolean isOn() {
        return isOn;
    }

    /**
     * Assigna si està actiu o no
     * @param on Determina si està actiu o no
     * */
    public void setOn(boolean on) {
        isOn = on;
    }

    /**
     * Retorna el id de la partida
     * @return idPartida representa el id de la partida
     * */
    public int getIdPartida() {
        return idPartida;
    }

    /**
     * Assigna el id de la partida
     * @param idPartida representa el id de la partida
     * */
    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    /**
     * Retorna una imatge en concret
     * @return  whichSprite representa una imatge d'un quadrat
     * */
    public String getWhichSprite() {
        return whichSprite;
    }

    /**
     * Assigna una imatge en concret
     * @param whichSprite representa una imatge d'un quadrat
     * */
    public void setWhichSprite(String whichSprite) {
        this.whichSprite = whichSprite;
    }

    /**
     * Retorna una posició y per defecte
     * @return defaultY representa una posició y per defecte
     * */
    public int getDefaultY() {
        return defaultY;
    }

    /**
     * Assigna una posició y per defecte
     * @param  defaultY representa una posició y per defecte
     * */
    public void setDefaultY(int defaultY) {
        this.defaultY = defaultY;
    }

    /**
     * Retorna el número de torres
     * @return numTorre representa el número de torres
     * */
    public int getNumTorre() {
        return numTorre;
    }

    /**
     * Assigna el número de torres
     * @param  numTorre representa el número de torres
     * */
    public void setNumTorre(int numTorre) {
        this.numTorre = numTorre;
    }

    @Override
    public String toString() {
        return "Tropa{" +
                "sprite=" + sprite +
                ", troopDirection=" + troopDirection +
                ", isMoving=" + isMoving +
                ", gameMap=" + gameMap +
                ", xVariation=" + xVariation +
                ", yVariation=" + yVariation +
                ", isFighting=" + isFighting +
                ", isOn=" + isOn +
                ", idPartida=" + idPartida +
                ", whichSprite='" + whichSprite + '\'' +
                ", defaultY=" + defaultY +
                ", troopType=" + troopType +
                ", idTropa=" + idTropa +
                ", vida=" + vida +
                ", cost=" + cost +
                ", atac=" + atac +
                ", alcance=" + alcance +
                ", ofensiva=" + ofensiva +
                ", tile=" + tile +
                ", isPlaying=" + isPlaying +
                ", xPosition=" + xPosition +
                ", yPosition=" + yPosition +
                '}';
    }


}
