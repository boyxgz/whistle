/**
 * 
 */
package com.surelution.whistle.push;

import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class UserInfo {

	// 用户的标识
	private String openId;
	// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
	private int subscribe;
	// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	private String subscribeTime;
	// 昵称
	private String nickname;
	// 用户的性别（1是男性，2是女性，0是未知）
	private int sex;
	// 用户所在国家
	private String country;
	// 用户所在省份
	private String province;
	// 用户所在城市
	private String city;
	// 用户的语言，简体中文为zh_CN
	private String language;
	// 用户头像
	private String headImgUrl;
	
	public Gender getGender() {
		return Gender.parse(sex);
	}
	
	public Date getLastSubscribedAt() {
		return new Date(Integer.parseInt(subscribeTime) * 1000);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
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
	
	public static UserInfo loadUserInfo(String openId) {
		String json = loadUserInfoAsJson(openId);
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException e1) {
			e1.printStackTrace(); //TODO throw some exception?
		}
		UserInfo user = null;
		if (null != jsonObject) {
			try {
				user = new UserInfo();
				// 用户的标识
				user.setOpenId(jsonObject.getString("openid"));
				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
				user.setSubscribe(jsonObject.getInt("subscribe"));
				// 用户关注时间
				user.setSubscribeTime(jsonObject.getString("subscribe_time"));
				// 昵称
				user.setNickname(jsonObject.getString("nickname"));
				// 用户的性别（1是男性，2是女性，0是未知）
				user.setSex(jsonObject.getInt("sex"));
				// 用户所在国家
				user.setCountry(jsonObject.getString("country"));
				// 用户所在省份
				user.setProvince(jsonObject.getString("province"));
				// 用户所在城市
				user.setCity(jsonObject.getString("city"));
				// 用户的语言，简体中文为zh_CN
				user.setLanguage(jsonObject.getString("language"));
				// 用户头像
				user.setHeadImgUrl(jsonObject.getString("headimgurl"));
			} catch (Exception e) {
				if (0 == user.getSubscribe()) {
					//log.error("用户{}已取消关注", weixinUserInfo.getOpenId());
				} else {
					try {
						int errorCode = jsonObject.getInt("errcode");
						String errorMsg = jsonObject.getString("errmsg");
						if(errorCode != 0) {
							// TODO throw some exception?
						}
					} catch (JSONException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
		return user;
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
		UserInfo ui = loadUserInfo("oklOVjgRVFE8UiqhmYCEBnkv-Nww");
	}
}
