/**
 * 
 */
package com.surelution.whistle.core.js;

import com.surelution.whistle.core.JsonUtils;
import com.surelution.whistle.push.CredentialHelper;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class JsApiHelper {

	private static long expireAt;
	private static String ticket;

	private static String getApiUrl() {
		return "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=" + CredentialHelper.getAccessToken() + "&type=jsapi";
	}

	public static String getJsapiTicket() {
		long now = System.currentTimeMillis();
		if(now >= expireAt) {
			System.out.println("fetch jsapi ticket");
			String url = getApiUrl();
			HttpGet get = new HttpGet(url);
			HttpClient client = HttpClients.createDefault();
			try {
				HttpResponse response = client.execute(get);
				InputStream is = response.getEntity().getContent();
				String content = IOUtils.toString(is, "utf-8");
					Map<String, String> map = JsonUtils.toMap(content);
					ticket = map.get("ticket");
			        String expireIn = map.get("expires_in");
			        expireAt = now + Integer.parseInt(expireIn) * 1000 - 10000;//提前10秒钟刷新access token
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ticket;
	}
}
