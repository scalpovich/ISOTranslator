/**
 * 
 */
package com.fss.translator.constants;

/**
 * ResponseMessages class defines all the response message keys for the 
 * response messages returned by the Translator Service.
 * The keys are mapped to key-value pairs in messages.properties
 * 
 *@author ravinaganaboyina
 *
 */
public class ResponseMessages {

	/**
	 * Constants class should not be instantiated.
	 */
	private ResponseMessages() 
	{
		throw new IllegalStateException("Constants class");
	}


	public static final String MESSAGE_DELIMITER = "-";

	public static final String SUCCESS = "00";
	public static final String INAVALID_HEADER_SRCAPPID = "100";
	public static final String INAVALID_HEADER_IPADDRESS = "101";
	public static final String INAVALID_HEADER_CORELATION= "102";
	public static final String INAVALID_SOURCE_MSGTYPE = "200";
	public static final String INAVALID_INVALID_API_KEY = "201";
	public static final String INAVALID_INVALID_REQUEST_PARSE = "202";
	public static final String GENERIC_ERR_MESSAGE = "300";
	public static final String CONFIGURATION_ERR_MESSAGE = "301";
	
	


	
}
