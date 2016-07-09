import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Satisfiability {

	public static volatile boolean flag = false;
	public static volatile int theResult = -1;
	public static volatile int notFound = 0;
	
	public static int [] numbers;
	public static int limit;
	
	public static void main(String []args) {
		
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
			limit = Integer.valueOf(st1.nextToken());
			
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
		
		//int delimeter = limit * 100;

		double max = Math.pow(2, limit); 		
		int theChunk = (int) max / limit;
		
		//int controller = 0;		
		for(int i = 0; i < limit; i++) {
			//if((i+ 1) * (theChunk) < max) {
				ChunkThread ct = new ChunkThread(i * theChunk, (i+ 1) * (theChunk));
				ct.start();
			//} else {
				//ChunkThread ct = new ChunkThread(i * theChunk, (int) max);
				//ct.start();
			//}
		}		
		int border = limit;
		
		if(limit * theChunk < (int) max) {
			ChunkThread ct = new ChunkThread( limit * theChunk, (int) max + 1);
			ct.start();
			
			border++;			
		} 
		
		while(!flag && notFound < border) {
			//System.out.println(notFound + " vs " + delimeter + " Active threads: " + Thread.activeCount());		
		}

		if(theResult != -1) {
			System.out.println("Solution found: [" + theResult + "]: " + Integer.toBinaryString(theResult));						
		} else System.out.println("Solution not found");	
		
		System.exit(0);		
	}
}