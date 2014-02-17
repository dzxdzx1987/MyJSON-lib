package com.motrex.jsonb2xml.test;
import java.io.File;

import com.motrex.jsonb2xml.xml.XMLParser;

public class XMLTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("C:" + File.separator + "sample-xml.xml");
		XMLParser parser = new XMLParser();
		System.out.println(parser.getRootElementName());
		parser.writeToJSON(file);
	}

}
