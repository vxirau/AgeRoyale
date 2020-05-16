package src.View;

import src.Partida;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GraphPanel extends JPanel {
    private boolean hiHaDades = true;
    private ArrayList<Partida> games;
    private ArrayList<Point2D> punts;
    private ArrayList<Point2D> scaledPoints;
    private int maxX;
    private int maxY;
    private int eixX;
    private int eixY;


    public GraphPanel(int selectedIndex, ArrayList<Partida> games) {
        punts = new ArrayList<>();
        scaledPoints= new ArrayList<>();
        this.games = games;
        this.eixX = 650;
        this.eixY = 400;
        this.setLayout(null);
        this.setOpaque(false);

        ferDataset(selectedIndex);
        scaleDataSet();

        if (!hiHaDades) {
            punts.removeAll(punts);
            JLabel k = new JLabel("No hi ha dades en aquestes dates");
            k.setFont(new Font("Arial", Font.BOLD, 30));
            k.setBounds(100, 200, 600, 40);
            k.setForeground(Color.decode("#FFDC60"));
            this.add(k);
        }

        this.setBounds(0, 50, 700, 400);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gd = (Graphics2D) g;

        if(hiHaDades){
            gd.setStroke(new BasicStroke(4));
            gd.setColor(Color.black);
            int i=0;
            gd.setColor(Color.decode("#FFDC60"));
            Line2D x= new Line2D.Float(10,410,660,410);
            Line2D y= new Line2D.Float(10,410,10,10);
            gd.draw(x);
            gd.draw(y);
            gd.setStroke(new BasicStroke(2));
            for (int j=1; j<scaledPoints.size()-1 ;j++){
                JLabel tag = new JLabel();
                tag.setText((int)punts.get(j).getY()+"");
                tag.setFont(new Font("Arial", Font.PLAIN , 10));
                tag.setForeground(Color.decode("#FFDC60"));
                tag.setBounds(0, (int) scaledPoints.get(j).getY()-5,20,20);
                this.add(tag);
                if(maxX <200){
                    JLabel tagX = new JLabel();
                    tagX.setText((int)punts.get(j).getX()+"");
                    tagX.setFont(new Font("Arial", Font.PLAIN , 10));
                    tagX.setForeground(Color.decode("#FFDC60"));
                    tagX.setBounds((int) scaledPoints.get(j).getX(),410 ,20,20);
                    this.add(tagX);
                    Rectangle2D.Double rect = new Rectangle2D.Double(scaledPoints.get(j).getX() - 2.5, scaledPoints.get(j).getY() - 2.5, 5, 5);
                    gd.draw(rect);
                    gd.fill(rect);
                }
                Line2D l= new Line2D.Float(scaledPoints.get(j), scaledPoints.get(j+1));
                gd.draw(l);

            }
            if(maxX <200){
                Rectangle2D.Double rect = new Rectangle2D.Double(scaledPoints.get(scaledPoints.size()-1).getX() - 2.5, scaledPoints.get(scaledPoints.size()-1).getY() - 2.5, 5, 5);
                gd.draw(rect);
                gd.fill(rect);
            }else{
                JLabel tagX = new JLabel();
                tagX.setText("No es mostren etiquetes per l'any degut al espai");
                tagX.setFont(new Font("Arial", Font.PLAIN , 10));
                tagX.setForeground(Color.decode("#FFDC60"));
                tagX.setBounds(200,410 ,500,20);
                this.add(tagX);
            }


        }

    }

    private void ferDataset(int selectedIndex) {

        int days = 0, total, ok=0;
        if(selectedIndex == 0){
            days = 7;
        }else if(selectedIndex == 1){
            days = 30;
        }else if(selectedIndex == 2){
            days = 365;
        }
        maxX = days-1;
        for(int i=days; i>=0; i--){
            String today = LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            total = totalGamesToday(today);
            if(total == 0){
                ok++;
            }
            if(total>maxY){
                maxY = total;
            }
            Point2D p = new Point2D.Float(i, total);
            punts.add(p);
        }
        if(ok==days){
            hiHaDades = false;
        }
    }
    public void scaleDataSet(){
        if(maxY == 0 || maxX == 0){
           hiHaDades = false;
        }else{
            int ratioX = this.eixX/maxX;
            int ratioY = this.eixY/maxY;
            for (int i=0; i<punts.size() ;i++){
                Point2D p = new Point2D.Double(ratioX*punts.get(i).getX()+10, (this.eixY)-ratioY*punts.get(i).getY()+10);
                scaledPoints.add(p);
            }
        }

    }
    private int totalGamesToday(String data){
        int total = 0;
        for(Partida p : games){
            if(p.getData().equals(data)){
                total++;
            }
        }
        return total;
    }
}
