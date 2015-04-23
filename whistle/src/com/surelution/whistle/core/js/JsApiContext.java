/**
 * 
 */
package com.surelution.whistle.core.js;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import com.surelution.whistle.core.Configure;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class JsApiContext {

	public static JsApiContext build(String url) {
		return new JsApiContext(url);
	}

	private String url;
	private String ticket;
	private String nonceStr;
	private String timestamp;
	private String signature;
	private String appId;

	public String getNonceStr() {
		return nonceStr;
	}

	public String getUrl() {
		return url;
	}

	public String getTicket() {
		return ticket;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getSignature() {
		return signature;
	}

	public String getAppId() {
		return appId;
	}

	private JsApiContext(String url) {
		this.url = url;
		ticket = JsApiHelper.getJsapiTicket();
		nonceStr = UUID.randomUUID().toString();
		timestamp = Long.toString(System.currentTimeMillis() / 1000);
		appId = Configure.config().getAppid();

		String string1 = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr
				+ "&timestamp=" + timestamp + "&url=" + url;

		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(string1.getBytes("UTF-8"));
			signature = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
