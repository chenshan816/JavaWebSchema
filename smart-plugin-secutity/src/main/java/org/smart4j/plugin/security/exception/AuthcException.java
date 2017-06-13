package org.smart4j.plugin.security.exception;

/**
 * 认证异常
 * @author cs
 * @since 1.0.0
 */
public class AuthcException extends Exception{

	public AuthcException() {
		super();
	}

	public AuthcException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthcException(String message) {
		super(message);
	}

	public AuthcException(Throwable cause) {
		super(cause);
	}
	
	
}
