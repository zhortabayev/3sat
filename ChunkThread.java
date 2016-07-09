
public class ChunkThread extends Thread {

	int start, end;

	public static boolean isInterrupted = false;	
	
	public ChunkThread(int start, int end) {
		this.start = start;
		this.end = end;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub		
		execute();	        	        
	}
	
	public void execute() {
		
		int counter = start, controller, negativeCounter;		
		char [] assignedBool = new char[Satisfiability.limit];

		while(counter < end && !isInterrupted) {			
			controller = 0;			
			assignedBool = String.format("%" + Satisfiability.limit + "s", Integer.toBinaryString(counter)).replace(" ", "0").toCharArray();
			negativeCounter = 0;

			for(int i = 0; i < Satisfiability.numbers.length; i++) {				
	
				int temp = Satisfiability.numbers[i];
				Boolean cond = null;
				
				if(temp < 0) {
					temp = Math.abs(temp);
					cond = !charToBool(assignedBool[temp - 1]);
				} else cond = charToBool(assignedBool[temp - 1]);

				if(cond == true) {
					i = (i/3 + 1) * 3 - 1;
					negativeCounter = 0;
					controller = i;
				} else negativeCounter++; 
				
				if(negativeCounter == 3) break; 				
			}
			
			if(controller >= Satisfiability.numbers.length - 1) {
				Satisfiability.theResult = counter;
				Satisfiability.flag = true;	
				isInterrupted = true;
				break;				
			} 			
			counter++;		
		}				
		//if(counter == end + 1)
		Satisfiability.notFound++;
	}
	
	private boolean charToBool(char c) {	    
		if(c == '1')
			return true;
		return false;
	}
}
