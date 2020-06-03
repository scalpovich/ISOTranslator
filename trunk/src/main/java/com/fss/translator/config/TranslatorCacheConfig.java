package com.fss.translator.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;

import com.fss.translator.constants.TranslatorConstants;
import com.fss.translator.exception.ServiceException;
import com.fss.translator.resource.TranslatorResources;
import com.fss.translator.util.Util;

@Configuration
public class TranslatorCacheConfig {


	@Autowired
	CacheManager cacheManager;

	@Autowired
	TranslatorResources translatorResources;

	@Value("${tranlator.config.path}")
	String tranlatorPath;

	public static final String TRANSLATOR_DATA_CACHE = "translateDataCache";


	public static final String TRANSALATOR_CACHE_INSTITUTIONS = "translator_institutions";

	public static final String TRANSALATOR_CACHE_REQUESTFIEDS = "translator_requestfieds";

	public static final String TRANSALATOR_CACHE_ISO20022PROPERTYMAPPING = "iso20022property_mapping";

	public static final String TRANSALATOR_CACHE_ISO20022XSDMAPPING = "iso20022XSD_mapping";

	Logger logger = LogManager.getLogger(TranslatorCacheConfig.class);

	@PostConstruct
	public void loadTranlatorsInstitutionsToLocalCache()throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		cacheManager.getCache(TranslatorConstants.TRANSLATOR_DATA_CACHE).put(TRANSALATOR_CACHE_INSTITUTIONS,
				getFileMap(false));

		logger.debug(TranslatorConstants.EXIT);
	}

	@PostConstruct
	public void loadRequestValidationToLocalCache()throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		cacheManager.getCache(TranslatorConstants.TRANSLATOR_DATA_CACHE).put(TRANSALATOR_CACHE_REQUESTFIEDS,
				translatorResources.getRequestConfigElements());

		logger.debug(TranslatorConstants.EXIT);
	}
	@PostConstruct
	public void loadMappingPropertiesToLocalCach()throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		cacheManager.getCache(TranslatorConstants.TRANSLATOR_DATA_CACHE).put(TRANSALATOR_CACHE_ISO20022PROPERTYMAPPING,
				getFileMap(true));
		logger.debug(TranslatorConstants.EXIT);
	}

	@PostConstruct
	public void loadMappingXSDToLocalCache()throws ServiceException {
		logger.debug(TranslatorConstants.ENTER);
		cacheManager.getCache(TranslatorConstants.TRANSLATOR_DATA_CACHE).put(TRANSALATOR_CACHE_ISO20022XSDMAPPING,
				translatorResources.getXSDMap());
		logger.debug(TranslatorConstants.EXIT);
	}

	private Map<String,Map<String,String>> getFileMap (boolean isRequire){
		logger.debug(TranslatorConstants.ENTER);
		Map<String,Map<String,String>> setMap=new HashMap<>();
		File folder = new File(tranlatorPath+TranslatorConstants.PROPERTIES_PATH+File.separator);
		File[] listOfFiles = folder.listFiles();
		try {
			for (File file : listOfFiles) {
				if (file.isFile()) {
					String fileName= file.getName();
					if(!isRequire && fileName.startsWith(TranslatorConstants.INST_PREFIX) &&  fileName.endsWith(TranslatorConstants.PEROPERTY_SUFFIX)){
						Map<String, String> getInstMap;
						getInstMap = translatorResources.getPropertyMap(fileName);
						setMap.put(Util.getPropertKey(fileName), getInstMap);
					}else if( isRequire && !fileName.startsWith(TranslatorConstants.INST_PREFIX) && fileName.endsWith(TranslatorConstants.PEROPERTY_SUFFIX) && isNot(fileName)){
						setMap.put(Util.getPropertKey(fileName), translatorResources.getPropertyMap(fileName));
					}
				}

			}

		} catch (ServiceException e) {

			logger.info("Institution configuration not loaded into property file");
		}
		logger.debug(TranslatorConstants.ENTER);
		return setMap;
	}
	private boolean isNot(String fileName){
		boolean isflag=false;
		if(!TranslatorConstants.REQUEST_VALIDATION_FILE.equalsIgnoreCase(fileName) && !TranslatorConstants.REQUEST_ISO22_MAP_FILE.endsWith(fileName))
			isflag=true;
		return isflag;
	}


}