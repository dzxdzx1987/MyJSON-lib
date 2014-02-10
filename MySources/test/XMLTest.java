package com.motrex.xml.test;
import com.motrex.xml.*;
import java.io.File;

public class XMLTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("C:" + File.separator + "sample-xml.xml");
		XMLParser parser = new XMLParser();
		parser.toJSON(file);
	}

}
