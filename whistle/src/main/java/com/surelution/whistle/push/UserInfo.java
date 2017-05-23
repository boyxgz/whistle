/**
 * 
 */
package com.surelution.whistle.push;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.annotation.JSONField;
import com.surelution.whistle.core.Configure;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class UserInfo {
	private UserInfo(){}
	
	// 用户的标识
	@JSONField
	private String openId;
	// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
	@JSONField
	private int subscribe;
	// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	@JSONField(name="subscribe_time")
	private String subscribeTime;
	// 昵称
	@JSONField
	private String nickname;
	// 用户的性别（1是男性，2是女性，0是未知）
	@JSONField
	private int sex;
	// 用户所在国家
	@JSONField
	private String country;
	// 用户所在省份
	@JSONField
	private String province;
	// 用户所在城市
	@JSONField
	private String city;
	// 用户的语言，简体中文为zh_CN
	@JSONField
	private String language;
	// 用户头像
	@JSONField(name = "headimgurl")
	private String headImgUrl;

	@JSONField
	private String unionid;
	
	public String getUnionid() {
		return unionid;
	}

	public Gender getGender() {
		return Gender.parse(getSex());
	}
	
	public Date getLastSubscribedAt() {
		return new Date(Integer.parseInt(getSubscribeTime()) * 1000);
	}

	public String getOpenId() {
		return openId;
	}

//	public void setOpenId(String openId) {
//		this.openId = openId;
//	}

	public int getSubscribe() {
		return subscribe;
	}

//	public void setSubscribe(int subscribe) {
//		this.subscribe = subscribe;
//	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

//	public void setSubscribeTime(String subscribeTime) {
//		this.subscribeTime = subscribeTime;
//	}

	public String getNickname() {
		return nickname;
	}

//	public void setNickname(String nickname) {
//		this.nickname = nickname;
//	}

	public int getSex() {
		return sex;
	}

//	public void setSex(int sex) {
//		this.sex = sex;
//	}

	public String getCountry() {
		return country;
	}

//	public void setCountry(String country) {
//		this.country = country;
//	}

	public String getProvince() {
		return province;
	}

//	public void setProvince(String province) {
//		this.province = province;
//	}

	public String getCity() {
		return city;
	}

//	public void setCity(String city) {
//		this.city = city;
//	}

	public String getLanguage() {
		return language;
	}

//	public void setLanguage(String language) {
//		this.language = language;
//	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public static String loadUserInfoAsJson(String openId) {
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

	public static void updateUserGroup(String openId, String groupId) {
		String url = "https://api.weixin.qq.com/cgi-bin/groups/members/update?";
		Pusher p = new Pusher();
		p.setApiUrl(url);
		try {
			//{"openid":"oDF3iYx0ro3_7jD4HFRDfrjdCM58","to_groupid":108}
			StringBuilder sb = new StringBuilder("{\"openid\":\"");
			sb.append(openId);
			sb.append("\",\"to_groupid\":");
			sb.append(groupId);
			sb.append("}");
			System.out.println(sb);
			p.push(sb.toString());
		} catch (NetworkException e) {
			e.printStackTrace();
		}
	
	}
	
	private static UserInfo drawFromJson(String json) {
		return JSON.parseObject(json, UserInfo.class);
	}
	
	public static UserInfo loadUserInfo(String openId) {
		String json = loadUserInfoAsJson(openId);
		UserInfo user = drawFromJson(json);
		return user;
	}

	public static UserInfo loadUserInfoViaSnsCode(String code) throws Exception {
		String openid = null, accessToken = null;
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
		Map<String, String> map = JSON.parseObject(response.toString(),
				new TypeReference<Map<String, String>>(String.class, String.class) {
				});
 
			openid = map.get("openid");
			accessToken  = map.get("access_token");

		
		String ret = null;
		String url2 = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN&openid=" + openid + "&";
		Pusher p = new Pusher();
		p.setRequestMethod("GET");
		p.setApiUrl(url2);
		try {
			ret = p.push(null, accessToken);
		} catch (NetworkException e) {
			e.printStackTrace();
		}
		
		return drawFromJson(ret);
	}

	public enum Gender {
		
		MALE(1), FEMALE(2), UNKNOWN(0);
		
		private int value;
		
		Gender(int i) {
			value = i;
		}
		
		public int getValue() {
			return value;
		}
		
		public static Gender parse(int i) {
			Gender g;
			switch(i) {
				case 1: g = MALE;
				break;
				case 2: g = FEMALE;
				break;
				case 3: g = UNKNOWN;
				break;
				default:
					g = UNKNOWN;
			}
			return g;
		}
		
	}
	
	public static void main(String[] args) {
		UserInfo ui = loadUserInfo("oEtjTs27yKb7NiNaIzJYlb7gTcr0");
		System.out.println(ui.getNickname());
		System.out.println(ui.getUnionid());
		System.out.println(ui.getCity());
		System.out.println(ui.getCountry());
		System.out.println(ui.getHeadImgUrl());
		System.out.println(ui.getLanguage());
		System.out.println(ui.getNickname());
		System.out.println(ui.getOpenId());
		System.out.println(ui.getProvince());
		System.out.println(ui.getSex());
		System.out.println(ui.getSubscribe());
		System.out.println(ui.getSubscribeTime());
		System.out.println(ui.getUnionid());
		System.out.println(ui.getGender());
		System.out.println(ui.getLastSubscribedAt());
	}
}
