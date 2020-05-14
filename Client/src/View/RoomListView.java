package src.View;

import src.Controller.GameController;
import src.Controller.RoomsController;
import src.Model.Database.DAO.partidaDAO;
import src.Model.Network.UserService;
import src.Partida;
import src.Usuari;
import src.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

public class RoomListView extends JFrame{

	private ArrayList<Partida> allGames;
	private ArrayList<Partida> pPrivades = new ArrayList<>();
	private ArrayList<Partida> pPubliques= new ArrayList<>();
	private JPanel jpPare;
	private JPanel jpPartidesPubliques;
	private JPanel jpPartidesPrivades;
	private JButton nova;
	private JToggleButton visibilitat;
	private JScrollPane scrollPubliquesF;
	private JScrollPane scrollPrivadesF;
	private boolean visible=false;
	private Usuari usuari;
	private Object[] options = {"Entèsos"};

	private RoomsController roomsController;

	private void dividirPartides(){
		for(Partida p : allGames){
			if(p.isPublic()){
				pPubliques.add(p);
			}else{
				if(isFriendGame(p)){
					pPrivades.add(p);
				}
			}
		}
	}

	private boolean isFriendGame(Partida game){
		return game.getHost().equals(this.usuari.getNickName());
	}

	public JPanel getJpPare(){
		return jpPare;
	}

	public RoomListView(RoomsController roomsController, Usuari user){
		this.roomsController = roomsController;
		this.usuari = user;
		roomsController.initMessage();
	}

