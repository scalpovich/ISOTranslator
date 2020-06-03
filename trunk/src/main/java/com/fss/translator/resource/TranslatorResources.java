package com.fss.translator.resource;

import java.util.Map;

import com.fss.translator.exception.ServiceException;


/**
 * Class helps reading properties and get the map of object
 * @author ravinaganaboyina
 *
 */

public interface TranslatorResources {

	public Map<String,String> getRequestConfigElements() throws ServiceException;
	public Map<String,Map<String,String>> getXSDMap() throws ServiceException;
	
	public Map<String, String> getPropertyMap(String fileName)throws ServiceException;	
	public Map<String, String> xsdtoMap(String file, String root)throws ServiceException;
	

	public  Map<String, String> getInstitutionData(String id)throws ServiceException;
	public void setInstitutionData(String id) throws ServiceException;

	public  Map<String, String> getRequestFields() throws ServiceException;
	public void setRequestFields() throws ServiceException;
	
	public void setISO20022XSDmapping(String name)throws ServiceException;
	public Map<String, String> getISO20022XSDmapping(String name)throws ServiceException;
	
	public void setISO20022propertymapping(String name)throws ServiceException;
	public Map<String, String> getISO20022propertymapping(String name)throws ServiceException;


}
