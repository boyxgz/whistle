/**
 * 
 */
package com.surelution.whistle.core;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">Guangzong</a>
 *
 */
public abstract class BaseMessageProcessor {

	private Map<String, String> params;
	private Map<String, TextMessage> outcomeParams = new HashMap<String, TextMessage>();
	
	final protected void feed(Map<String, String> map) {
		params = map;
		put(TextMessage.KEY_ToUserName, getParam(TextMessage.KEY_FromUserName));
		put(TextMessage.KEY_FromUserName, getParam(TextMessage.KEY_ToUserName));
		put(TextMessage.KEY_CreateTime, String.valueOf(System.currentTimeMillis()), false);
		put(TextMessage.KEY_MsgType, TextMessage.Msg_Type_TEXT);
		put(TextMessage.KEY_FuncFlag, "0", false);
	}
	
	final public String getParam(String key) {
		return params.get(key);
	}

	public abstract boolean accept();
	
	public boolean goOn() {
		return true;
	}

	public abstract void process();

	final public String getXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for(Entry<String, TextMessage> entry : outcomeParams.entrySet()) {
			sb.append(entry.getValue().toXml());
		}
		sb.append("</xml>");
		return sb.toString();
	}

	final protected void put(String key, String message, boolean isCData) {
		outcomeParams.put(key, new TextMessage(key, message, isCData));
	}

	final protected void put(String key, String message) {
		put(key, message, true);
	}

	final protected void put(TextMessage message) {
		outcomeParams.put(message.getKey(), message);
		List<TextMessage> fellows = message.getFellows();
		if(fellows != null) {
			for(TextMessage fellow : fellows) {
				outcomeParams.put(fellow.getKey(), fellow);
			}
		}
	}

	final protected String getMessage(String key, String... args) {
		ResourceBundle messages = ResourceBundle.getBundle(getClass().getPackage().getName() + ".resources", Locale.SIMPLIFIED_CHINESE);
		String pattern;
		if(messages.containsKey(key)) {
			pattern = messages.getString(key);
		} else {
			messages = ResourceBundle.getBundle("resources", Locale.SIMPLIFIED_CHINESE);
			pattern = messages.getString(key);
		}
		MessageFormat mf = new MessageFormat(pattern, Locale.SIMPLIFIED_CHINESE);
		return mf.format(args);
	}
}
