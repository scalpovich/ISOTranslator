package com.fss.translator.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.fss.translator.dto.ValueDTO;
import com.fss.translator.exception.ServiceException;

/**
 * This class will the process request elements 
 * @author ravinaganaboyina
 *
 */


public interface TranslatorService {
	
	public ResponseEntity<Object> requestProcesTranslate(String requetBody,Map<String, String> headers)throws ServiceException;
	
	public void doTranslateProcess(ValueDTO valyueDto)throws ServiceException;
	

}
