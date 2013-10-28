package model;

public class SleepCode extends Code{

	public Status run(Object obj) throws Exception{
		try {
		Thread.sleep((Long) obj);
			//System.out.println("["+((Long) obj)+"]");
		} catch (InterruptedException e) {
			return Status.FAIL;
		}
		return Status.SUCCESS;
	}
}
