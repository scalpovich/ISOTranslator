/**
 * 
 */
package com.fss.translator.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.core.lookup.StrSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fss.translator.constants.ResponseMessages;


/**
 * ResponseBuilder class builds the response returned by the Translator Service.
 * 
 * @author ravinaganaboyinaa
 *
 */

@Component
public class ResponseBuilder {
	
	// the locale handler
	@Autowired
	private LocaleHandler localeHandler;

	
	/**
	 * Returns a Response for a successful response.
	 * 
	 * @param data The data to be sent in the response.
	 * @param message The message to be sent in the response.

	 * @return the Response for the success response.
	 */
	public Response buildSuccessResponse(String responseData)
	{
		Response response=new Response();
		response.setResponseCode(ResponseMessages.SUCCESS);;
		response.setResponseMessage(localeHandler.getLocalizedMessage(ResponseMessages.SUCCESS));		
		response.setResponseData(responseData);	
		
		return response;
	}
	
	
	/**
	 * Returns a Response for a failure response.
	 * 
	 * @param message The message to be sent in the response.

	 * @return the Response for the failure response.
	 */
	public Response buildFailureHeaderResponse(String message,String code,boolean isJson)
	{
		Response response=new Response();
		response.setResponseCode(code);
		response.setResponseMessage(localeHandler.getLocalizedMessage(code));		
		
		return response;
	}
	
	public Response buildFailureResponse(String responseData, String message,String code)
	{
		Response response=new Response();
		response.setResponseCode(code);
		response.setResponseMessage(localeHandler.getLocalizedMessage(code));	
		response.setResponseData(responseData);	
		return response;
	}
	public Response buildGenericResponse(String responseData, String message)
	{
		Response response=new Response();
		response.setResponseCode(ResponseMessages.GENERIC_ERR_MESSAGE);
		response.setResponseMessage(localeHandler.getLocalizedMessage(ResponseMessages.GENERIC_ERR_MESSAGE));	
		response.setResponseData(responseData);	
		return response;
	}
	
}
