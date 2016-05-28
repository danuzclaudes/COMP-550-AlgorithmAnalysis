package background-timer-toy;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimerWidget extends JPanel implements ActionListener {

	private double elapsed;
	private JButton start_stop;
	private JLabel elapsed_display;
	private BackgroundTimer background_timer;
	
	public TimerWidget() {
		elapsed = 0.0;

		elapsed_display = new JLabel("XXX");
		elapsed_display.setPreferredSize(new Dimension(45,10));

		start_stop = new JButton("Start");
		start_stop.setActionCommand("start");
		start_stop.addActionListener(this);
		
		JButton reset = new JButton("Reset");
		reset.setActionCommand("reset");
		reset.addActionListener(this);
		
		add(elapsed_display);
		add(start_stop);
		add(reset);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("start")) {
			background_timer = new BackgroundTimer(this);
			background_timer.start();
			start_stop.setText("Stop");
			start_stop.setActionCommand("stop");
		} else if (cmd.equals("stop")) {
			background_timer.halt();
			try {
				background_timer.join();
			} catch (InterruptedException e1) {
			}
			start_stop.setText("Start");
			start_stop.setActionCommand("start");
		} else if (cmd.equals("reset")) {
			elapsed = 0.0;
			elapsed_display.setText("0.0");
		}
	}
	
	public void updateElapsed(double delta) {
		elapsed += delta;
		elapsed_display.setText(String.format("%5.2f", elapsed));
	}
}

