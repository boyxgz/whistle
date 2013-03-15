/**
 * 
 */
package com.surelution.whistle.core;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">Guangzong</a>
 *
 */
public abstract class BaseAction {

	private Map<String, String> params;
	private Map<String, Attribute> outcomeParams = new HashMap<String, Attribute>();
	
	final protected void feed(Map<String, String> map) {
		params = map;
		put(new Attribute(Attribute.KEY_ToUserName, getParam(Attribute.KEY_FromUserName)));
		put(new Attribute(Attribute.KEY_FromUserName, getParam(Attribute.KEY_ToUserName)));
		put(new Attribute(Attribute.KEY_CreateTime, String.valueOf(System.currentTimeMillis()), false));
		put(new Attribute(Attribute.KEY_FuncFlag, "0", false));
	}
	
	final public String getParam(String key) {
		return params.get(key);
	}
	
	final public Set<String> paramNames() {
		HashSet<String> names = new HashSet<String>();
		for(String name : params.keySet()) {
			names.add(name);
		}
		return names;
	}

	public abstract boolean accept();

	/**
	 * some request should be terminated, implements it to return false
	 * @return
	 */
	public boolean moveOn() {
		return true;
	}

	/**
	 * handle the request. process the request the parameters, save to db, build the result etc.
	 */
	public abstract void execute();
	
	public void preExecute() {
		
	}

	final public String buildXml() {
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		for(Entry<String, Attribute> entry : outcomeParams.entrySet()) {
			sb.append(entry.getValue().toXml());
		}
		sb.append("</xml>");
		return sb.toString();
	}

	final protected void put(Attribute message) {
		outcomeParams.put(message.getName(), message);
		List<Attribute> fellows = message.fellows();
		if(fellows != null) {
			for(Attribute fellow : fellows) {
				outcomeParams.put(fellow.getName(), fellow);
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
