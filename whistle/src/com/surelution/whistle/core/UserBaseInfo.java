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
 * @deprecated
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
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
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
        return (String)result.get("openid");
	
	}
}
