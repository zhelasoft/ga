package ga;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.google.common.primitives.Primitives;
import io.jenetics.util.DoubleRange;
import static java.lang.Math.PI;
import static java.lang.Math.sin;
import static java.lang.Math.cos;

import io.jenetics.BitChromosome;
import io.jenetics.Optimize;

public class Main {
	
	private static double fitness(final double x) {
		return cos(0.5 + sin(x)) * cos(x);
	}
	
	public static void main(String args[]) {
		System.out.println(Runtime.class.getPackage().getImplementationVersion());
		SampleClass sc = new SampleClass();
		MethodProcesser mp;
		for(int i = 0; i < 10; i++) {
			System.out.println(BitChromosome.of(20, 0.5));
		}
		
//		try {
//			Class cls = sc.getClass();
//			Method methlist[] = cls.getDeclaredMethods();
//			for (int i = 0; i < methlist.length; i++) {
//				Method m = methlist[i];
//				mp = new MethodProcesser(m, sc);
//				if(mp.isQualified()) {
//					mp.populateData();
//				}
//			}
//		} catch (Throwable e) {
//			System.err.println(e);
//		}
		
	}
}