package src.View;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import src.Controller.ControllerServer;
import src.Usuari;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

import static java.awt.font.TextAttribute.FONT;

public class ViewServer extends JFrame {

	private JButton btnStart;
	private JButton btnStop;
	private Object[][] data;
	private JComboBox petList;

	public ViewServer(){

	}
	public void initAll(){
		JPanel panelPare = new JPanel(new GridLayout(1, 2));
		//panelPare.setLayout(new FlowLayout());
		panelPare.setOpaque(false);

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
		btnStop = new JButton("Stop");
		panel.add(btnStop);
		btnStop.setFont(new Font("Arial", Font.BOLD , 40));
		btnStop.setForeground(Color.decode("#FFDC60"));
		btnStop.setBorderPainted(false);
		btnStop.setContentAreaFilled(false);
		btnStop.setAlignmentY(Component.CENTER_ALIGNMENT);
		btnStop.setFocusPainted(false);
		btnStop.setOpaque(false);
		panel.setOpaque(false);


		panelPare.add(Start);
		panelPare.add(panel);
		panelPare.setBackground(Color.decode("#85201F"));
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Control Servidor", panelPare);
		tabbedPane.addTab("Top Usuaris", makePanellEstadistiques());
		tabbedPane.addTab("Partides Jugades", makePanellPartides());



		this.setContentPane(tabbedPane);
		this.setBackground(Color.decode("#85201F"));
		this.setTitle("SERVER CONSOLE");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(700, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private JScrollPane makePanellEstadistiques() {

		String[] columnNames = {"Username", "% Victòries", "Temps mitg x Victoria"};

		JTable table = new JTable(data, columnNames){
			public boolean isCellEditable(int data, int columns){
				return false;
			}
		};
		JTableHeader header= table.getTableHeader();
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

	private JPanel makePanellPartides() {
		JPanel grafica = new JPanel();
		grafica.setLayout(null);
		String[] intervals = { "Setmana", "Mes", "Any"};
		petList = new JComboBox(intervals);
		petList.setSelectedIndex(0);
		petList.setBounds(200, 30, 200, 50);

		CategoryDataset dataset = createDataset();

		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);
		add(chartPanel);


		grafica.setOpaque(false);
		grafica.add(petList);

		return grafica;
	}
	private CategoryDataset createDataset() {

		var dataset = new DefaultCategoryDataset();
		dataset.setValue(46, "Gold medals", "USA");
		dataset.setValue(38, "Gold medals", "China");
		dataset.setValue(29, "Gold medals", "UK");
		dataset.setValue(22, "Gold medals", "Russia");
		dataset.setValue(13, "Gold medals", "South Korea");
		dataset.setValue(11, "Gold medals", "Germany");

		return dataset;
	}
	private JFreeChart createChart(CategoryDataset dataset) {

		JFreeChart barChart = ChartFactory.createBarChart(
				"Olympic gold medals in London",
				"",
				"Gold medals",
				dataset,
				PlotOrientation.VERTICAL,
				false, true, false);

		return barChart;
	}

	public void setTableRange(String range){

	}

  public void serverController(ActionListener controlador) {
	btnStart.addActionListener(controlador);
	btnStop.addActionListener(controlador);
	petList.addActionListener(controlador);

  }



}
