package de.liebig.lighthouse.exceptions;

public class ApiException extends Exception {

	public ApiException() {
		super();
	}

	public ApiException(String message) {
		super(message);
	}
	
	public ApiException(String message, Throwable cause) {
		super(message, cause);
	}

}
