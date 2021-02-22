package ga;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class MethodProcesser {
	
	private Method currentMethod;
	private List paramList;
	private GenerateData gd;
	
	MethodProcesser(Method m) {
		this.currentMethod = m;
		this.paramList = new ArrayList();
		this.gd = new GenerateData();
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
	
}