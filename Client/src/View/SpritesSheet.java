package src.View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

/**
* Classe que te la informació de la finestra del joc
* */
public class SpritesSheet implements Serializable {

    public  final int[] pixels;
    private final int width;
    private final int height;
    public static SpritesSheet arena;
    public static SpritesSheet troops;


    static {
        try {
            arena = new SpritesSheet("/resources/SpritesSheet.png", 320, 320);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static {
        try {
            troops = new SpritesSheet("/resources/playersSheet.png", 320, 320);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
    * Funció que retorna els pixels
     * @return array de enters amb els valors dels pixels
    * */
    public int[] getPixels() {
        return pixels;
    }

    /**
    * Retorna la amplada de la partida
     * @return enter amb el valor de l'amplada del joc
    * */
    public int getWidth() {
        return width;
    }

    /**
    * Retorna la alçada de la partida
     * @return enter amb la alçada
    * */
    public int getHeight() {
        return height;
    }

    /**
    * Constructor de la classe
     * @param path directori de la imatge
     * @param width grossor del sprite
     * @param height alçada del sprite
     * @throws IOException en cas que no hagi trobat el directori
    * */
    public SpritesSheet(final String path, final int width, final int height) throws IOException {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];

        BufferedImage image = ImageIO.read(SpritesSheet.class.getResource(path));
        image.getRGB(0, 0, width, height, pixels, 0, width);


    }
}
