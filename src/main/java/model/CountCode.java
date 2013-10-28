package model;

public class CountCode extends Code{
	
	private long sum;
	
	public long getSum(){ return sum;}
	
	public Status run(Object obj)  throws Exception{
		Long value = (Long) obj;
		sum = 0;
		while(value > 0){
			sum += value--;
		}
		//System.out.print(String.format("Sum: %d\n", sum));
		return Status.SUCCESS;
	}
}
