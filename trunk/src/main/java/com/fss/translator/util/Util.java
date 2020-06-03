package com.fss.translator.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fss.translator.constants.TranslatorConstants;

/**
 * Util class provides all utility methods which can be reused across the
 * service.
 * 
 *@author ravinaganaboyina
 *
 */

public class Util {

	private static final Logger logger = LogManager.getLogger(Util.class);

	/**
	 * Util class should not be instantiated.
	 */
	private Util() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Checks whether an input String is empty or not. Returns true is input string
	 * is null or has 0 length.
	 * 
	 * @param input
	 *            The input string to be checked.
	 * 
	 * @return boolean indicating whether input string is empty or not.
	 */
	public static boolean isEmpty(String input) {
		return (input == null || input.trim().isEmpty() || input.equalsIgnoreCase("null"));
	}

	/**
	 * 
	 * Converting a Map to JSON String
	 * @throws JsonProcessingException 
	 * 
	 * @throws Exception
	 */
	public static String mapToJson( Map<String, Object> productAttributes) throws JsonProcessingException  {

		ObjectMapper objectMapper = new ObjectMapper();
		String attributesJsonResp = null;
		attributesJsonResp = objectMapper.writeValueAsString(productAttributes);
		return attributesJsonResp;
	}

	public static String convertHashMapToJson( Map<String, Object> attributes) {
		String attributesJsonString = null;

		if (!attributes.isEmpty()) {
			ObjectMapper mapperObj = new ObjectMapper();
			try {
				attributesJsonString = mapperObj.writeValueAsString(attributes);

			} catch (JsonProcessingException e) {
				logger.error(e.getMessage());
			}
		}

		return attributesJsonString;
	}

	public static  Map<String, Object> convertJsonToHashMap(String attributesJsonString) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> productAttributes = null;

		if (!Util.isEmpty(attributesJsonString)) {
			try {
				mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
				TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String,Object>>() {};
				productAttributes = mapper.readValue(attributesJsonString, typeRef); 

				
	           /* = new TypeReference<HashMap<String,Object>>() {};
				productAttributes = mapper.readValue(attributesJsonString,
						new TypeReference<Map<String, Object>>() {
				});*/
				
				
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}

		return productAttributes;
	}

	/**
	 * 
	 * Converting a JSON String to Map
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * 
	 * @throws Exception
	 */
	public static Map<String, Object> jsonToMap(String productAttributesStr) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> productAttributes = null;

		productAttributes = objectMapper.readValue(productAttributesStr,
				new TypeReference< Map<String, Object>>() {
		});

		return productAttributes;

	}

	public static boolean useFilter(String filterName) {
		return (!isEmpty(filterName) && !"*".equals(filterName));

	}
	public static String getDateWithTimeZone(String input) {

		DateFormat pDfrmt = new SimpleDateFormat(input);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new java.util.Date());

		return pDfrmt.format(cal.getTime());
	}

	/**
	 * to left pad the string
	 * 
	 * @param asInput
	 *            a string that has to be padded
	 * @param aiLen
	 *            required length of the string
	 * @param aiPadChar
	 *            the character with which the string has to be padded
	 * @return a padded string
	 */
	public static String padLeft(String asInput, int aiLen, String aiPadChar) {
		int i = 0;
		int piLen = 0;
		String psretval = null;

		asInput = returnBlank(asInput);
		asInput = asInput.trim();
		piLen = asInput.length();
		psretval = asInput;

		for (i = 0; i < (aiLen - piLen); i++)
			psretval = aiPadChar + psretval;

		return (psretval);
	}

	/**
	 * returns the balnk string whereever a null value is got or the input string is
	 * null
	 * 
	 * @param inputVal
	 *            a string which has to be compared
	 * @return the blank string
	 */
	public static String returnBlank(String inputVal) {
		if (inputVal == null) {
			return "";
		}
		if (inputVal.equals("null")) {
			return "";
		}

		return inputVal;
	}


	public static String convertAsString(Object dataObject) {
		return (dataObject != null) && !"null".equalsIgnoreCase(String.valueOf(dataObject).trim())
				&& !"".equalsIgnoreCase(String.valueOf(dataObject).trim()) ? String.valueOf(dataObject) : "";
	}

	/**
	 * 
	 * Masking the sensitive data
	 * 

	 */
	public static String maskString(String data) {

		if(!isEmpty(data)) {
			return data.replace(data.substring(4, data.length() - 4), "****");
		}
		return data;

	}
	
	/**
	 * This method will check object has null or not
	 * @param obj
	 * @return
	 */
	
	public static boolean isNotNull(Object obj){
		
		Optional optObj=Optional.ofNullable(obj);
		
		if(optObj.isPresent()){
			return true;
		}else{
			return false;
		}
		
	
		
	
	}
	
	public static TreeMap<String,String> getTreeMap(Map<String,String> map){
		
		
     return map.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, 
             newValue) 
             -> newValue, 
         TreeMap::new)); 
	
	}
	
	public static String getPropertInstFileName(String name){
		return TranslatorConstants.INST_PREFIX+name+TranslatorConstants.PEROPERTY_SUFFIX;
	}
	public static String getPropertFileName(String name){
		return name+TranslatorConstants.PEROPERTY_SUFFIX;
	}
	public static String getPropertKey(String name){
		return name.replace(TranslatorConstants.PEROPERTY_SUFFIX, "");
	}
	
	public static String getXSDKey(String name){
		return name.replace(TranslatorConstants.XSD_SUFFIX, "");
	}
	
	public static String getXSDFileName(String name){
		
		return name+TranslatorConstants.XSD_SUFFIX;
	}
	

}
