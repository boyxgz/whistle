package com.surelution.whistle.push;

import com.alibaba.fastjson.JSON;
import com.surelution.whistle.core.JsonUtils;

import java.util.List;
import java.util.Map;

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
		String ret = null;
		Pusher p = new Pusher();
		p.setRequestMethod("GET");
		p.setApiUrl(url);
		try {
			ret = p.push(query);
			GroupInfo gi = JSON.parseObject(ret, GroupInfo.class);
			if(gi != null) {
				return gi.getId();
			}
		} catch (NetworkException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return group collection
	 */
	public static List<GroupInfo> listAll() {
		String url = "https://api.weixin.qq.com/cgi-bin/groups/get?";

		Pusher p = new Pusher();
		p.setRequestMethod("GET");
		p.setApiUrl(url);
		try {
			String ret = p.push(null);
			List<GroupInfo> gis = JSON.parseArray(ret, GroupInfo.class);
			return gis;
		} catch (NetworkException e) {
			e.printStackTrace();
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

		Pusher p = new Pusher();
		p.setRequestMethod("GET");
		p.setApiUrl(url);
		try {
			String ret = p.push(query);
			Map<String,String> map = JsonUtils.toMap(ret);
			return map.get("groupid");
		} catch (NetworkException e) {
			e.printStackTrace();
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
