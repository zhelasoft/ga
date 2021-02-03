package ga;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class MethodProcesser {
	
	private Method currentMethod;
	private Object obj;
	private List paramList;
	private GenerateData gd;
	
	MethodProcesser(Method m, Object obj) {
		this.currentMethod = m;
		this.paramList = new ArrayList();
		this.gd = new GenerateData();
		this.obj = obj;
	}
	
	private boolean isPublic() {
		return Modifier.isPublic(this.currentMethod.getModifiers());
	}
	
	private boolean isPrimitive() {
		return this.currentMethod.getReturnType().isPrimitive();
	}
	
	private boolean allMethodsArePrimitive() {
		
		Class params[] = this.currentMethod.getParameterTypes();
		
		for (int i = 0; i < params.length; i++) {
			
			if(! params[i].isPrimitive()) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isQualified() {
		return this.isPublic() && 
			   this.isPrimitive() && 
			   this.allMethodsArePrimitive();
	}
	
	public void populateData() {
		
		Class params[] = this.currentMethod.getParameterTypes();
		
		System.out.println("Starting generating data for method " + this.currentMethod.getName());
		System.out.println("------------------------");
		

		for (int i = 0; i < 5; i++) {
			System.out.println("Starting generting data for " + i + " iteration");

			for (int j = 0; j < params.length; j++) {
				
				try {
					Object data = gd.getData(params[j].toString());
					this.currentMethod.invoke(this.obj, data);
					System.out.println("It did not throw the exception because it is a valid data which is " + data);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
//					System.out.println("It throw the exception because it is not a valid data!");
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
//				System.out.println(gd.getData(params[j].toString()));
			}
			System.out.println("Ending generting data for " + i + " iteration");
		}
		
		System.out.println("------------------------");
		System.out.println("Ending generating data for method " + this.currentMethod.getName());
		System.out.println("------------------------");
	}
	
	
}