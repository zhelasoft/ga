package ga;

import java.lang.reflect.*;
import java.time.Duration;
import java.time.Instant;

public class Main {

	
	public static void main(String args[]) {
		
		SampleClass sc = new SampleClass();
		MethodProcesser mp;
		TheAlgorithm ta;
		
		Instant start = Instant.now();
		
		try {
			Class cls = sc.getClass();
			Method methlist[] = cls.getDeclaredMethods();
			for (int i = 0; i < methlist.length; i++) {
				Method m = methlist[i];
				mp = new MethodProcesser(m);
				if(mp.isQualified()) {
					ta = new TheAlgorithm(m, sc);
					for(int j = 0; j < Config.ROUND; j++) {
						ta.run(j);
					}
					
					// You may want to go from here
				}
			}
		} catch (Throwable e) {
			System.err.println(e);
		}
		
		Instant end = Instant.now();
		 Duration timeElapsed = Duration.between(start, end);
		 
		 System.out.println("The total elapsed time is = " + timeElapsed.getSeconds() + " seconds");
		
		
	}
}