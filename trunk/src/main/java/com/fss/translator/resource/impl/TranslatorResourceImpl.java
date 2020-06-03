package com.fss.translator.resource.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fss.translator.constants.ResponseMessages;
import com.fss.translator.constants.TranslatorConstants;
import com.fss.translator.exception.ServiceException;
import com.fss.translator.resource.TranslatorResources;
import com.fss.translator.service.impl.TranslatorCacheServiceImpl;
import com.fss.translator.util.Util;
import com.fss.translator.xsd.XSDElement;
import com.fss.translator.xsd.XSDParser;

/**
 * This class handle all properties file
 * 
 * @author ravinaganaboyina
 *
 */
@Service
public class TranslatorResourceImpl implements TranslatorResources {

	@Value("${tranlator.config.path}")
	String tranlatorPath;

	@Autowired
	TranslatorCacheServiceImpl translatorCacheServiceImpl;

	StringBuilder st = new StringBuilder();

	private static final Logger logger = LogManager.getLogger(TranslatorResourceImpl.class);

	@Override
	public Map<String, String> getRequestConfigElements() throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		Map<String, String> mapObj=null;
		mapObj=getPropertyMap(TranslatorConstants.REQUEST_VALIDATION_FILE);
		logger.debug(TranslatorConstants.EXIT);
		return mapObj;
	}


	@Override
	public Map<String, String> getPropertyMap(String fileName)throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);

		if(!Util.isEmpty(fileName)){
			fileName=getPropertypath(fileName);
		}

		Properties prop=getProperty(fileName);
		if(!Util.isNotNull(prop)){
			throw new ServiceException("",ResponseMessages.GENERIC_ERR_MESSAGE);
		}

		logger.debug(TranslatorConstants.EXIT);
		return prop.entrySet().stream().collect(
				Collectors.toMap(
						e -> e.getKey().toString(),
						e -> e.getValue().toString()
						)
				);
	}

	@SuppressWarnings("unused")
	private Properties getProperty(String fileName) throws ServiceException  {
		logger.debug(TranslatorConstants.ENTER);
		Properties prop=null;

		try{
			prop = new Properties();
			if(new File(fileName).exists()){
				InputStream  inputStream = new FileInputStream(fileName);
				prop.load(inputStream);
			}
		}catch(Exception e){
			throw new ServiceException("");
		}
		logger.debug(TranslatorConstants.EXIT);

		return prop;
	}


	@Override
	public Map<String, Map<String, String>> getXSDMap() throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		Map<String, Map<String, String>> xsdMap = new HashMap<>();
		Map<String, String>  innerMap=getPropertyMap(TranslatorConstants.REQUEST_ISO22_MAP_FILE);
		for (Map.Entry<String,String> map : innerMap.entrySet()) {
			xsdMap.put(Util.getXSDKey(map.getKey()), xsdtoMap(Util.getXSDFileName(map.getKey()), map.getValue()));
		}
		logger.debug(TranslatorConstants.EXIT);
		return xsdMap;
	}

	public Map<String, String> xsdtoMap(String file, String root)throws ServiceException{
		logger.debug(TranslatorConstants.ENTER);
		Map<String, String> map=null;
		try{
			if(!Util.isEmpty(file)){
				file=getXSDpath(file);
			}
			XSDElement mainElement = XSDParser.parseXSD(file, root);
			StringBuilder str = new StringBuilder();
			map = new TreeMap<>();
			printData(mainElement, 0, map, str);
			st=new StringBuilder();
		}catch(Exception e){
			logger.error("xsdtoMap:",e);
		}
		logger.debug(TranslatorConstants.EXIT);
		return map;
	}

	private void printData(XSDElement xsdElement, int level, Map<String, String> map, StringBuilder str) {
		logger.debug(TranslatorConstants.ENTER);
		String subName = "";

		if (str.length() <= 0)
			st.append(xsdElement.getName());
		map.put(st.append(str).toString(),xsdElement.getType());

		
		if (!xsdElement.getChildren().isEmpty() && xsdElement.getChildren().size() > 0) {
			for (XSDElement child : xsdElement.getChildren()) {
				subName = child.getName();
				printData(child, level + 2, map, new StringBuilder("." + subName));
			}
		}
		int lastIndex = st.lastIndexOf(".");
		if (lastIndex != -1)
			st.delete(lastIndex, st.length());

		logger.debug(TranslatorConstants.EXIT);

	}


	@Override
	public  Map<String, String> getInstitutionData(String id) throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		Map<String,String> instMap=null;
		Map<String,Map<String,String>> detailsMap=translatorCacheServiceImpl.getInstitutionData(null);
		if(Util.isNotNull(detailsMap) && !detailsMap.isEmpty()){
			instMap=detailsMap.get(TranslatorConstants.INST_PREFIX+id);
			if(Util.isNotNull(instMap) && CollectionUtils.isEmpty(instMap)){
				String fileName=Util.getPropertInstFileName(id);
				instMap=getPropertyMap(fileName);
				detailsMap.put(Util.getPropertKey(fileName), instMap);
				translatorCacheServiceImpl.setInstitutionData(detailsMap);
			}
		}
		logger.debug(TranslatorConstants.EXIT);	
		return instMap;
	}

	@Override
	public void setInstitutionData(String id) throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		Map<String,Map<String,String>> detailsMap=translatorCacheServiceImpl.getInstitutionData(null);
		if(Util.isNotNull(detailsMap) && !detailsMap.isEmpty()){
			String fileName=Util.getPropertInstFileName(id);
			Map<String,String> instMap=getPropertyMap(fileName);
			detailsMap.put(Util.getPropertKey(fileName), instMap);
			translatorCacheServiceImpl.setInstitutionData(detailsMap);
		}

		logger.debug(TranslatorConstants.EXIT);	

	}


	@Override
	public  Map<String, String> getRequestFields() throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);

		Map<String,String> requestFielMap=translatorCacheServiceImpl.getRequestFields(null);
		if(Util.isNotNull(requestFielMap) && requestFielMap.isEmpty()){
			requestFielMap=getRequestConfigElements();
			translatorCacheServiceImpl.setRequestFields(getRequestConfigElements());
		}
		logger.debug(TranslatorConstants.EXIT);

		return requestFielMap;
	}

	@Override
	public void setRequestFields() throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		translatorCacheServiceImpl.setRequestFields(getRequestConfigElements());
		logger.debug(TranslatorConstants.EXIT);

	}




	public void setISO20022XSDmapping(String name) throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		Map<String,Map<String,String>> iso2022XSDmap=translatorCacheServiceImpl.getISO20022XSDmapping(null);
		if(Util.isNotNull(iso2022XSDmap) &&! iso2022XSDmap.isEmpty()){
				Map<String, String>  innerMap=getPropertyMap(TranslatorConstants.REQUEST_ISO22_MAP_FILE);
				String root=innerMap.get(name);
				iso2022XSDmap.put(name, xsdtoMap(Util.getXSDFileName(name), root));
				translatorCacheServiceImpl.setISO20022XSDmapping(iso2022XSDmap);
				logger.debug(TranslatorConstants.EXIT);		
		}


	}

	public Map<String, String> getISO20022XSDmapping(String name) throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		Map<String,String> painMap=null;
		Map<String,Map<String,String>> iso2022XSDmap=translatorCacheServiceImpl.getISO20022XSDmapping(null);
		if(Util.isNotNull(iso2022XSDmap) && !iso2022XSDmap.isEmpty()){
			painMap=iso2022XSDmap.get(name);
			if(!Util.isNotNull(painMap)){
				Map<String, String>  innerMap=getPropertyMap(TranslatorConstants.REQUEST_ISO22_MAP_FILE);
				if(Util.isNotNull(innerMap) &&!innerMap.isEmpty()){
					String root=innerMap.get(name);
					iso2022XSDmap.put(name, xsdtoMap(Util.getXSDFileName(name), root));
				}
			}
		}
		logger.debug(TranslatorConstants.EXIT);
		return painMap;
	}


	public void setISO20022propertymapping(String name) throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		Map<String,String> isoPropMap=null;
		Map<String,Map<String,String>> detailsMap=translatorCacheServiceImpl.getISO20022propertymapping(null);
		if(Util.isNotNull(detailsMap) && !detailsMap.isEmpty()){
				String fileName=Util.getPropertFileName(name);
				isoPropMap=getPropertyMap(fileName);
				detailsMap.put(name, isoPropMap);
				translatorCacheServiceImpl.setISO20022propertymapping(detailsMap);
		}
		logger.debug(TranslatorConstants.EXIT);	

	}

	public Map<String, String> getISO20022propertymapping(String name) throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		Map<String,String> isoPropMap=null;
		Map<String,Map<String,String>> detailsMap=translatorCacheServiceImpl.getISO20022propertymapping(null);
		if(Util.isNotNull(detailsMap) && !detailsMap.isEmpty()){
			isoPropMap=detailsMap.get(name);
			if(!Util.isNotNull(isoPropMap)){
				String fileName=Util.getPropertFileName(name);
				isoPropMap=getPropertyMap(fileName);
				detailsMap.put(name, isoPropMap);
				translatorCacheServiceImpl.setInstitutionData(detailsMap);
			}
		}
		Map<String,String> treeMap=new TreeMap<>();
		treeMap.putAll(isoPropMap);
		logger.debug(TranslatorConstants.EXIT);
		return treeMap;
	}



	private String getPropertypath(String fileName){

		return tranlatorPath+TranslatorConstants.PROPERTIES_PATH+File.separator+fileName;
	}

	private String getXSDpath(String fileName){

		return tranlatorPath+TranslatorConstants.XSD_PATH+File.separator+fileName;
	}


}
