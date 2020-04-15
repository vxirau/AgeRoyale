package src.View;

import src.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Flow;

public class LoginView extends JFrame implements ActionListener {
    private JPanel jpPare;
    private JTextField jtfusername;
    private JTextField jtfpassword;
    private JPanel jpLoginView;
    private JButton botoInici;

    public LoginView(){
        //setBackground(Color.RED);
        setTitle("Login");
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //String imageURl = "/resources/fondo-rojo-oscuro-marron_28629-798.png";
        //BackView backView = new BackView(imageURl);
        //this.add(backView, BorderLayout.CENTER);
    }

    private void initComponents() {
        colocarPanel();
        colocarElements();
    }

    private void colocarPanel(){
        jpPare = new JPanel();
        jpPare.setLayout(null);
        jpPare.setOpaque(true);
        this.getContentPane().add(jpPare);
    }

    private void colocarElements() {
        //Títol: AGE ROYALE
        JLabel jlTitol = new JLabel();
        jlTitol.setText("AGE ROYALE");
        jlTitol.setBounds(85, 30, 300, 80);
        jlTitol.setHorizontalAlignment(SwingConstants.CENTER);
        jlTitol.setOpaque(false);
        jlTitol.setForeground(Color.WHITE);
        jlTitol.setFont(new Font("Herculanum", Font.BOLD, 50));
        jpPare.add(jlTitol);

        //Icona
        ImageIcon imagen = new ImageIcon(this.getClass().getResource("/resources/escut.png"));
        JLabel jlIcon = new JLabel(imagen);
        jlIcon.setBounds(190, 115, 100, 100);
        jpPare.add(jlIcon);

        //Text: Inici sessió
        JLabel jlText1 = new JLabel();
        jlText1.setText("INICI SESSIÓ");
        jlText1.setBounds(70, 240, 200, 80);
        jlText1.setOpaque(false);
        jlText1.setForeground(Color.WHITE);
        jlText1.setFont(new Font("Herculanum", 0, 30));
        jpPare.add(jlText1);

        //Text: usuari
        JLabel jlText2 = new JLabel();
        jlText2.setText("Username");
        jlText2.setBounds(70, 280, 200, 80);
        jlText2.setOpaque(false);
        jlText2.setForeground(Color.WHITE);
        jlText2.setFont(new Font("Herculanum", 0, 15));
        jpPare.add(jlText2);

        //Rellenar nom usuari
        jtfusername = new JTextField();
        jtfusername.setBounds(70, 330, 310, 50);
        jpPare.add(jtfusername);

        //Text: usuari
        JLabel jlText3 = new JLabel();
        jlText3.setText("Password");
        jlText3.setBounds(70, 360, 200, 80);
        jlText3.setOpaque(false);
        jlText3.setForeground(Color.WHITE);
        jlText3.setFont(new Font("Herculanum", 0, 15));
        jpPare.add(jlText3);

        //Rellenar contrasenya
        jtfpassword = new JTextField();
        jtfpassword.setBounds(70, 410, 310, 50);
        jpPare.add(jtfpassword);

        //Text: pregunta
        JLabel jlText4 = new JLabel();
        jlText4.setText("No estas registra't?");
        jlText4.setBounds(70, 440, 200, 80);
        jlText4.setOpaque(false);
        jlText4.setForeground(Color.WHITE);
        jlText4.setFont(new Font("Helvetica", 0, 15));
        jpPare.add(jlText4);

        //Text: registrat
        JLabel jlText5 = new JLabel();
        jlText5.setText("Registra't ara");
        jlText5.setBounds(205, 440, 200, 80);
        jlText5.setOpaque(false);
        jlText5.setForeground(Color.WHITE);
        jlText5.setFont(new Font("Helvetica", Font.BOLD, 15));
        jpPare.add(jlText5);

        //Boto inici sessio
        JButton botoInici = new JButton();
        botoInici.setText("INICIAR SESSIÓ");
        botoInici.setEnabled(true);
        botoInici.setBounds(130, 600, 200, 40);
        botoInici.setFont(new Font("Herculanum", Font.BOLD, 20));
        botoInici.setBackground(Color.decode("#FEAB24"));
        botoInici.setForeground(Color.decode("#FEAB24"));
        botoInici.setOpaque(true);
        jpPare.add(botoInici);

        ImageIcon imagen2 = new ImageIcon(this.getClass().getResource("/resources/fondo-rojo-oscuro-marron_28629-798.png"));
        Icon icono = new ImageIcon(imagen2.getImage().getScaledInstance(450, 800, Image.SCALE_DEFAULT));
        //Image image = imagen2.getImage();
        JLabel fondo = new JLabel();
        fondo.setIcon(icono);
        getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
        fondo.setBounds(0, 0, 450, 800);
        jpPare.add(fondo);


        setSize(450, 800);

        this.setContentPane(jpPare);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //listeners dels jpanels
    }


