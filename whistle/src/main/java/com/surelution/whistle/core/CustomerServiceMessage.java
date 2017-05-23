/**
 * 
 */
package com.surelution.whistle.core;

import com.alibaba.fastjson.JSON;
import com.surelution.whistle.push.*;

import java.util.HashMap;
import java.util.Map;

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
		return JSON.toJSONString(map);
	}
	
	public void send() throws ReplyTimeoutException {
		Pusher p = new Pusher();
		p.setApiUrl("https://api.weixin.qq.com/cgi-bin/message/custom/send?");
		try {
			String ret = p.push(toJson());
			Map<String, String> content = JsonUtils.toMap(ret);
			int errorCode = Integer.parseInt(content.get("errcode"));
			String errorMsg = content.get("errmsg");
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
		}
	}
}
