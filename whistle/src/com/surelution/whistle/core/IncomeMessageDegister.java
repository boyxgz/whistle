/**
 * 
 */
package com.surelution.whistle.core;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">Guangzong</a>
 *
 */
public class IncomeMessageDegister {
	
	/**
	 * @param is
	 * @return
	 */
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

	public static Map<String, String> parse(Map<String, String[]> paramsMap, InputStream is) throws WxMsgCryptException {
		return parse(paramsMap, is, false);
	}

	public static Map<String, String> parse(Map<String, String[]> paramsMap, InputStream is, boolean insertFromUser) throws WxMsgCryptException {
		String encryptType = paramsMap.get("encrypt_type") != null?paramsMap.get("encrypt_type")[0]:null;
		if("aes".equals(encryptType)) {
			Configure config = Configure.config();
			WXBizMsgCrypt pc;
			String plainText;
			try {
				pc = new WXBizMsgCrypt(config.getToken(), config.getEncodingAESKey(), config.getAppid());
				StringBuffer sb = new StringBuffer();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String line = br.readLine();
				while(line != null) {
					sb.append(line);
					line = br.readLine();
				}
				if(insertFromUser) {
					//due to the rubbish library from weixin, the content must have <ToUserName> 
					sb.insert(sb.indexOf("<AppId>"), "<ToUserName></ToUserName>");
				}
				plainText = pc.decryptMsg(paramsMap.get("msg_signature")[0], paramsMap.get("timestamp")[0], paramsMap.get("nonce")[0], sb.toString());
				System.out.println(plainText);
				InputStream is2 = new ByteArrayInputStream(plainText.getBytes("utf-8"));
				return parse(is2);
			} catch (Exception e) {
				throw new WxMsgCryptException();
			}
		} else {
			return parse(is);
		}
	}
	
}
