/**
 * 
 */
package com.surelution.whistle.core;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 * @deprecated
 */
public class AccessToken {
	
	public AccessToken(Configure c) {
		this.c = c;
	}
	
	private Configure c;
	
	public String getAccessToken() {
		if(expireAt < System.currentTimeMillis()) {
			try {
				map = pull();
				String expiresIn = (String)map.get("expires_in");
				expireAt = System.currentTimeMillis() + Long.parseLong(expiresIn) * 1000 - 10000;
				accessToken = (String)map.get("access_token");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return accessToken;
	}
	
	private String accessToken;
	private long expireAt;
	
	private Map map;
	
	private Map pull() throws Exception {
		System.out.println("pull token ...");
		String appid = c.getAppid();
		String secret = c.getSecret();
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=");
		sb.append(appid);
		sb.append("&secret=");
		sb.append(secret);
		HttpGet httpGet = new HttpGet(sb.toString());
		HttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line = br.readLine();
		StringBuilder jsonString = new StringBuilder(line);
		while(line != null) {
			line = br.readLine();
			jsonString.append(line);
		}
		
		EntityUtils.consume(entity);
		
		JSONObject jsonObject = new JSONObject(jsonString.toString());

        Map result = new HashMap();
        Iterator iterator = jsonObject.keys();
        String key = null;
        String value = null;
        
        while (iterator.hasNext()) {
            key = (String) iterator.next();
            value = jsonObject.getString(key);
            result.put(key, value);
        }
//        System.out.println(result);
        return result;
	}

	public static void main(String[] args) throws Exception {
		Configure c = Configure.config();
		AccessToken at = new AccessToken(c);
		System.out.println(at.getAccessToken());
	}
}
