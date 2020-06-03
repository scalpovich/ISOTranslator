package com.fss.translator.service;

import java.util.List;
import java.util.Map;


/**
 * This class is used for cache 
 * @author ravinaganaboyina
 * 
 */

public interface TranslatorCacheService {

	public Map<String, Map<String, Object>> getInstitutionData(Map<String, Map<String, Object>> institutionMap);
	public Map<String, Map<String, Object>> getISO22Data(Map<String, Map<String, Object>> institutionMap);
	//public Map<String, List<Template>> getISO8583Data(Map<String, Map<String, Object>> institutionMap);
	public Map<String, Map<String, Object>> getRequestFields(Map<String, Map<String, Object>> institutionMap);


}
