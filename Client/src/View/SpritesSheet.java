package src.View;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SpritesSheet {

    public  final int[] pixels;
    private final int width;
    private final int height;
    public static SpritesSheet arena;

    static {
        try {
            arena = new SpritesSheet("/resources/SpritesSheet.png", 320, 320);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public SpritesSheet(final String path, final int width, final int height) throws IOException {
        this.width = width;
        this.height = height;
        this.pixels = new int[width * height];

        BufferedImage image = ImageIO.read(SpritesSheet.class.getResource(path));
        image.getRGB(0, 0, width, height, pixels, 0, width);


    }
}
