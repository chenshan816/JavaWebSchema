package org.smart4j.plugin.security.exception;

/**
 * 授权异常（当权限无效是抛出）
 * @author cs
 * @since 1.0.0
 */
public class AuthzException extends RuntimeException{

	public AuthzException() {
		super();
	}

	public AuthzException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthzException(String message) {
		super(message);
	}

	public AuthzException(Throwable cause) {
		super(cause);
	}
	
}
