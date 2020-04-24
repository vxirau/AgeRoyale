package src.Controller;

import src.View.GameView;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

public class UpdateTask extends JFrame {
    private int x = 0;
    private int y = 0;
    private GameView view;

    public UpdateTask(GameView view){
        this.view = view;
    }

    public void moveBall() {
        //x = x + 1;
        y = y + 1;
    }

    @Override
    public void paint(Graphics g) {
        view.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillOval(x, y, 20, 20);
    }

}
