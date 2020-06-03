package com.fss.translator.service.impl;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fss.translator.constants.ResponseMessages;
import com.fss.translator.constants.TranslatorConstants;
import com.fss.translator.dto.ValueDTO;
import com.fss.translator.exception.ServiceException;
import com.fss.translator.service.ISO20022TranslateService;
import com.fss.translator.service.TargetResponseformat;
import com.fss.translator.util.Response;
import com.fss.translator.util.ResponseBuilder;
import com.fss.translator.util.Util;

/**
 * Class derived the implimentaton of response format based mimeType and configuration type.  
 * @author ravinaganaboyina
 *
 */
@Service
public class TargetResponseformatImpl implements TargetResponseformat{


	@Autowired
	ResponseBuilder responseBuilder;

	@Autowired
	ISO20022TranslateService iso22TranslateService;


	private static final Logger logger = LogManager.getLogger(TargetResponseformatImpl.class);


	@Override
	public Object contentTypeResponse(ValueDTO valueDto) throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);

		Object object=null;
		String targetResponseFormat="";
		if(Util.isNotNull(valueDto) && Util.isNotNull(valueDto.getRequestObject())){
			targetResponseFormat=valueDto.getRequestObject().get(TranslatorConstants.CONTENT_TYPE)+"";
		}
		switch(targetResponseFormat){

		case TranslatorConstants.CONTENT_RESPONSE_ISJSON:{
			object=getResponseObject(valueDto);
			break;

		}
		case TranslatorConstants.CONTENT_RESPONSE_ISXML:{
			object=xmlResponseFormat(valueDto);
			break;

		}
		default :{
			logger.info("Target message format has not available on system");

			break;
		}





		}
		logger.debug(TranslatorConstants.EXIT);
		return object;
	}


	private Object xmlResponseFormat(ValueDTO valueDto) throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		Response response=null;
		String responseObj="";

		try{

			response=getResponseObject(valueDto);
			JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);

			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			StringWriter sw = new StringWriter();
			jaxbMarshaller.marshal(response, sw);
			responseObj= sw.toString();

		}catch(Exception e){
			String responseCode=valueDto.getRequestObject().get(TranslatorConstants.RESPONSE_CODE)+"";
			throw new ServiceException("",responseCode);
		}
		logger.debug(TranslatorConstants.EXIT);
		return responseObj;
	}

	private Response getResponseObject(ValueDTO valueDto){
		logger.debug(TranslatorConstants.ENTER);
		Response response=null;
		String responseMessage="";
		if(Util.isNotNull(valueDto) && Util.isNotNull(valueDto.getRequestObject())){
			if(ResponseMessages.SUCCESS.equals(valueDto.getRequestObject().get(TranslatorConstants.RESPONSE_CODE))){
				responseMessage=valueDto.getResponseDataa();
				response=responseBuilder.buildSuccessResponse(responseMessage);
			}else{
				response=responseBuilder.buildFailureResponse(valueDto.getRequestObject().get(TranslatorConstants.RESPONSE_DATA)+"", "", valueDto.getRequestObject().get(TranslatorConstants.RESPONSE_CODE)+"");
			}
		}
		logger.debug(TranslatorConstants.EXIT);
		return response;
	}


	@Override
	public void targetConverstion(ValueDTO valueDto) throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		String targetFormat=null;

		if(Util.isNotNull(valueDto) && Util.isNotNull(valueDto.getInstituation()) && !valueDto.getInstituation().isEmpty()){
			targetFormat=valueDto.getInstituation().get(TranslatorConstants.TARGET_RESPONSE_FORMAT)+"";

			switch(targetFormat){
			case TranslatorConstants.TARGET_RESPONSE_ISO8583_87:{
				break;

			}
			case TranslatorConstants.TARGET_RESPONSE_ISO8583_93:{
				break;

			}
			case TranslatorConstants.TARGET_RESPONSE_ISO20022:{
				iso22TranslateService.doMarshalling(valueDto);
				break;

			}case TranslatorConstants.TARGET_RESPONSE_JSON:{
				break;

			}

			default:
				valueDto.getRequestObject().put(TranslatorConstants.RESPONSE_CODE,ResponseMessages.CONFIGURATION_ERR_MESSAGE);
				logger.info("Target conversation not set for this instiuttion");

			}
			logger.debug(TranslatorConstants.EXIT);

		}

	}




}
