package com.fss.translator.validate.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fss.translator.constants.ResponseMessages;
import com.fss.translator.constants.TranslatorConstants;
import com.fss.translator.dto.ValueDTO;
import com.fss.translator.exception.ServiceException;
import com.fss.translator.resource.impl.TranslatorResourceImpl;
import com.fss.translator.util.Util;
import com.fss.translator.validate.TranslatorValidator;

@Service
public class TranslatorValidatorImpl implements TranslatorValidator{

	@Autowired
	TranslatorResourceImpl translatorResourceImpl;


	private static final Logger logger = LogManager.getLogger(TranslatorResourceImpl.class);


	@Override
	public void validateRequest(ValueDTO valueDto) throws ServiceException {

		logger.debug(TranslatorConstants.ENTER);

		Map<String,String> configRequest=translatorResourceImpl.getRequestFields();
		Map<String,Object> validatMap=new HashMap<>();
		validatMap.put(TranslatorConstants.RESPONSE_CODE, ResponseMessages.SUCCESS);
		Map<String,Object> requestBody=valueDto.getRequestObject();
		configRequest.entrySet().forEach(map->{
			if(requestBody.containsKey(map.getKey())){
				if(TranslatorConstants.IS_MANDATORY.equals(map.getValue()) && Util.isEmpty(requestBody.get(map.getKey())+"")) {
					setValidation(map.getKey(), validatMap);
				}/*else if(TranslatorConstants.IS_OPTIONA.equals(map.getValue()) && !Util.isEmpty(requestBody.get(map.getKey())+"") ){
					setValidation(map.getKey(), validatMap);
				}*/

			}else {
				if(!TranslatorConstants.IS_NON_MANDATORY.equals(map.getValue()) && !TranslatorConstants.IS_OPTIONA.equals(map.getValue())){
					setValidation(map.getKey(), validatMap);
				}
			}

		});

		if(!ResponseMessages.SUCCESS.equals(validatMap.get(TranslatorConstants.RESPONSE_CODE))){
			throw new ServiceException("",validatMap.get(TranslatorConstants.RESPONSE_CODE)+"");
		}else{
			requestBody.put(TranslatorConstants.RESPONSE_CODE, ResponseMessages.SUCCESS);

		}
		logger.debug(TranslatorConstants.EXIT);

	}


	@SuppressWarnings("unused")
	private void setValidation(String stringKey,Map<String,Object> validatmap){
		logger.debug(TranslatorConstants.ENTER);
		if(TranslatorConstants.CONTENT_TYPE.equals(stringKey))
			validatmap.put(TranslatorConstants.RESPONSE_CODE,ResponseMessages.INAVALID_INVALID_REQUEST_PARSE);
		if(TranslatorConstants.ACCEPT.equals(stringKey))
			validatmap.put(TranslatorConstants.RESPONSE_CODE,ResponseMessages.INAVALID_INVALID_REQUEST_PARSE);

		if(TranslatorConstants.SRCAPPID.equals(stringKey))
			validatmap.put(TranslatorConstants.RESPONSE_CODE,ResponseMessages.INAVALID_HEADER_SRCAPPID);

		if(TranslatorConstants.IPADDRESS.equals(stringKey))
			validatmap.put(TranslatorConstants.RESPONSE_CODE,ResponseMessages.INAVALID_HEADER_IPADDRESS);

		if(TranslatorConstants.CORRELATIONID.equals(stringKey))
			validatmap.put(TranslatorConstants.RESPONSE_CODE,ResponseMessages.INAVALID_HEADER_CORELATION);

		if(TranslatorConstants.SOURCEMESSAGETYPE.equals(stringKey))
			validatmap.put(TranslatorConstants.RESPONSE_CODE,ResponseMessages.INAVALID_SOURCE_MSGTYPE);

		if(TranslatorConstants.APIKEY.equals(stringKey))
			validatmap.put(TranslatorConstants.RESPONSE_CODE,ResponseMessages.INAVALID_INVALID_API_KEY);

		if(TranslatorConstants.REQUESTDATA.equals(stringKey))
			validatmap.put(TranslatorConstants.RESPONSE_CODE,ResponseMessages.INAVALID_INVALID_REQUEST_PARSE);

		logger.debug(TranslatorConstants.EXIT);
	}




}
