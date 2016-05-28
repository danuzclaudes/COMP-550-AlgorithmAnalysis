package background-timer-toy;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame main_frame = new JFrame();
		main_frame.setTitle("Timer Skeleton");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		TimerWidget timer = new TimerWidget();
		main_frame.setContentPane(timer);

		main_frame.pack();
		main_frame.setVisible(true);
	}

}
