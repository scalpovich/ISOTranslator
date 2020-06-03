package com.fss.translator.controller;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fss.translator.constants.TranslatorConstants;
import com.fss.translator.exception.ServiceException;
import com.fss.translator.resource.TranslatorResources;
import com.fss.translator.util.Util;

import io.swagger.annotations.Api;

/**
 * Class is used for update to cache
 * @author ravinaganaboyina
 *
 */



@RestController
@Api(value="Translator Cache update")
@RequestMapping(value="/translateData")
public class TranslatorCacheUpdateController {

	@Autowired
	TranslatorResources translatorResources;

	private static final Logger logger = LogManager.getLogger(TranslatorCacheUpdateController.class);
	@RequestMapping(value="/updateInst/{inst}",method=RequestMethod.PUT)
	public ResponseEntity<Object> updateInstitutionDetails(@Required @PathVariable("inst") String inst ) throws ServiceException{
		logger.debug(TranslatorConstants.ENTER);
		if(!Util.isEmpty(inst)){
			translatorResources.setInstitutionData(inst);
		logger.debug(TranslatorConstants.EXIT);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value="/updateReqfield",method=RequestMethod.PUT)
	public ResponseEntity<Object> updateupdateReqfield() throws ServiceException{
		logger.debug(TranslatorConstants.ENTER);
		translatorResources.setRequestFields();
		logger.debug(TranslatorConstants.EXIT);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value="/updateMapClasses/{name}",method=RequestMethod.PUT)
	public ResponseEntity<Object> updateXSDmappingCls(@Required @PathVariable("name") String name ) throws ServiceException{
		logger.debug(TranslatorConstants.ENTER);
		translatorResources.setISO20022XSDmapping(name);
		logger.debug(TranslatorConstants.EXIT);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/updateMapProperty/{name}",method=RequestMethod.PUT)
	public ResponseEntity<Object> updateMapProperty(@Required @PathVariable("name") String name) throws ServiceException{
		logger.debug(TranslatorConstants.ENTER);
		translatorResources.setISO20022propertymapping(name);
		logger.debug(TranslatorConstants.EXIT);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
