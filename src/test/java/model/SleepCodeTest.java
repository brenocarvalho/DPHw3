package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class SleepCodeTest {

	@Test
	public void testRun() {
		CountCode count = new CountCode();
		//long begin = 0, end = 0;
		long interval;
		for(interval = 10; interval < 1000; interval+=10){
			//begin = System.nanoTime();
			try {
				count.run(interval);
				assertTrue("Error with the sumation", count.getSum() == (interval+1)*(interval/2));
			} catch (Exception e) {
				fail("Exception while sleeping");
			}
		}
	}

}
