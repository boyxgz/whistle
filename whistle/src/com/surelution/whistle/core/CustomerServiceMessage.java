/**
 * 
 */
package com.surelution.whistle.core;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class CustomerServiceMessage {

	private HashMap<String, Object> map = new HashMap<String, Object>();

	public String getTouser() {
		return (String) map.get("touser");
	}

	public void setTouser(String touser) {
		put("touser", touser);
	}

	protected void put(String key, Object value) {
		map.put(key, value);
	}
	
	public String toJson() {
		JSONObject jo = new JSONObject();
		for(Entry<String, Object> es : map.entrySet()) {
			try {
				jo.put(es.getKey(), es.getValue());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return jo.toString();
	}
}
