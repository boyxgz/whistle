/**
 * 
 */
package com.surelution.whistle.core;

import java.util.ArrayList;
import java.util.List;

import com.surelution.whistle.push.NetworkException;
import com.surelution.whistle.push.Pusher;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class TemplateMessage {

	private String toUser;
	private String templateId;
	private String url;
	private String topcolor = "#FF0000";
	private List<TemplateMessageEntry> data;
	
	public void addEntry(TemplateMessageEntry entry) {
		if(data == null) {
			data = new ArrayList<TemplateMessage.TemplateMessageEntry>();
		}
		data.add(entry);
	}
	
	public void addEntry(String key, String value, String color) {
		TemplateMessageEntry entry = new TemplateMessageEntry(key, value, color);
		addEntry(entry);
	}
	
	public void addEntry(String key, String value) {
		String color = "#FF0000";
		addEntry(key, value, color);
	}
	
	public String getToUser() {
		return toUser;
	}

	public void setToUser(String toUser) {
		this.toUser = toUser;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}
	
	public void send() throws NetworkException {
		StringBuffer sb = new StringBuffer("{\n");
		sb.append(buildPair("touser", toUser));
		sb.append(",\n");
		sb.append(buildPair("template_id", templateId));
		sb.append(",\n");
		sb.append(buildPair("url", url));
		sb.append(",\n");
		sb.append(buildPair("topcolor", topcolor));
		sb.append(",\n");
		StringBuffer sb2 = new StringBuffer();
		boolean first = true;
		for(TemplateMessageEntry entry : data) {
			if(!first) {
				sb2.append(",");
			} else {
				first = false;
			}
			StringBuffer sb3 = new StringBuffer();
			sb3.append(buildPair("value", entry.value));
			sb3.append(",");
			sb3.append(buildPair("color", entry.color));
			sb3.append("\n");
			sb2.append(buildPair2(entry.key, sb3.toString()));
		}
		sb.append(buildPair2("data", sb2.toString()));
		sb.append("}");
		System.out.println(sb);
		Pusher p = new Pusher();
		p.setApiUrl("https://api.weixin.qq.com/cgi-bin/message/template/send?");
		String ret = 
				p.push(sb.toString());
		System.out.println(ret);
	}
	
	private String buildPair(String key, String value) {
		StringBuffer sb = new StringBuffer("\"");
		sb.append(key);
		sb.append("\":\"");
		sb.append(value);
		sb.append("\"");
		return sb.toString();
	}
	private String buildPair2(String key, String value) {
		StringBuffer sb = new StringBuffer("\"");
		sb.append(key);
		sb.append("\":{");
		sb.append(value);
		sb.append("}");
		return sb.toString();
	}

	public static class TemplateMessageEntry {
		private String key;
		private String value;
		private String color;
		
		public TemplateMessageEntry(String key, String value, String color) {
			this.key = key;
			this.value = value;
			this.color = color;
		}
		
		public TemplateMessageEntry(String key, String value) {
			this(key, value, "#FF0000");
		}
		
	}
}


