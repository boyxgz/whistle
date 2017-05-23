/**
 * 
 */
package com.surelution.whistle.core;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.Map;

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
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=");
		sb.append(appid);
		sb.append("&secret=");
		sb.append(secret);
		HttpGet httpGet = new HttpGet(sb.toString());
		HttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		String jsonString = IOUtils.toString(is, "utf-8");
		
		EntityUtils.consume(entity);

		Map<String, String> result = JsonUtils.toMap(jsonString);
		
        return result;
	}

	public static void main(String[] args) throws Exception {
	}
}
