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

	public static final String KEY_Location_Y = "Location_Y";
	public static final String KEY_Location_X = "Location_X";
	public static final String KEY_Content = "Content";
	public static final String KEY_FuncFlag = "FuncFlag";
	public static final String KEY_MsgType = "MsgType";
	public static final String KEY_CreateTime = "CreateTime";
	public static final String KEY_FromUserName = "FromUserName";
	public static final String KEY_ToUserName = "ToUserName";
	
	public static final String Msg_Type_NEWS = "news";
	public static final String Msg_Type_TEXT = "text";

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

	/**
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @return
	 */
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
