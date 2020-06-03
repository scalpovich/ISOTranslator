package com.fss.translator.service;

import com.fss.translator.dto.ValueDTO;
import com.fss.translator.exception.ServiceException;

/**
 * Class is used for parsing of ISO20022
 * @author ravinaganaboyina
 *
 */
public interface ISO20022TranslateService {
	
	public void doMarshalling(ValueDTO value)throws ServiceException;
	
	public void doUnmarshalling(ValueDTO value)throws ServiceException;
}
