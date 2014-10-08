/**
 * 
 */
package com.surelution.whistle.push.qrcode;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.surelution.whistle.push.Pusher;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class QrCode {
	
	public static InputStream getQr(int id) {
		try {
			String ticket = getTicket(id);
			URL url = new URL("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
        	System.out.println(url);
        	InputStream is = url.openStream();
        	return is;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static InputStream getTempQr(long id, int expireSeconds) {
		try {
			String ticket = getTempTicket(id, expireSeconds);
			URL url = new URL("https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
        	System.out.println(url);
        	InputStream is = url.openStream();
        	return is;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

    public static void main(String[] args) throws Exception {
//    	String s = getTempTicket(123456778, 1200);
//    	System.out.println(s);
    	for(int i = 540; i < 541; i++) {
    		try{
	    		String ticket = getTicket(i);
	    		System.out.println(ticket);
	        	URL url = new URL("http://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=" + ticket);
	        	System.out.println(url);
	        	InputStream is = url.openStream();
	        	File file = new File("/Users/johnny/hunan-qr/" + i + ".jpg");
	        	FileOutputStream fos = new FileOutputStream(file);
	        	IOUtils.copy(is, fos);
    		}catch(Exception e) {
    			e.printStackTrace();
    			System.out.println(i);
    		}
    	}
    }
    
    private static String getTicket(int id) throws Exception {
    	String s = "{\"action_name\": \"QR_LIMIT_SCENE\", \"action_info\": {\"scene\": {\"scene_id\": ";
    	s += id;
    	s += "}}}";
    	System.out.println(s);
        Pusher p = new Pusher();
        p.setApiUrl("http://api.weixin.qq.com/cgi-bin/qrcode/create?");
        String ret = p.push(s);
        JSONObject o = new JSONObject(ret);
        String ticket = o.getString("ticket");
        return URLEncoder.encode(ticket, "utf-8");
    }
    
    private static String getTempTicket(long id, int seconds) throws Exception {
    	String s = "{\"action_name\": \"QR_SCENE\",\"expire_seconds\": ";
    	s += seconds;
    	s += " ,\"action_info\": {\"scene\": {\"scene_id\": ";
    	s += id;
    	s += "}}}";
    	System.out.println(s);
        Pusher p = new Pusher();
        p.setApiUrl("https://api.weixin.qq.com/cgi-bin/qrcode/create?");
        String ret = p.push(s);
        JSONObject o = new JSONObject(ret);
        String ticket = o.getString("ticket");
        return URLEncoder.encode(ticket, "utf-8");
    }
}
