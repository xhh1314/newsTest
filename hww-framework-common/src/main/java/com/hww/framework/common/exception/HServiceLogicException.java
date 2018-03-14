package com.hww.framework.common.exception;

public class HServiceLogicException extends RuntimeException{

   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HServiceLogicException() {
        super();
    }

  
    public HServiceLogicException(String message) {
        super(message);
    }

    public HServiceLogicException(String message, Throwable cause) {
        super(message, cause);
    }

    public HServiceLogicException(Throwable cause) {
        super(cause);
    }

}
