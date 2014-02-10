import java.io.File;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.*;
import org.dom4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
public class DOM4JRead {
	public static void main(String[] args){
		File file = new File("C:" + File.separator + "sample-xml.xml");
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(file);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		System.out.println(root.getName());
		recurve(root.elementIterator());
	}
	public static void recurve(Iterator iter){
		while (iter.hasNext()){
			Element subElement = (Element)iter.next();
			System.out.println(subElement.getName());
			System.out.println(subElement.getTextTrim());
			Iterator attributeIter = subElement.attributeIterator();
			while (attributeIter.hasNext()){
				org.dom4j.Attribute att = (org.dom4j.Attribute) attributeIter.next();
				System.out.println(att.toString());
			}
			recurve(subElement.elementIterator());
		}
	}
}