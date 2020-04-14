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


		private JTextField loginText;
		private JTextField passText;
		private JTextField passConfirmText;
		private JTextField emailText;
		private JButton botoRegistre;

    public ViewRegistre() throws IOException {


				/*this.setContentPane(new JPanel() {
					@Override
					public void paintComponent(Graphics g){
							System.out.println("paintComponent");
							Dimension tamanio = getSize();
              ImageIcon imagenFondo = new ImageIcon(getClass().
											getResource("/resources/fondo-rojo-oscuro-marron_28629-798.png"));
							g.drawImage(imagenFondo.getImage(), 0, 0,
											tamanio.width, tamanio.height, null);
							setOpaque(false);
							super.paintComponent(g);
					}
				});

      	ImagePanel image = new ImagePanel(new ImageIcon(getClass().
              getResource("/resources/fondo-rojo-oscuro-marron_28629-798.png")).getImage());*/

				/*JPanel panelPare=new JPanel(){
					@Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            // This is faking transparency, so the panelPareground color
	            // will be see through
	            Graphics2D g2d = (Graphics2D) g.create();
	            Composite old = g2d.getComposite();
	            g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
	            g2d.fillRect(0, 0, getWidth(), getHeight());
	            g2d.setComposite(old);
	        }
				};*/

				JPanel panelPare = new JPanel();
				panelPare.setLayout(new GridLayout(9,1));
				panelPare.setOpaque(false);

				JPanel jpAuxT = new JPanel(new BorderLayout());
				JLabel titol = new JLabel("<html><font color='white'>AGE ROYALE</font></html>");
				titol.setHorizontalAlignment(JLabel.CENTER);
				jpAuxT.add(titol, BorderLayout.CENTER);
				jpAuxT.setOpaque(false);


				JPanel jpAuxLogo = new JPanel();
				jpAuxLogo.add(new JLabel(new ImageIcon(getClass().getResource("/resources/escut.png"))));
				jpAuxLogo.setOpaque(false);
				jpAuxLogo.setPreferredSize(new Dimension(50,50));


				JPanel margen[] = new JPanel[15];
				for(int i=0; i<15 ;i++){
					margen[i] = new JPanel();
					margen[i].setOpaque(false);
				}

				JPanel jpAux1 = new JPanel(new FlowLayout());
				jpAux1.add(new JLabel("<html><font color='white'>"+ Utils.ferEspais(10) +" Nom d'usuari:"+ Utils.ferEspais(2) +"</font></html>"), BorderLayout.LINE_START);
				loginText = new JTextField();
			  loginText.setColumns(10);
				jpAux1.add(loginText);
				jpAux1.setOpaque(false);

				JPanel jpAux2 = new JPanel(new FlowLayout());
				jpAux2.add(new JLabel("<html><font color='white'>"+ Utils.ferEspais(21) +" Correu:"+ Utils.ferEspais(2) +"</font></html>"), BorderLayout.LINE_START);
				emailText = new JTextField();
			  emailText.setColumns(10);
				jpAux2.add(emailText);
				jpAux2.setOpaque(false);

				JPanel jpAux3 = new JPanel(new FlowLayout());
				jpAux3.add(new JLabel("<html><font color='white'>"+ Utils.ferEspais(12) +" Contrasenya:"+ Utils.ferEspais(2) +"</font></html>"), BorderLayout.LINE_START);
				passText = new JPasswordField();
			  passText.setColumns(10);
				jpAux3.add(passText);
				jpAux3.setOpaque(false);

				JPanel jpAux5 = new JPanel(new FlowLayout());
				jpAux5.add(new JLabel("<html><font color='white'>Repeteix Contrasenya:"+ Utils.ferEspais(1) +"</font></html>"), BorderLayout.LINE_START);
				passConfirmText = new JPasswordField();
			  passConfirmText.setColumns(10);
				jpAux5.add(passConfirmText);
				jpAux5.setOpaque(false);


				JPanel jpAux4 = new JPanel(new GridLayout(3,3));
				jpAux4.add(margen[1]);
				jpAux4.add(margen[2]);
				jpAux4.add(margen[3]);
				jpAux4.add(margen[4]);
				botoRegistre = new JButton("Registra't");
				jpAux4.add(botoRegistre);
				jpAux4.add(margen[5]);
				jpAux4.add(margen[6]);
				jpAux4.add(margen[7]);
				jpAux4.add(margen[8]);
				jpAux4.setOpaque(false);


				panelPare.add(jpAuxT);
				panelPare.add(jpAuxLogo);
				panelPare.add(margen[0]);
				panelPare.add(jpAux1);
				panelPare.add(jpAux2);
				panelPare.add(jpAux3);
				panelPare.add(jpAux5);
				panelPare.add(jpAux4);


				//this.getContentPane().add(panelPare);
				this.setContentPane(panelPare);
				this.setBackground(Color.BLACK);
    			this.setTitle("AgeRoyale - C10");
    			this.setLocationRelativeTo(null);
				this.setResizable(false);
        		this.setSize(450, 800);
        		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //listeners dels jpanels
    }

		public void registerController(ActionListener controlador) {
			botoRegistre.addActionListener(controlador);
		}

		public String getName(){
				return loginText.getText();
		}

		public String getEmail(){
			return emailText.getText();
		}

		public String getPassword(){
			return passText.getText();
		}

		public String getRePass(){
			return passConfirmText.getText();
		}

}
