/**
 * 
 */
package com.surelution.whistle.push;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.surelution.whistle.core.Configure;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class OAuthHelper {

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
	
	public static void main(String[] args) throws Exception {
//		String s = "{ 'access_token ': 'OezXcEiiBSKSxW0eoylIeG6H_hrTRAVJO-TatR1HI2Dc-5RE0QQ4qZuWAw3QDgfy9qCF4keMd6IVQGueKmLWxKld9tiKd7-EzaozDuJxCFMTohAPwNgR5NxoQXHn9oiDk6mjY_JVOc10DxnlT8iy7Q ', 'expires_in ':7200, 'refresh_token ': 'OezXcEiiBSKSxW0eoylIeG6H_hrTRAVJO-TatR1HI2Dc-5RE0QQ4qZuWAw3QDgfylcZWglrusnd2HdyjV1_MQE5HKw6MGIp88n0EVrIqOPV-UknVqub53yKMQhwfBcBh3tKPj9InSWyZ5IMgZTbYzg ', 'openid ': 'opvOft9Iezj9ZcjyZ1NNXP7ZYgVI ', 'scope ': 'snsapi_base '}";
//
//		String openid = "";
//		JSONObject jsonObject = null;
//		try {
//			jsonObject = new JSONObject(s);
//			openid = (String)jsonObject.getString("openid ");
//			
//			System.out.println(openid);
//		} catch (JSONException e1) {
//			e1.printStackTrace(); //TODO throw some exception?
//		}
		System.out.println(getOpenidByCode("01c51c7405752cdbdc1b92f71e05be61"));
	}
}
