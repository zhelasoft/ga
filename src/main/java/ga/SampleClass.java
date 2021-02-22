package ga;

import ga.exception.CountableException;
import ga.exception.UncountableException;

public class SampleClass {
	
//	private Object f1(Object p, int x) throws NullPointerException {
//		if (p == null)
//			throw new NullPointerException();  
//		return null;
//	}
//	
//	public int f2(Object p, int x) throws NullPointerException {
//		if (p == null)
//			throw new NullPointerException();
//		return x;
//	}
//	
//	public int f3(int p, int x) throws NullPointerException {
//		if (p == 0)
//			throw new NullPointerException();
//		return x;
//	}
//	
//	public int add(int a, int b)
//    {
//       return a + b;
//    }
//	
//	public double multiply(double x, double y)
//    {
//       return x * y;
//    }
//	
//	public char firstLetter(char letter) {
//		return letter;
//	}
	
//	public boolean isTeenage(long age) throws SampleException {
//		
//		if(age > 120 || age < 1) {
//			throw new SampleException("Invalid age number");
//		}
//		
//		if(! (age > 12 && age < 20)) {
//			throw new SampleException("Is not a teenage");
//		}
//		
//		return true;
//	}
	
	public boolean isQualifiedForRetirement(long age, long yearsOfService) throws UncountableException, CountableException {
		
		if(age < 1 || age > 120 || yearsOfService < 1 || yearsOfService > 60 || age < yearsOfService) {
			throw new UncountableException("Invalid number range!");
		}
		
		if(yearsOfService < 15) {
			throw new CountableException("Not enough years of service");
		}
		
		if(age < 60) {
			throw new CountableException("The person is young, therefore not qualified for retirement");
		}
		
		
		return true;
	}
	
//	public boolean isYoung(char age) {
//		return age == 'c';
//	}
}
