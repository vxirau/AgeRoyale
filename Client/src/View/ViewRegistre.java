package src.View;

import src.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ViewRegistre extends JFrame implements ActionListener {


		private JPanel jpPare;
		private JTextField jtfusername;
		private JTextField jtfpassword;
		private JTextField jtfpassword2;
		private JTextField jtfCorreu;
		private JPanel jpRegisterView;
		private JButton botoRegistre;
		private JButton jbRegistrat;
		private JButton jbAtras;


		public ViewRegistre(){
				setTitle("Register");
				setLocationRelativeTo(null);
				setResizable(false);

				initComponents();
				setDefaultCloseOperation(EXIT_ON_CLOSE);

			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
			int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
			setLocation(x, y);
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


				JLabel jlTitol = new JLabel();
				jlTitol.setText("AGE ROYALE");
				jlTitol.setBounds(85, 30, 300, 80);
				jlTitol.setHorizontalAlignment(SwingConstants.CENTER);
				jlTitol.setOpaque(false);
				jlTitol.setForeground(Color.WHITE);
				jlTitol.setFont(new Font("Herculanum", Font.BOLD, 50));
				jpPare.add(jlTitol);

				ImageIcon imagen = new ImageIcon(this.getClass().getResource("/resources/escut.png"));
				JLabel jlIcon = new JLabel(imagen);
				jlIcon.setBounds(190, 115, 100, 100);
				jpPare.add(jlIcon);

				JLabel jlText1 = new JLabel();
				jlText1.setText("REGISTRA'T");
				jlText1.setBounds(70, 240, 200, 80);
				jlText1.setOpaque(false);
				jlText1.setForeground(Color.WHITE);
				jlText1.setFont(new Font("Herculanum", 0, 30));
				jpPare.add(jlText1);

				JLabel jlText2 = new JLabel();
				jlText2.setText("Nom d'usuari:");
				jlText2.setBounds(70, 280, 200, 80);
				jlText2.setOpaque(false);
				jlText2.setForeground(Color.WHITE);
				jlText2.setFont(new Font("Herculanum", 0, 15));
				jpPare.add(jlText2);

				jtfusername = new JTextField();
				jtfusername.setBounds(70, 330, 310, 50);
				jpPare.add(jtfusername);

				JLabel jlTextCorreu = new JLabel();
				jlTextCorreu.setText("Correu electrònic:");
				jlTextCorreu.setBounds(70, 360, 200, 80); //+80
				jlTextCorreu.setOpaque(false);
				jlTextCorreu.setForeground(Color.WHITE);
				jlTextCorreu.setFont(new Font("Herculanum", 0, 15));
				jpPare.add(jlTextCorreu);

				jtfCorreu = new JTextField();
				jtfCorreu.setBounds(70, 410, 310, 50); //+50
				jpPare.add(jtfCorreu);

				JLabel jlcontra = new JLabel();
				jlcontra.setText("Contrasenya:");
				jlcontra.setBounds(70, 440, 200, 80);
				jlcontra.setOpaque(false);
				jlcontra.setForeground(Color.WHITE);
				jlcontra.setFont(new Font("Herculanum", 0, 15));
				jpPare.add(jlcontra);

				jtfpassword = new JPasswordField();
				jtfpassword.setBounds(70, 490, 310, 50);
				jpPare.add(jtfpassword);

				JLabel jlText4 = new JLabel();
				jlText4.setText("Repeteix la contrasenya:");
				jlText4.setBounds(70, 520, 200, 80);
				jlText4.setOpaque(false);
				jlText4.setForeground(Color.WHITE);
				jlText4.setFont(new Font("Herculanum", 0, 15));
				jpPare.add(jlText4);

				jtfpassword2 = new JPasswordField();
				jtfpassword2.setBounds(70, 570, 310, 50);
				jpPare.add(jtfpassword2);

				botoRegistre = new JButton();
				botoRegistre.setText("REGISTRAR-SE");
				botoRegistre.setEnabled(true);
				botoRegistre.setBounds(130, 660, 200, 40);
				botoRegistre.setFont(new Font("Herculanum", Font.BOLD, 20));
				botoRegistre.setBackground(Color.decode("#FEAB24"));
				botoRegistre.setOpaque(true);
				jpPare.add(botoRegistre);

				//setBounds(x, y, width, height)
				jbAtras = new JButton();
				jbAtras.setText("ATRASH");
				jbAtras.setBounds(130, 710, 200, 35);
				jbAtras.setOpaque(false);
				jbAtras.setForeground(Color.WHITE);
				jbAtras.setContentAreaFilled(false);
				jbAtras.setBorderPainted(false);
				jbAtras.setFont(new Font("Helvetica", Font.BOLD, 15));
				jpPare.add(jbAtras);

				ImageIcon imagen2 = new ImageIcon(this.getClass().getResource("/resources/fondo-rojo-oscuro-marron_28629-798.png"));
				Icon icono = new ImageIcon(imagen2.getImage().getScaledInstance(450, 800, Image.SCALE_DEFAULT));
				JLabel fondo = new JLabel();
				fondo.setIcon(icono);
				getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
				fondo.setBounds(0, 0, 450, 800);
				jpPare.add(fondo);

				setSize(450, 800);

				this.setContentPane(jpPare);

		}


		public String getName(){
			return jtfusername.getText().toString();
		}

		public String getEmail(){
			return jtfCorreu.getText().toString();
		}

		public String getPassword(){
			return jtfpassword.getText().toString();
		}

		public String getRePass(){
			return jtfpassword2.getText().toString();
		}


		@Override
		public void actionPerformed(ActionEvent e) {

		}

		public void registerController(ActionListener controlador) {
			botoRegistre.addActionListener(controlador);
			jbAtras.addActionListener(controlador);
		}

}
