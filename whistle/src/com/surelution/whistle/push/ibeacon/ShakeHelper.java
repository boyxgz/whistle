/**
 * 
 */
package com.surelution.whistle.push.ibeacon;

import org.json.JSONObject;

import com.surelution.whistle.push.Pusher;

/**
 * @author <a href="mailto:guangzong.syu@gmail.com">guangzong</a>
 *
 */
public class ShakeHelper {

	public static ShakeInfo loadByTicket(String ticket) throws Exception {
		String s = "{";
		s += "\"ticket\":\"";
    	s += ticket;
    	s += "\" ";
    	s += "}";
    	System.out.println(s);
        Pusher p = new Pusher();
        p.setApiUrl("https://api.weixin.qq.com/shakearound/user/getshakeinfo?");
        String ret = p.push(s);
        JSONObject o = new JSONObject(ret).getJSONObject("data");
        ShakeInfo si = new ShakeInfo();
        si.setPageId(o.getString("page_id"));
        si.setOpenId(o.getString("openid"));
        IbeaconInfo ii = new IbeaconInfo();
        si.setIbeaconInfo(ii);
        JSONObject jo = o.getJSONObject("beacon_info");
        ii.setDistance(jo.getString("distance"));
        ii.setMajor(jo.getString("major"));
        ii.setMinor(jo.getString("minor"));
        ii.setUuid(jo.getString("uuid"));
        return si;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(loadByTicket("0d164211a6035318a0a8ef5592226bd2"));
	}
}
