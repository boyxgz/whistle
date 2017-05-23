package com.qq.weixin.mp.aes;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * 以json格式发送post请求
 * @author 603_02
 *
 */
public class HttpPostJson {
	
	
	/**
	 * @param urlStr String类型的URL
	 * @param map Map<String,String> 需发送的数据
	 * @return 响应的正文
	 */
	public static String post(String urlStr,Map<String, String> map){
		URL url;
		try {
			url = new URL(urlStr);
			 HttpURLConnection connection = (HttpURLConnection) url
		                .openConnection();
		        connection.setDoOutput(true);
		        connection.setDoInput(true);
		        connection.setRequestMethod("POST");
		        connection.setUseCaches(false);
		        connection.setInstanceFollowRedirects(true);
		        connection.setRequestProperty("Content-Type",
		                "application/x-www-form-urlencoded");

		        connection.connect();
		        DataOutputStream out = new DataOutputStream(
	                    connection.getOutputStream());
		        out.writeBytes(JSON.toJSONString(map));
	            out.flush();
	            out.close();
		        
	            BufferedReader reader = new BufferedReader(new InputStreamReader(
	                    connection.getInputStream()));
	            String lines;
	            StringBuffer sb = new StringBuffer("");
	            while ((lines = reader.readLine()) != null) {
	                lines = new String(lines.getBytes(), "utf-8");
	                sb.append(lines);
	            }
	            reader.close();
	            // 断开连接
	            connection.disconnect();
	            return sb.toString();
		} catch (MalformedURLException e) {
			// 
			e.printStackTrace();
		} catch (IOException e) {
			// 
			e.printStackTrace();
		}
		return "";
       
	}
}
