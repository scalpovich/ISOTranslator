package com.fss.translator.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.Cache;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fss.translator.config.CacheConfiguration;
import com.fss.translator.service.TranslatorCacheService;

/**
 * Class is used for caching the objects
 * @author ravinaganaboyina
 *
 */
@Service
@CacheConfig(cacheManager = "cacheManager")
public class TranslatorCacheServiceImpl {

	public static final String TRANSALATOR_CACHE_INSTITUTIONS = "translator_institutions";

	public static final String TRANSALATOR_CACHE_REQUESTFIEDS = "translator_requestfieds";
	
	public static final String TRANSALATOR_CACHE_ISo8583MAPPING = "iso8583_mapping";
	
	public static final String TRANSALATOR_CACHE_ISO20022PROPERTYMAPPING = "iso20022property_mapping";
	
	public static final String TRANSALATOR_CACHE_ISO20022XSDMAPPING = "iso20022XSD_mapping";


	private final Logger logger = LogManager.getLogger(this.getClass());




	

	@Cacheable(cacheNames = CacheConfiguration.TRANSLATOR_DATA_CACHE, key = "#root.target.TRANSALATOR_CACHE_REQUESTFIEDS")
	public  Map<String, String> getRequestFields( Map<String, String> requestFields) {
		logger.info("Adding TRANSALATOR_CACHE_REQUESTFIEDS to LOCAL Cache: " + requestFields);

		return requestFields;
	}
	
	@CachePut(cacheNames = CacheConfiguration.TRANSLATOR_DATA_CACHE, key = "#root.target.TRANSALATOR_CACHE_REQUESTFIEDS")
	public  Map<String, String> setRequestFields( Map<String, String> requestFields) {
		logger.info("Adding TRANSALATOR_CACHE_REQUESTFIEDS to LOCAL Cache: " + requestFields);

		return requestFields;
	}
	

	@Cacheable(cacheNames = CacheConfiguration.TRANSLATOR_DATA_CACHE, key = "#root.target.TRANSALATOR_CACHE_INSTITUTIONS")
	public Map<String, Map<String, String>> getInstitutionData(Map<String, Map<String, String>> institutionMap) {
		logger.info("Adding TRANSALATOR_CACHE_INSTITUTIONS to LOCAL Cache: " + TRANSALATOR_CACHE_INSTITUTIONS);

		return institutionMap;
	}

	@CachePut(cacheNames = CacheConfiguration.TRANSLATOR_DATA_CACHE, key = "#root.target.TRANSALATOR_CACHE_INSTITUTIONS")
	public Map<String, Map<String, String>> setInstitutionData(Map<String, Map<String, String>> institutionMap) {
		logger.info("Adding TRANSALATOR_CACHE_INSTITUTIONS to LOCAL Cache: " + TRANSALATOR_CACHE_INSTITUTIONS);

		return institutionMap;
	}
	
	@CachePut(cacheNames = CacheConfiguration.TRANSLATOR_DATA_CACHE, key = "#root.target.TRANSALATOR_CACHE_ISO20022XSDMAPPING")
	public Map<String, Map<String, String>> setISO20022XSDmapping(Map<String, Map<String, String>> iso22_mapping) {

		logger.info("Adding TRANSALATOR_CACHE_ISO22MAPPING to LOCAL Cache: " + iso22_mapping);

		return iso22_mapping;
	}
	
	@Cacheable(cacheNames = CacheConfiguration.TRANSLATOR_DATA_CACHE, key = "#root.target.TRANSALATOR_CACHE_ISO20022XSDMAPPING")
	public Map<String, Map<String, String>> getISO20022XSDmapping(Map<String, Map<String, String>> iso22_mapping) {

		logger.info("Adding TRANSALATOR_CACHE_ISO22MAPPING to LOCAL Cache: " + iso22_mapping);

		return iso22_mapping;
	}
	
	
	@CachePut(cacheNames = CacheConfiguration.TRANSLATOR_DATA_CACHE, key = "#root.target.TRANSALATOR_CACHE_ISO20022PROPERTYMAPPING")
	public Map<String, Map<String, String>> setISO20022propertymapping(Map<String, Map<String, String>> iso22_mapping) {

		logger.info("Adding TRANSALATOR_CACHE_ISO22MAPPING to LOCAL Cache: " + iso22_mapping);

		return iso22_mapping;
	}

	@Cacheable(cacheNames = CacheConfiguration.TRANSLATOR_DATA_CACHE, key = "#root.target.TRANSALATOR_CACHE_ISO20022PROPERTYMAPPING")
	public Map<String, Map<String, String>> getISO20022propertymapping(Map<String, Map<String, String>> iso22_mapping) {

		logger.info("Adding TRANSALATOR_CACHE_ISO22MAPPING to LOCAL Cache: " + iso22_mapping);

		return iso22_mapping;
	}
}
