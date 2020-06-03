
package com.fss.translator.dto;

import java.util.Map;

/**
 * This class used for passing object to across all methods for process request
 * 
 * @author ravinaganaboyina
 *
 */

public class ValueDTO {
	
	private String responseData;
	
	private Map<String,String> instituation;
	
	private Map<String,Object> requestObject;
	
	private Map<String,Object> transferObject;
	
	

	public Map<String, Object> getTransferObject() {
		return transferObject;
	}

	public void setTransferObject(Map<String, Object> transferObject) {
		this.transferObject = transferObject;
	}

	public String getResponseDataa() {
		return responseData;
	}

	public void setResponseData(String requestData) {
		this.responseData = requestData;
	}

	public Map<String, String> getInstituation() {
		return instituation;
	}

	public void setInstituation(Map<String, String> instituation) {
		
		this.instituation = instituation;
	}

	public Map<String, Object> getRequestObject() {
		return requestObject;
	}

	public void setRequestObject(Map<String, Object> requestObject) {
		this.requestObject = requestObject;
	}
	
	

	

	@Override
	public String toString() {
		return "ValueDTO [responseData=" + responseData + ", instituation=" + instituation + ", requestObject="
				+ requestObject + ", transferObject=" + transferObject + "]";
	}
	
	
	
	

}
