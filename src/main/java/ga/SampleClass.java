package ga;

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
	
	public boolean isTeenage(long age) throws SampleException {
		
		if(age > 120 || age < 1) {
			throw new SampleException("Invalid age number");
		}
		
		if(! (age > 12 && age < 20)) {
			throw new SampleException("Is not a teenage");
		}
		
		return true;
	}
	
	public boolean isYoung(char age) {
		return age == 'c';
	}
}
