package com.fss.translator.validate;

import java.util.Map;

import com.fss.translator.dto.ValueDTO;
import com.fss.translator.exception.ServiceException;

/**
 * Class used for validating request-body and header elements
 * @author ravinaganaboyina
 *
 */

public interface TranslatorValidator {
	
	
	public void validateRequest(ValueDTO valueDto) throws ServiceException;
	
	

}
