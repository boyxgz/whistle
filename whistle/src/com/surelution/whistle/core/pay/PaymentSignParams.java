/**
 * 
 */
package com.surelution.whistle.core.pay;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class PaymentSignParams {

	private TreeMap<String, String> params = new TreeMap<String, String>();
	private String apiKey;
	
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public void add(String key, String value) {
		params.put(key, value);
	}

	public String getSign() {
		StringBuilder sb = new StringBuilder();
		Set<Entry<String,String>> entries = params.entrySet();
		boolean firstEntry = true;
		for(Entry<String,String> entry : entries) {
			String value = entry.getValue();
			if(StringUtils.isNotBlank(value)) {
				if(firstEntry) {
					firstEntry = false;
				} else {
					sb.append("&");
				}
				sb.append(entry.getKey());
				sb.append("=");
				sb.append(entry.getValue());
			}
		}

		sb.append("&key=");
		sb.append(apiKey);
		String content = sb.toString();
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bs = md.digest(content.getBytes());
			StringBuilder md5 = new StringBuilder();
			for(byte b : bs) {
				md5.append(Integer.toString( (b & 0xff) + 0x100, 16).substring(1));
			}
			return md5.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getDoc() {
		StringBuilder sb = new StringBuilder("<xml>");
		Set<Entry<String,String>> entries = params.entrySet();
		for(Entry<String,String> entry : entries) {
			String key = entry.getKey();
			String value = entry.getValue();
			if(StringUtils.isNotBlank(value)) {
				sb.append("<");
				sb.append(key);
				sb.append(">");
				sb.append(value);
				sb.append("</");
				sb.append(key);
				sb.append(">");
			}
		}
		sb.append("<sign>");
		sb.append(getSign());
		sb.append("</sign>");
		sb.append("</xml>");
		return sb.toString();
	}
}
