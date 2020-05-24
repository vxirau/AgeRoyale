package src.View;

import src.Partida;
import src.Usuari;

import javax.swing.*;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ViewServer extends JFrame {

	private JButton btnStart;
	private Object[][] data;
	private JComboBox selector;
	private JTabbedPane tabbedPane;
	private boolean tipoh=true;
	private ArrayList<Partida> games;
	private boolean hiHaDades=false;

	public ViewServer(){

	}

	public void initAll(){
		JPanel panelPare = new JPanel(new GridLayout(1, 1));
		panelPare.setOpaque(false);
		String[] intervals = { "Setmana", "Mes", "Any"};
		selector = new JComboBox(intervals);
		selector.setBounds(550, 0, 130, 50);


		JPanel Start = new JPanel( new BorderLayout());
		btnStart = new JButton("Start");
		Start.add(btnStart);
		btnStart.setFont(new Font("Arial", Font.BOLD , 40));
		btnStart.setForeground(Color.decode("#FFDC60"));
		btnStart.setAlignmentY(Component.CENTER_ALIGNMENT);
		btnStart.setBorderPainted(false);
		btnStart.setContentAreaFilled(false);
		btnStart.setFocusPainted(false);
		btnStart.setOpaque(false);
		Start.setOpaque(false);

		JPanel panel = new JPanel(new BorderLayout());
		/*btnStop = new JButton("Stop");
		panel.add(btnStop);
		btnStop.setFont(new Font("Arial", Font.BOLD , 40));
		btnStop.setForeground(Color.decode("#FFDC60"));
		btnStop.setBorderPainted(false);
		btnStop.setContentAreaFilled(false);
		btnStop.setAlignmentY(Component.CENTER_ALIGNMENT);
		btnStop.setFocusPainted(false);
		btnStop.setOpaque(false);*/
		panel.setOpaque(false);


		panelPare.add(Start);
		//panelPare.add(panel);

		panelPare.setBackground(Color.decode("#85201F"));
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Control Servidor", panelPare);
		tabbedPane.addTab("Top Usuaris", makePanellEstadistiques());
		tabbedPane.addTab("Partides Jugades", makeGraph(selector.getSelectedIndex()));

		this.setContentPane(tabbedPane);
		this.setBackground(Color.decode("#85201F"));
		this.setTitle("SERVER CONSOLE");
		this.setResizable(false);
		this.setSize(700, 500);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - this.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - this.getHeight()) / 2);
		this.setLocation(x, y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public JPanel makeGraph(int selectedIndex) {
		GraphPanel j = new GraphPanel(selectedIndex, this.games);
		selector.setSelectedIndex(selectedIndex);
		j.add(selector);
		return j;
	}


	private JScrollPane makePanellEstadistiques() {

		String[] columnNames = {"Username", "% Victòries", "Temps mig x Victoria"};

		JTable table = new JTable(data, columnNames){
			public boolean isCellEditable(int data, int columns){
				return false;
			}
		};
		JTableHeader header= table.getTableHeader();
		((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
				.setHorizontalAlignment(JLabel.CENTER);
		header.setBackground(Color.decode("#FFDC60"));
		header.setForeground(Color.BLACK);
		header.setFont(new Font("Arial", Font.BOLD , 20));
		table.getTableHeader().setReorderingAllowed(false);
		table.setBackground(Color.decode("#85201F"));
		table.setForeground(Color.decode("#FFDC60"));
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment( SwingConstants.CENTER );
		table.setSelectionBackground(Color.WHITE.darker());
		table.setSelectionForeground(Color.decode("#FFDC60"));

		table.setRowHeight(23);
		table.setFont(new Font("Arial", Font.BOLD , 15));

		table.setSize(700, 300);
		table.setFillsViewportHeight(true);
		table.setBorder(null);


		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setSize(700, 300);
		scrollPane.setBackground(Color.decode("#85201F"));
		scrollPane.setBorder(null);
		return scrollPane;
	}
	public static float round(float d, int decimalPlace) {
		BigDecimal bd = new BigDecimal(Float.toString(d));
		bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
		return bd.floatValue();
	}
	public void setAllGames(ArrayList<Partida> all){
		this.games = all;
	}
	public void setTableContents(ArrayList<Usuari> all){
		Object[][] data = new Object[all.size()][3];
		for(int i=0; i<all.size() ;i++){
			data[i][0] = all.get(i).getNickName();
			if(all.get(i).getStats().getTotalVictories()==0){
				data[i][1] = "0 %";
			}else{
				data[i][1] = round((((float)all.get(i).getStats().getTotalVictories() / all.get(i).getStats().getTotalPartides()))*100, 2) + " %";
			}
			data[i][2] = all.get(i).getStats().getAvgDurationVictories();
		}
		this.data = data;
	}
	public void setDades(boolean v){
		this.hiHaDades = v;
	}
	public JTabbedPane getTabbedPane(){
		return this.tabbedPane;
	}
	public void serverController(ActionListener controlador) {
		btnStart.addActionListener(controlador);
		//btnStop.addActionListener(controlador);
		selector.addActionListener(controlador);

	}
	public void refresh(JTabbedPane p){
		this.tabbedPane = p;
		revalidate();
		repaint();
	}


}
