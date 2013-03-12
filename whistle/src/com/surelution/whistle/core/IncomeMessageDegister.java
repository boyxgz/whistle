/**
 * 
 */
package com.surelution.whistle.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">Guangzong</a>
 *
 */
public class IncomeMessageDegister {

	public static Map<String, String> parse(InputStream is) {
		final HashMap<String, String> map = new HashMap<String, String>();
		SAXParserFactory factory = SAXParserFactory.newInstance();
		try {
			SAXParser parser = factory.newSAXParser();
			parser.parse(is, new DefaultHandler(){
				
				String eleName;
				/* (non-Javadoc)
				 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
				 */
				@Override
				public void startElement(String uri, String localName,
						String qName, Attributes attributes)
						throws SAXException {
					eleName = qName;
				}
				
				/* (non-Javadoc)
				 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
				 */
				@Override
				public void characters(char[] ch, int start, int length)
						throws SAXException {
					String value = new String(ch, start, length);
					if(value != null && !"".equals(value.trim())) 
						map.put(eleName, value);
				}
			});
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
}
