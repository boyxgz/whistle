/**
 * 
 */
package com.surelution.whistle.core;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;

import com.surelution.whistle.push.ErrorCodeUtil;
import com.surelution.whistle.push.NetworkException;
import com.surelution.whistle.push.Pusher;
import com.surelution.whistle.push.ReplyTimeoutException;
import com.surelution.whistle.push.ReturnCodeException;

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
	
	public void send() throws ReplyTimeoutException {
		Pusher p = new Pusher();
		p.setApiUrl("https://api.weixin.qq.com/cgi-bin/message/custom/send?");
		try {
			String ret = p.push(toJson());
			JSONObject o = new JSONObject(ret);
			int errorCode = o.getInt("errcode");
			String errorMsg = o.getString("errmsg");
			if(errorCode != 0) {
				System.out.println(errorMsg);
				try {
					ErrorCodeUtil.process(errorCode);
				} catch (ReplyTimeoutException e) {
					throw e;
				} catch(ReturnCodeException e) {
					e.printStackTrace();
				}
			}
		} catch (NetworkException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
