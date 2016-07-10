public class ChunkThread extends Thread {
	
	int start, end;	
	public static boolean isInterrupted = false;		
	int [] numbers;
	
	public ChunkThread(int start, int end) {
		this.start = start;
		this.end = end;
		numbers = Satisfiability.numbers;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub	
		execute();		
	}
	
	public void execute() {
		
		int counter = start;
		
		int controller, negativeCounter;		
		//char [] assignedBool = new char[Satisfiability.upperBound];
		String str;
		int rightPosition;
		
		while(counter < end && !isInterrupted) {
			
			controller = 0;			
			//assignedBool = String.format("%" + Satisfiability.upperBound + "s", Integer.toBinaryString(counter)).replace(" ", "0").toCharArray();
			str = Integer.toBinaryString(counter);	
			negativeCounter = 0;
			rightPosition = Satisfiability.upperBound - str.length();

			for(int i = 0; i < numbers.length; i++) {				
		
				int temp = numbers[i];
				Boolean cond = null;
				
				if(Math.abs(temp) - 1 < rightPosition) {
					cond = false;					
				} 
				else if(temp < 0) {
					temp = Math.abs(temp);
					cond = !isTrue(str, temp - rightPosition - 1);
				} else cond = isTrue(str, temp - rightPosition - 1);
				
				if(cond == true) {	
					i = (i/3 + 1) * 3 - 1;
					negativeCounter = 0;
					controller = i;
				} else negativeCounter++; 
				
				if(negativeCounter == 3) break; 							
			}			
				if(controller >= numbers.length - 1) {
					Satisfiability.theResult = counter;
					Satisfiability.flag = true;
					isInterrupted = true;
					break;
				} 		
				counter++;
			}	
	}
	/*
	private boolean charToBool(char c) {	    
		if(c == '1')
			return true;
		return false;
	}
	*/
	private boolean isTrue(String s, int position) {	    
		if( s.charAt(position)== '1')
			return true;
		return false;
	}
}
