package ga;

import java.util.ArrayList;
import java.util.List;

public class ParamModel {
	private final List<Object> params;
	
	public ParamModel(int paramCount) {
		this.params = new ArrayList<Object>(paramCount);
	}
	
	public void addParam(Object param) {
		this.params.add(param);
	}
	
	public Object[] getParams() {
		return this.params.toArray();
	}
	
	public String toString() {
		return this.params.toString();
	}
}
