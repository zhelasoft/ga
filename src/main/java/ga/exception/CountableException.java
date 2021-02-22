package ga.exception;

public class CountableException extends BaseException {

	public CountableException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	@Override
	boolean isCountable() {
		// TODO Auto-generated method stub
		return true;
	}


	
}
