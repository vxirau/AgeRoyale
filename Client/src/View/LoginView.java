package src.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
* Aquesta classe és la classe destinada a la creació de la vista de login. Hereda de JFrame ja que és una finestra i volem que es comporti com a tal.
* */
public class LoginView extends JFrame {
    private JPanel jpPare;
    private JTextField jtfusername;
    private JTextField jtfpassword;
    private JButton botoInici;
    private JButton jbRegistrat;

    /**
    * Constructor de la classe
    * */
    public LoginView(){
        setTitle("Login");
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
        setLocation(x, y);
    }

    /**
    * Inicialitza els components de la pantalla grafica
    * */
    private void initComponents() {
        colocarPanel();
        colocarElements();
    }

    /**
    * Inicialitza i coloca el JPanel principal de la vista
    * */
    private void colocarPanel(){
        jpPare = new JPanel();
        jpPare.setLayout(null);
        jpPare.setOpaque(true);
        this.getContentPane().add(jpPare);
    }

    /**
    * Coloca els elements de UI a la finestra gràfica
    * */
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

    /**
    * Retorna l'usuari introduit per text
     * @return string amb el nom de usuari
    * */
    public String getUsuari(){
        return jtfusername.getText();
    }

    /**
     * Retorna la contrasenya introduit per text
     * @return string amb la contrasenya
     * */
    public String getPassword(){
        return jtfpassword.getText();
    }

    /**
     * Assigna el valor del TextField al que reb per paràmetre
     * @param us nom de usuari
     * */
	public void setUsuari(String us){
        jtfusername.setText(us);
    }

    /**
     * Assigna el valor del TextField al que reb per paràmetre
     * @param pass contrasenya desitjada
     * */
    public void setPassword(String pass){
        jtfpassword.setText(pass);
    }

    /**
     * Assignació de tots els controladors als elements de UI que els necessiten. En aquest cas només 2.
     * @param controlador controlador de la vista Login.
     * */
    public void loginViewsetListener(ActionListener controlador) {
        botoInici.addActionListener(controlador);
        jbRegistrat.addActionListener(controlador);
    }


}
