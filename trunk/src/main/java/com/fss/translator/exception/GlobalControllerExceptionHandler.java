package com.fss.translator.exception;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fss.translator.constants.ResponseMessages;
import com.fss.translator.util.ResponseBuilder;



/**
 * GlobalControllerExceptionHandler class handles all the exceptions 
 * at the controller level in a consistent manner.
 * 
 * @author ravinaganaboyina
 *
 */

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@Autowired
	private ResponseBuilder responseBuilder;

	private static final Logger logger = LogManager.getLogger(GlobalControllerExceptionHandler.class);


	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> exceptionHandler(Exception exception) 
	{

		String errMessage = ResponseMessages.GENERIC_ERR_MESSAGE;	

		if (exception instanceof ServiceException){
			errMessage  = exception.getMessage();
		}
		logger.error(errMessage);

		Object responseDto = responseBuilder.buildGenericResponse(errMessage,errMessage);

		return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}

}
