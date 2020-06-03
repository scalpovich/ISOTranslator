package com.fss.translator.xsd;

import java.util.ArrayList;
import java.util.List;

/** Represents an "attribute" schema definition.
 * 
 * @author Harikrishna Agnikondu
 *
 */
public class XSDAttribute {

	private String name;
	private boolean required;
	private String type;
	private List<String> options = new ArrayList<>();
	private String defaultValue;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getOptions() {
		return options;
	}
	public void setOptions(List<String> options) {
		this.options = options;
	}
  public String getDefaultValue() {
    return defaultValue;
  }

public void setDefaultValue(String defaultValue) {
    this.defaultValue = defaultValue;
  }
	

}
