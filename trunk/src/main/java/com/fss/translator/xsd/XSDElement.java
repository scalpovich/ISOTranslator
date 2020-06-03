package com.fss.translator.xsd;

import java.util.ArrayList;
import java.util.List;

import org.apache.xerces.xs.XSElementDeclaration;

/** Represents an "element" schema definition.
 * 
 * @author Harikrishna Agnikondu
 *
 */
public class XSDElement {

	private String name;
	private XSElementDeclaration xsDeclaration;	
	private XSDElement parent;	
	private List<XSDAttribute> attributes = new ArrayList<>();
	private List<XSDElement> children = new ArrayList<>();
	private int minOcurrs;
	private boolean maxOcurrsUnbounded;
	private int maxOcurrs;
	private String type;
	private String defaultValue;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public XSElementDeclaration getXsDeclaration() {
		return xsDeclaration;
	}
	public void setXsDeclaration(XSElementDeclaration xsDeclaration) {
		this.xsDeclaration = xsDeclaration;
	}
	
	public XSDElement getParent() {
		return parent;
	}
	public void setParent(XSDElement parent) {
		this.parent = parent;
	}
	
	public List<XSDAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<XSDAttribute> attributes) {
		this.attributes = attributes;
	}

	public List<XSDElement> getChildren() {
		return children;
	}
	public void setChildren(List<XSDElement> children) {
		this.children = children;
	}
	public int getMinOcurrs() {
		return minOcurrs;
	}
	public void setMinOcurrs(int minOcurrs) {
		this.minOcurrs = minOcurrs;
	}
	public boolean isMaxOcurrsUnbounded() {
		return maxOcurrsUnbounded;
	}
	public void setMaxOcurrsUnbounded(boolean maxOcurrsUnbounded) {
		this.maxOcurrsUnbounded = maxOcurrsUnbounded;
	}
	public int getMaxOcurrs() {
		return maxOcurrs;
	}
	public void setMaxOcurrs(int maxOcurrs) {
		this.maxOcurrs = maxOcurrs;
	}
	
	public void addChildren(XSDElement child) {
	  children.add(child);
	}
	
	public void addAttribute(XSDAttribute attribute) {
    attributes.add(attribute);
  }
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	

}
