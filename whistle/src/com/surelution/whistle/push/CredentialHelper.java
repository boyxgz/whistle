/**
 * 
 */
package com.surelution.whistle.push;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.surelution.whistle.core.Configure;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class CredentialHelper {
	
	private static long lastFetch;
	private static long expireAt;
	private static String accessToken;

	public static final String API_URL_TEMPLATE = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=<appid>&secret=<secret>";
	
	private static String getApiUrl() {
		Configure c = Configure.config();
		return API_URL_TEMPLATE.replace("<appid>", c.getAppid()).replace("<secret>", c.getSecret());
	}
	
	public static String getAccessToken() {
		long now = System.currentTimeMillis();
		if(now >= expireAt) {
			System.out.println("fetch access token");
			lastFetch = System.currentTimeMillis();
			String url = getApiUrl();
			HttpGet get = new HttpGet(url);
			HttpClient client = new DefaultHttpClient();
			try {
				HttpResponse response = client.execute(get);
				InputStream is = response.getEntity().getContent();
				String content = IOUtils.toString(is);
				try {
					JSONObject o = new JSONObject(content);
//					if(o.getInt("errcode") != 0) {
//						//TODO some exception should be thrown?
//					}
					accessToken = o.getString("access_token");
			        String expireIn = o.getString("expires_in");
			        expireAt = now + Integer.parseInt(expireIn) * 1000 - 10000;//提前10秒钟刷新access token
			        lastFetch = now;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return accessToken;
	}
}
