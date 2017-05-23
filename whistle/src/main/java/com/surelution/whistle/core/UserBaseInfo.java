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
 * @deprecated
 * refer to <code>com.surelution.whistle.push.UserInfo</code>
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 * snsapi_base of wechat api
 */
public class UserBaseInfo {
	
	public UserBaseInfo(Configure c) {
		this.c = c;
	}
	
	private Configure c;

	public String getUserOpenId(String code) throws Exception {
		String appid = c.getAppid();
		String secret = c.getSecret();
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		StringBuilder sb = new StringBuilder("https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid=");
		sb.append(appid);
		sb.append("&secret=");
		sb.append(secret);
		sb.append("&code=");
		sb.append(code);
		HttpGet httpGet = new HttpGet(sb.toString());
		HttpResponse response = httpclient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		String jsonString = IOUtils.toString(is, "utf-8");

		EntityUtils.consume(entity);
		
		Map<String, String> jsonObject = JsonUtils.toMap(jsonString);

        return jsonObject.get("openid");
	
	}
}
