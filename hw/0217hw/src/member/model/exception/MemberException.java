package member.model.exception;

import java.sql.SQLException;

/*
 * checkedException : extends Exception
 * 
 * uncheckedException : extends RuntimeException
 * 
 */

public class MemberException extends RuntimeException {

	public MemberException() {
		super();
	}

	public MemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	//cause는 애초에 발생한 exception
	public MemberException(String message, Throwable cause) {
		super(message, cause);
	}

	public MemberException(String message) {
		super(message);
	}

	public MemberException(Throwable cause) {
		super(cause);
	}
}