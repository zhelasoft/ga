package ga.exception;

import java.lang.reflect.InvocationTargetException;

public abstract class BaseException extends InvocationTargetException{

	public BaseException(String message) {
		super();
	}
	
	abstract boolean isCountable();

}
