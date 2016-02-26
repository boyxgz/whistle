package com.surelution.whistle.push;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GroupInfo {

	private String id;
	private String name;
	private Integer count;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getCount() {
		return count;
	}

	/**
	 * 
	 * @param group name
	 * @return group id
	 */
	public static String create(String name) {
		String url = "https://api.weixin.qq.com/cgi-bin/groups/create?";
		String query = "{\"group\":{\"name\":\"";
		query += name;
		query += "\"}}";
		JSONObject jsonObject = queryForJson(url, query);
		JSONObject g = null;
		try {
			g = jsonObject.getJSONObject("group");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if(g != null) {
			return extractFromJson(g, "id");
		}
		return null;
	}

	/**
	 * 
	 * @return group collection
	 */
	public static List<GroupInfo> listAll() {
		String url = "https://api.weixin.qq.com/cgi-bin/groups/get?";
		JSONObject jsonObject = queryForJson(url, null);
		try {
			JSONArray g = jsonObject.getJSONArray("groups");
			List<GroupInfo> list = new ArrayList<GroupInfo>();
			for(int i = 0; i < g.length(); i++) {
				JSONObject o = g.getJSONObject(i);
				GroupInfo gi = new GroupInfo();
				gi.count = o.getInt("count");
				gi.id = o.getString("id");
				gi.name = o.getString("name");
				list.add(gi);
			}
			return list;
		} catch (JSONException e1) {
			e1.printStackTrace(); //TODO throw some exception?
		}
		return null;
	}

	/**
	 * 
	 * @param user's openid
	 * @return group id
	 * TODO
	 */
	public static String findGroupByOpenId(String openid) {
		String url = "https://api.weixin.qq.com/cgi-bin/groups/getid?";
		String query = "{\"openid\":\"";
		query += openid;
		query += "\"}";
		JSONObject jsonObject = queryForJson(url, query);
		try {
			return jsonObject.getString("groupid");
		} catch (JSONException e1) {
			e1.printStackTrace(); //TODO throw some exception?
		}
		return null;
	}

	/**
	 * 
	 * @param group id
	 * @param group name
	 * TODO
	 */
	public static void changeName(String groupId, String name) {
		
	}

	/**
	 * @deprecated move to UserInfo.updateUserGroup()
	 * @param user's openid
	 * @param groupId
	 */
	public static void moveUserToGroup(String openid, String groupId) {
		UserInfo.updateUserGroup(openid, groupId);
	}

	/**
	 * 
	 * @param groupId
	 */
	public static void delete(String groupId) {
		
	}

	private static JSONObject queryForJson(String url, String queryString) {
		String ret = null;
		Pusher p = new Pusher();
		p.setRequestMethod("GET");
		p.setApiUrl(url);
		try {
			ret = p.push(queryString);

			JSONObject jsonObject = null;
			try {
				jsonObject = new JSONObject(ret);
				return jsonObject;
			} catch (JSONException e1) {
				e1.printStackTrace(); //TODO throw some exception?
			}
		} catch (NetworkException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String extractFromJson(JSONObject jsonObject, String key) {
		String value = null;
		try {
			value = jsonObject.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return value;
	}
	
	@Override
	public String toString() {
		return "GroupInfo [id=" + id + ", name=" + name + ", count=" + count
				+ "]";
	}
	
	public static void main(String[] args) {
		moveUserToGroup("oklOVjgRVFE8UiqhmYCEBnkv-Nww", "100");
		System.out.println(findGroupByOpenId("oklOVjgRVFE8UiqhmYCEBnkv-Nww"));
	}
}
