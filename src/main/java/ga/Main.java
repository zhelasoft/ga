package ga;

import java.lang.reflect.*;

public class Main {

	
	public static void main(String args[]) {
		
		SampleClass sc = new SampleClass();
		MethodProcesser mp;
		TheAlgorithm ta;
		
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
		
	}
}