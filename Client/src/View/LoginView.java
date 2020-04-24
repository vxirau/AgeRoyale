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
    private JButton jbRegistrat;

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
        jtfpassword = new JPasswordField();
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
        jbRegistrat = new JButton();
        jbRegistrat.setText("Registra't ara");
        jbRegistrat.setBounds(150, 440, 200, 80);
        jbRegistrat.setOpaque(false);
        jbRegistrat.setForeground(Color.WHITE);
        jbRegistrat.setContentAreaFilled(false);
        jbRegistrat.setBorderPainted(false);
        jbRegistrat.setFont(new Font("Helvetica", Font.BOLD, 15));
        jpPare.add(jbRegistrat);


        //Boto inici sessio
        botoInici = new JButton();
        botoInici.setText("INICIAR SESSIÓ");
        botoInici.setEnabled(true);
        botoInici.setBounds(130, 600, 200, 40);
        botoInici.setFont(new Font("Herculanum", Font.BOLD, 20));
        botoInici.setBackground(Color.decode("#FEAB24"));
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

		public void setUsuari(String us){
        jtfusername.setText(us);
    }

    public void setPassword(String pass){
        jtfpassword.setText(pass);
    }

    public void loginViewsetListener(ActionListener controlador) {
        botoInici.addActionListener(controlador);
        jbRegistrat.addActionListener(controlador);
    }


}
