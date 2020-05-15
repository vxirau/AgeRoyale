package src.View;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.Axis;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import src.Partida;
import src.Usuari;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ViewServer extends JFrame {

	private JButton btnStart;
	private JButton btnStop;
	private Object[][] data;
	private JComboBox selector;
	private JTabbedPane tabbedPane;
	private JToggleButton graphType;
	private boolean tipoh=true;
	private ArrayList<Partida> games;
	private JLabel textGraph;
	private boolean hiHaDades=false;

	public ViewServer(){

	}
	public void initAll(){
		JPanel panelPare = new JPanel(new GridLayout(1, 2));
		panelPare.setOpaque(false);
		String[] intervals = { "Setmana", "Mes", "Any"};
		selector = new JComboBox(intervals);
		selector.setBounds(330, 0, 200, 50);
		graphType = new JToggleButton();
		textGraph = new JLabel("Canvia tipus de gràfica:");
		textGraph.setBounds(15, 17, 190, 16);
		textGraph.setFont(new Font("Arial", Font.BOLD, 14));
		textGraph.setForeground(Color.decode("#FFDC60"));
		graphType.setBounds(180, 13, 140, 25);
		graphType.setText("Gràfic de Línies");
		graphType.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(tipoh){
					tipoh = false;
					graphType.setText("Gràfic de Línies");
					tabbedPane.setComponentAt(2, makeLinePartides(selector.getSelectedIndex()));
					tabbedPane.setSelectedIndex(2);
				}else{
					tipoh = true;
					graphType.setText("Gràfic de Barres");
					getTabbedPane().setComponentAt(2, makePanellPartides(selector.getSelectedIndex()));
					getTabbedPane().setSelectedIndex(2);
				}
			}
		});


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
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Control Servidor", panelPare);
		tabbedPane.addTab("Top Usuaris", makePanellEstadistiques());
		if(tipoh){
			tabbedPane.addTab("Partides Jugades", makeLinePartides(selector.getSelectedIndex()));
		}else{
			tabbedPane.addTab("Partides Jugades", makePanellPartides(selector.getSelectedIndex()));
		}
		graphType.setSelected(tipoh);



		this.setContentPane(tabbedPane);
		this.setBackground(Color.decode("#85201F"));
		this.setTitle("SERVER CONSOLE");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(700, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	public JPanel makeLinePartides(int selectedIndex) {
		JPanel lineGraph = new JPanel();
		lineGraph.setLayout(null);
		lineGraph.setOpaque(false);
		selector.setSelectedIndex(selectedIndex);

		//----------------------------------------------------------------------------------------------------
		XYDataset dataset = createXYDataset(selectedIndex);

		if(hiHaDades){
			JFreeChart chart = createLineChart(dataset, selectedIndex);

			ChartPanel chartPanel = new ChartPanel(chart);

			XYPlot cplot = (XYPlot)chart.getPlot();
			cplot.setBackgroundPaint(Color.decode("#8C1018"));
			cplot.setDomainGridlinePaint(Color.decode("#FFFDFF"));
			cplot.setRangeGridlinePaint(Color.decode("#FFFDFF"));
			chart.getTitle().setPaint(Color.decode("#FFDC60"));
			chart.setBorderVisible(false);

			Stroke solid = new BasicStroke(2);

			Axis domainAxis = ((XYPlot)chart.getPlot()).getDomainAxis();
			domainAxis.setAxisLinePaint(Color.decode("#FFDC60"));
			domainAxis.setLabelPaint(Color.decode("#FFDC60"));
			domainAxis.setTickLabelPaint(Color.decode("#FFDC60"));
			domainAxis.setAxisLineStroke(solid);

			Axis valueAxis = ((XYPlot)chart.getPlot()).getRangeAxis();
			valueAxis.setAxisLinePaint(Color.decode("#FFDC60"));
			valueAxis.setLabelPaint(Color.decode("#FFDC60"));
			valueAxis.setTickLabelPaint(Color.decode("#FFDC60"));
			valueAxis.setLabelFont(new Font("Arial", Font.PLAIN , 10));
			valueAxis.setAxisLineStroke(solid);

			((XYPlot)chart.getPlot()).setOutlinePaint(Color.decode("#FFDC60"));
			((XYPlot)chart.getPlot()).setBackgroundImageAlpha((float)0.0);
//		XYLineAndShapeRenderer r = (XYLineAndShapeRenderer)chart.getCategoryPlot().getRenderer();
			//r.setSeriesPaint(0, Color.decode("#FFDC60"));



			chartPanel.setBounds(0, 50, 700, 400);
			lineGraph.add(chartPanel);
		}else{
			JLabel j = new JLabel("No hi ha dades en aquestes dates");
			j.setFont(new Font("Arial", Font.BOLD, 30));
			j.setBounds(100, 200, 600, 40);
			j.setForeground(Color.decode("#FFDC60"));
			lineGraph.add(j);
		}
		//----------------------------------------------------------------------------------------------------
		lineGraph.add(textGraph);
		lineGraph.add(graphType);
		lineGraph.add(selector);
		return lineGraph;
	}
	private XYDataset createXYDataset(int criteri){
		int days = 0, total, ok=0;
		var series = new XYSeries("Partides x Dia");
		if(criteri == 0){
			days = 7;
		}else if(criteri == 1){
			days = 30;
		}else if(criteri == 2){
			days = 365;
		}

		for(int i=days-1; i>=0; i--){
			String today = LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			total = totalGamesToday(today);
			if(total == 0){
				ok++;
			}
			series.add(i, totalGamesToday(today));
		}

		if(ok==days){
			hiHaDades = false;
		}

		var dataset = new XYSeriesCollection();
		dataset.addSeries(series);
		return dataset;
	}
	private JFreeChart createLineChart(XYDataset dataset, int period) {
		String titol = "";
		if(period == 0){
			titol += "la última: Setmana";
		}else if(period == 1){
			titol += "l'últim: Mes";
		}else if(period == 2){
			titol += "l'últim: Any";
		}

		JFreeChart chart = ChartFactory.createXYLineChart( "Partides Jugades en " + titol,"", "",
				dataset,
				PlotOrientation.VERTICAL, false, true, false
		);

		chart.setBackgroundPaint(Color.decode("#85201F"));


		XYPlot plot = chart.getXYPlot();

		var renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.decode("#FFDC60"));
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));

		plot.setRenderer(renderer);
		plot.setBackgroundPaint(Color.white);

		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		chart.setTitle(new TextTitle("Partides Jugades en " + titol,new Font("Serif", java.awt.Font.BOLD, 18)));

		return chart;
	}
	private JScrollPane makePanellEstadistiques() {

		String[] columnNames = {"Username", "% Victòries", "Temps mitg x Victoria"};

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
	public JPanel makePanellPartides(int index) {
		JPanel grafica = new JPanel();
		grafica.setLayout(null);
		grafica.setOpaque(false);

		selector.setSelectedIndex(index);


		CategoryDataset dataset = createDataset(selector.getSelectedItem().toString());
		if(hiHaDades){
			JFreeChart chart = createChart(dataset, selector.getSelectedItem().toString());
			ChartPanel chartPanel = new ChartPanel(chart);

			CategoryPlot cplot = (CategoryPlot)chart.getPlot();
			cplot.setBackgroundPaint(Color.decode("#8C1018"));
			chart.getTitle().setPaint(Color.decode("#FFDC60"));
			chart.setBorderVisible(false);


			Stroke solid = new BasicStroke(2);

			CategoryAxis domainAxis = cplot.getDomainAxis();
			domainAxis.setAxisLinePaint(Color.decode("#FFDC60"));
			domainAxis.setLabelPaint(Color.decode("#FFDC60"));
			domainAxis.setTickLabelPaint(Color.decode("#FFDC60"));
			domainAxis.setAxisLineStroke(solid);

			ValueAxis valueAxis = cplot.getRangeAxis();
			valueAxis.setAxisLinePaint(Color.decode("#FFDC60"));
			valueAxis.setLabelPaint(Color.decode("#FFDC60"));
			valueAxis.setTickLabelPaint(Color.decode("#FFDC60"));
			valueAxis.setLabelFont(new Font("Arial", Font.PLAIN , 10));
			valueAxis.setAxisLineStroke(solid);

			cplot.setOutlinePaint(Color.decode("#FFDC60"));
			cplot.setBackgroundImageAlpha((float)0.0);
			((BarRenderer)cplot.getRenderer()).setBarPainter(new StandardBarPainter());
			BarRenderer r = (BarRenderer)chart.getCategoryPlot().getRenderer();
			r.setSeriesPaint(0, Color.decode("#FFDC60"));


			chartPanel.setBounds(0, 50, 700, 400);
			grafica.add(chartPanel);
		}else{
			JLabel j = new JLabel("No hi ha dades en aquestes dates");
			j.setFont(new Font("Arial", Font.BOLD, 30));
			j.setBounds(100, 200, 600, 40);
			j.setForeground(Color.decode("#FFDC60"));
			grafica.add(j);
		}

		grafica.add(graphType);
		grafica.add(textGraph);
		grafica.add(selector);

		return grafica;
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
	private CategoryDataset createDataset(String criteri) {
		int days = 0, total, ok=0;
		var dataset = new DefaultCategoryDataset();
		if(criteri.equals("Setmana")){
			days = 7;
		}else if(criteri.equals("Mes")){
			days = 30;
		}else if(criteri.equals("Any")){
			days = 365;
		}
		for(int i=0; i<days; i++){
			String today = LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			total = totalGamesToday(today);
			if(total == 0){
				ok++;
			}
			dataset.setValue(totalGamesToday(today), "", today);
		}

		if(ok==days){
			hiHaDades = false;
		}

		return dataset;
	}
	private JFreeChart createChart(CategoryDataset dataset, String period) {
		String titol = "";
		if(period.equals("Setmana")){
			titol += "la última: Setmana";
		}else{
			titol += "l'últim: " + period;
		}

		JFreeChart barChart = ChartFactory.createBarChart("Partides Jugades en " + titol, "", "", dataset,
				PlotOrientation.VERTICAL,
				false, true, false);

		barChart.setBackgroundPaint(Color.decode("#8C1018"));
		return barChart;
	}
	public void setDades(boolean v){
		this.hiHaDades = v;
	}
	public JTabbedPane getTabbedPane(){
		return this.tabbedPane;
	}
	public boolean getTipo(){
		return this.tipoh;
	}
	public void serverController(ActionListener controlador) {
		btnStart.addActionListener(controlador);
		btnStop.addActionListener(controlador);
		selector.addActionListener(controlador);

	}
	public void refresh(JTabbedPane p){
		this.tabbedPane = p;
		revalidate();
		repaint();
	}


}
