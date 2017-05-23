package com.surelution.whistle.core;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class WxmpHelper {

	public static boolean checkSignature(String token, String timestamp, String nonce, String signature) {
		String[] sha1Params = {token, timestamp, nonce};
		Arrays.sort(sha1Params);
		StringBuilder sb = new StringBuilder();
		for(String param : sha1Params) {
			sb.append(param);
		}
		StringBuilder sha1 = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] bs = md.digest(sb.toString().getBytes());
			for(byte b : bs) {
				sha1.append(Integer.toString( (b & 0xff) + 0x100, 16).substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sha1.toString().equals(signature);
	}
}
