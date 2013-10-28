package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class CountCodeTest {

	@Test
	public void testRun() {
		Code sleep = new SleepCode();
		long begin = 0, end = 0, interval;
		for(interval = 10; interval < 100; interval+=10){
			begin = System.nanoTime();
			try {
				sleep.run(interval);
			} catch (Exception e) {
				fail("Exception while sleeping");
			}
			end = System.nanoTime();
		}
		assertTrue("Sleep not working properly!", (end-begin-interval) > interval*0.05);
	}

}
