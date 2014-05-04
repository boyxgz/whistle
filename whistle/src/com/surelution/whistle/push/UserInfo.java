/**
 * 
 */
package com.surelution.whistle.push;


/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class UserInfo {

	public static String loadUserInfo(String openId) {
		String ret = null;
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?openid=" + openId + "&";
		Pusher p = new Pusher();
		p.setRequestMethod("GET");
		p.setApiUrl(url);
		try {
			ret = p.push(null);
		} catch (NetworkException e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	public static void main(String[] args) {
		loadUserInfo("oklOVjgRVFE8UiqhmYCEBnkv-Nww");
	}
}
