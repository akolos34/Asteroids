package util;

public class GameTimer {
	
		//number of nanoseconds in a second
		private final int NANOSECONDS = 1000000000;

		private boolean timerStarted; // is the timer running?
		private int fpsCap, fps;	  // the frames/sec and the max fps
		private long beginTime, timeDifference; // start time, time between start time and end time

		public GameTimer(int desiredFPS) { // constructor

			fpsCap = desiredFPS; // maximum fps is the desired frame rate
			fps = 0;
			timerStarted = false;
			beginTime = 0;
			timeDifference = 0;

		}

		//timer is re-started at the beginning of every loop cycle
		public void start() {

			timerStarted = true; // timer is running
			beginTime = System.nanoTime(); // get the beginning time

		}

		public void stop() {

			timerStarted = false; // timer is NOT running
			beginTime = 0; // reset start time to 0

		}

		public void syncTime() {

			if (timerStarted == true) { // if timer is running
				timeDifference = System.nanoTime() - beginTime; // get the difference in times
			}
			else {
				timeDifference = 0;
			}
		
			try {
				Thread.sleep(20); // nap		
			}
			catch (InterruptedException e) {
				System.out.println("Exception e");
			}

			timerStarted = false;
			beginTime = 0;
			timeDifference = 0;
			
		}

		public int getFPS() {

			return fps;

		}		

	

}
