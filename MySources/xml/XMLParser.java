package com.motrex.jsonb2xml.xml;

import java.io.File;
import java.util.*;

import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.dom4j.Attribute;
public class XMLParser {
	private String rootElementName;
//  private Map<Element, Attribute>elements = new HashMap<Element, Attribute>();
	private List<CustomizedElement> elements = new ArrayList<CustomizedElement>();
	
	public String getRootElementName(){
		return this.rootElementName;
	}
	public void setRootElementName(String rootElementName){
		this.rootElementName = rootElementName;
	}
	
	public String writeToJSON(File file){
		String jsonObjectStart ="{";
		String jsonObjectEnd = "}";
		String jsonArrayStart = "[";
		String jsonArrayEnd = "]";
		String jsonObjectPairSeparator = ":";
		String jsonSeparator = ",";
		if (validateFile(file)){
			cacheXMLElement(file);
			Iterator iterElement = elements.iterator();
			while (iterElement.hasNext()){
				CustomizedElement customizedElement = (CustomizedElement)iterElement.next();
				System.out.println(customizedElement.getText());
				System.out.println(customizedElement.getName());
				System.out.println(customizedElement.getAttributes());
				ArrayList list = (ArrayList) customizedElement.getSubElement();
				Iterator iter = list.iterator();
				while (iter.hasNext()){
					CustomizedElement subElemet = (CustomizedElement)iter.next();
					System.out.println(subElemet.getName());
					System.out.println(subElemet.getText());
					System.out.println(subElemet.getAttributes());
				}
			}
		}else{
			System.out.println("This is not a xml file");
		}
		return null;
	}
	private boolean validateFile(File file){
		boolean flag = false;
		String  fileName = file.toString();
		if (fileName.contains(".xml")){
			flag = true;
		}
		return flag;
	}
	private void cacheXMLElement(File file){
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		String rootElementName = root.getName();
		this.setRootElementName(rootElementName);
		
		System.out.println(rootElementName);
		
		Iterator iter = root.elementIterator();
		while(iter.hasNext()){
			Element element = (Element)iter.next();
			CustomizedElement customizedElement = new CustomizedElement();
			customizedElement.setName(element.getName());
			customizedElement.setText(element.getTextTrim());
			Iterator attributeIter = element.attributeIterator();
			while (attributeIter.hasNext()){
				Attribute att = (Attribute) attributeIter.next();
				String attributeName = att.getName();
				String attributeText = att.getText();
				Map<String, String>atts = new HashMap<String, String>();
				atts.put(attributeName, attributeText);
				customizedElement.setAttributes(atts);
			}
			elements.add(customizedElement);
			readSubElement(customizedElement, element.elementIterator());
		}
		
		//parseRecursiveXML(root.elementIterator());
	}
	
	private void readSubElement(CustomizedElement customizedElement, Iterator iter){
		Element subElement;
		List<CustomizedElement> subElementList = new ArrayList<CustomizedElement>();
		if (iter.hasNext()){
		while (iter.hasNext()){
			subElement = (Element)iter.next();
			CustomizedElement customizedSubElement = new CustomizedElement();
			customizedSubElement.setName(subElement.getName());
			customizedSubElement.setText(subElement.getTextTrim());
			Iterator attributeIter = subElement.attributeIterator();
			while (attributeIter.hasNext()){
				Attribute att = (Attribute) attributeIter.next();
				String attributeName = att.getName();
				String attributeText = att.getText();
				Map<String, String>atts = new HashMap<String, String>();
				atts.put(attributeName, attributeText);
				customizedSubElement.setAttributes(atts);
			}
			subElementList.add(customizedSubElement);
			readSubElement(customizedElement, subElement.elementIterator());
			customizedElement.setSubElement(subElementList);
		}
			
		}
	}
}
