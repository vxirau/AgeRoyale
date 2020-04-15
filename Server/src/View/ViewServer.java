package src.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewServer extends JFrame {

	private JButton btnStart;
	private JButton btnStop;

	public ViewServer(){
		JPanel panelPare = new JPanel();
		panelPare.setLayout(new FlowLayout());
		panelPare.setOpaque(false);

		JPanel Start = new JPanel(new FlowLayout());
		btnStart = new JButton("Start");
		Start.add(btnStart);
		Start.setOpaque(false);

		JPanel panel = new JPanel(new FlowLayout());
		btnStop = new JButton("Stop");
		panel.add(btnStop);
		panel.setOpaque(false);

		panelPare.add(Start);
		panelPare.add(panel);

		this.setContentPane(panelPare);
		this.setTitle("SERVER CONSOLE");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setSize(400, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
  public void serverController(ActionListener controlador) {
    btnStart.addActionListener(controlador);
    btnStop.addActionListener(controlador);
  }



}
