/**
 * 
 */
package com.surelution.whistle.core;

import java.util.List;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">Guangzong</a>
 *
 */
public class Message {

	private String key;
	private String message;
	private boolean isCData;
	
	public Message(String key, String message, boolean isCData) {
		this.key = key;
		this.message = message;
		this.isCData = isCData;
	}
	
	public Message(String key, String message) {
		this(key, message, true);
	}
	
	public String getKey() {
		return key;
	}

	public String getMessage() {
		return message;
	}

	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		sb.append(key);
		sb.append(">");
		if(isCData) {
			sb.append("<![CDATA[");
		}
		sb.append(getMessage());
		if(isCData) {
			sb.append("]]>");
		}
		sb.append("</");
		sb.append(key);
		sb.append(">\n");
		return sb.toString();
	}

	protected List<Message> getFellows() {
		return null;
	}
}
