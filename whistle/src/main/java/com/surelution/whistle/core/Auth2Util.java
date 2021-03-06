/**
 * 
 */
package com.surelution.whistle.core;

import com.surelution.whistle.push.Pusher;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class Auth2Util {
	
	public static final String KEY_CODE = "code";
	
	public static final String KEY_STATE = "state";

	public static String buildRedirectUrl(String dest, String state, AuthScope scope) {
		String s1 = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=";
		String s2 = "&redirect_uri=";
		String s3 = "&response_type=code&scope=";
		String s4 = "&state=";
		String s5 = "#wechat_redirect";
		StringBuilder sb = new StringBuilder(s1);
		Configure c = Configure.config();
		sb.append(c.getAppid());
		sb.append(s2);
		try {
			sb.append(URLEncoder.encode(dest, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.append(s3);
		sb.append(scope);
		if(state != null) {
			sb.append(s4);
			sb.append(state);
		}
		sb.append(s5);
		return sb.toString();
	}

	public static String getOpenidByCode(String code) throws Exception {
		String openid = null;
		Configure c = Configure.config();
		StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token?appid=");
		sb.append(c.getAppid());
		sb.append("&secret=");
		sb.append(c.getSecret());
		sb.append("&code=");
		sb.append(code);
		sb.append("&grant_type=authorization_code");
		
		Pusher p = new Pusher();
		p.setApiUrl(sb.toString());
		String json = p.push(null, null);

		Map<String,String> content = JsonUtils.toMap(json);
		openid = content.get("openid");
		return openid;
	}
	
	public enum AuthScope {
		BASE("snsapi_base"), USER_INFO("snsapi_userinfo");
		
		private String value;
		private AuthScope(String v) {
			value = v;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return value;
		}
	}
}
