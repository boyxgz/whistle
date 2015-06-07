/**
 * 
 */
package com.surelution.whistle.core.pay;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class PlainXmlDocHelper {

	public static Map<String, String> parse(InputStream is) throws ParserConfigurationException, SAXException, IOException {

		Map<String, String> map = new HashMap<String, String>();
		DocumentBuilderFactory builderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		Document doc = builder.parse(is);
		Element root = doc.getDocumentElement();
		root.getElementsByTagName("appid").item(0).getTextContent();
		NodeList nl = root.getChildNodes();
		int length = nl.getLength();
		System.out.println(length);
		for(int i = 0; i < length; i++) {
			Node n = nl.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE) {
				String value = n.getTextContent();
				String name = n.getNodeName();
				map.put(name, value);
			}
		}
		return map;
	}
	
	public static void main(String[] args) throws Exception {
		PlainXmlDocHelper.parse(new FileInputStream("/Users/johnny/Desktop/a.xml"));
	}
}
