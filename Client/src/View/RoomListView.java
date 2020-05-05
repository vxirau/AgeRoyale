package src.View;

import src.Controller.GameController;
import src.Controller.RoomsController;
import src.Partida;
import src.Utils;

import javax.swing.*;
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
	private JPanel[] partidesPubliques;
	private JPanel[] partidesPrivades;
	private JButton nova;
	private JToggleButton visibilitat;
	private JScrollPane scrollPubliques;
	private JScrollPane scrollPrivades;
	private boolean visible=false;
	private Object[] options = {"Entèsos"};

	private RoomsController roomsController;

	private void dividirPartides(){
		for(Partida p : allGames){
			if(p.isPublic()){
				pPubliques.add(p);
			}else{
				pPrivades.add(p);
			}
		}
	}

	public JPanel getJpPare(){
		return jpPare;
	}

	public RoomListView(RoomsController roomsController){
		this.roomsController = roomsController;
		roomsController.initMessage();
	}

	private void initAll(){
		dividirPartides();
		partidesPubliques = new JPanel[pPubliques.size()];
		partidesPrivades = new JPanel[pPrivades.size()];
		if((pPrivades.size() + pPubliques.size()) != allGames.size()){
			JOptionPane.showOptionDialog(new JFrame(), "Alguna cosa no ha funcionat","Alerta", JOptionPane.PLAIN_MESSAGE, JOptionPane.WARNING_MESSAGE, null,options,options[0]);
		}
		//setTitle("AGE ROYALE");
		//setLocationRelativeTo(null);
		//setResizable(false);

		initComponents();
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initComponents() {
		colocarPanel();
		colocarElements();
	}

	private void colocarPanel(){
		jpPare = new JPanel();
		jpPare.setLayout(null);
		jpPare.setOpaque(true);
		jpPartidesPubliques =new JPanel();
		jpPartidesPubliques.setOpaque(false);
		jpPartidesPubliques.setLayout(null);
		jpPartidesPrivades = new JPanel();
		jpPartidesPrivades.setOpaque(false);
		jpPartidesPrivades.setLayout(null);

		scrollPubliques = new JScrollPane();
		scrollPubliques.setBounds(0,200, 450, 520);
		scrollPubliques.setOpaque(false);
		scrollPrivades= new JScrollPane();
		scrollPrivades.setBounds(0,200, 450, 520);
		scrollPrivades.setOpaque(false);
		//this.getContentPane().add(jpPare);

	}

	private JPanel crearElement(Partida p, int total){
		JPanel element = new JPanel(new GridLayout(3, 1)) {
			protected void paintComponent(Graphics g) {
				ImageIcon elementButton = new ImageIcon(this.getClass().getResource("/resources/fonsElement.png"));
				//Icon iconElement = new ImageIcon(elementButton.getImage().getScaledInstance(400, 70, Image.SCALE_FAST));
				g.drawImage(elementButton.getImage(), 0, 0, null);
				super.paintComponent(g);
			}
		};
		element.setOpaque(false);
		element.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				RoomListView.this.setVisible(false);
				RoomsController.startGame(total,0, new Partida());
			}
		});

		JLabel nom = new JLabel();
		nom.setText("<html><font color='white'>" + Utils.ferEspais(28) + " Room Name: " + p.getName() + "</font></html>");
		nom.setForeground(Color.WHITE);
		nom.setFont(new Font("Helvetica", 0, 15));
		nom.setBounds(120, 180, 250, 15);
		element.add(nom);

		JLabel persones = new JLabel();
		persones.setText("<html><font color='white'> " + Utils.ferEspais(28) + " Total Connected: " + (0)+ "</font></html>");
		persones.setForeground(Color.WHITE);
		persones.setBounds(120, 200, 250, 15);
		persones.setFont(new Font("Helvetica", 0, 15));
		element.add(persones);

		JLabel create = new JLabel();
		create.setText("<html><font color='white'> " + Utils.ferEspais(28) + "  Host: " + p.getHost() +  "</font></html>");
		create.setForeground(Color.WHITE);
		create.setBounds(120, 220, 250, 15);
		create.setFont(new Font("Helvetica", 0, 15));
		element.add(create);

		element.setBounds(20, 20+(total*110), 410, 90);

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
			mostrar.setText("Mostrar solo privadas");
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
						scrollPrivades.setVisible(false);
						jpPartidesPrivades.setVisible(false);
						scrollPubliques.setVisible(true);
						jpPartidesPubliques.setVisible(true);
						show.setText("No");
					}else{
						visible = true;
						scrollPrivades.setVisible(true);
						jpPartidesPrivades.setVisible(true);
						scrollPubliques.setVisible(false);
						jpPartidesPubliques.setVisible(false);
						show.setText("Si");
					}
				}});

			showPrivate.add(show);
			showPrivate.setBounds(35, 160, 350, 50);
			jpPare.add(showPrivate);

			//------------------------------ELEMENT------------------------------
			for(int i=0; i<pPubliques.size() ;i++){
				partidesPubliques[i] = crearElement(pPubliques.get(i), i);
				jpPartidesPubliques.add(partidesPubliques[i]);
			}

			for(int i =0; i<pPrivades.size(); i++){
				partidesPrivades[i] = crearElement(pPrivades.get(i), i);
				jpPartidesPrivades.add(partidesPrivades[i]);
			}

			scrollPrivades.setViewportView(jpPartidesPrivades);
			scrollPubliques.setViewportView(jpPartidesPubliques);
			scrollPubliques.getViewport().setOpaque(false);
			scrollPrivades.getViewport().setOpaque(false);

			if(!visible){
				visible = false;
				scrollPrivades.setVisible(false);
				jpPartidesPrivades.setVisible(false);
				scrollPubliques.setVisible(true);
				jpPartidesPubliques.setVisible(true);
			}else{
				visible = true;
				scrollPrivades.setVisible(true);
				jpPartidesPrivades.setVisible(true);
				scrollPubliques.setVisible(false);
				jpPartidesPubliques.setVisible(false);
			}

			jpPare.add(scrollPrivades);
			jpPare.add(scrollPubliques);
			//jpPare.add(element);
		}

		//Fons fusta
		ImageIcon img = new ImageIcon(this.getClass().getResource("/resources/fondoMadera.png"));
		Icon icono = new ImageIcon(img.getImage().getScaledInstance(450, 700, Image.SCALE_DEFAULT));
		JLabel fondo = new JLabel();
		fondo.setIcon(icono);
		getLayeredPane().add(fondo, JLayeredPane.FRAME_CONTENT_LAYER);
		fondo.setBounds(0, 0, 450, 700);
		jpPare.add(fondo);
		//setSize(450, 800);
		//this.setContentPane(jpPare);
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
