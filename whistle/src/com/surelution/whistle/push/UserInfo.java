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
	private UserInfo(){}
	
	private JSONObject jsonObject;

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
	
	private String unionid;
	
	private String exactFromJson(String key) {
		String value = null;
		try {
			value = jsonObject.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	public String getUnionid() {
		if(unionid == null) {
			unionid = exactFromJson("unionid");
		}
		return unionid;
	}

	public Gender getGender() {
		return Gender.parse(getSex());
	}
	
	public Date getLastSubscribedAt() {
		return new Date(Integer.parseInt(getSubscribeTime()) * 1000);
	}

	public String getOpenId() {
		if(openId == null) {
			openId = exactFromJson("openid");
		}
		return openId;
	}

//	public void setOpenId(String openId) {
//		this.openId = openId;
//	}

	public int getSubscribe() {
		if(subscribe == 0) {
			try {
				subscribe = jsonObject.getInt("subscribe");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return subscribe;
	}

//	public void setSubscribe(int subscribe) {
//		this.subscribe = subscribe;
//	}

	public String getSubscribeTime() {
		if(subscribeTime == null) {
			subscribeTime = exactFromJson("subscribe_time");
		}
		return subscribeTime;
	}

//	public void setSubscribeTime(String subscribeTime) {
//		this.subscribeTime = subscribeTime;
//	}

	public String getNickname() {
		if(nickname == null) {
			nickname = exactFromJson("nickname");
		}
		return nickname;
	}

//	public void setNickname(String nickname) {
//		this.nickname = nickname;
//	}

	public int getSex() {
		if(sex == 0) {
			try {
				sex = jsonObject.getInt("sex");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return sex;
	}

//	public void setSex(int sex) {
//		this.sex = sex;
//	}

	public String getCountry() {
		if(country == null) {
			country = exactFromJson("country");
		}
		return country;
	}

//	public void setCountry(String country) {
//		this.country = country;
//	}

	public String getProvince() {
		if(province == null) {
			province = exactFromJson("province");
		}
		return province;
	}

//	public void setProvince(String province) {
//		this.province = province;
//	}

	public String getCity() {
		if(city == null) {
			city = exactFromJson("city");
		}
		return city;
	}

//	public void setCity(String city) {
//		this.city = city;
//	}

	public String getLanguage() {
		if(language == null) {
			language = exactFromJson("language");
		}
		return language;
	}

//	public void setLanguage(String language) {
//		this.language = language;
//	}

	public String getHeadImgUrl() {
		if(headImgUrl == null) {
			headImgUrl = exactFromJson("headimgurl");
		}
		return headImgUrl;
	}

//	public void setHeadImgUrl(String headImgUrl) {
//		this.headImgUrl = headImgUrl;
//	}

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
			user = new UserInfo();
			user.jsonObject = jsonObject;
			try {
//				
//				// 用户的标识
//				user.openId =jsonObject.getString("openid");
//				// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
//				user.subscribe = jsonObject.getInt("subscribe");
//				// 用户关注时间
//				user.subscribeTime = jsonObject.getString("subscribe_time");
//				// 昵称
//				user.nickname = jsonObject.getString("nickname");
//				// 用户的性别（1是男性，2是女性，0是未知）
//				user.sex = jsonObject.getInt("sex");
//				// 用户所在国家
//				user.country = jsonObject.getString("country");
//				// 用户所在省份
//				user.province = jsonObject.getString("province");
//				// 用户所在城市
//				user.city = jsonObject.getString("city");
//				// 用户的语言，简体中文为zh_CN
//				user.language = jsonObject.getString("language");
//				// 用户头像
//				user.headImgUrl = jsonObject.getString("headimgurl");
//				
//				user.unionid = jsonObject.getString("unionid");
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
