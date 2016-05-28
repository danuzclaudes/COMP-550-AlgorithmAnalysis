package background-timer-toy;

import javax.swing.SwingUtilities;

public class BackgroundTimer extends Thread {
	private boolean done;
	private TimerWidget tw;
	
	public BackgroundTimer(TimerWidget tw) {
		this.tw = tw;
		done = false;
	}

	public void halt() {
		done = true;
	}
	
	public void run() {
		long start = System.currentTimeMillis();
		
		while (!done) {
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			long end = System.currentTimeMillis();
			
			// tw.updateElapsed((double) (end-start) / 1000.0);
			double delta = ((double) (end-start) / 1000.0);
			SwingUtilities.invokeLater(new WidgetUpdater(tw, delta));
			
			start = end;
		}
		long end = System.currentTimeMillis();
		
		// tw.updateElapsed((double) (end-start) / 1000.0);
		double delta = ((double) (end-start) / 1000.0);
		SwingUtilities.invokeLater(new WidgetUpdater(tw, delta));
		
		start = end;
	}
}

// class exists only to be a helper for BgTimer
class WidgetUpdater implements Runnable {

	private TimerWidget tw;
	private double delta;
	
	public WidgetUpdater(TimerWidget tw, double delta) {
		this.tw = tw;
		this.delta = delta;
	}
	
	@Override
	public void run() {
		tw.updateElapsed(delta);
	}
}
