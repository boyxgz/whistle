/**
 * 
 */
package com.surelution.whistle.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
public class Configure {
	
	private static Map<String, Configure> instances = new HashMap<String, Configure>();

	private String token;
	final private ArrayList<String> names = new ArrayList<String>();

	public String getToken() {
		return token;
	}

	public Iterable<String> getProcessorNames() {
		Iterable<String> iter = new Iterable<String>() {
			
			@Override
			public Iterator<String> iterator() {
				return names.iterator();
			}
		};
		return iter;
	}
	
	private Configure(String file) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(file);
		SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser;
			try {
				parser = factory.newSAXParser();
				parser.parse(is, new DefaultHandler(){
					boolean tokenEle = false;
					boolean processorEle = false;

					/* (non-Javadoc)
					 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
					 */
					@Override
					public void startElement(String uri, String localName,
							String qName, Attributes attributes)
							throws SAXException {
						if("token".equals(qName)) {
							tokenEle = true;
						} else if("processor".equals(qName)) {
							processorEle = true;
						}
					}
					
					/* (non-Javadoc)
					 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
					 */
					@Override
					public void endElement(String uri, String localName,
							String qName) throws SAXException {
						if("token".equals(qName)) {
							tokenEle = false;
						} else if("processor".equals(qName)) {
							processorEle = false;
						}
					}
					
					/* (non-Javadoc)
					 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
					 */
					@Override
					public void characters(char[] ch, int start, int length)
							throws SAXException {
						if(tokenEle) {
							String s= new String(ch, start, length);
							if(s != null && !s.equals("")) {
								token = s;
							}
						} else if(processorEle) {
							String s = new String(ch, start, length);
							if(s != null && !s.equals("")) {
								names.add(s);
							}
						}
					}
				});
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public static Configure config() {
		return config("whistle.xml");
	}
	
	public synchronized static Configure config(String file) {
		Configure cfg;
		if(!instances.containsKey(file)) {
			cfg = new Configure(file);
			instances.put(file, cfg);
		} else {
			cfg = instances.get(file);
		}
		return cfg;
	}
}
