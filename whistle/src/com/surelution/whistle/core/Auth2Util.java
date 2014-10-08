/**
 * 
 */
package com.surelution.whistle.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class Auth2Util {

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

		URL url = new URL(sb.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type","text/plain; charset=utf-8");
        conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
 
        conn.setDoOutput(true);
 
        int responseCode = conn.getResponseCode(); //TODO how to handle the code?
 
        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        
        List<String> lines = IOUtils.readLines(in);
        for(String line : lines) {
        	response.append(line);
        }
        in.close();
 
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(response.toString());
			openid = jsonObject.getString("openid");
		} catch (JSONException e1) {
			e1.printStackTrace(); //TODO throw some exception?
		}
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
