/**
 * 
 */
package com.fss.translator.util;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.fss.translator.constants.ResponseMessages;



/**
 * LocaleHandler manages all the locale related operations.
 * 
 *@author ravinaganaboyina
 *
 */
@Component
public class LocaleHandler {

	@Autowired
	private MessageSource messageSource;
	
	
	/**
	 * Returns a localized message based on input locale.
	 * 
	 * @param message the message to be localized.
	 * 
	 * @return the localized string message.
	 */
	
	public String getLocalizedMessage(String message)
	{
		if (Util.isEmpty(message))
			return message;
		
		Locale locale = LocaleContextHolder.getLocale();
		
		StringBuilder sb = new StringBuilder();
		String[] msgs = message.split(ResponseMessages.MESSAGE_DELIMITER);
		
		if (msgs.length == 1)
			return messageSource.getMessage(msgs[0],null,locale);
		
		for (String msg : msgs)
			sb.append(messageSource.getMessage(msg,null,locale) + "\n");
		
		return sb.toString();
	}

}
