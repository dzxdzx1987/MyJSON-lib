package com.motrex.xml;
import java.util.*;
public class CustomizedElement {
	private String name;
	private String text;
	private String namespace;
	private Map<String, String>attributes = new HashMap<String, String>();
	private List<CustomizedElement>subElement = new ArrayList<CustomizedElement>();
	
	public List<CustomizedElement> getSubElement() {
		return subElement;
	}
	public void setSubElement(List<CustomizedElement> subElement) {
		this.subElement = subElement;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	
}
