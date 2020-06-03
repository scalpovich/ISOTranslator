/**
 * 
 */
package com.fss.translator.exception;

/**
 * ServiceException class handles all the exceptions from the Service layer.
 * @author ravinaganaboyina
 *
 */

public class ServiceException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String code;
	private String message;
	
	
	public ServiceException(String message)
	{
		super(message);
		this.message=message;
	}
	public ServiceException(String message,String code)
	{
		this.code=code;
		this.message=message;
	}
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Override 
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