	private void initAll(){
		dividirPartides();
		if((pPrivades.size() + pPubliques.size()) != allGames.size()){
			JOptionPane.showOptionDialog(new JFrame(), "Alguna cosa no ha funcionat","Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
		}
		initComponents();
	}

	private void initComponents() {
		colocarPanel();
		colocarElements();
	}

	private void colocarPanel(){
		this.removeAll();

		jpPare = new JPanel();
		jpPare.setLayout(null);
		jpPare.setOpaque(true);

		jpPartidesPubliques =new JPanel();
		jpPartidesPubliques.setLayout(new BoxLayout(jpPartidesPubliques, BoxLayout.Y_AXIS));
		jpPartidesPubliques.setOpaque(false);

		jpPartidesPrivades = new JPanel();
		jpPartidesPrivades.setLayout(new BoxLayout(jpPartidesPubliques, BoxLayout.Y_AXIS));
		jpPartidesPrivades.setOpaque(false);

		scrollPrivadesF = new JScrollPane();
		scrollPubliquesF = new JScrollPane();
		scrollPrivadesF.setBounds(0, 200, 450, 490);
		scrollPrivadesF.setEnabled(true);
		scrollPrivadesF.setOpaque(false);
		scrollPrivadesF.getViewport().setOpaque(false);

		scrollPubliquesF.setBounds(0, 200, 450, 490);
		scrollPubliquesF.setEnabled(true);
		scrollPubliquesF.setOpaque(false);
		scrollPubliquesF.getViewport().setOpaque(false);

	}

	private JPanel crearElement(Partida p, int total){
		JPanel element = new JPanel(new GridLayout(3, 1)) {
			protected void paintComponent(Graphics g) {
				ImageIcon elementButton = new ImageIcon(this.getClass().getResource("/resources/fonsElement.png"));
				g.drawImage(elementButton.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};

		element.setOpaque(false);
		element.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
		RoomListView.this.setVisible(false);
				System.out.println("Game pressed");
				try {
					RoomsController.startGame(total,0, p);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		});

		JLabel nom = new JLabel();
		nom.setText("<html><font color='white'>" + Utils.ferEspais(28) + " Room Name: " + p.getName() + "</font></html>");
		nom.setForeground(Color.WHITE);
		nom.setFont(new Font("Helvetica", 0, 15));
		nom.setPreferredSize(new Dimension(250,15));
		element.add(nom);

		JLabel persones = new JLabel();
		persones.setText("<html><font color='white'> " + Utils.ferEspais(28) + " Total Connected: " + (0) + "</font></html>");
		persones.setForeground(Color.WHITE);
		persones.setPreferredSize(new Dimension(250,15));
		persones.setFont(new Font("Helvetica", 0, 15));
		element.add(persones);

		JLabel create = new JLabel();
		create.setText("<html><font color='white'> " + Utils.ferEspais(28) + "  Host: " + p.getHost() +  "</font></html>");
		create.setForeground(Color.WHITE);
		create.setPreferredSize(new Dimension(250,15));
		create.setFont(new Font("Helvetica", 0, 15));
		element.add(create);

		element.setPreferredSize(new Dimension(430,100));
		element.setMaximumSize(new Dimension(430,100));
		element.setSize(430, 100);
		return element;
	}

	private void colocarElements(){
		nova = new JButton();
		nova.setText("Crear nova partida");
		nova.setBounds(20, 70, 410, 70);
		nova.setOpaque(false);
		nova.setHorizontalTextPosition(JButton.CENTER);
		nova.setVerticalTextPosition(JButton.CENTER);
		nova.setFont(new Font("Helvetica", Font.BOLD, 30));
		nova.setForeground(Color.WHITE);
		nova.setContentAreaFilled(false);
		nova.setBorderPainted(false);
		nova.addActionListener(roomsController.getActionListenerCreaPartida());
		ImageIcon fonsButton= new ImageIcon(this.getClass().getResource("/resources/newGameBanner.png"));
		Icon iconoButton = new ImageIcon(fonsButton.getImage().getScaledInstance(400, 70, Image.SCALE_FAST));
		nova.setIcon(iconoButton);
		jpPare.add(nova);

		if(allGames.size()>0) {
			JPanel showPrivate = new JPanel();
			JLabel mostrar = new JLabel();
			showPrivate.setOpaque(false);
			mostrar.setText("Mostrar només privades");
			mostrar.setForeground(Color.WHITE);
			mostrar.setFont(new Font("Helvetica", Font.BOLD, 20));
			mostrar.setBounds(35, 165, 250, 15);
			showPrivate.add(mostrar);
			JToggleButton show = new JToggleButton();
			show.setBounds(300, 165, 50, 15);
			show.setText("No");
			show.addChangeListener(new ChangeListener( ) {
				public void stateChanged(ChangeEvent ev) {
					if(visible){
						visible = false;
						getScrollPrivades().setVisible(false);
						jpPartidesPrivades.setVisible(false);
						getScrollPubliques().setVisible(true);
						jpPartidesPubliques.setVisible(true);
						show.setText("No");
					}else{
						visible = true;
						getScrollPrivades().setVisible(true);
						jpPartidesPrivades.setVisible(true);
						getScrollPubliques().setVisible(false);
						jpPartidesPubliques.setVisible(false);
						show.setText("Si");
					}
				}});

			showPrivate.add(show);
			showPrivate.setBounds(35, 160, 350, 50);
			jpPare.add(showPrivate);

			//------------------------------ELEMENT------------------------------
			for(int i=0; i<pPubliques.size() ;i++){
				jpPartidesPubliques.add(crearElement(pPubliques.get(i), i));
				jpPartidesPubliques.add(addSeparator());
			}

			for(int i =0; i<pPrivades.size(); i++){
				jpPartidesPrivades.add(crearElement(pPrivades.get(i), i));
				jpPartidesPrivades.add(addSeparator());
			}


			if(!visible){
				visible = false;
				scrollPrivadesF.setVisible(false);
				scrollPubliquesF.setVisible(true);
			}else{
				visible = true;
				scrollPrivadesF.setVisible(true);
				scrollPubliquesF.setVisible(false);
			}

			scrollPrivadesF.setViewportView(jpPartidesPrivades);
			scrollPubliquesF.setViewportView(jpPartidesPubliques);

			jpPare.add(scrollPrivadesF);
			jpPare.add(scrollPubliquesF);
		}

		//Fons fusta
		ImageIcon img = new ImageIcon(this.getClass().getResource("/resources/fondoMadera.png"));
		Icon icono = new ImageIcon(img.getImage().getScaledInstance(450, 700, Image.SCALE_DEFAULT));
		JLabel fondo = new JLabel();
		fondo.setIcon(icono);
		getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
		fondo.setBounds(0, 0, 450, 700);
		jpPare.add(fondo);

		revalidate();
		repaint();
	}

	public JScrollPane getScrollPubliques(){
		return this.scrollPubliquesF;
	}


	public JScrollPane getScrollPrivades(){
		return this.scrollPrivadesF;
	}

	public JPanel addSeparator(){
		JPanel separator = new JPanel();
		separator.setPreferredSize(new Dimension(430, 10));
		separator.setOpaque(false);
		separator.setMaximumSize(new Dimension(430,10));
		separator.setSize(430, 10);
		return separator;
	}

	public void setAllGames(ArrayList<Partida> partides){
		if(partides!=null){
			allGames = partides;
			initAll();
		}else{
			JOptionPane.showOptionDialog(new JFrame(), "LOKO HI HA QUELCOM MALAMENT" , "Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
		}
	}

}
