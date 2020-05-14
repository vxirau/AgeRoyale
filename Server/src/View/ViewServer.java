package src.View;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.ui.TextAnchor;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import src.Controller.ControllerServer;
import src.Partida;
import src.Usuari;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static java.awt.font.TextAttribute.FONT;

public class ViewServer extends JFrame {

	private JButton btnStart;
	private JButton btnStop;
	private Object[][] data;
	private JComboBox selector;
	private ArrayList<Partida> games;

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

	private JPanel makePanellPartides() {
		JPanel grafica = new JPanel();
		grafica.setLayout(null);
		grafica.setOpaque(false);
		String[] intervals = { "Setmana", "Mes", "Any"};
		selector = new JComboBox(intervals);
		selector.setSelectedIndex(0);
		selector.setBounds(230, 0, 200, 50);

		CategoryDataset dataset = createDataset(selector.getSelectedItem().toString());

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



		chart.setBackgroundPaint(Color.decode("#00FFFFFF"));
		chart.setBackgroundImageAlpha((float) 0.0);
		chartPanel.setBackground(Color.decode("#00FFFFFF"));
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBounds(0, 50, 700, 400);
		grafica.add(chartPanel);


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
		for(int i=days-1; i>=0; i--){
			String today = LocalDate.now().minusDays(i).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
			total = totalGamesToday(today);
			if(total == 0){
				ok++;
			}
			dataset.setValue(totalGamesToday(today), "", today);
		}

		if(ok==days){
			System.out.println("No games in these dates");
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

	public void setTableRange(String range){

	}

  public void serverController(ActionListener controlador) {
	btnStart.addActionListener(controlador);
	btnStop.addActionListener(controlador);
	selector.addActionListener(controlador);

  }



}
