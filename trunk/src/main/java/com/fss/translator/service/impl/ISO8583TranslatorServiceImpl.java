package com.fss.translator.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.stereotype.Service;

import com.fss.translator.constants.ResponseMessages;
import com.fss.translator.constants.TranslatorConstants;
import com.fss.translator.dto.ValueDTO;
import com.fss.translator.exception.ServiceException;
import com.fss.translator.service.ISO8583TranslatorService;
import com.fss.translator.util.Util;



@Service
public class ISO8583TranslatorServiceImpl implements ISO8583TranslatorService {



	private static final Logger logger = LogManager.getLogger(ISO8583TranslatorServiceImpl.class);


	@Override
	public void unpackService(ValueDTO dto) throws ServiceException {
		
			logger.debug(TranslatorConstants.ENTER);
			Map<String,String> instmap= dto.getInstituation();
			String instKeyValue=instmap.get(TranslatorConstants.ISO8583PACKAGER_FILE)+"";
			try(InputStream is = new FileInputStream(instKeyValue);){
			String data=dto.getRequestObject().get(TranslatorConstants.REQUESTDATA)+"";
			data=getActualString(data,12);
			GenericPackager packager = new GenericPackager(is);
			ISOMsg isoMsg = new ISOMsg();
			isoMsg.setPackager(packager);
			isoMsg.unpack(data.getBytes());
			Map<String,Object> mapIso=logISOMsg(isoMsg);
			dto.setTransferObject(mapIso);
			logger.debug(TranslatorConstants.EXIT);
		}catch(Exception e){
			logger.error("Exception ",e);
			throw new ServiceException("",ResponseMessages.INAVALID_INVALID_REQUEST_PARSE);
		}
		logger.debug(TranslatorConstants.EXIT);

	}

	@Override
	public void packService(ValueDTO dto) throws ServiceException {

	}

	private  Map<String,Object> logISOMsg(ISOMsg msg) throws ISOException {
		logger.debug(TranslatorConstants.ENTER);
		logger.debug("----ISO MESSAGE-----");
		Map<String,Object>  isoMap=new HashMap<>();
		for (int i=0;i<=msg.getMaxField();i++) {
			if (msg.hasField(i)) {
				logger.debug("    Field-"+i+" : "+msg.getString(i));
				Object object=msg.getString(i);
				isoMap.put(msg.getPackager().getFieldDescription(msg, i),object);

			}
		}
		logger.debug("----ISO MESSAGE-----");
		logger.debug(TranslatorConstants.EXIT);
		return isoMap;
	}
	
	private String getActualString(String isoMsg,int position){
		if(!Util.isEmpty(isoMsg) &&isoMsg.length()>position )
			isoMsg=isoMsg.substring(position);
		return isoMsg;
	}


}