    public String getUsuari(){
        return jtfusername.getText();
    }

    public String getPassword(){
        return jtfpassword.getText();
    }


    /*private void createPanels(){
        JPanel jpPare = new JPanel();
        jpPare.setLayout(null);
        jpPare.setOpaque(false);

        //Títol: AGE ROYALE
        JLabel jlTitol = new JLabel();
        jlTitol.setText("AGE ROYALE");
        jlTitol.setBounds(60, 60, 300, 50);
        jlTitol.setHorizontalAlignment(SwingConstants.CENTER);
        jlTitol.setFont(new Font("Herculanum", Font.BOLD, 40));
        jlTitol.setOpaque(false);
        jpPare.add(jlTitol);

        //Icona
        ImageIcon imagen = new ImageIcon("/resources/escut.png");
        JLabel jlIcon = new JLabel(imagen);
        //jpAuxLogo.add(new JLabel(new ImageIcon(getClass().getResource("/resources/escut.png"))));
        jlIcon.setBounds(10, 80, 50, 50);
        jpPare.add(jlIcon);


        this.setContentPane(jpPare);
        /*JPanel panelPare = new JPanel();
        panelPare.setLayout(new GridLayout(9,1));
        panelPare.setOpaque(false);

        JPanel margen[] = new JPanel[15];
        for(int i=0; i<15 ;i++){
            margen[i] = new JPanel();
            margen[i].setOpaque(false);
        }

        JPanel jpAuxT = new JPanel(new BorderLayout());
        JLabel titol = new JLabel("<html><font color='white'>Age Royale</font></html>");
        titol.setHorizontalAlignment(JLabel.CENTER);
        jpAuxT.add(titol, BorderLayout.CENTER);
        jpAuxT.setOpaque(false);

        JPanel jpAuxLogo = new JPanel();
        jpAuxLogo.add(new JLabel(new ImageIcon(getClass().getResource("/resources/escut.png"))));
        jpAuxLogo.setOpaque(false);
        jpAuxLogo.setPreferredSize(new Dimension(50,50));

        JPanel jpAux1 = new JPanel(new FlowLayout());
        jpAux1.add(new JLabel("<html><font color='white'>"+ Utils.ferEspais(10) +" Nom d'usuari:"+ Utils.ferEspais(2) +"</font></html>"), BorderLayout.LINE_START);
        jtfusername = new JTextField();
        jtfusername.setColumns(10);
        jpAux1.add(jtfusername);
        jpAux1.setOpaque(false);


        JPanel jpAux2 = new JPanel(new FlowLayout());
        jpAux2.add(new JLabel("<html><font color='white'>"+ Utils.ferEspais(21) +" Correu:"+ Utils.ferEspais(2) +"</font></html>"), BorderLayout.LINE_START);
        jtfpassword = new JTextField();
        jtfpassword.setColumns(10);
        jpAux2.add(jtfpassword);
        jpAux2.setOpaque(false);

        JPanel jpAux4 = new JPanel(new GridLayout(3,3));
        jpAux4.add(margen[1]);
        jpAux4.add(margen[2]);
        jpAux4.add(margen[3]);
        jpAux4.add(margen[4]);
        botoInici = new JButton("Inicia sessió");
        jpAux4.add(botoInici);
        jpAux4.add(margen[5]);
        jpAux4.add(margen[6]);
        jpAux4.add(margen[7]);
        jpAux4.add(margen[8]);
        jpAux4.setOpaque(false);


        panelPare.add(jpAuxT);
        panelPare.add(jpAuxLogo);
        panelPare.add(margen[0]);
        panelPare.add(jpAux1, BorderLayout.WEST);
        panelPare.add(jpAux1);
        panelPare.add(jpAux2);
        panelPare.add(jpAux4);

        this.setContentPane(panelPare);*/


}