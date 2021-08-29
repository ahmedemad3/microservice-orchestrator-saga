package eg.com.payment.Exception;

public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8194987804260085209L;
	private int code;
	private String message;

	public ResourceNotFoundException(int code, String message) {
		super();
		this.code = code;
		this.message = message;
	}
	
	public ResourceNotFoundException(String message) {
		super();
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
