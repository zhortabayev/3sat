import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Satisfiability {

	public static volatile boolean flag = false;
	public static volatile int theResult = -1;
		
	public static int [] numbers;
	public static int upperBound;
	
	public static void main(String []args) {
		
		long startTime = System.currentTimeMillis();

		if(args.length != 1) {
			System.out.println("Usage: <input file>");
			return;
			}
		
		String input = args[0];		
		int clausesNumber = 0;
		
		try {
			FileReader fr = new FileReader(input);
			BufferedReader br = new BufferedReader(fr);
			
			String firstLine;
			String str = "";
			
			firstLine = br.readLine();			
			StringTokenizer st1 = new StringTokenizer(firstLine, " ");
			
			clausesNumber = Integer.valueOf(st1.nextToken());
			upperBound = Integer.valueOf(st1.nextToken());
			
			numbers = new int[clausesNumber * 3]; 
			
			int counter = 0;
			while((str = br.readLine()) != null) {
				StringTokenizer st2 = new StringTokenizer(str, " ");				
				
				int temp = 0;
				while(temp < 3) {
					numbers[counter] = Integer.valueOf(st2.nextToken());				
					counter++;
					temp++;
				}
			}			
			br.close();
			
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException io) {
			io.printStackTrace();
		}
		
		int max = (int) Math.pow(2, upperBound); 		
		
		int chunker = 1000;
		int theChunk = (int) max / chunker;

		for(int i = 0; i < chunker - 2; i++) {
				ChunkThread ct = new ChunkThread(i * theChunk, (i+ 1) * (theChunk));
				ct.start();
		}			
		ChunkThread ct = new ChunkThread( (chunker - 1) * theChunk, (int) max + 1);
		ct.start();
		
		System.out.println("Threads are working...");
		while(!flag && Thread.activeCount() != 1) {
			/*wait while working*/
		}		
		if(theResult != -1) {
			System.out.println("Solution found: [" + theResult + "]: " + Integer.toBinaryString(theResult));						
		} else System.out.println("Solution not found");	
		
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println("The time spent is: "  + elapsedTime);
	    
		System.exit(0);		
	}
}