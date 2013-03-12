/**
 * 
 */
package com.surelution.whistle.core;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">Guangzong</a>
 *
 */
public class Attribute {

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

	private String name;
	private String value;
	private boolean isCData;
	
	public Attribute(String key, String message, boolean isCData) {
		this.name = key;
		this.value = message;
		this.isCData = isCData;
	}
	
	public Attribute(String key, String message) {
		this(key, message, true);
	}
	
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * 
	 * @return
	 */
	public String toXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<");
		sb.append(name);
		sb.append(">");
		if(isCData) {
			sb.append("<![CDATA[");
		}
		sb.append(getValue());
		if(isCData) {
			sb.append("]]>");
		}
		sb.append("</");
		sb.append(name);
		sb.append(">\n");
		return sb.toString();
	}

	protected List<Attribute> fellows() {
		ArrayList<Attribute> fellows = new ArrayList<Attribute>();
		fellows.add(new Attribute(Attribute.KEY_MsgType, Attribute.Msg_Type_TEXT));
		return fellows;
	}
}
