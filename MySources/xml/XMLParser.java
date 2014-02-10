package com.motrex.xml;

import java.io.File;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.*;
import org.dom4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
public class XMLParser {
	private String rootElementName;
//  private Map<Element, Attribute>elements = new HashMap<Element, Attribute>();
	private List<CustomizedElement> elements = new ArrayList<CustomizedElement>();
	
	public String getRootElementName(){
		return this.rootElementName;
	}
	
	public String toJSON(File file){
		String jsonObjectStart ="{";
		String jsonObjectEnd = "}";
		String jsonArrayStart = "[";
		String jsonArrayEnd = "]";
		String jsonObjectPairSeparator = ":";
		String jsonSeparator = ",";
		read(file);
		
		return null;
	}
	private void read(File file){
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		rootElementName = root.getName();
		recursive(root.elementIterator());
	}
	
	private void recursive(Iterator iter){
		Element element;
		while (iter.hasNext()){
			element = (Element)iter.next();
			CustomizedElement customizedElement = new CustomizedElement();
			customizedElement.setName(element.getName());
			customizedElement.setText(element.getTextTrim());
			Iterator attributeIter = element.attributeIterator();
			while (attributeIter.hasNext()){
				org.dom4j.Attribute att = (org.dom4j.Attribute) attributeIter.next();
				String attributeName = att.getName();
				String attributeText = att.getText();
				Map<String, String>atts = new HashMap<String, String>();
				atts.put(attributeName, attributeText);
				customizedElement.setAttributes(atts);
			}
			elements.add(customizedElement);
			recursive(element.elementIterator());
			
		}
	}
}
