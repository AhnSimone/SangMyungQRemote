package client.remote;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Timer;
import java.util.TimerTask;

public class MouseMove2 {
	public static void main(String args[]) throws AWTException {
		
		long s = 0;
		long e = 0;
		
		final Timer t = new Timer();
		final TT tt = new TT(0, 0, 1280, 1024, 10000);
		
		final int repeatRate = 10;
		
		s = System.currentTimeMillis();
		t.scheduleAtFixedRate(tt, 0, repeatRate);
		
		while(!tt.done);
		e = System.currentTimeMillis();
		t.cancel();
		System.out.println("Total time: " + (float) (e - s)/1000);
	}
	private static class TT extends TimerTask {
		private final int x0;
		private final int y0;
		
		private final int dx;
		private final int dy;
		private final float ti;
		private float a;
		
		private final Robot r;
		public boolean done;
		private final long start;
		
		public TT(int x0, int y0, int x1, int y1, int time) throws AWTException {
		
			this.x0 = x0;
			this.y0 = y0;
			
			// pre-calc vars for run()
			dx = x1 - x0;
			dy = y1 - y0;
			ti = 1f/time;
			a = 0;
			
			r = new Robot();
			done = false;

			start = System.currentTimeMillis();
		}
		public void run() {
			a = (System.currentTimeMillis() - start)*ti;
			r.mouseMove(x0 + (int) (a*dx), y0 + (int) (a*dy));
			if(a >= 1) {
				done = true;
			}
		}
	}

}
