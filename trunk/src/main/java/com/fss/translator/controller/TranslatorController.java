
package com.fss.translator.controller;


import java.util.Map;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fss.translator.exception.ServiceException;
import com.fss.translator.service.TranslatorService;
import io.swagger.annotations.Api;

/**
 * Translator Controller provides all the REST operations pertaining to Tranlate the message.
 * @author ravinaganaboyina
 *
 */

@RestController
@Api(value="Translator Services")
public class TranslatorController {

	@Autowired
	TranslatorService translatorService;

	private static final Logger logger = LogManager.getLogger(TranslatorController.class);

	@RequestMapping(value = "/translateData",method = RequestMethod.POST)
	public ResponseEntity<Object> translateData(@RequestBody String requetBody,@RequestHeader Map<String, String> headers) throws ServiceException{
		logger.debug("ENTER");
		Long startTime=System.currentTimeMillis();
		ResponseEntity<Object> response=translatorService.requestProcesTranslate(requetBody,headers);
		Long endTime=System.currentTimeMillis();
		logger.info("Proceed Time "+(endTime-startTime));
		logger.debug("Exit");
		return  response;
	}

	@RequestMapping(value = "/ping/{pingRequest}",method = RequestMethod.GET)
	public String transactToY(@PathVariable String pingRequest) throws ServiceException{
		logger.debug("ENTER");
		Long startTime=System.currentTimeMillis();

		logger.info("Test url process:"+pingRequest);
		Long endTime=System.currentTimeMillis();

		logger.info("Proceed Time "+(endTime-startTime));
		logger.debug("Exit");
		return  pingRequest;
	}


}
