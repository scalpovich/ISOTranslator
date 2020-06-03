package com.fss.translator.service;

import com.fss.translator.dto.ValueDTO;
import com.fss.translator.exception.ServiceException;

public interface ISO8583TranslatorService {
	
	public void packService(ValueDTO dto)throws ServiceException;
	
	public void unpackService(ValueDTO dto)throws ServiceException;

}
