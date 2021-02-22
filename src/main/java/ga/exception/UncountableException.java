package ga.exception;

public class UncountableException extends BaseException {

	public UncountableException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean isCountable() {
		// TODO Auto-generated method stub
		return false;
	}


	
}
