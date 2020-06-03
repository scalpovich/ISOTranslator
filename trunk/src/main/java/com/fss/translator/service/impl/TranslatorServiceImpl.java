package com.fss.translator.service.impl;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fss.translator.constants.ResponseMessages;
import com.fss.translator.constants.TranslatorConstants;
import com.fss.translator.dto.ValueDTO;
import com.fss.translator.exception.ServiceException;
import com.fss.translator.resource.TranslatorResources;
import com.fss.translator.service.ISO20022TranslateService;
import com.fss.translator.service.ISO8583TranslatorService;
import com.fss.translator.service.TranslatorService;
import com.fss.translator.util.Util;
import com.fss.translator.validate.impl.TranslatorValidatorImpl;
/**
 * Class is used for identification mime type and translation request based on format.
 * @author ravinaganaboyina
 *
 */

@Service
public class TranslatorServiceImpl implements TranslatorService {

	@Autowired
	TargetResponseformatImpl targetResponse;

	@Autowired
	TranslatorValidatorImpl translatorValidatorImpl;

	
	
	
	@Autowired
	TranslatorResources translatorResources;
	@Autowired
	ISO20022TranslateService iso22TranslateService;
	
	@Autowired
	ISO8583TranslatorService translatorJposService;

	private static final Logger logger = LogManager.getLogger(TranslatorServiceImpl.class);

	@Override
	public ResponseEntity<Object> requestProcesTranslate(String requetBody, Map<String, String> headers)throws ServiceException {
		ValueDTO valueDto=null;
		ResponseEntity<Object> responseEnity=null;
		try{
			logger.debug(TranslatorConstants.ENTER);
			valueDto=new ValueDTO();
			doDefaultPopulate(valueDto, headers, requetBody);
			translatorValidatorImpl.validateRequest(valueDto);
			doTranslateProcess(valueDto);
			targetResponse.targetConverstion(valueDto);

		}catch(ServiceException ex){
			logger.info("Process got failed due to-ServiceException :"+ex.getMessage(),ex);
			
			if(valueDto!=null ){
				Map<String,Object> responObj=valueDto.getRequestObject();
				if(Util.isNotNull(responObj) && !responObj.isEmpty())
				valueDto.getRequestObject().put(TranslatorConstants.RESPONSE_CODE,ex.getCode() );

			}

		}catch(Exception e){
			logger.info("Process got failed due to -Exception:"+e.getMessage(),e);
			if( valueDto!=null ){
				Map<String,Object> responObj=valueDto.getRequestObject();
				if(Util.isNotNull(responObj) && !responObj.isEmpty())
				valueDto.getRequestObject().put(TranslatorConstants.RESPONSE_CODE,ResponseMessages.GENERIC_ERR_MESSAGE);
			}

		}finally{
			Object obj=targetResponse.contentTypeResponse(valueDto);
			responseEnity=new ResponseEntity<Object>(obj,HttpStatus.OK);
			logger.debug(TranslatorConstants.EXIT);
		}
		
		return responseEnity;
	}

	@Override
	public void doTranslateProcess(ValueDTO valueDto)throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);

		String sourceMessageType="";
		if(Util.isNotNull(valueDto) && !valueDto.getRequestObject().isEmpty())
			sourceMessageType=valueDto.getRequestObject().get(TranslatorConstants.SOURCEMESSAGETYPE)+"";

		switch(sourceMessageType){

		case TranslatorConstants.SOURCEMESSAGETYPE_ISO020022:{
			iso22TranslateService.doUnmarshalling(valueDto);
			break;

		}
		case TranslatorConstants.SOURCEMESSAGETYPE_IS08085:{
			translatorJposService.unpackService(valueDto);
			break;

		}
		default :
			logger.info("sourceMessageType not configure on system");

			break;
		}
		
		logger.debug(TranslatorConstants.EXIT);

	}
	
	private void doDefaultPopulate(ValueDTO valuedto,Map<String,String> header,String requetBody ) throws ServiceException{
		logger.debug(TranslatorConstants.ENTER);

		@SuppressWarnings("unused")
		Map<String,Object> requestObject=new HashMap<>();
		Map<String,Object> requestBody=null;
		valuedto.setRequestObject(requestObject);

		if(Util.isNotNull(valuedto) && Util.isNotNull(valuedto.getRequestObject())){
			valuedto.getRequestObject().put(TranslatorConstants.CONTENT_TYPE, header.get(TranslatorConstants.CONTENT_TYPE)+"");
			valuedto.getRequestObject().put(TranslatorConstants.ACCEPT, header.get(TranslatorConstants.ACCEPT)+"");
			valuedto.getRequestObject().put(TranslatorConstants.SRCAPPID, header.get(TranslatorConstants.SRCAPPID)+"");
			valuedto.getRequestObject().put(TranslatorConstants.IPADDRESS, header.get(TranslatorConstants.IPADDRESS)+"");
			valuedto.getRequestObject().put(TranslatorConstants.CORRELATIONID, header.get(TranslatorConstants.CORRELATIONID)+"");
			valuedto.getRequestObject().put(TranslatorConstants.RRN, header.get(TranslatorConstants.CORRELATIONID)+"");
			ThreadContext.put("RRN", header.get(TranslatorConstants.CORRELATIONID)+"");
		}

		if(!Util.isEmpty(requetBody)){
			requestBody=Util.convertJsonToHashMap(requetBody);
		}
		valuedto.getRequestObject().putAll(requestBody);
		if(Util.isNotNull(valuedto) && !valuedto.getRequestObject().isEmpty()){
			String instid=valuedto.getRequestObject().get(TranslatorConstants.SRCAPPID)+"";
			Map<String,String> instConfig=translatorResources.getInstitutionData(instid);
			valuedto.setInstituation(instConfig);
		}	

		logger.debug(TranslatorConstants.EXIT);

	}



}
