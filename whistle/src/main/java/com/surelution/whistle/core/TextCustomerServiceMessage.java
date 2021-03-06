/**
 * 
 */
package com.surelution.whistle.core;

import java.util.HashMap;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class TextCustomerServiceMessage extends CustomerServiceMessage {

	public TextCustomerServiceMessage() {
		put("msgtype", "text");
	}

	public void setContent(String content) {
		HashMap<String, String> c = new HashMap<String, String>();
		c.put("content", content);
		put("text", c);
	}
	
	public static void main(String[] args) throws Exception {
		TextCustomerServiceMessage tsm = new TextCustomerServiceMessage();
		tsm.setContent("dasfds");
		tsm.setTouser("oklOVjgRVFE8UiqhmYCEBnkv-Nww");
		tsm.send();
	}
}
